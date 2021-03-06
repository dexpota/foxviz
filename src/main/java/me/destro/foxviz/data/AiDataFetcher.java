package me.destro.foxviz.data;

import com.github.javafaker.Faker;
import com.squareup.moshi.Moshi;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.data.model.AiData;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.utilities.MathUtilities;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AiDataFetcher {
    private Faker faker;
    private String categories[] = {"a", "b", "c"};

    private static Moshi moshi = new Moshi.Builder().add(new TopWord.TopWordAdapter()).build();
    private static Marker marker = MarkerManager.getMarker(AiDataFetcher.class.getName());

    public AiDataFetcher() {
        this.faker = new Faker();


        // Fetch top50 words
        if (Configuration.debug) {
            generateWord()
                    .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                    .subscribeOn(Schedulers.io())
                    .subscribe(AiDataStorage::setTop50Words);

            /*generateWord()
                    .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                    .subscribeOn(Schedulers.io())
                    .subscribe(DataStorage::setTop350Words);*/

            generateConnection()
                    .repeatWhen(completed -> completed.delay(Configuration.aiDataRepeatTime, TimeUnit.SECONDS))
                    .subscribeOn(Schedulers.io())
                    .subscribe(AiDataStorage::updateTablesConnectionsByWord);
        }else {
            fetchData()
                    .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                    .subscribeOn(Schedulers.computation())
                    .subscribe(data -> {
                        //System.out.println(String.format("top350 size: %d", data.top350.size()));
                        AiDataStorage.setTop50Words(data.top50);
                        DataStorage.setTop350Words(data.top350);
                        AiDataStorage.updateTablesConnectionsByWord(data.tablesConnectionsByWord);
                    }, error -> {
                        System.out.println(error.getMessage());
                        System.out.println("A fatal error occured fetching data from the AI.");
                    });
        }
    }

    public Observable<AiData> fetchData() {
        return Observable.create(emitter -> {
            OkHttpClient client = Main.client;
            Request request = new Request.Builder()
                    .url(Configuration.aiDataUrl)
                    .build();

            Response response;
            try {
                Main.logger.debug(marker,
                        String.format("Downloading data from server, url %s", Configuration.aiDataUrl));

                response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    AiData data = parseData(jsonData);
                    emitter.onNext(data);
                }else {
                    throw new IOException("Response was not succesful.");
                }

                emitter.onComplete();
            } catch (IOException e) {
                Main.logger.error(marker,
                        String.format("Error retrieving file from the server, url %s.",
                                Configuration.aiDataUrl));

                Main.logger.error(marker,
                        String.format("Message: %s.", e.getMessage()));
                emitter.onError(e);
            } catch (AiDataParsingException e) {
                Main.logger.error(marker,
                        String.format("Error parsing file, %s.",
                                e.getMessage()));
                emitter.onError(e);
            }
        });
    }

    private AiData parseData(String json) throws AiDataParsingException {
        try {
            return moshi.adapter(AiData.class).fromJson(json);
        }catch (IOException e) {
            throw new AiDataParsingException(json);
        }
    }

    public Observable<List<TopWord>> generateWord() {
        return Observable.create(emitter -> {
            emitter.onNext(generateFake());
            emitter.onComplete();
        });
    }

    public Observable<Map<String, List<Integer>>> generateConnection() {
        return Observable.create(emitter -> {
            Map<String, List<Integer>> connections = new HashedMap<>();

            List<Integer> tables = new LinkedList<>();
            int count = MathUtilities.random(10, 20);
            for (int i = 0; i < count; ++i) {
                int table = MathUtilities.random(0, 69);
                tables.add(table);
            }

            connections.putIfAbsent("a", tables);
            emitter.onNext(connections);
        });
    }

    private List<TopWord> generateFake() {
        int n = MathUtilities.random(10, 50);
        List<TopWord> topWords = new ArrayList<>(n);

        for (int i = 0; i < n; ++i) {
            String word = faker.lorem().word();
            String category = MathUtilities.pickValue(categories);
            topWords.add(new TopWord(word, category, 0));
        }

        return topWords;
    }

    class AiDataParsingException extends Exception {
        AiDataParsingException(String message) {
            super(message);
        }
    }
}

package me.destro.foxviz.data;

import com.github.javafaker.Faker;
import com.squareup.moshi.Moshi;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.data.model.AiData;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.model.Connection;
import me.destro.foxviz.utilities.MathUtilities;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AiDataFetcher {
    private Faker faker;
    private String categories[] = {"a", "b", "c"};

    private static Moshi moshi = new Moshi.Builder().add(new TopWord.TopWordAdapter()).build();
    private static Marker marker = MarkerManager.getMarker(AiDataFetcher.class.getName());

    public AiDataFetcher() {
        this.faker = new Faker();

        generateConnection()
                .subscribeOn(Schedulers.computation())
                .subscribe(connection -> {
                    DataStorage.tablesConnections.add(connection);
                });

        generateWord()
                .subscribeOn(Schedulers.computation())
                .subscribe(aiWord -> {
                    DataStorage.smartWords.add(aiWord);
                });
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

    public Observable<AiWord> generateWord() {

        Observable<Long> interval = Observable.interval(0, 2, TimeUnit.SECONDS);
        return Observable.create(emitter -> {
            while (true) {
                Thread.sleep(1000 * MathUtilities.random(1, 2));
                emitter.onNext(generateFake());
            }
        });
    }

    public Observable<Connection> generateConnection() {
        return Observable.create(emitter -> {
            while (true) {
                Thread.sleep(10 * MathUtilities.random(1, 2));
                int a = MathUtilities.random(0, 64);
                int b = MathUtilities.random(0, 64);
                Connection c = new Connection();
                c.a = a;
                c.b = b;
                emitter.onNext(c);
            }
        });
    }

    private AiWord generateFake() {
        int index = MathUtilities.random(0, 3);
        String word = faker.lorem().word();
        String category = categories[index];
        AiWord aiWord = new AiWord();
        aiWord.word = word;
        aiWord.category = category;
        return aiWord;
    }

    class AiDataParsingException extends Exception {
        AiDataParsingException(String message) {
            super(message);
        }
    }
}

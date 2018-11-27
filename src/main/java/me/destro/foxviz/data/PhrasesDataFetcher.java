package me.destro.foxviz.data;

import com.github.javafaker.Faker;
import io.reactivex.Observable;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.utilities.MathUtilities;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PhrasesDataFetcher {
    private Faker faker;
    private static Marker marker = MarkerManager.getMarker(PhrasesDataFetcher.class.getName());

    public PhrasesDataFetcher() {
        faker = new Faker();

        fetchFakeData()
                .repeatWhen(completed -> completed.delay(Configuration.aiDataRepeatTime, TimeUnit.SECONDS))
                .retry()
                .subscribe(PhrasesDataStorage::set,
                        error -> System.out.println("An error."));
    }

    public Observable<List<String>> fetchData() {
        return Observable.create(emitter -> {
            OkHttpClient client = Main.client;
            Request request = new Request.Builder()
                    .url(Configuration.phrasesDataUrl)
                    .build();

            Response response;
            try {
                Main.logger.debug(marker,
                        String.format("Downloading data from server, url %s", Configuration.aiDataUrl));

                response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    List<String> phrases = new LinkedList<>();

                    CSVParser csvParser = new CSVParser(response.body().charStream(), CSVFormat.DEFAULT.withDelimiter(';'));
                    for (CSVRecord csvRecord : csvParser) {
                        phrases.add(csvRecord.get(1));
                    }
                    emitter.onNext(phrases);
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
            }
        });
    }

    public Observable<List<String>> fetchFakeData() {
        return Observable.create(emitter -> {
            List<String> phrases = new LinkedList<>();

            int num = MathUtilities.random(1, 10);
            String text = "";
            for (int i=0; i<num; ++i) {
                int r = MathUtilities.random(10, 256);
                text += faker.lorem().fixedString(r);
                phrases.add(text);
            }

            emitter.onNext(phrases);
            emitter.onComplete();
        });
    }

}

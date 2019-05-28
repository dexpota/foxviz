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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PhrasesDataLoader {

    public PhrasesDataLoader(String filename) {
        File file = new File(filename);

        List<String> phrases = new LinkedList<>();

        CSVParser csvParser = null;
        try {
            csvParser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter(';'));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (CSVRecord csvRecord : csvParser) {
            phrases.add(csvRecord.get(1));
        }

        PhrasesDataStorage.set(phrases);
    }

}

package me.destro.foxviz.data;

import com.github.javafaker.Faker;
import com.squareup.moshi.Moshi;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AiDataLoader {
    private static Moshi moshi = new Moshi.Builder().add(new TopWord.TopWordAdapter()).build();

    public AiDataLoader(String filename) {

        try {
            String stringData = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            AiData data = parseData(stringData);
            AiDataStorage.setTop50Words(data.top50);
            DataStorage.setTop350Words(data.top350);
            AiDataStorage.updateTablesConnectionsByWord(data.tablesConnectionsByWord);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AiDataParsingException e) {
            e.printStackTrace();
        }

    }

    private AiData parseData(String json) throws AiDataParsingException {
        try {
            return moshi.adapter(AiData.class).fromJson(json);
        }catch (IOException e) {
            throw new AiDataParsingException(json);
        }
    }

    class AiDataParsingException extends Exception {
        AiDataParsingException(String message) {
            super(message);
        }
    }
}

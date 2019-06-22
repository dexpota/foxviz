package me.destro.foxviz.data;

import com.squareup.moshi.Moshi;
import me.destro.foxviz.data.model.AiData;
import me.destro.foxviz.data.model.TopWord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwitterDataLoader {
    private static Moshi moshi = new Moshi.Builder().add(new TopWord.TopWordAdapter()).build();

    public TwitterDataLoader(String filename) {
        TweetsDataStorage.set(readFile(filename));
    }

    private List<String> readFile(String filename) {
        List<String> tweets = new ArrayList<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {

                if (!line.isEmpty()) {
                    tweets.add(line);
                }

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tweets;
    }

    private AiData parseData(String json) throws AiDataParsingException {
        try {
            return moshi.adapter(AiData.class).fromJson(json);
        } catch (IOException e) {
            throw new AiDataParsingException(json);
        }
    }

    class AiDataParsingException extends Exception {
        AiDataParsingException(String message) {
            super(message);
        }
    }
}

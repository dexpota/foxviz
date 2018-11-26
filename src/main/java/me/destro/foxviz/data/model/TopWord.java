package me.destro.foxviz.data.model;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;

public class TopWord {
    public String word;
    public int frequency;

    public TopWord(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public static class TopWordAdapter {
        @FromJson
        public TopWord fromJson(JsonReader reader) throws Exception {
            reader.beginArray();
            String word = reader.nextString();
            int frequency = reader.nextInt();
            reader.endArray();
            return new TopWord(word, frequency);
        }
    }
}

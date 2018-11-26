package me.destro.foxviz.data.model;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;

public class TopWord{
    public String word;
    public String category;
    public int frequency;

    public String getWord() {
        return word;
    }

    public TopWord(String word, String category, int frequency) {
        this.word = word;
        this.category = category;
        this.frequency = frequency;
    }

    public static class TopWordAdapter {
        @FromJson
        public TopWord fromJson(JsonReader reader) throws Exception {
            reader.beginArray();
            String word = reader.nextString();
            int frequency = reader.nextInt();
            String category = reader.nextString();
            reader.endArray();
            return new TopWord(word, category, frequency);
        }
    }
}

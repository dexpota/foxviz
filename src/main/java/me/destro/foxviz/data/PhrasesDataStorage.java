package me.destro.foxviz.data;

import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.model.Connection;
import me.destro.foxviz.utilities.MathUtilities;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PhrasesDataStorage {

    static private AtomicReference<List<String>> phrases = new AtomicReference<>(null);
    static private AtomicBoolean updated = new AtomicBoolean(false);

    static public boolean isUpdated() { return updated.get(); }

    static public List<String> get() {
        updated.set(false);
        return phrases.get();
    }

    static public void set(List<String> value){
        phrases.set(value);
        updated.set(true);
    }

}

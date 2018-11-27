package me.destro.foxviz.data;

import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.model.TableConnection;
import me.destro.foxviz.utilities.MathUtilities;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DataStorage {

    static private AtomicReference<List<TopWord>> top350Words = new AtomicReference<>(null);
    static private AtomicBoolean top350WordsUpdated = new AtomicBoolean(false);

    static public boolean isTop350Updated() { return top350WordsUpdated.get(); }

    static public List<TopWord> getTop350Words() {
        top350WordsUpdated.set(false);
        return top350Words.get();
    }

    static public void setTop350Words(List<TopWord> value){
        top350Words.set(value);
        top350WordsUpdated.set(true);
    }

    static public ConcurrentLinkedQueue<TableConnection> tablesConnections = new ConcurrentLinkedQueue<>();

    static public ConcurrentLinkedQueue<AiWord> smartWords = new ConcurrentLinkedQueue<>();

    static private long lastTimeFetched = System.currentTimeMillis();

    static private long lastTimeWordFetched = System.currentTimeMillis();

    public static List<TableConnection> fetchConnection() {
        long current = System.currentTimeMillis();

        int random = MathUtilities.random(2, 3);

        if (current - lastTimeFetched > 5*1000 && DataStorage.tablesConnections.size() > random) {
            lastTimeFetched = System.currentTimeMillis();
            if (tablesConnections.peek() != null) {
                List<TableConnection> connections = new LinkedList<>();
                for (int i = 0; i<random; i++) {
                    connections.add(tablesConnections.remove());
                }
                return connections;
            }
        }
        return null;
    }

    /*private static TableConnection searchConnection(TableConnection c) {
        for (TableConnection c : tablesConnections) {
            if ()
        }
    }*/

    public static AiWord fetchAiWord() {
        long current = System.currentTimeMillis();
        if (current - lastTimeWordFetched > 5*1000) {
            lastTimeWordFetched = System.currentTimeMillis();
            if (smartWords.peek() != null)
                return smartWords.remove();
        }

        return null;
    }
}

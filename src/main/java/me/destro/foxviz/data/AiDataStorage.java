package me.destro.foxviz.data;

import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.model.TableConnection;
import org.apache.commons.math3.util.Combinations;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AiDataStorage {

    static private AtomicReference<List<TopWord>> top50Words = new AtomicReference<>(null);
    static private AtomicBoolean top50WordsUpdated = new AtomicBoolean(false);

    static public boolean isTop50Updated() { return top50WordsUpdated.get(); }

    static public List<TopWord> getTop50Words() {
        top50WordsUpdated.set(false);
        return top50Words.get();
    }

    static public void setTop50Words(List<TopWord> value){
        top50Words.set(value);
        top50WordsUpdated.set(true);
    }

    static private AtomicReference<Set<TableConnection>> connectionsByWord = new AtomicReference<>(null);
    static private AtomicBoolean connectionsByWordUpdated = new AtomicBoolean(false);

    public static void updateTablesConnectionsByWord(Map<String, List<Integer>> tablesConnectionsByWord) {
        Set<TableConnection> allConnections = new HashSet<>();

        for (Map.Entry<String, List<Integer>> connections : tablesConnectionsByWord.entrySet()) {
            List<Integer> value = new LinkedList<>(new HashSet<>(connections.getValue()));

            if (value.size() >= 2) {
                Combinations c = new Combinations(value.size(), 2);
                for (int[] indices : c) {
                    int table1 = value.get(indices[0]);
                    int table2 = value.get(indices[1]);

                    allConnections.add(new TableConnection(table1, table2));
                }
            }
        }

        connectionsByWord.set(allConnections);
        connectionsByWordUpdated.set(true);
    }

    public static Set<TableConnection> getWordsConnections() {
        connectionsByWordUpdated.set(true);
        return connectionsByWord.get();
    }

    public static boolean areConnectionsUpdated() {
        return connectionsByWordUpdated.get();
    }

}

package me.destro.foxviz;

import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.model.Connection;
import me.destro.foxviz.utilities.MathUtilities;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataStorage {

    static public ConcurrentLinkedQueue<Connection> tablesConnections = new ConcurrentLinkedQueue<>();

    static public ConcurrentLinkedQueue<AiWord> smartWords = new ConcurrentLinkedQueue<>();

    static private long lastTimeFetched = System.currentTimeMillis();

    static private long lastTimeWordFetched = System.currentTimeMillis();

    public static List<Connection> fetchConnection() {
        long current = System.currentTimeMillis();

        int random = MathUtilities.random(2, 3);

        if (current - lastTimeFetched > 5*1000 && DataStorage.tablesConnections.size() > random) {
            lastTimeFetched = System.currentTimeMillis();
            if (tablesConnections.peek() != null) {
                List<Connection> connections = new LinkedList<>();
                for (int i = 0; i<random; i++) {
                    connections.add(tablesConnections.remove());
                }
                return connections;
            }
        }

        return null;
    }

    /*private static Connection searchConnection(Connection c) {
        for (Connection c : tablesConnections) {
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

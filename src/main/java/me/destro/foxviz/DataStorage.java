package me.destro.foxviz;

import me.destro.foxviz.model.Connection;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DataStorage {

    static public ConcurrentLinkedQueue<Connection> tablesConnections = new ConcurrentLinkedQueue<>();

    static private long lastTimeFetched = System.currentTimeMillis();


    public static Connection fetchConnection() {
        long current = System.currentTimeMillis();
        if (current - lastTimeFetched > 5*1000) {
            lastTimeFetched = System.currentTimeMillis();
            if (tablesConnections.peek() != null)
                return tablesConnections.remove();
        }

        return null;
    }
}

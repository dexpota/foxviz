package me.destro.foxviz;

import static processing.core.PApplet.unhex;

public final class Configuration {
    public static int backgroundColor;

    static {
        backgroundColor = unhex("FF171717");
    }
}

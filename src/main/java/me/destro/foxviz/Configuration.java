package me.destro.foxviz;

import static processing.core.PApplet.unhex;

public final class Configuration {
    // TODO load configuration from ini file
    public static int backgroundColor;

    static {
        backgroundColor = unhex("FF171717");
    }
}

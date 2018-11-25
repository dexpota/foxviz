package me.destro.foxviz;

import java.awt.*;

import static processing.core.PApplet.unhex;

public final class Configuration {
    // TODO load configuration from ini file
    public static int backgroundColor;

    public static int fontSize = 20;

    public static String fontName = "Space Mono";

    public static int firstColumnTextSize = 20;

    public static Color basicConnectionColors[] = {
            Color.decode("0xfff1aa"),
            Color.decode("0x2ac2d6"),
            Color.decode("0x28e798"),
            Color.decode("0x293bda"),
            Color.decode("0x583f90"),
            Color.decode("0xe84b51"),
    };

    static {
        backgroundColor = unhex("FF171717");
    }
}

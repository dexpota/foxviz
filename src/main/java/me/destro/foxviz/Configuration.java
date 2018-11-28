package me.destro.foxviz;

import java.awt.*;

import static processing.core.PApplet.unhex;

public final class Configuration {
    public static Color red = Color.decode("0xe94b51");
    public static Color purple = Color.decode("0x583f91");

    public static String phrasesDataUrl = "https://www.visionarydays.it/webapp/corpo.csv";

    // TODO insert a more appropriate time
    public static int aiDataRepeatTime = 120;
    public static String aiDataUrl = "https://www.visionarydays.it/viz/output_corpo.json";

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

    public static int thirdScreenMinWaitTime = 1;
    public static int thirdScreenMaxWaitTime = 1;

    public static int secondScreenMaxWords = 1000;
    public static int secondScreenMinWaitTime = 1;
    public static int secondScreenMaxWaitTime = 1;

    public static int firstScreenMinWaitTime = 1;
    public static int firstScreenMaxWaitTime = 5;


    static {
        backgroundColor = unhex("FF171717");
    }
}

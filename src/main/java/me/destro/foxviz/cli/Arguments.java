package me.destro.foxviz.cli;

public class Arguments {
    public int width;
    public int height;
    public double pixelSize;
    public String tablePhrases;
    public String ai;
    public String twitter;

    public Arguments(int width, int height, double pixelSize, String tablePhrases, String ai, String twitter) {
        this.width = width;
        this.height = height;
        this.pixelSize = pixelSize;
        this.tablePhrases = tablePhrases;
        this.ai = ai;
        this.twitter = twitter;
    }
}

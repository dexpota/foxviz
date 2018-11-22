package me.destro.foxviz.scenes;

import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.unhex;

public class Table {
    public Node node;
    private int color;

    public Table(float x, float y) {
        Frame f1 = new Frame();
        f1.translate(x, y);
        color = unhex("FF40a361");

        /*node = new Node(f1, (PApplet context) -> {
            context.pg().fill(color);
            context.pg().ellipse(0, 0, 10, 10);
        });*/
    }

    public static List<Table> generateTables(int w, int h, int count){
        double[] xs = MathUtilities.linspace(0, w, count);
        double[] ys = MathUtilities.linspace(0, h, count);

        List<Table> tables = new ArrayList<>();
        for(double x = 0; x < w; x = x + ((double) w)/count) {
            for (double y = 0; y < h; y = y + ((double) h)/count) {
                System.out.println(String.format("x %f, y %f", x, y));
                tables.add(new Table((float) x, (float) y));
            }
        }
        return tables;
    }


}

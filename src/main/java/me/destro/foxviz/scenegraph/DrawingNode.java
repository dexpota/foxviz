package me.destro.foxviz.scenegraph;

import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;

import java.util.function.Function;

public class DrawingNode extends Node {
    Drawable drawing;

    public interface Drawable {
        void draw(PApplet scene);
    }

    public DrawingNode(Drawable drawing) {
        this.drawing = drawing;
    }

    @Override
    public void draw(PApplet scene) {
        this.drawing.draw(scene);
    }
}

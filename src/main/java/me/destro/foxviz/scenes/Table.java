package me.destro.foxviz.scenes;

import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.unhex;

public class Table extends Node {
    public Node node;
    private int color;

    public Table() {
        color = unhex("FF40a361");
    }

    @Override
    public void draw(PApplet scene) {
        scene.fill(255);
        scene.ellipse(0, 0, 100, 100);
    }
}

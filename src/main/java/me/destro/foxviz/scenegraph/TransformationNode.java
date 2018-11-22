package me.destro.foxviz.scenegraph;

import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;

public class TransformationNode extends Node {
    Frame frame;

    // TODO this is an hack, change it
    int y;

    public TransformationNode(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void draw(PApplet scene) {
        MathUtilities.applyTransformation(scene, frame, true);
        scene.translate(0, y);
    }
}

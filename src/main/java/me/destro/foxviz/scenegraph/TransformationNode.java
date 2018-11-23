package me.destro.foxviz.scenegraph;

import processing.core.PApplet;

public class TransformationNode extends Node {
    float x, y;
    float angle;
    float sx, sy;


    public TransformationNode() {this(0.f, 0.f, 0.f, 1.f, 1.f);}

    public TransformationNode(float x, float y) {this(x, y, 0.f, 1.f, 1.f);}

    public TransformationNode(float x, float y, float angle, float sx, float sy) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    public void draw(PApplet scene) {
        scene.translate(x, y);
        scene.rotate(angle);
        scene.scale(sx, sy);
    }
}

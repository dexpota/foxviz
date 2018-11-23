package me.destro.foxviz.scenegraph;

import processing.core.PApplet;

public class ClipNode extends Node {
    float x, y, w, h;

    public ClipNode(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void draw(PApplet scene) {
        scene.clip(x, y, w, h);
    }
}

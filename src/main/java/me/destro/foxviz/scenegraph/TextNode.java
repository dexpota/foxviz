package me.destro.foxviz.scenegraph;

import me.destro.foxviz.Main;
import processing.core.PApplet;

public class TextNode extends Node {
    String text;
    int fontSize;
    float w, h;

    public TextNode(String text) {
        this(text, 16);
    }

    public TextNode(String text, int fontSize) {
        this(text, fontSize, (float) (1000.f/Main.arguments.pixelSize), (float) (1000.f/Main.arguments.pixelSize));
    }

    public TextNode(String text, int fontSize, float w, float h) {
        this.text = text;
        this.fontSize = fontSize;
        this.w = w;
        this.h = h;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(PApplet scene) {
        // Back to screen space or weird things happen with the text
        // TODO this is a screen transformation node
        scene.scale((float) Main.arguments.pixelSize);
        scene.textSize(fontSize);
        scene.textLeading(22);
        scene.fill(255);
        scene.text(this.text, 0, 0, w, h);
        scene.scale((float) (1.0f/Main.arguments.pixelSize));
    }
}

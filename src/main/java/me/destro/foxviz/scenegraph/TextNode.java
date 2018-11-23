package me.destro.foxviz.scenegraph;

import me.destro.foxviz.Main;
import processing.core.PApplet;

public class TextNode extends Node {
    String text;

    public TextNode(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(PApplet scene) {
        // Back to screen space or weird things happen with the text
        // TODO this is a screen transformation node
        scene.scale((float) Main.arguments.pixelSize);
        scene.textSize(12);
        scene.fill(255);
        scene.text(this.text, 0, 0,
                (float) (1000.f/Main.arguments.pixelSize),
                (float) (1000.f/Main.arguments.pixelSize));
        scene.scale((float) (1.0f/Main.arguments.pixelSize));
    }
}

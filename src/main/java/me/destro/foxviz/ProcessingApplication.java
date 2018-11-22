package me.destro.foxviz;

import processing.core.PApplet;

public class ProcessingApplication extends PApplet {
    private boolean centered = false;

    @Override
    public void settings() {
        super.settings();
        size(Main.arguments.width, Main.arguments.height, P2D);
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void draw() {
        if (!centered)
            centerWindow();

        translate(width/2.0f, height/2.0f);
        text("Hello processing!", 0, 0);
    }

    private void centerWindow() {
        if(frame != null && !centered) {
            frame.setLocation(displayWidth/2 - width/2,displayHeight/2 - height/2);
            centered = true;
        }
    }
}

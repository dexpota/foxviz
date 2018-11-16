package me.destro.foxviz;

import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.cli.Utilities;
import processing.core.PApplet;

public class Main extends PApplet {
    private static Arguments arguments;
    private boolean centered = false;

    public static void main(String[] args) {
        arguments = Utilities.parseArguments(args);

        PApplet.main(Main.class, args);
    }

    @Override
    public void settings() {
        super.settings();
        size(arguments.width, arguments.height);
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void draw() {
        super.draw();

        if (!centered)
            centerWindow();

        background(255, 255, 255);
        line(5, 5, 50, 50);
    }
    
    private void centerWindow()
    {
        if(frame != null && centered == false) {
            frame.setLocation(displayWidth/2 - width/2,displayHeight/2 - height/2);
            centered = true;
        }
    }
}

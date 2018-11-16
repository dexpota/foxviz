package me.destro.foxviz;

import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.cli.Utilities;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.NodeWalker;
import processing.core.PApplet;

public class Main extends PApplet {
    private static Arguments arguments;
    private boolean centered = false;
    private Node root;

    public static void main(String[] args) {
        arguments = Utilities.parseArguments(args);

        PApplet.main(Main.class, args);
    }

    @Override
    public void settings() {
        super.settings();

        root = new Node((PApplet context) -> {});

        Node screen1 = new Node((PApplet context) -> {
            translate(0, 0);
            scale(3.0f/width, height);
            clip(0, 0, 1, 1);
        });

        Node geometry = new Node((PApplet context) -> {
            fill(255, 0, 0);
            //translate(0.5f, 0.5f);
            ellipse(0, 0, 1.0f, 1.0f);
        });

        screen1.addNode(geometry);

        Node screen2 = new Node((PApplet context) -> {
            translate(width/3.0f, 0);
            //scale(3.0f/width, 1.0f/height);
            scale(width/3.0f, height);
            clip(0, 0, 1, 1);
        });

        screen2.addNode(geometry);

        Node screen3 = new Node((PApplet context) -> {
            translate(2.0f * width/3.0f, 0);
            scale(3.0f/width, height);
            clip(0, 0, 1, 1);
        });

        screen3.addNode(geometry);

        root.addNode(screen2);
        //root.addNode(screen2);
        //root.addNode(screen3);

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
        NodeWalker.walk(root, this);
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

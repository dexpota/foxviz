package me.destro.foxviz;

import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.cli.Utilities;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.NodeWalker;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

public class Main extends PApplet {
    private static Arguments arguments;
    private boolean centered = false;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Node screen1;
    private Node screen2;
    private Node screen3;

    public static void main(String[] args) {
        arguments = Utilities.parseArguments(args);

        PApplet.main(Main.class, args);
    }

    @Override
    public void settings() {
        super.settings();

        screen1 = new Node(new Frame(), (Scene context) -> {
            context.pg().background(0);
            context.pg().ellipse(0, 0, 100, 100);
        });

        screen2 = new Node((Scene context) -> {
            context.pg().background(0);
            context.pg().ellipse(0, 0, 200, 400);
        });

        screen3 = new Node((Scene context) -> {
            context.pg().background(0);
            context.pg().ellipse(0, 0, 100, 100);
        });

        size(arguments.width, arguments.height, JAVA2D);
    }

    @Override
    public void setup() {
        super.setup();
        scene1 = new Scene(this, createGraphics((int) (width/3.0), height, JAVA2D));
        scene2 = new Scene(this, createGraphics((int) (width/3.0), height, JAVA2D), (int) (width/3.0), 0);
        scene3 = new Scene(this, createGraphics((int) (width/3.0), height, JAVA2D), (int) (2.0 * width/3.0), 0);

        //scene.setAxesVisualHint(false); // hide axis
        //scene.setGridVisualHint(false); // hide grid
    }

    @Override
    public void draw() {
        if (!centered)
            centerWindow();

        scene1.beginDraw();
        NodeWalker.walk(screen1, scene1);
        scene1.endDraw();
        scene1.display();

        scene2.beginDraw();
        NodeWalker.walk(screen2, scene2);
        scene2.endDraw();
        scene2.display();

        scene3.beginDraw();
        NodeWalker.walk(screen3, scene3);
        scene3.endDraw();
        scene3.display();
    }
    
    private void centerWindow()
    {
        if(frame != null && centered == false) {
            frame.setLocation(displayWidth/2 - width/2,displayHeight/2 - height/2);
            centered = true;
        }
    }
}

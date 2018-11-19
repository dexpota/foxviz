package me.destro.foxviz;

import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.cli.Utilities;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.NodeWalker;
import me.destro.foxviz.scenes.Table;
import me.destro.foxviz.utilities.SceneUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

import java.util.List;

public class Main extends PApplet {
    private static Arguments arguments;
    private boolean centered = false;

    List<Scene> screens;
    List<Node> scenes;

    public static void main(String[] args) {
        arguments = Utilities.parseArguments(args);

        PApplet.main(Main.class, args);
    }

    @Override
    public void settings() {
        super.settings();
        size(arguments.width, arguments.height, P2D);
    }

    @Override
    public void setup() {
        super.setup();

        screens = SceneUtilities.buildScreens(this, width, height);
        scenes = SceneUtilities.buildScenes();
    }

    @Override
    public void draw() {
        if (!centered)
            centerWindow();

        for (int index = 0; index < scenes.size(); index++) {
            screens.get(index).beginDraw();
            NodeWalker.walk(scenes.get(index), screens.get(index));
            screens.get(index).endDraw();
            screens.get(index).display();
        }
    }
    
    private void centerWindow()
    {
        if(frame != null && !centered) {
            frame.setLocation(displayWidth/2 - width/2,displayHeight/2 - height/2);
            centered = true;
        }
    }
}

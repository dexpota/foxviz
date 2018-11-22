package me.destro.foxviz;

import de.looksgood.ani.Ani;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.NodeWalker;
import me.destro.foxviz.utilities.SceneUtilities;
import processing.core.PApplet;

public class ProcessingApplication extends PApplet {
    private boolean centered = false;
    Node root;

    @Override
    public void settings() {
        super.settings();
        size(Main.arguments.width, Main.arguments.height, P2D);
    }

    @Override
    public void setup() {
        super.setup();
        Ani.init(this);
        root = SceneUtilities.buildScene();
    }

    @Override
    public void draw() {
        if (!centered)
            centerWindow();

        NodeWalker.walk(root, this);
    }

    private void centerWindow() {
        if(frame != null && !centered) {
            frame.setLocation(displayWidth/2 - width/2,displayHeight/2 - height/2);
            centered = true;
        }
    }

}

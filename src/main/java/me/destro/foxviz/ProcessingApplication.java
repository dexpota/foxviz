package me.destro.foxviz;

import de.looksgood.ani.Ani;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.NodeWalker;
import me.destro.foxviz.utilities.SceneUtilities;
import processing.core.PApplet;
import processing.core.PFont;

public class ProcessingApplication extends PApplet {
    private boolean centered = false;
    private Node root;

    @Override
    public void settings() {
        super.settings();
        fullScreen();
        size(Main.arguments.width, Main.arguments.height, P2D);
    }

    @Override
    public void setup() {
        super.setup();
        Ani.init(this);

        float offset_w = (displayWidth - Main.arguments.width)/2.0f;
        float offset_h = (displayHeight - Main.arguments.height)/2.0f;

        root = SceneUtilities.buildScene(0, 0);

        root.getTransformation().translate(0, 0);

        PFont mono = createFont(Configuration.fontName, Configuration.fontSize);
        Main.title = createFont("Segoe UI light", Configuration.fontSize);
        Main.secondScreen = createFont("Segoe UI", Configuration.fontSize);
        Main.firstScreen = createFont(Configuration.fontName, Configuration.fontSize);

        textFont(mono);
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

package me.destro.foxviz.utilities;

import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.*;
import me.destro.foxviz.scenes.ScreenThreeScene;
import me.destro.foxviz.scenes.ScreenTwoScene;
import me.destro.foxviz.scenes.TextSpawningNode;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import remixlab.proscene.Scene;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PConstants.P2D;

public class SceneUtilities {

    public static List<Scene> buildScreens(PApplet pApplet, int width, int height) {
        List<Scene> scenes = new ArrayList<>();

        scenes.add(new Scene(pApplet, pApplet.createGraphics((int) (width/3.0), height, P2D)));
        scenes.add(new Scene(pApplet, pApplet.createGraphics((int) (width/3.0), height, P2D), (int) (width/3.0), 0));
        scenes.add(new Scene(pApplet, pApplet.createGraphics((int) (width/3.0), height, P2D), (int) (2.0 * width/3.0), 0));

        scenes.get(0).setAxesVisualHint(false); // hide axis
        scenes.get(0).setGridVisualHint(false); // hide grid

        scenes.get(1).setAxesVisualHint(false); // hide axis
        scenes.get(1).setGridVisualHint(false); // hide grid

        scenes.get(2).setAxesVisualHint(false); // hide axis
        scenes.get(2).setGridVisualHint(false); // hide grid

        return scenes;
    }

    public static Node buildScene() {
        float scale = (float) (1.0/ Main.arguments.pixelSize);

        Node root = new DrawingNode(scene -> {
            scene.background(Configuration.backgroundColor);
        });
        root.getTransformation().scale(scale);

        Node screen1 = buildFirstScreen();
        Node screen2 = buildSecondScreen();
        Node screen3 = buildThirdScreen();

        // TODO restore, now i work only on the second screen.
        root.addNode(screen1);
        //root.addNode(screen2);
        //root.addNode(screen3);

        return root;
    }


    private static Node buildFirstScreen() {
        Node root = new DrawingNode(scene -> {});

        Node left = new ClipNode(0, 0,
                (float) (1000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));

        left.addNode(new TextSpawningNode());
        left.getTransformation().translate(100, 100);

        Node right = new ClipNode((float) (1000.f/ Main.arguments.pixelSize), 0,
                (float) (1000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
        right.getTransformation().translate(1050, 0);
        right.addNode(new TextSpawningNode());

        root.addNode(left);
        root.addNode(right);
        return root;
    }

    private static Node buildSecondScreen() {
        Node screen2 = new ClipNode((float) (2000.f/ Main.arguments.pixelSize), 0,
                (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));

        screen2.getTransformation().translate(2000, 0);

        screen2.appendNode(new DrawingNode(scene -> scene.background(Configuration.backgroundColor)))
                .appendNode(new ScreenTwoScene());

        return screen2;
    }

    private static Node buildThirdScreen() {
        Node screen3 = new ClipNode((float) (4000.f/ Main.arguments.pixelSize), 0,
                (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));

        screen3.getTransformation().translate(4000, 0);

        screen3.appendNode(new DrawingNode(scene -> scene.background(Configuration.backgroundColor)))
                .appendNode(new ScreenThreeScene());

        return screen3;
    }
}

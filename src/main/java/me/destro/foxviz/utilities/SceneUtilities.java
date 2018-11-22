package me.destro.foxviz.utilities;

import me.destro.foxviz.Configuration;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenes.Table;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.unhex;
import static processing.core.PConstants.JAVA2D;
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

    public static List<Node> buildScenes() {
        List<Node> scenes = new ArrayList<>();

        Node scene1 = new Node(new Frame(), (Scene context) -> {
            context.pg().background(Configuration.backgroundColor);
            //context.pg().ellipse(0, 0, 100, 100);
        });

        Node scene2 = new Node((Scene context) -> {
            context.pg().background(Configuration.backgroundColor);
            context.pg().ellipse(0, 0, 200, 400);
        });

        Node scene3 = new Node((Scene context) -> {
            context.pg().background(Configuration.backgroundColor);
            //context.pg().ellipse(0, 0, 100, 100);
        });

        Frame froot = new Frame();
        froot.translate(-100, -100);
        Node root = new Node(froot, scene->{});
        scene3.addNode(root);
        List<Table> tables = Table.generateTables(200, 200, 3);
        for(Table t : tables) {
            root.addNode(t.node);
        }

        scenes.add(scene1);
        scenes.add(scene2);
        scenes.add(scene3);
        return scenes;
    }
}

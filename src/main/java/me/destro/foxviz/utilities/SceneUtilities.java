package me.destro.foxviz.utilities;

import com.github.javafaker.Faker;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextSpawningNode;
import me.destro.foxviz.scenegraph.TransformationNode;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
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
        Frame root_frame = new Frame();
        root_frame.scale((float) (1.0/ Main.arguments.pixelSize));
        Node root = new TransformationNode(root_frame);
        Node root_drawing = new DrawingNode(scene -> scene.background(Configuration.backgroundColor));

        root.addNode(root_drawing);

        Faker faker = new Faker();
        String text = faker.lorem().fixedString(250) + "END";

        Frame frame1 = new Frame();
        frame1.translate(0, 0);
        Node screen1 = new TransformationNode(frame1);
        Node screen1_drawing = new TextSpawningNode();
        screen1.addNode(screen1_drawing);

        Frame frame2 = new Frame();
        frame2.translate(2000, 0);
        Node screen2 = new TransformationNode(frame2);
        Node screen2_drawing = new DrawingNode(scene -> {
            scene.clip((float) (2000.f/Main.arguments.pixelSize), 0, (float) (2000.f/Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
            scene.fill(128, 0, 0);
            scene.noStroke();
            scene.rect(0, 0, 1000, 1000);
        });

        screen2.addNode(screen2_drawing);

        Frame frame3 = new Frame();
        frame3.translate(4000, 0);
        Node screen3 = new TransformationNode(frame3);
        Node screen3_drawing = new DrawingNode( scene -> {
            scene.clip((float) (2*2000.f/Main.arguments.pixelSize), 0, (float) (2000.f/Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
            scene.fill(64);
            scene.noStroke();
            scene.rect(0, 0, 1000, 1000);

            // Back to screen space or weird things happen with the text
            //scene.scale((float) Main.arguments.pixelSize);
            //scene.textSize(32);
            // scene.text(text, 0, (float) (2000.f/Main.arguments.pixelSize));
            //scene.scale((float) (1.0f/Main.arguments.pixelSize));
        });

        screen3.addNode(screen3_drawing);

        root_drawing.addNode(screen1);
        root_drawing.addNode(screen2);
        root_drawing.addNode(screen3);
        return root;
    }

}

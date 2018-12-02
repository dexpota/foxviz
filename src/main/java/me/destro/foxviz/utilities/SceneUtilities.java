package me.destro.foxviz.utilities;

import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.data.PhrasesDataStorage;
import me.destro.foxviz.data.TweetsDataStorage;
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

    public static Node buildScene(float offset_w, float offset_h) {
        float scale = (float) (1.0/ Main.arguments.pixelSize);

        Node root = new DrawingNode(scene -> {
            scene.background(Configuration.backgroundColor);
        });
        root.getTransformation().scale(scale);

        Node screen1 = buildFirstScreen(offset_w, offset_h);
        Node screen2 = buildSecondScreen(offset_w, offset_h);
        Node screen3 = buildThirdScreen(offset_w, offset_h);

        root.addNode(screen1);
        root.addNode(screen2);
        root.addNode(screen3);

        return root;
    }


    private static Node buildFirstScreen(float offset_w, float offset_h) {
        Node root = new ClipNode(offset_w, offset_h,
                (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
        root.appendNode(new DrawingNode(scene -> {
            scene.background(Configuration.backgroundColor);
        }));

        Node title1 = new DrawingNode(new DrawingNode.Drawable() {
            @Override
            public void draw(PApplet scene) {
                scene.scale((float) Main.arguments.pixelSize);
                scene.textFont(Main.title);
                scene.textSize(18);
                scene.textLeading(22);
                scene.fill(Configuration.red.getRed(), Configuration.red.getGreen(), Configuration.red.getBlue());
                scene.text("1/12\nSala Fucine, Torino", 0, 0);
                scene.scale((float) (1.0f/Main.arguments.pixelSize));
            }
        });
        title1.getTransformation().translate(100, 80);
        root.addNode(title1);

        Node title2 = new DrawingNode(new DrawingNode.Drawable() {
            @Override
            public void draw(PApplet scene) {
                scene.scale((float) Main.arguments.pixelSize);
                scene.textFont(Main.title);
                scene.textSize(18);
                scene.textLeading(22);
                scene.fill(Configuration.red.getRed(), Configuration.red.getGreen(), Configuration.red.getBlue());
                scene.text("1/12\nMondo, Twitter", 0, 0);
                scene.scale((float) (1.0f/Main.arguments.pixelSize));
            }
        });
        title2.getTransformation().translate(1050, 80);
        root.addNode(title2);

        float h_offset = 200.0f;

        Node left = new ClipNode((float) (offset_w + 100.f/Main.arguments.pixelSize),
                (float) (offset_h + h_offset/Main.arguments.pixelSize),
                (float) (850.f/ Main.arguments.pixelSize), (float) (3800/Main.arguments.pixelSize));

        left.addNode(new TextSpawningNode(PhrasesDataStorage::get));
        left.getTransformation().translate(100, 150);

        Node right = new ClipNode(offset_w + (float) (1050.f/ Main.arguments.pixelSize),
                (float) (offset_h + h_offset/Main.arguments.pixelSize),
                (float) (850.f/ Main.arguments.pixelSize), (float) (3800/Main.arguments.pixelSize));
        right.getTransformation().translate(1050, 150);
        right.addNode(new TextSpawningNode(TweetsDataStorage::get));

        root.addNode(left);
        root.addNode(right);
        return root;
    }

    private static Node buildSecondScreen(float offset_w, float offset_h) {
        Node screen2 = new ClipNode(offset_w + (float) (2000.f/ Main.arguments.pixelSize), offset_h,
                (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));

        screen2.getTransformation().translate(2000, 0);

        screen2.appendNode(new DrawingNode(scene -> scene.background(Configuration.backgroundColor)))
                .appendNode(new ScreenTwoScene());

        return screen2;
    }

    private static Node buildThirdScreen(float offset_w, float offset_h) {
        Node root = new ClipNode(offset_w + (float) (4000.f/ Main.arguments.pixelSize), offset_h,
                (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
        root.appendNode(new DrawingNode(scene -> {
            scene.background(Configuration.backgroundColor);
        }));

        root.getTransformation().translate(4000, 0);

        Node title1 = new DrawingNode(new DrawingNode.Drawable() {
            @Override
            public void draw(PApplet scene) {
                scene.scale((float) Main.arguments.pixelSize);
                scene.textFont(Main.title);
                scene.textSize(18);
                scene.textLeading(22);
                scene.fill(Configuration.red.getRed(), Configuration.red.getGreen(), Configuration.red.getBlue());
                scene.text("1/12\nSala Fucine, Torino", 0, 0);
                scene.scale((float) (1.0f/Main.arguments.pixelSize));
            }
        });
        title1.getTransformation().translate(100, 80);
        root.addNode(title1);

        Node title2 = new DrawingNode(new DrawingNode.Drawable() {
            @Override
            public void draw(PApplet scene) {
                scene.scale((float) Main.arguments.pixelSize);
                scene.textFont(Main.title);
                scene.textSize(18);
                scene.textLeading(22);
                scene.fill(Configuration.red.getRed(), Configuration.red.getGreen(), Configuration.red.getBlue());
                scene.text("1/12\nMondo, Twitter", 0, 0);
                scene.scale((float) (1.0f/Main.arguments.pixelSize));
            }
        });
        title2.getTransformation().translate(1050, 80);
        root.addNode(title2);

        float h_offset = 200.0f;

        Node left = new ClipNode((float) (offset_w + 4100.f/Main.arguments.pixelSize),
                (float) (offset_h + (h_offset + 100.f)/Main.arguments.pixelSize),
                (float) ((850.f)/ Main.arguments.pixelSize), (float) (3800/Main.arguments.pixelSize));

        left.addNode(new TextSpawningNode(PhrasesDataStorage::get));
        left.getTransformation().translate(100, 100);

        Node right = new ClipNode(offset_w + (float) (5050.f/ Main.arguments.pixelSize),
                (float) (offset_h + (h_offset + 100.f)/Main.arguments.pixelSize),
                (float) ((850.f)/ Main.arguments.pixelSize), (float) (3800/Main.arguments.pixelSize));
        right.getTransformation().translate(1050, 100);
        right.addNode(new TextSpawningNode(TweetsDataStorage::get));

        root.addNode(left);
        root.addNode(right);
        return root;
    }
}

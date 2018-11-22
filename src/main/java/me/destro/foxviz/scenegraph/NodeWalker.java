package me.destro.foxviz.scenegraph;

import processing.core.PApplet;

public class NodeWalker {
    public static void walk(Node root, PApplet scene) {
        scene.pushMatrix();
        root.draw(scene);
        for (Node node : root) {
            walk(node, scene);
        }
        scene.popMatrix();
    }
}

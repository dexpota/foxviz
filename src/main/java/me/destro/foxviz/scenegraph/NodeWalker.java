package me.destro.foxviz.scenegraph;

import remixlab.proscene.Scene;

public class NodeWalker {
    public static void walk(Node root, Scene scene) {
        scene.pg().pushMatrix();
        root.visit(scene);
        for (Node node : root) {
            walk(node, scene);
        }
        scene.pg().popMatrix();
    }
}

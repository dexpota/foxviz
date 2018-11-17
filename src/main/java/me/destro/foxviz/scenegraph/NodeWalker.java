package me.destro.foxviz.scenegraph;

import remixlab.proscene.Scene;

public class NodeWalker {
    public static void walk(Node root, Scene scene) {
        root.visit(scene);
        for (Node node : root) {
            //scene.pApplet().pushMatrix();
            node.visit(scene);
            walk(node, scene);
            //scene.pApplet().popMatrix();
        }
    }
}

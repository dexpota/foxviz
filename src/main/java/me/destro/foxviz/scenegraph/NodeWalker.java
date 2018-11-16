package me.destro.foxviz.scenegraph;

import processing.core.PApplet;

public class NodeWalker {
    public static void walk(Node root, PApplet context) {
        for (Node node : root) {
            context.pushMatrix();
            node.visit(context);
            walk(node, context);
            context.popMatrix();
        }
    }
}

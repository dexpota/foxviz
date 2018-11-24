package me.destro.foxviz.scenes;


import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TransformationNode;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class ScreenThreeScene extends Node {
    Node root;

    public ScreenThreeScene() {
        root = this.appendNode(new TransformationNode(1000, 2000));
        this.addNode(root);
        generateTables(root);
    }

    private void generateTables(Node root){
        //double[] xs = MathUtilities.linspace(0, w, count);
        //double[] ys = MathUtilities.linspace(0, h, count);

        double[][] arc_angles = {
                MathUtilities.linspace(0, 2 * Math.PI, 11),
                MathUtilities.linspace(Math.PI/4.0, 2 * Math.PI, 6)
        };

        double[] arc_radius = {
                300
        };

        for (int i = 0; i < 1; i++) {
            double[] angles = arc_angles[i];
            double radius = arc_radius[i];

            for (double angle : angles) {
                Point p = MathUtilities.polarToCartesian(radius, angle);
                root.appendNode(new TransformationNode(p.x(), p.y()))
                        .appendNode(new Table());
            }
        }
    }

    @Override
    public void draw(PApplet scene) {

    }
}

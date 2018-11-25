package me.destro.foxviz.scenes;


import de.looksgood.ani.Ani;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.DataStorage;
import me.destro.foxviz.Main;
import me.destro.foxviz.model.Connection;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TransformationNode;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static processing.core.PApplet.unhex;

public class ScreenThreeScene extends Node {
    Node root;
    List<Table> tables;
    ConcurrentLinkedQueue<Connection> connections;

    public class Table extends Node {
        private int color;
        public int id;
        public Point point;
        private TransformationNode transformationNode;

        public Table(int id, Point p) {
            color = unhex("FF40a361");
            this.id = id;
            transformationNode = new TransformationNode();
            this.point = p;
            this.appendNode(transformationNode)
                    .appendNode(new TransformationNode(p.x(), p.y()))
                    .appendNode(new DrawingNode(scene -> {
                scene.fill(255);
                scene.ellipse(0, 0, 100, 100);
            }));
        }

        @Override
        public void draw(PApplet scene) {

        }

        public Point getPosition() {
            return point;
        }
    }


    public ScreenThreeScene() {
        root = this.appendNode(new TransformationNode(1000, 2000));
        DrawingNode d = new DrawingNode(scene -> {
            for (Connection c : connections) {
                scene.stroke(255);
                scene.strokeWeight(4);
                scene.line(c.p1.x(), c.p1.y(), c.x, c.y);
            }
        });
        root.appendNode(d);
        this.addNode(root);
        tables = new LinkedList<>();
        generateTables(root);
        this.connections = new ConcurrentLinkedQueue<>();
    }

    class Connection {
        public Point p1;
        public float x, y;
    }

    private void generateTables(Node root){
        //double[] xs = MathUtilities.linspace(0, w, count);
        //double[] ys = MathUtilities.linspace(0, h, count);

        double[][] arc_angles = {
                MathUtilities.linspace(0, 2 * Math.PI, 11),
                MathUtilities.linspace(Math.toRadians(-33), Math.toRadians(-180+33), 6),
                MathUtilities.linspace(Math.toRadians(-56), Math.toRadians(-180+56), 5),
                MathUtilities.linspace(Math.toRadians(-60), Math.toRadians(-180+60), 6),
                MathUtilities.linspace(Math.toRadians(-68), Math.toRadians(-180+68), 5),
                MathUtilities.linspace(Math.toRadians(-71), Math.toRadians(-180+71), 5),

                MathUtilities.linspace(Math.toRadians(33), Math.toRadians(180-33), 6),
                MathUtilities.linspace(Math.toRadians(56), Math.toRadians(180-56), 5),
                MathUtilities.linspace(Math.toRadians(60), Math.toRadians(180-60), 6),
                MathUtilities.linspace(Math.toRadians(68), Math.toRadians(180-68), 5),
                MathUtilities.linspace(Math.toRadians(71), Math.toRadians(180-71), 5),
                MathUtilities.linspace(Math.toRadians(73), Math.toRadians(180-73), 5)
        };

        double[] arc_radius = {
                1000*0.4175,
                1000*0.4175*1.59,
                1000*0.4175*2.19,
                1000*0.4175*2.77,
                1000*0.4175*3.35,
                1000*0.4175*3.96,

                1000*0.4175*1.59,
                1000*0.4175*2.19,
                1000*0.4175*2.77,
                1000*0.4175*3.35,
                1000*0.4175*3.96,
                1000*0.4175*4.58
        };

        int id = 0;

        for (int i = 0; i < 12; i++) {
            double[] angles = arc_angles[i];
            double radius = arc_radius[i];

            for (double angle : angles) {
                Point p = MathUtilities.polarToCartesian(radius, angle);
                Table t = new Table(id, p);
                root.appendNode(t);
                tables.add(t);
                id++;
            }
        }
    }

    public Table getTable(int id) {
        for (Table t : tables) {
            if(t.id == id)
                return t;
        }
        return null;
    }

    @Override
    public void draw(PApplet scene) {
        me.destro.foxviz.model.Connection c = DataStorage.fetchConnection();
        if (c != null) {
            Table t1 = getTable(c.a);
            Table t2 = getTable(c.b);
            Point p1 = t1.getPosition();
            Point p2 = t2.getPosition();

            Connection c1 = new Connection();
            c1.p1 = p1;
            c1.x = p1.x();
            c1.y = p1.y();

            if (!this.connections.contains(c1)) {
                this.connections.add(c1);
            }

            Ani.to(c1, 5, String.format("x:%d,y:%d", p2.x(), p2.y()), Ani.LINEAR);

        }
    }
}

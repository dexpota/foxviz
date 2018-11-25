package me.destro.foxviz.scenes;


import de.looksgood.ani.Ani;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TransformationNode;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static processing.core.PApplet.unhex;

public class ScreenThreeScene extends Node {
    Node root;
    List<Table> tables;

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

    List<Connection> connections;

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
        this.connections = new ArrayList<>();

        Main.ai.generateConnection()
                .subscribeOn(Schedulers.computation())
                .subscribe(connection -> {
            Table t1 = getTable(connection.a);
            Table t2 = getTable(connection.b);
            Point p1 = t1.getPosition();
            Point p2 = t2.getPosition();

            Connection c = new Connection();
            c.p1 = p1;
            c.x = p1.x();
            c.y = p1.y();

            Ani.to(c, 5, String.format("x:%d,y:%d", p2.x(), p2.y()), Ani.LINEAR);

            this.connections.add(c);
            /*ScreenThreeScene.this.root.addNode(new DrawingNode(scene -> {
                scene.stroke(255);
                scene.strokeWeight(4);
                scene.line(p1.x(), p1.y(), p2.x(), p2.y());
            }));*/
        });
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
                MathUtilities.linspace(Math.PI/4.0, 2 * Math.PI, 6)
        };

        double[] arc_radius = {
                300
        };

        int id = 0;

        for (int i = 0; i < 1; i++) {
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

    }
}

package me.destro.foxviz.scenes;


import de.looksgood.ani.Ani;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.DataStorage;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.scenegraph.TransformationComponent;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Point;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static processing.core.PApplet.unhex;

public class ScreenThreeScene extends Node {
    List<Table> tables;
    List<Connection> connections;

    public class Table extends Node {
        int size = 100;
        private int color;
        public int id;
        public Point point;

        public Table(int id, Point p) {
            color = unhex("FF40a361");
            this.id = id;
            this.point = p;

            this.transformation = new TransformationComponent(p.x(), p.y());

            Node label = new TextNode(String.valueOf(this.id + 1));
            label.getTransformation().translate(-40, -50).rotate((float) Math.toRadians(-45));

            this.addNode(label);
        }

        @Override
        public void draw(PApplet scene) {
            scene.stroke(255);
            scene.strokeWeight(3);
            scene.fill(Configuration.backgroundColor);
            scene.ellipse(0, 0, size, size);

            scene.fill(255);
            scene.noStroke();
            scene.ellipse(0, 0, 20, 20);
        }

        public Point getPosition() {
            return point;
        }
    }


    public ScreenThreeScene() {
        // TODO make a configuration value
        this.transformation = new TransformationComponent(1070, 1880);

        tables = new LinkedList<>();
        this.connections = new LinkedList<>();
        generateTables();
    }

    class Connection {
        public Point p1;
        public float x, y;
        public float alpha = 0.0f;
        public Color color;

        float f1 = 0.0f;
        float angle1 = 0.0f;
        float f2 = 0.0f;
        float angle2 = 0.0f;
    }

    private void generateTables(){
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
                1000*0.4175*1.59*1.05,
                1000*0.4175*2.19*1.05,
                1000*0.4175*2.77*1.05,
                1000*0.4175*3.35*1.05,
                1000*0.4175*3.96*1.05,

                1000*0.4175*1.59*1.05,
                1000*0.4175*2.19*1.05,
                1000*0.4175*2.77*1.05,
                1000*0.4175*3.35*1.05,
                1000*0.4175*3.96*1.05,
                1000*0.4175*4.58*1.05
        };

        int id = 0;

        for (int i = 0; i < 12; i++) {
            double[] angles = arc_angles[i];
            double radius = arc_radius[i];

            for (double angle : angles) {
                Point p = MathUtilities.polarToCartesian(radius, angle);
                Table t = new Table(id, p);
                this.addNode(t);
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
        drawConnections(scene);
        update();
    }

    private void drawConnections(PApplet scene) {
        for (Connection c : connections) {
            scene.noFill();
            scene.strokeWeight(3);
            scene.stroke(c.color.getRed(), c.color.getGreen(), c.color.getBlue(), c.alpha);

            Point start = new Point(c.p1);
            Point end = new Point(c.x, c.y);

            Point v = new Point(end.x() - start.x(), end.y() - start.y());

            Point v1 = MathUtilities.rotateVector(v, Math.toRadians(c.angle1));

            Point vv = new Point(-v.x(), -v.y());
            Point v2 = MathUtilities.rotateVector(vv, Math.toRadians(-c.angle1));

            scene.bezier(c.p1.x(), c.p1.y(),
                    c.p1.x() + v1.x()*c.f1, c.p1.y() + v1.y()*c.f1,
                    c.x + v2.x()*c.f1, c.y + v2.y()*c.f1,
                    c.x, c.y);
        }
    }


    private void update() {
        List<me.destro.foxviz.model.Connection> connections = DataStorage.fetchConnection();

        if (connections == null)
            return;

        for (me.destro.foxviz.model.Connection c : connections) {
            if (c != null) {
                Table t1 = getTable(c.a);
                Table t2 = getTable(c.b);
                Point p1 = t1.getPosition();
                Point p2 = t2.getPosition();

                Connection c1 = new Connection();
                c1.p1 = p1;
                c1.x = p1.x();
                c1.y = p1.y();

                c1.color = MathUtilities.generateRandomColor(MathUtilities.pickValue(Configuration.basicConnectionColors));

                c1.f1 = MathUtilities.randomFloat() * 0.25f + 0.25f;
                c1.f2 = MathUtilities.randomFloat() * 0.25f + 0.25f;

                c1.angle1 = MathUtilities.randomFloat() * 30.0f + 15.0f;
                c1.angle2 = MathUtilities.randomFloat() * 30.0f + 15.0f;

                if (!this.connections.contains(c1)) {
                    this.connections.add(c1);
                    if (t1.size < 200)
                        Ani.to(t1, 4.0f, "size", t1.size + 20, Ani.ELASTIC_IN_OUT);
                    if (t2.size < 200)
                        Ani.to(t2, 4.0f, "size", t2.size + 20, Ani.ELASTIC_IN_OUT);

                    Ani.to(c1, 1.5f, "alpha", 255.0f, Ani.LINEAR);
                    Ani.to(c1, 1.5f, String.format("x:%d,y:%d", p2.x(), p2.y()), Ani.CIRC_IN_OUT);
                } else {
                    System.out.println("already present.");
                }

            }
        }
    }
}

package me.destro.foxviz.scenes;


import com.google.common.base.Stopwatch;
import de.looksgood.ani.Ani;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.data.AiDataStorage;
import me.destro.foxviz.model.TableConnection;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.scenegraph.TransformationComponent;
import me.destro.foxviz.utilities.MathUtilities;
import org.apache.commons.collections4.CollectionUtils;
import processing.core.PApplet;
import remixlab.dandelion.geom.Point;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static processing.core.PApplet.unhex;

public class ScreenThreeScene extends Node {
    Queue<TableConnection> toInsertConnections = new LinkedList<>();
    Queue<TableConnection> toRemoveConnections = new LinkedList<>();

    List<TableConnection> onScreen;

    Stopwatch insertionStopWatch = Stopwatch.createStarted();
    int insertionWaitTime;

    Stopwatch removingStopwatch = Stopwatch.createStarted();
    int removingWaitTime;

    Stopwatch togglingStopwatch = Stopwatch.createStarted();
    int togglingTime;

    List<Table> tables;

    public class Table extends Node {
        int size = 100;
        private int color;
        public int id;
        public Point point;

        private Ani shaking;
        private Ani growing;

        public Table(int id, Point p) {
            color = unhex("FF40a361");
            this.id = id;
            this.point = p;

            this.transformation = new TransformationComponent(p.x(), p.y());

            Node label = new TextNode(String.valueOf(this.id + 1));
            label.getTransformation().translate(-40, -50).rotate((float) Math.toRadians(-45));

            this.addNode(label);
        }

        public void growTable() {
            if (canGrow()) {
                growing = new Ani(this, 4.0f, "size", size + 20, Ani.ELASTIC_IN_OUT);
            }
        }

        public void shrinkTable() {
            if (canShrink()) {
                 Ani.to(this, 4.0f, "size", size - 20, Ani.ELASTIC_IN_OUT);
            }
        }

        private boolean canGrow() {
            return size < 200;
        }

        private boolean canShrink() {
            return size > 100;
        }

        public void shakeTable() {
            if (!growing.isPlaying()) {
                shaking = new Ani(this, 4.0f, "size", this.size + 20, Ani.ELASTIC_IN_OUT);
            }
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
        transformation = new TransformationComponent(1070, 1880);

        insertionWaitTime = MathUtilities.random(Configuration.thirdScreenMinWaitTime, Configuration.thirdScreenMaxWaitTime);
        removingWaitTime = MathUtilities.random(Configuration.thirdScreenMinWaitTime, Configuration.thirdScreenMaxWaitTime);
        togglingTime = MathUtilities.random(5, 10);
        onScreen = new LinkedList<>();

        tables = new LinkedList<>();
        generateTables();
    }

    class ConnectionNode extends Node {

        public void toogle() {
            if (alpha == 0)
                Ani.to(this, 1.5f, "alpha", 255.0f, Ani.LINEAR);
            else if (alpha == 255)
                Ani.to(this, 1.5f, "alpha", 0.0f, Ani.LINEAR);
        }

        public ConnectionNode(TableConnection c) {
            this.c = c;
        }

        public TableConnection getConnection() {
            return c;
        }

        TableConnection c;

        public Point p1;
        public float x, y;
        public float alpha = 0.0f;
        public Color color;

        float f1 = 0.0f;
        float angle1 = 0.0f;
        float f2 = 0.0f;
        float angle2 = 0.0f;

        @Override
        public void draw(PApplet scene) {
            scene.noFill();
            scene.strokeWeight(3);
            scene.stroke(color.getRed(), color.getGreen(), color.getBlue(), alpha);

            Point start = new Point(p1);
            Point end = new Point(x, y);

            Point v = new Point(end.x() - start.x(), end.y() - start.y());

            Point v1 = MathUtilities.rotateVector(v, Math.toRadians(angle1));

            Point vv = new Point(-v.x(), -v.y());
            Point v2 = MathUtilities.rotateVector(vv, Math.toRadians(-angle1));

            scene.bezier(p1.x(), p1.y(),
                    p1.x() + v1.x()*f1, p1.y() + v1.y()*f1,
                    x + v2.x()*f1, y + v2.y()*f1,
                    x, y);
        }
    }

    private void generateTables(){
        double[][] arc_angles = {
                MathUtilities.linspace(Math.toRadians(-34), Math.toRadians(-164), 5),
                MathUtilities.linspace(Math.toRadians(-180+33), Math.toRadians(-33), 6),
                MathUtilities.linspace(Math.toRadians(-56), Math.toRadians(-180+56), 5),
                MathUtilities.linspace(Math.toRadians(-180+60), Math.toRadians(-60), 6),
                MathUtilities.linspace(Math.toRadians(-68), Math.toRadians(-180+68), 5),
                MathUtilities.linspace(Math.toRadians(-180+71), Math.toRadians(-71), 5),

                MathUtilities.linspace(0, Math.toRadians(164), 6),
                MathUtilities.linspace(Math.toRadians(180-33), Math.toRadians(33), 6),
                MathUtilities.linspace(Math.toRadians(56), Math.toRadians(180-56), 5),
                MathUtilities.linspace(Math.toRadians(180-60), Math.toRadians(60), 6),
                MathUtilities.linspace(Math.toRadians(68), Math.toRadians(180-68), 5),
                MathUtilities.linspace(Math.toRadians(180-71), Math.toRadians(71), 5),
                MathUtilities.linspace(Math.toRadians(73), Math.toRadians(180-73), 5)
        };

        double[] arc_radius = {
                1000*0.4175,
                1000*0.4175*1.59*1.05,
                1000*0.4175*2.19*1.05,
                1000*0.4175*2.77*1.05,
                1000*0.4175*3.35*1.05,
                1000*0.4175*3.96*1.05,

                1000*0.4175,
                1000*0.4175*1.59*1.05,
                1000*0.4175*2.19*1.05,
                1000*0.4175*2.77*1.05,
                1000*0.4175*3.35*1.05,
                1000*0.4175*3.96*1.05,
                1000*0.4175*4.58*1.05
        };

        int id = 0;

        for (int i = 0; i < 6; i++) {
            double[] angles = arc_angles[i];
            double radius = arc_radius[i];

            for (double angle : angles) {
                Point p = MathUtilities.polarToCartesian(radius, angle);
                Table t = new Table(id, p);
                this.addNode(t);
                tables.add(t);
                id = id + 2;
            }
        }

        id = 1;
        for (int i = 6; i < 13; i++) {
            double[] angles = arc_angles[i];
            double radius = arc_radius[i];

            for (double angle : angles) {
                Point p = MathUtilities.polarToCartesian(radius, angle);
                Table t = new Table(id, p);
                this.addNode(t);
                tables.add(t);
                id = id + 2;
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
        update();
    }

    private void update() {
        if (AiDataStorage.areConnectionsUpdated()) {
            Set<TableConnection> connections = AiDataStorage.getWordsConnections();
            updateQueues(connections);
        }


        if (insertionStopWatch.elapsed(TimeUnit.SECONDS) > insertionWaitTime
                && toInsertConnections.peek() != null) {

            generateConnectionOnScreen(toInsertConnections.remove());
            insertionStopWatch.stop();
            insertionStopWatch.reset();
            insertionStopWatch.start();
        }

        if (removingStopwatch.elapsed(TimeUnit.SECONDS) > removingWaitTime &&
                toRemoveConnections.peek() != null) {

            TableConnection remove = toRemoveConnections.remove();
            removeConnection(remove);
            removingStopwatch.stop();
            removingStopwatch.reset();
            removingStopwatch.start();
        }

        // TODO if nothing happens
        if (toInsertConnections.isEmpty() && toRemoveConnections.isEmpty() && !onScreen.isEmpty()
                && togglingStopwatch.elapsed(TimeUnit.SECONDS) > togglingTime) {

            int index = MathUtilities.random(0, this.getChildrenCount());
            int i = 0;
            for (Node n : this) {
                if (i == index && n instanceof ConnectionNode) {
                    ConnectionNode node = (ConnectionNode)this.getNode(index);
                    node.toogle();

                    if (node.alpha == 0) {
                        Table table0 = getTable(node.getConnection().a);
                        Table table1 = getTable(node.getConnection().b);
                        table0.shrinkTable();
                        table1.shrinkTable();
                    }else {
                        Table table0 = getTable(node.getConnection().a);
                        Table table1 = getTable(node.getConnection().b);
                        table0.shrinkTable();
                        table1.shrinkTable();
                    }

                    togglingTime = MathUtilities.random(5, 10);
                    togglingStopwatch.stop();
                    togglingStopwatch.reset();
                    togglingStopwatch.start();
                }
                i++;
            }
        }
    }

    private void removeConnection(TableConnection connection) {
        if (removeNodeIf(node -> node instanceof ConnectionNode
                && ((ConnectionNode) node).getConnection() == connection)) {
            onScreen.removeIf(nn -> nn.equals(connection));
        }

        Table table0 = getTable(connection.a);
        Table table1 = getTable(connection.b);
        table0.shrinkTable();
        table1.shrinkTable();
    }

    private void updateQueues(Set<TableConnection> connections) {
        toInsertConnections.clear();
        toRemoveConnections.clear();

        Collection<TableConnection> newConnectionsToInsert
                = CollectionUtils.subtract(connections, onScreen);

        // add these words to the queue
        toInsertConnections.addAll(newConnectionsToInsert);

        // which of the words on the screen are no longer on?
        Collection<TableConnection> newToRemove
                = CollectionUtils.subtract(onScreen, connections);

        toRemoveConnections.addAll(newToRemove);
    }

    private void generateConnectionOnScreen(TableConnection connection) {
        Table t1 = getTable(connection.a);
        Table t2 = getTable(connection.b);

        if (t1 != null && t2 != null) {
            Point p1 = t1.getPosition();
            Point p2 = t2.getPosition();

            ConnectionNode c1 = new ConnectionNode(connection);
            c1.p1 = p1;
            c1.x = p1.x();
            c1.y = p1.y();

            c1.color = MathUtilities.generateRandomColor(MathUtilities.pickValue(Configuration.basicConnectionColors));

            double x = Math.sqrt(Math.pow(c1.x - p2.x(), 2) + Math.pow(c1.y - p2.y(), 2));

            double factor = -0.5f/4000*x+0.6;

            c1.f1 = (float) factor;
            c1.f2 = (float) factor;

            c1.angle1 = MathUtilities.randomFloat() * 30.0f + 15.0f;
            c1.angle2 = MathUtilities.randomFloat() * 30.0f + 15.0f;

            t1.growTable();
            t2.growTable();

            Ani.to(c1, 1.5f, "alpha", 255.0f, Ani.LINEAR);
            Ani.to(c1, 1.5f, String.format("x:%d,y:%d", p2.x(), p2.y()), Ani.CIRC_IN_OUT);

            onScreen.add(connection);
            addNode(c1);
        }
    }


}

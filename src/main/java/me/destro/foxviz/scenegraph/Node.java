package me.destro.foxviz.scenegraph;

import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node implements Iterable<Node> {
    List<Node> nodes;
    Visitor beahviour;
    Frame frame;

    public interface Visitor {
        void visit(Scene scene);
    }

    public Node(Visitor beahviour) {
        this(new Frame(), beahviour);
    }

    public Node(Frame frame, Visitor beahviour) {
        this.nodes = new LinkedList<>();
        this.frame = frame;
        this.beahviour = beahviour;
    }

    public void addNode(Node node) {
        if (node == this)
            throw new IllegalArgumentException();
        nodes.add(node);
    }

    public boolean detachNode(Node node) {
        if(nodes.contains(node)) {
            nodes.remove(node);
            return true;
        }

        return false;
    }

    public void visit(Scene scene) {
        scene.applyTransformation(frame);
        beahviour.visit(scene);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }
}

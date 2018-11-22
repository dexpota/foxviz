package me.destro.foxviz.scenegraph;

import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.proscene.Scene;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Node implements Iterable<Node> {
    List<Node> nodes;

    public Node() {
        this.nodes = new LinkedList<>();
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

    public abstract void draw(PApplet scene);

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }
}

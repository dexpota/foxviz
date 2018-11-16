package me.destro.foxviz.scenegraph;

import processing.core.PApplet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node implements Iterable<Node> {
    List<Node> nodes;
    NodeBeahviour beahviour;

    public interface NodeBeahviour {
        void visit(PApplet context);
    }

    public Node(NodeBeahviour beahviour) {
        nodes = new LinkedList<>();
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

    public void visit(PApplet context) {
        beahviour.visit(context);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }
}

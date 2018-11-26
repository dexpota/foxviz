package me.destro.foxviz.scenegraph;
import processing.core.PApplet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Node implements Iterable<Node> {
    protected TransformationComponent transformation;
    List<Node> nodes;

    public TransformationComponent getTransformation() {
        return transformation;
    }

    public Node() {
        this(new TransformationComponent());
    }

    public Node(TransformationComponent transformation) {
        this.transformation = transformation;
        this.nodes = new LinkedList<>();
    }

    public void addNode(Node node) {
        if (node == this)
            throw new IllegalArgumentException();
        nodes.add(node);
    }

    public boolean removeNode(Node node) {
        if(nodes.contains(node)) {
            nodes.remove(node);
            return true;
        }

        return false;
    }

    public Node appendNode(Node node) {
        if (node == this)
            throw new IllegalArgumentException();
        nodes.add(node);
        return node;
    }

    public abstract void draw(PApplet scene);

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }
}

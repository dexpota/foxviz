package me.destro.foxviz.scenegraph;
import processing.core.PApplet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

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

    public boolean removeNodeIf(Predicate<? super Node> predicate) {
        return nodes.removeIf(predicate);
    }

    public Node appendNode(Node node) {
        if (node == this)
            throw new IllegalArgumentException();
        nodes.add(node);
        return node;
    }

    public int childrenCount() {
        return nodes.size();
    }

    public abstract void draw(PApplet scene);

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    public int getChildrenCount() {
        return this.nodes.size();
    }

    public Node getNode(int i) {
        return this.nodes.get(i);
    }
}

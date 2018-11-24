package me.destro.foxviz.scenes;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.scenegraph.TransformationNode;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;

public class ScreenTwoScene extends Node {
    public ScreenTwoScene() {
        Disposable subscription = Main.ai.generateWord()
                .subscribeOn(Schedulers.computation())
                .subscribe(word -> onWordReceived(word));
    }

    private void onWordReceived(String word) {
        Node translationNode = new TransformationNode(1000, 2000);
        Node scalingNode = new TransformationNode();

        int target_x = MathUtilities.random(0, 2000);
        int target_y = MathUtilities.random(0, 4000);

        Node animationNode = new DrawingNode(scene -> {
            Ani.to(scalingNode, 5, "sx:0.2,sy:0.2", Ani.LINEAR);
            Ani.to(translationNode, 5, String.format("x:%s,y:%s", target_x, target_y), Ani.LINEAR);
        });
        Node textNode = new TextNode(word);

        this.appendNode(translationNode)
                .appendNode(scalingNode)
                .appendNode(animationNode)
                .appendNode(textNode);
    }

    @Override
    public void draw(PApplet scene) {
    }
}

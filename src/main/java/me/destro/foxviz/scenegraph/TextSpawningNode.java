package me.destro.foxviz.scenegraph;

import de.looksgood.ani.Ani;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;

import java.util.LinkedList;
import java.util.Queue;

public class TextSpawningNode extends Node {
    String text;
    Queue<String> tweets;

    public TextSpawningNode() {
        tweets = new LinkedList<>();
        Disposable subscription = Main.api.searchTweets()
                .subscribeOn(Schedulers.computation())
                .subscribe(tweet -> {
                    text = tweet.text;
                    tweets.add(text);
                });
    }

    @Override
    public void draw(PApplet scene) {
        scene.background(Configuration.backgroundColor);

        String tweet = tweets.peek();
        if(tweet != null) {
            tweet = tweets.remove();

            Node transformationNode =  new TransformationNode(0, -1000);

            Ani.to(transformationNode, 90, "y", 4100);

            Node textNode = new TextNode(tweet, Configuration.firstColumnTextSize);

            this.appendNode(transformationNode).appendNode(textNode);
        }

        for (Node n : nodes) {
            if (n instanceof TransformationNode) {
                if (((TransformationNode) n).y > 4050){
                    this.removeNode(n);
                }
            }
        }
    }
}

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
                    System.out.println(String.format("Tweet: %s", tweet.text));
                });
    }

    @Override
    public void draw(PApplet scene) {
        scene.background(Configuration.backgroundColor);

        // TODO Moving this to a function
        // TODO remove the nodes when they are not visible anymore.
        String tweet = tweets.peek();
        if(tweet != null) {
            tweet = tweets.remove();

            Node transformationNode =  new TransformationNode(0, -1000);

            Node animationNode = new DrawingNode(scene1 -> {
                Ani.to(transformationNode, 20, "y", 4000);
            });

            Node textNode = new TextNode(tweet);

            animationNode.appendNode(transformationNode).appendNode(textNode);
            this.addNode(animationNode);
        }
    }
}

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
        scene.clip(0, 0, (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
        scene.background(Configuration.backgroundColor);
        scene.fill(128, 0, 0);
        scene.noStroke();
        scene.rect(0, 0, 1000, 1000);

        // TODO Moving this to a function
        String t = tweets.peek();
        if(t != null) {
            t = tweets.remove();
            Frame f = new Frame();
            Node n =  new TransformationNode(f);
            String finalT = t;
            Node g = new DrawingNode(scene1 -> {
                // TODO make a TextNode type
                // Back to screen space or weird things happen with the text
                scene.scale((float) Main.arguments.pixelSize);
                scene.textSize(12);
                scene.fill(255);
                scene.text(finalT, 0, 0,
                        (float) (1000.f/Main.arguments.pixelSize),
                        (float) (1000.f/Main.arguments.pixelSize));
                scene.scale((float) (1.0f/Main.arguments.pixelSize));

                Ani.to(n, 10, "y", 4000);
            });
            n.addNode(g);
            this.addNode(n);
        }
    }
}

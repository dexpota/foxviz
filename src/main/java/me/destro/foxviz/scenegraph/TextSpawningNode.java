package me.destro.foxviz.scenegraph;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Main;
import processing.core.PApplet;

public class TextSpawningNode extends Node {
    String text;

    public TextSpawningNode() {
        Disposable subscription = Main.api.searchTweets()
                .subscribeOn(Schedulers.computation())
                .subscribe(tweet -> {
                    text = tweet.text;
                    System.out.println(String.format("Tweet: %s", tweet.text));
                });
    }

    @Override
    public void draw(PApplet scene) {
        scene.clip(0, 0, (float) (2000.f/ Main.arguments.pixelSize), (float) (4000/Main.arguments.pixelSize));
        scene.fill(128, 0, 0);
        scene.noStroke();
        scene.rect(0, 0, 1000, 1000);

        // Back to screen space or weird things happen with the text
        scene.scale((float) Main.arguments.pixelSize);
        scene.textSize(12);
        scene.fill(255);
        if (text != null)
            scene.text(text, 0, 0,
                (float) (1000.f/Main.arguments.pixelSize),
                (float) (1000.f/Main.arguments.pixelSize));
        scene.scale((float) (1.0f/Main.arguments.pixelSize));
    }
}

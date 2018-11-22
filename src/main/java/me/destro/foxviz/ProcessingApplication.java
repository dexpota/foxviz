package me.destro.foxviz;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import processing.core.PApplet;

public class ProcessingApplication extends PApplet {
    private Disposable subscribtion;
    private boolean centered = false;
    private String text;

    @Override
    public void settings() {
        super.settings();
        size(Main.arguments.width, Main.arguments.height, P2D);
    }

    @Override
    public void setup() {
        super.setup();

        text = "Hello processing";
        subscribtion = Main.api.searchTweets()
                .subscribeOn(Schedulers.computation())
                .subscribe(tweet -> {
                    text = tweet.text;
                    System.out.println(String.format("Tweet: %s", tweet.text));
                });
    }

    @Override
    public void draw() {
        if (!centered)
            centerWindow();

        background(Configuration.backgroundColor);
        translate(width/2.0f, height/2.0f);
        textSize(32);
        text(text, 0, 0);
    }

    private void centerWindow() {
        if(frame != null && !centered) {
            frame.setLocation(displayWidth/2 - width/2,displayHeight/2 - height/2);
            centered = true;
        }
    }

    @Override
    public void stop() {
        if (subscribtion != null && !subscribtion.isDisposed())
            subscribtion.dispose();
        super.stop();
    }
}

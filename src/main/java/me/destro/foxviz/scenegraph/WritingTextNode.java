package me.destro.foxviz.scenegraph;

import com.google.common.base.Stopwatch;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.Node;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.concurrent.TimeUnit;

import static me.destro.foxviz.utilities.TextUtilities.createLineBreaks;
import static processing.core.PConstants.CORNER;

public class WritingTextNode extends Node {
    // the text and a holder
    String text, displayText;

    //positioning
    PVector bgPos, textPos;
    float w, h;

    //timimng
    Stopwatch timer = Stopwatch.createStarted();
    int wait;

    float fontSize = 16.0f;

    /*************THE CONSTRUCTOR**********/
    //it takes the text, positioning stuff and init everything
    public WritingTextNode(String _tx, PVector _bg, float _w, float fontSize) {
        bgPos   = _bg;
        w       = _w;
        h       = 26;
        text    = _tx;
        displayText = "";
        this.fontSize = fontSize;

        //calc text pos relative to bg pos
        textPos = new PVector(bgPos.x + 27, (bgPos.y + h) - 8);

        // the speed of "typing"
        wait = 100;
    }

    void update() {
        if (!isFinished() && timer.elapsed(TimeUnit.MILLISECONDS) > wait) {
            displayText = text.substring(0, displayText.length()+1);

            timer.stop();
            timer.reset();
            timer.start();
        }
    }//update()

    boolean isFinished() {
        return displayText.length() == text.length();
    }//isFinished

    void drawText(PApplet scene) {
        scene.scale((float) Main.arguments.pixelSize);
        scene.textSize(fontSize);
        scene.textLeading(22);
        scene.fill(255);
        scene.text(createLineBreaks(displayText, this.w, scene), textPos.x, textPos.y + scene.textAscent());
        //scene.text(displayText, textPos.x, textPos.y);
        scene.scale((float) (1.0f/Main.arguments.pixelSize));
    }//drawText

    @Override
    public void draw(PApplet scene) {
        update();
        drawText(scene);
    }
}
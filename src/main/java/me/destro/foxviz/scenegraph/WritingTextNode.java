package me.destro.foxviz.scenegraph;

import com.google.common.base.Stopwatch;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.utilities.TextUtilities;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.concurrent.TimeUnit;

import static me.destro.foxviz.utilities.TextUtilities.createLineBreaks;
import static processing.core.PConstants.CORNER;

public class WritingTextNode extends Node {
    // the text and a holder
    String text, displayText;
    String fullTextWithBreakingLines;

    float textLeading;

    //positioning
    PVector bgPos, textPos;
    float w, h;

    //timimng
    Stopwatch timer = Stopwatch.createStarted();
    int wait;

    float fontSize = 16.0f;

    /*************THE CONSTRUCTOR**********/
    //it takes the text, positioning stuff and init everything
    public WritingTextNode(String _tx, PVector _bg, float _w, float fontSize, float textLeading) {
        bgPos   = _bg;
        w       = _w;
        text    = _tx;
        displayText = "";
        this.fontSize = fontSize;
        this.textLeading = textLeading;

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
        scene.textLeading(textLeading);

        if (this.fullTextWithBreakingLines == null) {
            this.fullTextWithBreakingLines = createLineBreaks(this.text, this.w, scene);
        }

        //scene.fill(200, 20, 0, 20);
        //scene.rect(0, 0, this.w, TextUtilities.textHeight(this.fullTextWithBreakingLines, scene));

        scene.fill(255);
        //scene.text(createLineBreaks(displayText, this.w, scene), 0, scene.textAscent());
        //scene.text(displayText, textPos.x, textPos.y);
        scene.text(String.valueOf(TextUtilities.textHeight(createLineBreaks(this.fullTextWithBreakingLines, this.w, scene), scene)), 0, 0);
        scene.scale((float) (1.0f/Main.arguments.pixelSize));
    }//drawText

    public float getTextHeight(PApplet scene) {
        scene.pushStyle();
        scene.textSize(fontSize);
        scene.textLeading(textLeading);

        if (this.fullTextWithBreakingLines == null) {
            this.fullTextWithBreakingLines = createLineBreaks(this.text, this.w, scene);
        }

        float v = TextUtilities.textHeight(createLineBreaks(this.fullTextWithBreakingLines, this.w, scene), scene);
        scene.popStyle();

        return v;
    }
    @Override
    public void draw(PApplet scene) {
        update();
        drawText(scene);
    }
}
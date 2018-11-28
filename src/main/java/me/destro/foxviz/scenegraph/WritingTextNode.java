package me.destro.foxviz.scenegraph;

import com.google.common.base.Stopwatch;
import me.destro.foxviz.Main;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.utilities.TextUtilities;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static me.destro.foxviz.utilities.TextUtilities.computeTextHeight;
import static me.destro.foxviz.utilities.TextUtilities.createLineBreaks;
import static processing.core.PConstants.CORNER;

public class WritingTextNode extends Node {
    PApplet scene;
    // the text and a holder
    String text, displayText;
    String fullTextWithBreakingLines;

    float textLeading;

    //positioning
    PVector bgPos;
    float w, h;

    //timimng
    Stopwatch timer = Stopwatch.createStarted();
    int wait;

    float fontSize;

    public String getText() {
        return this.text;
    }

    /*************THE CONSTRUCTOR**********/
    //it takes the text, positioning stuff and init everything
    public WritingTextNode(PApplet scene, String _tx, PVector _bg, float _w, float fontSize, float textLeading) {
        this.scene = scene;
        this.bgPos   = _bg;
        this.w       = _w;
        this.text    = _tx;
        this.displayText = "";
        this.fontSize = fontSize;
        this.textLeading = textLeading;
        this.fullTextWithBreakingLines = createLineBreaks(this.text, this.w, scene);

        // the speed of "typing"
        wait = 100;
    }

    void update() {
        if (!isFinished() && timer.elapsed(TimeUnit.MILLISECONDS) > wait) {
            displayText = fullTextWithBreakingLines.substring(0, displayText.length()+1);

            timer.stop();
            timer.reset();
            timer.start();
        }
    }

    boolean isFinished() {
        return displayText.length() == text.length();
    }//isFinished

    void drawText() {
        scene.scale((float) Main.arguments.pixelSize);
        scene.textSize(fontSize);
        scene.textLeading(textLeading);
        scene.fill(255);

        //scene.text(createLineBreaks(this.displayText, this.w, scene), 0, scene.textAscent());
        //scene.text(displayText, 0, scene.textAscent());
        scene.text(fullTextWithBreakingLines, 0, scene.textAscent());
        scene.scale((float) (1.0f/Main.arguments.pixelSize));
    }

    public float getTextHeight() {
        scene.pushStyle();
        scene.textSize(fontSize);
        scene.textLeading(textLeading);
        float v = TextUtilities.textHeight(this.fullTextWithBreakingLines, scene);
        scene.popStyle();

        return v;
    }

    public PVector getWordPosition(String word) {
        scene.pushStyle();
        scene.textSize(fontSize);
        scene.textLeading(textLeading);

        int index = TextUtilities.index(fullTextWithBreakingLines, word);

        if (index != -1) {
            int lines_count = (int) fullTextWithBreakingLines.substring(0, index).chars().filter(ch -> ch == '\n').count();

            int last_new_line = (int) fullTextWithBreakingLines.substring(0, index).lastIndexOf('\n');

            int start_index = last_new_line == -1 ? 0 : last_new_line;

            float y = computeTextHeight(scene, lines_count) - scene.textDescent() - scene.textAscent();
            float x = scene.textWidth(fullTextWithBreakingLines.substring(start_index, index));
            scene.popStyle();

            return new PVector(x, y);
        }
        scene.popStyle();

        return null;
    }

    @Override
    public void draw(PApplet scene) {
        update();
        drawText();
    }
}
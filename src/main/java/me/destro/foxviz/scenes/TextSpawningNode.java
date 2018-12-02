package me.destro.foxviz.scenes;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import de.looksgood.ani.Ani;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.data.AiDataStorage;
import me.destro.foxviz.data.PhrasesDataStorage;
import me.destro.foxviz.data.model.AiData;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.scenegraph.WritingTextNode;
import me.destro.foxviz.utilities.MathUtilities;
import me.destro.foxviz.utilities.TextUtilities;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class TextSpawningNode extends Node {
    private Supplier<List<String>> get;
    List<String> phrases;
    List<TopWord> top50;

    Stopwatch insertionStopWatch = Stopwatch.createStarted();
    int insertionWaitTime;

    public TextSpawningNode(Supplier<List<String>> get) {
        this.get = get;
        insertionWaitTime = MathUtilities.random(Configuration.firstScreenMinWaitTime, Configuration.firstScreenMaxWaitTime);
    }


    @Override
    public void draw(PApplet scene) {
        scene.background(Configuration.backgroundColor);
        scene.textFont(Main.firstScreen);

        phrases = get.get();
        top50 = AiDataStorage.getTop50Words();

        if (insertionStopWatch.elapsed(TimeUnit.SECONDS) > insertionWaitTime
                && phrases != null && canGeneratePhrase()) {

            String phrase = MathUtilities.pickValue(phrases);

            if (phrase != null) {
                addTextNode(scene, phrase);

                insertionWaitTime = MathUtilities.random(Configuration.firstScreenMinWaitTime, Configuration.firstScreenMaxWaitTime);
                insertionStopWatch.stop();
                insertionStopWatch.reset();
                insertionStopWatch.start();
            }
        }

        deleteOffscreenNodes();
    }

    private void highlightWords(WritingTextNode textNode) {
        if (top50 == null)
            return;

        for (TopWord word : top50) {
            if (TextUtilities.contain(textNode.getText(), word.word)) {
                PVector position = textNode.getWordPosition(word.word);

                if (position != null) {
                    textNode.addNode(new DrawingNode(scene ->  {
                        scene.noStroke();

                        scene.fill(Configuration.red.getRed(), Configuration.red.getGreen(), Configuration.red.getBlue(), 80);
                        scene.rect((float) (position.x * Main.arguments.pixelSize),
                                (float) (position.y * Main.arguments.pixelSize),
                                (float) (scene.textWidth(word.word) * Main.arguments.pixelSize), 80);
                    }));
                }
            }
        }
    }

    private boolean canGeneratePhrase() {
        for (Node n : this) {
            if (n instanceof WritingTextNode) {
                if (n.getTransformation().y() < 0){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean doOffset;

    private void addTextNode(PApplet scene, String text) {
        float offset = doOffset ? 100 : 0;
        doOffset = !doOffset;

        WritingTextNode textNode = new WritingTextNode(scene, text,
                new PVector(0, 0), (float) ((850.0f - offset)/Main.arguments.pixelSize), 20, 22);

        float textHeight = textNode.getTextHeight();

        float startPosition = (float) (-textHeight*Main.arguments.pixelSize - MathUtilities.random(50, 200)) ;
        float endPosition = (float) 4200;

        textNode.getTransformation().translate(offset, startPosition);


        float space = endPosition - startPosition;
        float time = space / 50;

        Ani.to(textNode.getTransformation(), time, "y", endPosition, Ani.LINEAR);

        highlightWords(textNode);

        this.addNode(textNode);
    }

    private void deleteOffscreenNodes() {
        removeNodeIf(node -> node instanceof TextNode && node.getTransformation().y() > 4150);
    }

}

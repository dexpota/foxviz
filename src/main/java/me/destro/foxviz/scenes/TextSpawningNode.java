package me.destro.foxviz.scenes;

import com.google.common.base.Stopwatch;
import de.looksgood.ani.Ani;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.Main;
import me.destro.foxviz.data.PhrasesDataStorage;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.scenegraph.WritingTextNode;
import me.destro.foxviz.utilities.MathUtilities;
import me.destro.foxviz.utilities.TextUtilities;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class TextSpawningNode extends Node {
    List<String> phrases;

    Stopwatch insertionStopWatch = Stopwatch.createStarted();
    int insertionWaitTime;

    public TextSpawningNode() {
        phrases = new LinkedList<>();

        insertionWaitTime = MathUtilities.random(Configuration.firstScreenMinWaitTime, Configuration.firstScreenMaxWaitTime);
    }

    @Override
    public void draw(PApplet scene) {
        scene.background(Configuration.backgroundColor);

        phrases = PhrasesDataStorage.get();

        if (insertionStopWatch.elapsed(TimeUnit.SECONDS) > insertionWaitTime
                && canGeneratePhrase()) {

            String phrase = MathUtilities.pickValue(phrases);

            if (phrase != null) {
                WritingTextNode textNode = new WritingTextNode(phrase,
                        new PVector(0, 0), (float) (1000.0f/Main.arguments.pixelSize), 22, 22);

                float textHeight = textNode.getTextHeight(scene);
                textNode.getTransformation().translate(0, (float) (-textHeight*Main.arguments.pixelSize));

                Ani.to(textNode.getTransformation(), 60, "y",
                        (float) (4200+(textHeight*Main.arguments.pixelSize)), Ani.LINEAR);

                this.addNode(textNode);

                insertionWaitTime = MathUtilities.random(Configuration.firstScreenMinWaitTime, Configuration.firstScreenMaxWaitTime);
                insertionStopWatch.stop();
                insertionStopWatch.reset();
                insertionStopWatch.start();
            }
        }

        deleteOffscreenNodes();
    }

    private boolean canGeneratePhrase() {
        for (Node n : this) {
            if (n instanceof WritingTextNode) {
                if (((WritingTextNode) n).getTransformation().y() < 0){
                    return false;
                }
            }
        }
        return true;
    }

    private void deleteOffscreenNodes() {
        removeNodeIf(node -> node instanceof TextNode && node.getTransformation().y() > 4150);
    }

}

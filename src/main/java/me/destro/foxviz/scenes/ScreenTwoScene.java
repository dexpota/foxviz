package me.destro.foxviz.scenes;

import com.google.common.base.Stopwatch;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.data.DataStorage;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.utilities.MathUtilities;
import me.destro.foxviz.utilities.Utilities;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import processing.core.PApplet;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenTwoScene extends Node {
    Queue<TopWord> toInsertWords;
    Queue<TopWord> toRemoveWords;

    List<TopWord> onScreen;

    Stopwatch insertionStopWatch = Stopwatch.createStarted();
    int insertionWaitTime;

    Stopwatch removingStopwatch = Stopwatch.createStarted();
    int removingWaitTime;

    public ScreenTwoScene() {
        toInsertWords = new CircularFifoQueue<>(Configuration.secondScreenMaxWords);
        toRemoveWords = new CircularFifoQueue<>(Configuration.secondScreenMaxWords);

        onScreen = new LinkedList<>();

        insertionWaitTime = MathUtilities.random(Configuration.secondScreenMinWaitTime, Configuration.secondScreenMaxWaitTime);
        removingWaitTime = MathUtilities.random(Configuration.secondScreenMinWaitTime, Configuration.secondScreenMaxWaitTime);
    }

    private void pulseSameCategory(String category) {
        for (Node n : this) {
            if (n instanceof TopWordNode) {
                TopWordNode node = (TopWordNode) n;
                if (node.getTopWord().category.equals(category)) {
                    node.pulse();
                }
            }
        }
    }

    @Override
    public void draw(PApplet scene) {

        if (DataStorage.isTop350Updated()) {
            List<TopWord> topWords = DataStorage.getTop350Words();
            updateQueues(topWords);
        }

        if (insertionStopWatch.elapsed(TimeUnit.SECONDS) > insertionWaitTime
                && toInsertWords.peek() != null) {

            generateWordOnScreen(toInsertWords.remove());
            insertionStopWatch.stop();
            insertionStopWatch.reset();
            insertionStopWatch.start();
        }

        if (removingStopwatch.elapsed(TimeUnit.SECONDS) > removingWaitTime &&
                toRemoveWords.peek() != null) {
            removeWord(toRemoveWords.remove());
            removingStopwatch.stop();
            removingStopwatch.reset();
            removingStopwatch.start();
        }

        // TODO if insertion queue is empty?
        if (toInsertWords.isEmpty() && !onScreen.isEmpty()) {
            TopWord topWord = onScreen.get(0);
            toInsertWords.add(topWord);
            toRemoveWords.add(topWord);
        }

        // TODO if we got too many words on screen what happen?
    }

    private void updateQueues(List<TopWord> topWords) {
        // we clear the queue
        toInsertWords.clear();
        toRemoveWords.clear();

        // Everytime we receive the top 350 words we have to check
        // which ones of these words are already on screen? we are not going to insert these.
        Collection<TopWord> newWordsToInsert
                = Utilities.subtract(topWords, onScreen, Comparator.comparing(TopWord::getWord));

        // add these words to the queue
        toInsertWords.addAll(newWordsToInsert);

        // which of the words on the screen are no longer on?
        Collection<TopWord> newWordsToRemove
                = Utilities.subtract(onScreen, topWords, Comparator.comparing(TopWord::getWord));

        toRemoveWords.addAll(newWordsToRemove);
    }

    private void generateWordOnScreen(TopWord topWord) {
        int target_x = MathUtilities.random(0, 2000);
        int target_y = MathUtilities.random(0, 4000);

        TopWordNode n = new TopWordNode(topWord);
        n.move(target_x, target_y);

        pulseSameCategory(topWord.category);

        this.onScreen.add(topWord);
        this.addNode(n);
    }

    private void removeWord(TopWord topWord) {
        for (Node n : this) {
            if (n instanceof TopWordNode) {
                TopWordNode node = (TopWordNode) n;
                if (node.getTopWord().word.equals(topWord.word)) {
                    this.removeNode(node);
                    this.onScreen.removeIf(nn -> nn.word.equals(topWord.word));
                    return;
                }
            }
        }
    }
}

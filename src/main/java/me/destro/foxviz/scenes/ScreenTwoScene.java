package me.destro.foxviz.scenes;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Main;
import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.scenegraph.DrawingNode;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.scenegraph.TransformationNode;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class ScreenTwoScene extends Node {

    class AiWordNode extends Node {
        Node transformationNode;
        Node textNode;
        AiWord aiWord;
        boolean canPulse = false;

        public AiWordNode(AiWord word) {
            transformationNode = new TransformationNode(1000, 2000);
            textNode = new TextNode(word.word);

            aiWord = word;

            this.appendNode(transformationNode)
                    .appendNode(textNode);

        }

        public void move(int target_x, int target_y) {
            Ani.to(transformationNode, 5, "sx:0.2,sy:0.2", Ani.LINEAR);
            Ani.to(transformationNode, 5, String.format("x:%s,y:%s", target_x, target_y), Ani.LINEAR, this, "onEnd:setCanPulse");
        }

        public void pulse() {
            if (canPulse) {
                canPulse = false;

                Ani scalingX = new Ani(transformationNode, 2, 0.5f, "sx", 1.0f, Ani.EXPO_IN_OUT);
                Ani scalingY = new Ani(transformationNode, 2, 0.5f, "sy", 1.0f, Ani.EXPO_IN_OUT, this, "onEnd:setCanPulse");
                // repeat yoyo style (go up and down)
                scalingX.setPlayMode(Ani.YOYO);
                scalingY.setPlayMode(Ani.YOYO);
                // repeat 3 times
                scalingX.repeat(2);
                scalingY.repeat(2);
            }
        }

        public void setCanPulse() {
            canPulse = true;
        }

        @Override
        public void draw(PApplet scene) {

        }
    }

    List<AiWordNode> nodes;

    public ScreenTwoScene() {
        Disposable subscription = Main.ai.generateWord()
                .subscribeOn(Schedulers.computation())
                .subscribe(word -> onWordReceived(word));
        nodes = new ArrayList<>();
    }

    private void onWordReceived(AiWord word) {
        int target_x = MathUtilities.random(0, 2000);
        int target_y = MathUtilities.random(0, 4000);

        AiWordNode n = new AiWordNode(word);
        n.move(target_x, target_y);
        nodes.add(n);

        pulseSameCategory(word.category);

        this.addNode(n);
    }

    private void pulseSameCategory(String category) {
        for (AiWordNode node : nodes) {
            if (node.aiWord.category.equals(category)) {
                node.pulse();
            }
        }
    }

    @Override
    public void draw(PApplet scene) {
    }
}

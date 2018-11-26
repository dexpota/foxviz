package me.destro.foxviz.scenes;

import de.looksgood.ani.Ani;
import me.destro.foxviz.data.DataStorage;
import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.utilities.MathUtilities;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class ScreenTwoScene extends Node {

    class AiWordNode extends Node {
        Node textNode;
        AiWord aiWord;
        boolean canPulse = false;

        public AiWordNode(AiWord word) {
            // TODO make this a configuration, spawning point
            this.transformation.translate(1000, 2000);
            aiWord = word;

            textNode = new TextNode(word.word);
            this.addNode(textNode);
        }

        public void move(int target_x, int target_y) {
            Ani.to(this.transformation, 5, "sx:0.2,sy:0.2", Ani.LINEAR);
            Ani.to(this.transformation, 5, String.format("x:%s,y:%s", target_x, target_y), Ani.LINEAR, this, "onEnd:setCanPulse");
        }

        public void pulse() {
            if (canPulse) {
                canPulse = false;

                //Ani scalingX = new Ani(transformationNode, 2, 0.5f, "sx", 1.0f, Ani.EXPO_IN_OUT);
                //Ani scalingY = new Ani(transformationNode, 2, 0.5f, "sy", 1.0f, Ani.EXPO_IN_OUT, this, "onEnd:setCanPulse");

                Ani[] anis = Ani.to(this.transformation, 2, String.format("sx:%f,sy:%f", 1.0f, 1.0f), Ani.EXPO_IN_OUT, this, "onEnd:setCanPulse");
                anis[0].setPlayMode(Ani.YOYO);
                anis[1].setPlayMode(Ani.YOYO);
                anis[0].repeat(2);
                anis[1].repeat(2);
                // repeat yoyo style (go up and down)
                /*scalingX.setPlayMode(Ani.YOYO);
                scalingY.setPlayMode(Ani.YOYO);
                scalingX.repeat(2);
                scalingY.repeat(2);
                scalingX.start();
                scalingY.start();*/
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
        AiWord aiWord = DataStorage.fetchAiWord();

        if (aiWord != null) {
            onWordReceived(aiWord);
        }
    }
}

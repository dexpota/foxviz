package me.destro.foxviz.scenes;

import de.looksgood.ani.Ani;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.scenegraph.Node;
import me.destro.foxviz.scenegraph.TextNode;
import me.destro.foxviz.utilities.Utilities;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

class TopWordNode extends Node {
    Node textNode;
    TopWord topWord;

    List<Ani[]> movingAnimation;
    Ani[] yoyoAnimation;

    public TopWordNode(TopWord word) {
        // TODO make this a configuration, spawning point
        this.transformation.translate(1000, 2000);

        this.topWord = word;

        textNode = new TextNode(word.word);
        this.addNode(textNode);
    }

    public TopWord getTopWord() {
        return topWord;
    }

    public void move(int target_x, int target_y) {
        if (movingAnimation == null) {
            movingAnimation = new LinkedList<>();

            movingAnimation.add(Ani.to(this.transformation, 5, "sx:0.2,sy:0.2", Ani.LINEAR));
            movingAnimation.add(Ani.to(this.transformation, 5, String.format("x:%s,y:%s", target_x, target_y), Ani.LINEAR));
        }
    }

    /*public void dissapear() {
        if (canPulse) {
            Ani.to(this, 5, "sx:0.2,sy:0.2", Ani.LINEAR, "onEnd:suicide");
        }
    }*/

    public void pulse() {
        if (movingAnimation != null && !Utilities.isAnimationPlaying(movingAnimation)) {

            if (yoyoAnimation == null || (yoyoAnimation != null
                    && !yoyoAnimation[0].isPlaying() && !yoyoAnimation[1].isPlaying())) {
                yoyoAnimation = Ani.to(this.transformation, 2, String.format("sx:%f,sy:%f", 1.0f, 1.0f), Ani.EXPO_IN_OUT);
                yoyoAnimation[0].setPlayMode(Ani.YOYO);
                yoyoAnimation[1].setPlayMode(Ani.YOYO);
                yoyoAnimation[0].repeat(2);
                yoyoAnimation[1].repeat(2);
                yoyoAnimation[0].start();
                yoyoAnimation[1].start();
            }
        }
    }

    @Override
    public void draw(PApplet scene) {

    }
}
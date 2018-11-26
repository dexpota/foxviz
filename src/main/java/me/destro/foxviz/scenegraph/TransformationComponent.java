package me.destro.foxviz.scenegraph;

import processing.core.PApplet;

public class TransformationComponent {
    float x, y;
    float angle;
    float sx, sy;

    public TransformationComponent() {this(0.f, 0.f, 0.f, 1.f, 1.f);}

    public TransformationComponent(float x, float y) {this(x, y, 0.f, 1.f, 1.f);}

    public TransformationComponent(float x, float y, float angle) {this(x, y, angle, 1.f, 1.f);}

    public TransformationComponent(float x, float y, float angle, float sx, float sy) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.sx = sx;
        this.sy = sy;
    }

    public TransformationComponent translate(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public TransformationComponent rotate(float rad) {
        this.angle = rad;
        return this;
    }

    public TransformationComponent scale(float s) {
        this.sx = s;
        this.sy = s;
        return this;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public void apply(PApplet scene) {
        scene.translate(x, y);
        scene.rotate(angle);
        scene.scale(sx, sy);
    }
}

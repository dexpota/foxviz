package me.destro.foxviz.utilities;

import processing.core.PApplet;
import remixlab.dandelion.geom.Frame;
import remixlab.dandelion.geom.Point;
import remixlab.dandelion.geom.Quat;

import java.awt.*;
import java.util.List;
import java.util.Random;

public final class MathUtilities {
    private static Random randomGenerator;

    public static Point rotateVector(Point v, double angle) {
        double cs = Math.cos(angle);
        double sn = Math.sin(angle);

        double px = v.x() * cs - v.y() * sn;
        double py = v.x() * sn + v.y() * cs;
        return new Point((float) px, (float) py);
    }

    public static Point polarToCartesian(double radius, double angle) {
        Point p = new Point();
        p.set((float) (radius * Math.cos(angle)), (float) (radius * Math.sin(angle)));
        return p;
    }

    public static double[] linspace(double min, double max, int points) {
        double[] d = new double[points];
        for (int i = 0; i < points; i++){
            d[i] = min + i * (max - min) / (points - 1);
        }
        return d;
    }

    public static int random(int min, int max) {
        if (randomGenerator == null) {
            randomGenerator = new Random();
        }
        return randomGenerator.nextInt(max) + min;
    }

    public static float randomFloat() {
        if (randomGenerator == null) {
            randomGenerator = new Random();
        }
        return randomGenerator.nextFloat();
    }

    public static void applyTransformation(PApplet scene, Frame var1, boolean is2d) {
        if (is2d) {
            scene.translate(var1.translation().x(), var1.translation().y());
            scene.rotate(var1.rotation().angle());
            scene.scale(var1.scaling(), var1.scaling());
        } else {
            scene.translate(var1.translation().vec[0], var1.translation().vec[1], var1.translation().vec[2]);
            scene.rotate(var1.rotation().angle(), ((Quat)var1.rotation()).axis().vec[0], ((Quat)var1.rotation()).axis().vec[1], ((Quat)var1.rotation()).axis().vec[2]);
            scene.scale(var1.scaling(), var1.scaling(), var1.scaling());
        }
    }

    public static int pickValue(int[] values) {
        int index = random(0, values.length);
        return values[index];
    }

    public static String pickValue(String[] values) {
        int index = random(0, values.length);
        return values[index];
    }

    public static Color pickValue(Color[] values) {
        int index = random(0, values.length);
        return values[index];
    }

    public static String pickValue(List<String> values) {
        if (values.isEmpty())
            return null;
        int index = random(0, values.size());
        return values.get(index);
    }

    public static Color generateRandomColor(Color mix) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        if (mix != null) {
            red = (red + mix.getRed()) / 2;
            green = (green + mix.getGreen()) / 2;
            blue = (blue + mix.getBlue()) / 2;
        }

        Color color = new Color(red, green, blue);
        return color;
    }
}

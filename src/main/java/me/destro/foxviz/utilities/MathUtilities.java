package me.destro.foxviz.utilities;

import java.util.Random;

public final class MathUtilities {
    private static Random randomGenerator;

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
}

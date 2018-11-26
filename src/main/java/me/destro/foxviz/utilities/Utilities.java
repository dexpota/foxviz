package me.destro.foxviz.utilities;

import de.looksgood.ani.Ani;

import java.util.*;

public class Utilities {
    public static <Type> Collection<Type> subtract(Collection<Type> a, Collection<Type> b, Comparator<Type> c) {
        Set<Type> subtrahend = new TreeSet<Type>(c);
        subtrahend.addAll(b);
        Collection<Type> result = new ArrayList<Type>();
        for (Type item: a) {
            if (!subtrahend.contains(item)) result.add(item);
        }
        return result;
    }

    public static boolean isAnimationPlaying(List<Ani[]> animations) {
        for (Ani[] anis : animations) {
            for (Ani ani : anis) {
                if (ani.isPlaying())
                    return true;
            }
        }
        return false;
    }
}

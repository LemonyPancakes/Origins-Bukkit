package me.lemonypancakes.originsbukkit.util;

import java.util.Random;

public class ProbabilityUtil {

    public static boolean getChance(float percentage) {
        return new Random().nextInt((int) (percentage * 100)) == 0;
    }
}

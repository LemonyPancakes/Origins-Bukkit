package me.lemonypancakes.bukkit.origins.util;

import java.util.Random;

public class ProbabilityUtils {

    public static boolean getChance(float percentage) {
        return new Random().nextInt((int) (percentage * 100)) == 0;
    }
}

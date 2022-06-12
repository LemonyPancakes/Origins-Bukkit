package me.lemonypancakes.originsbukkit.util;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class EntityUtils {

    public static boolean isBeingRainedOn(Entity entity) {
        Location location = entity.getLocation();
        Block block = location.getBlock();
        Biome biome = block.getBiome();


        return false;
    }

    public static boolean isBeingSnowedOn(Entity entity) {
        entity.getLocation().getBlock().getBiome().getKey();
        return false;
    }

    public static boolean isInShade(Entity entity) {
        Location location = entity.getLocation();

        return (!(location.getBlockY() > entity.getWorld().getHighestBlockAt(location).getLocation().getBlockY()));
    }
}

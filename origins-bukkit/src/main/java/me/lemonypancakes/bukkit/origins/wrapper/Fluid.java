package me.lemonypancakes.bukkit.origins.wrapper;

import org.bukkit.block.Block;

public class Fluid {

    private final Block block;

    public Fluid(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}

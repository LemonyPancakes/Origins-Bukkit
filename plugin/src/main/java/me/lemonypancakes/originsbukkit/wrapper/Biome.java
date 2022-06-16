package me.lemonypancakes.originsbukkit.wrapper;

import org.bukkit.entity.Entity;

public class Biome {

    private final Entity entity;

    public Biome(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}

package me.lemonypancakes.originsbukkit.util;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.EnderPearl;

public enum Projectile {
    ABSTRACT_ARROW(AbstractArrow.class),
    ENDER_PEARL(EnderPearl.class);

    private final Class<? extends org.bukkit.entity.Projectile> aClass;

    Projectile(Class<? extends org.bukkit.entity.Projectile> aClass) {
        this.aClass = aClass;
    }

    public Class<? extends org.bukkit.entity.Projectile> getaClass() {
        return aClass;
    }
}

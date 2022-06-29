package me.lemonypancakes.bukkit.origins.wrapper;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage {

    private final double amount;
    private final Entity entity;
    private final EntityDamageEvent.DamageCause damageCause;

    public Damage(double amount, Entity entity, EntityDamageEvent.DamageCause damageCause) {
        this.amount = amount;
        this.entity = entity;
        this.damageCause = damageCause;
    }

    public double getAmount() {
        return amount;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }
}

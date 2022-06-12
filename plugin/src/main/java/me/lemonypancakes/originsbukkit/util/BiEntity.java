package me.lemonypancakes.originsbukkit.util;

import org.bukkit.entity.Entity;

import java.util.Objects;

public class BiEntity {

    private Entity actor;
    private Entity target;

    public BiEntity(Entity actor, Entity target) {
        this.actor = actor;
        this.target = target;
    }

    public Entity getActor() {
        return actor;
    }

    public void setActor(Entity actor) {
        this.actor = actor;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiEntity)) return false;
        BiEntity biEntity = (BiEntity) o;
        return Objects.equals(getActor(), biEntity.getActor()) && Objects.equals(getTarget(), biEntity.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActor(), getTarget());
    }

    @Override
    public String toString() {
        return "BiEntity{" +
                "actor=" + actor +
                ", target=" + target +
                '}';
    }
}

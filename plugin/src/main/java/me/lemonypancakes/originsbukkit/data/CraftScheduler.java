package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.Scheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class CraftScheduler implements Scheduler {

    private Identifier identifier;
    private BukkitTask bukkitTask;

    public CraftScheduler(Identifier identifier, BukkitTask bukkitTask) {
        this.identifier = identifier;
        this.bukkitTask = bukkitTask;
    }

    public CraftScheduler(Identifier identifier) {
        this(identifier, null);
    }

    public CraftScheduler() {}

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public BukkitTask getBukkitTask() {
        return bukkitTask;
    }

    public void setBukkitTask(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftScheduler)) return false;
        CraftScheduler that = (CraftScheduler) o;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getBukkitTask(), that.getBukkitTask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getBukkitTask());
    }

    @Override
    public String toString() {
        return "SchedulerContainer{" +
                "identifier=" + identifier +
                ", bukkitTask=" + bukkitTask +
                '}';
    }
}

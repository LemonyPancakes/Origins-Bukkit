package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Scheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class SchedulerContainer implements Scheduler {

    private Identifier identifier;
    private BukkitTask bukkitTask;

    public SchedulerContainer(Identifier identifier,
                              BukkitTask bukkitTask) {
        this.identifier = identifier;
        this.bukkitTask = bukkitTask;
    }

    public SchedulerContainer(Identifier identifier) {
        this(identifier, null);
    }

    public SchedulerContainer() {}

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
        if (!(o instanceof SchedulerContainer)) return false;
        SchedulerContainer that = (SchedulerContainer) o;
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

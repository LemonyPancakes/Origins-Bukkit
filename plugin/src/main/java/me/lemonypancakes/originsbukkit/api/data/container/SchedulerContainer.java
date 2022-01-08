package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Scheduler;
import org.bukkit.scheduler.BukkitTask;

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
}

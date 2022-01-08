package me.lemonypancakes.originsbukkit.api.data.type;

import org.bukkit.scheduler.BukkitTask;

public interface Scheduler {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);

    BukkitTask getBukkitTask();

    void setBukkitTask(BukkitTask bukkitTask);
}

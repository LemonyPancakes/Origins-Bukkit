package me.lemonypancakes.bukkit.origins;

import org.bukkit.scheduler.BukkitTask;

public interface Scheduler extends Identifiable {

    BukkitTask getBukkitTask();

    void setBukkitTask(BukkitTask bukkitTask);
}

package me.lemonypancakes.originsbukkit.listeners.world;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.events.world.WorldDayAndNightCycleEvent;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class DayAndNightCycleListener implements Listener {

    private final ListenerHandler listenerHandler;

    public DayAndNightCycleListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        Bukkit.getPluginManager().registerEvents(this, getListenerHandler().getPlugin());
        init();
    }

    private final Map<World, WorldDayAndNightCycleEvent.Time> worldTimeMap = new HashMap<>();

    private void init() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    WorldDayAndNightCycleEvent.Time time;

                    if (!worldTimeMap.containsKey(world)) {

                        if (world.getTime() > 0 && world.getTime() < 13000) {
                            time = WorldDayAndNightCycleEvent.Time.DAY;
                        } else {
                            time = WorldDayAndNightCycleEvent.Time.NIGHT;
                        }
                        worldTimeMap.put(world, time);
                        Bukkit.getScheduler().runTask(OriginsBukkit.getPlugin(), bukkitTask -> {
                            WorldDayAndNightCycleEvent event = new WorldDayAndNightCycleEvent(world, time);
                            Bukkit.getPluginManager().callEvent(event);
                        });
                    } else {
                        if (world.getTime() > 0 && world.getTime() < 13000) {
                            if (worldTimeMap.get(world) == WorldDayAndNightCycleEvent.Time.NIGHT) {
                                time = WorldDayAndNightCycleEvent.Time.DAY;
                                worldTimeMap.put(world, time);
                                Bukkit.getScheduler().runTask(OriginsBukkit.getPlugin(), bukkitTask -> {
                                    WorldDayAndNightCycleEvent event = new WorldDayAndNightCycleEvent(world, time);
                                    Bukkit.getPluginManager().callEvent(event);
                                });
                            }
                        } else {
                            if (worldTimeMap.get(world) == WorldDayAndNightCycleEvent.Time.DAY) {
                                time = WorldDayAndNightCycleEvent.Time.NIGHT;
                                worldTimeMap.put(world, time);
                                Bukkit.getScheduler().runTask(OriginsBukkit.getPlugin(), bukkitTask -> {
                                    WorldDayAndNightCycleEvent event = new WorldDayAndNightCycleEvent(world, time);
                                    Bukkit.getPluginManager().callEvent(event);
                                });
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getListenerHandler().getPlugin(), 0, 4);
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }
}

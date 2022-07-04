/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.listener.world;

import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.event.world.WorldDayAndNightCycleEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class DayAndNightCycleListener {

    private final OriginsBukkitPlugin plugin;
    private final Map<World, WorldDayAndNightCycleEvent.Time> worldTimeMap = new HashMap<>();

    public DayAndNightCycleListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
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
                        WorldDayAndNightCycleEvent worldDayAndNightCycleEvent = new WorldDayAndNightCycleEvent(world, time);
                        Bukkit.getPluginManager().callEvent(worldDayAndNightCycleEvent);
                    } else {
                        if (world.getTime() > 0 && world.getTime() < 13000) {
                            if (worldTimeMap.get(world) == WorldDayAndNightCycleEvent.Time.NIGHT) {
                                time = WorldDayAndNightCycleEvent.Time.DAY;
                                worldTimeMap.put(world, time);
                                WorldDayAndNightCycleEvent worldDayAndNightCycleEvent = new WorldDayAndNightCycleEvent(world, time);
                                Bukkit.getPluginManager().callEvent(worldDayAndNightCycleEvent);
                            }
                        } else {
                            if (worldTimeMap.get(world) == WorldDayAndNightCycleEvent.Time.DAY) {
                                time = WorldDayAndNightCycleEvent.Time.NIGHT;
                                worldTimeMap.put(world, time);
                                WorldDayAndNightCycleEvent worldDayAndNightCycleEvent = new WorldDayAndNightCycleEvent(world, time);
                                Bukkit.getPluginManager().callEvent(worldDayAndNightCycleEvent);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0, 1);
    }
}

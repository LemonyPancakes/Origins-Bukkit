/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
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
package me.lemonypancakes.originsbukkit.listeners.events.player;

import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerFluidInteractEvent;
import me.lemonypancakes.originsbukkit.listeners.events.CustomEventListenerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AsyncPlayerFluidInteract {

    private final CustomEventListenerHandler customEventListenerHandler;
    private final Map<UUID, Boolean> players = new HashMap<>();

    public CustomEventListenerHandler getCustomEventListenerHandler() {
        return customEventListenerHandler;
    }

    public Map<UUID, Boolean> getPlayers() {
        return players;
    }

    public AsyncPlayerFluidInteract(CustomEventListenerHandler customEventListenerHandler) {
        this.customEventListenerHandler = customEventListenerHandler;
        init();
    }

    private void init() {
        registerCustomListener();
    }

    private void registerCustomListener() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (AsyncPlayerFluidInteractEvent.getHandlerList().getRegisteredListeners().length != 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Location location = player.getLocation();
                        Block block = location.getBlock();
                        Material material = block.getType();
                        boolean water = player.isInWater();
                        boolean waterCauldron = material == Material.WATER_CAULDRON;
                        boolean rain = player.getWorld().hasStorm() && location.getBlockY() > player.getWorld()
                                .getHighestBlockAt(location)
                                .getLocation()
                                .getBlockY();
                        boolean lava = material == Material.LAVA || location
                                .add(0, 1, 0)
                                .getBlock()
                                .getType() == Material.LAVA;
                        boolean lavaCauldron = material == Material.LAVA_CAULDRON;
                        AsyncPlayerFluidInteractEvent asyncPlayerFluidInteractEvent;

                        if (water || waterCauldron || rain) {
                            asyncPlayerFluidInteractEvent
                                    = new AsyncPlayerFluidInteractEvent(player, FluidType.WATER);

                            Bukkit.getPluginManager().callEvent(asyncPlayerFluidInteractEvent);
                        }
                        if (lava || lavaCauldron) {
                            asyncPlayerFluidInteractEvent
                                    = new AsyncPlayerFluidInteractEvent(player, FluidType.LAVA);

                            Bukkit.getPluginManager().callEvent(asyncPlayerFluidInteractEvent);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getCustomEventListenerHandler()
                        .getListenerHandler()
                        .getPlugin(), 0L, 1L);
    }

    public enum FluidType {
        WATER,
        LAVA
    }
}

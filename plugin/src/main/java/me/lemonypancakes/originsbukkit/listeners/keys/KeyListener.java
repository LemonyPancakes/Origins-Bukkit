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
package me.lemonypancakes.originsbukkit.listeners.keys;

import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginAbilityUseEvent;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

/**
 * The type Key listener.
 *
 * @author LemonyPancakes
 */
public class KeyListener implements Listener {

    private final ListenerHandler listenerHandler;

    /**
     * Gets listener handler.
     *
     * @return the listener handler
     */
    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    /**
     * Instantiates a new Key listener.
     *
     * @param listenerHandler the listener handler
     */
    public KeyListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        getListenerHandler()
                .getPlugin()
                .getServer()
                .getPluginManager()
                .registerEvents(this, getListenerHandler().getPlugin());
    }

    /**
     * On ability use.
     *
     * @param event the event
     */
    @EventHandler
    private void onAbilityUse(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (player.isSneaking()) {
            if (getListenerHandler().getPlugin().getOrigins().containsKey(playerOrigin)) {
                getListenerHandler().getPlugin().getOrigins().forEach((key, value) -> {
                    if (Objects.equals(key, playerOrigin)) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                AsyncPlayerOriginAbilityUseEvent asyncPlayerOriginAbilityUseEvent = new AsyncPlayerOriginAbilityUseEvent(player, key);
                                Bukkit.getPluginManager().callEvent(asyncPlayerOriginAbilityUseEvent);
                            }
                        }.runTaskAsynchronously(getListenerHandler().getPlugin());
                        event.setCancelled(true);
                    }
                });
            }
        }
    }
}

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

import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerJoinEvent;
import me.lemonypancakes.originsbukkit.listeners.events.CustomEventListenerHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Async player join.
 *
 * @author LemonyPancakes
 */
public class AsyncPlayerJoin implements Listener {

    private final CustomEventListenerHandler customEventListenerHandler;
    private final Map<UUID, BukkitTask> players = new HashMap<>();
    private static final int MAX_ATTEMPTS = 1200;

    /**
     * Gets custom event listener handler.
     *
     * @return the custom event listener handler
     */
    public CustomEventListenerHandler getCustomEventListenerHandler() {
        return customEventListenerHandler;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public Map<UUID, BukkitTask> getPlayers() {
        return players;
    }

    /**
     * Gets max attempts.
     *
     * @return the max attempts
     */
    public static int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    /**
     * Instantiates a new Async player join.
     *
     * @param customEventListenerHandler the custom event listener handler
     */
    public AsyncPlayerJoin(CustomEventListenerHandler customEventListenerHandler) {
        this.customEventListenerHandler = customEventListenerHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        Bukkit.getPluginManager().registerEvents(
                this, getCustomEventListenerHandler()
                        .getListenerHandler()
                        .getPlugin());
    }

    /**
     * Async player join event.
     *
     * @param event the event
     */
    @EventHandler
    public void asyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            if (AsyncPlayerJoinEvent.getHandlerList().getRegisteredListeners().length != 0) {
                if (getPlayers().containsKey(event.getUniqueId())) {
                    getPlayers().remove(event.getUniqueId());
                    getPlayers().get(event.getUniqueId()).cancel();
                }
                BukkitTask bukkitTask = new BukkitRunnable() {
                    int attempts = 0;

                    @Override
                    public void run() {
                        Player player = Bukkit.getPlayer(event.getUniqueId());

                        if (attempts < getMaxAttempts()) {
                            if (player != null) {
                                AsyncPlayerJoinEvent asyncPlayerJoinEvent
                                        = new AsyncPlayerJoinEvent(player);

                                getPlayers().remove(event.getUniqueId());
                                Bukkit.getPluginManager().callEvent(asyncPlayerJoinEvent);
                                cancel();
                            }
                        } else {
                            getPlayers().remove(event.getUniqueId());
                            cancel();
                        }
                        attempts++;
                    }
                }.runTaskTimerAsynchronously(getCustomEventListenerHandler()
                        .getListenerHandler()
                        .getPlugin(), 0L, 1L);
                getPlayers().put(event.getUniqueId(), bukkitTask);
            }
        }
    }
}

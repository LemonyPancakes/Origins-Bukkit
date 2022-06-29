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
package me.lemonypancakes.bukkit.origins.listener.entity.player;

import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.event.entity.player.PlayerKeyEvent;
import me.lemonypancakes.bukkit.origins.util.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItemsEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public PlayerSwapHandItemsEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    private void onPlayerInteractAtEntity(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking()) {
            PlayerKeyEvent playerKeyEvent = new PlayerKeyEvent(player, Key.SECONDARY);
            Bukkit.getPluginManager().callEvent(playerKeyEvent);
        } else {
            PlayerKeyEvent playerKeyEvent = new PlayerKeyEvent(player, Key.PRIMARY);
            Bukkit.getPluginManager().callEvent(playerKeyEvent);
        }
        PlayerKeyEvent playerKeyEvent = new PlayerKeyEvent(player, Key.EITHER);
        Bukkit.getPluginManager().callEvent(playerKeyEvent);
        event.setCancelled(true);
    }
}

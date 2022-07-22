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

import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.menu.CraftOriginLayerMenu;
import me.lemonypancakes.bukkit.origins.menu.Menu;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class PlayerRespawnEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public PlayerRespawnEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Player player = event.getPlayer();
                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                if (originPlayer != null) {
                    Map<OriginLayer, Origin> origins = originPlayer.getOrigins();

                    if (origins != null) {
                        for (Map.Entry<OriginLayer, Origin> originLayerOriginEntry : origins.entrySet()) {
                            OriginLayer originLayer = originLayerOriginEntry.getKey();

                            if (originLayer != null) {
                                if (originLayer.isEnabled() && !originLayer.isHidden()) {
                                    Origin origin = originLayerOriginEntry.getValue();

                                    if (origin == null) {
                                        Menu menu = originLayer.getMenu();

                                        if (menu != null) {
                                            InventoryView inventoryView = player.getOpenInventory();
                                            Inventory topInventory = inventoryView.getTopInventory();
                                            InventoryHolder inventoryHolder = topInventory.getHolder();

                                            if (inventoryHolder != null) {
                                                if (inventoryHolder instanceof CraftOriginLayerMenu) {
                                                    CraftOriginLayerMenu craftOriginLayerMenu = (CraftOriginLayerMenu) inventoryHolder;

                                                    craftOriginLayerMenu.addExemption(player);
                                                    if (craftOriginLayerMenu != menu) {
                                                        menu.open(player);
                                                        craftOriginLayerMenu.removeExemption(player);
                                                    }
                                                } else {
                                                    if (inventoryHolder != menu) {
                                                        menu.open(player);
                                                    }
                                                }
                                            } else {
                                                menu.open(player);
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTask(plugin.getJavaPlugin());
    }
}

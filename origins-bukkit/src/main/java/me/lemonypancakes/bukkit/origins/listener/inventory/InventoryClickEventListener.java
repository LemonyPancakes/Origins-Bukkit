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
package me.lemonypancakes.bukkit.origins.listener.inventory;

import me.lemonypancakes.bukkit.origins.Origin;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.storage.other.Misc;
import me.lemonypancakes.bukkit.origins.event.entity.player.PlayerOriginChooseEvent;
import me.lemonypancakes.bukkit.origins.event.entity.player.PlayerOriginSetEvent;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InventoryClickEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public InventoryClickEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        Player player = (Player) humanEntity;
        ItemStack clickedItem = event.getCurrentItem();
        Map<UUID, Integer> viewers = Misc.VIEWERS;
        List<Inventory> guis = Misc.GUIS;
        Map<String, Inventory> originInfoGuis = Misc.ORIGINS_INFO_GUI;

        if (guis.contains(event.getInventory())) {

            event.setCancelled(true);
            if (clickedItem != null) {
                if (!clickedItem.getType().isAir()) {
                    if (event.getRawSlot() == 48) {
                        if (guis.indexOf(event.getClickedInventory()) != 0) {
                            player.openInventory(guis.get(guis.indexOf(event.getClickedInventory()) - 1));
                            if (viewers.containsKey(player.getUniqueId())) {
                                viewers.put(player.getUniqueId(), viewers.get(player.getUniqueId()) - 1);
                                player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                    if (event.getRawSlot() == 49) {
                        player.kickPlayer(null);
                    }
                    if (event.getRawSlot() == 50) {
                        if (!guis.get(guis.indexOf(event.getClickedInventory())).equals(guis.get(guis.size() - 1))) {
                            player.openInventory(guis.get(guis.indexOf(event.getClickedInventory()) + 1));
                            if (viewers.containsKey(player.getUniqueId())) {
                                viewers.put(player.getUniqueId(), viewers.get(player.getUniqueId()) + 1);
                                player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                    if (event.getRawSlot() == 22){
                        if (clickedItem.getItemMeta() != null) {
                            String identifierString = clickedItem.getItemMeta().getLocalizedName();
                            Identifier identifier = new Identifier(
                                    identifierString.split(":")[0],
                                    identifierString.split(":")[1]
                            );

                            if (!identifier.toString().equals("origins-bukkit:dummy_origin")) {
                                if (plugin.getRegistry().hasOrigin(identifier)) {
                                    Origin origin = plugin.getRegistry().getOrigin(identifier);

                                    if (origin != null) {
                                        PlayerOriginChooseEvent playerOriginChooseEvent = new PlayerOriginChooseEvent(player, origin);

                                        Bukkit.getPluginManager().callEvent(playerOriginChooseEvent);
                                        if (!playerOriginChooseEvent.isCancelled()) {
                                            PlayerOriginSetEvent playerOriginSetEvent = plugin.getOriginPlayer(player).setOrigin(playerOriginChooseEvent.getOrigin());

                                            if (playerOriginSetEvent != null) {
                                                if (!playerOriginSetEvent.isCancelled()) {
                                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
                                                } else {
                                                    player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                                                }
                                            }
                                        } else {
                                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                                        }
                                    }
                                }
                            } else {
                                player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                            }
                        }
                    }
                }
            }
        }
        if (originInfoGuis.containsValue(event.getInventory())) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
        }
    }
}

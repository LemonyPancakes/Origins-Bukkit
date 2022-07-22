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
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class PlayerInteractEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public PlayerInteractEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        InventoryView inventoryView = player.getOpenInventory();
        Inventory topInventory = inventoryView.getTopInventory();
        InventoryHolder inventoryHolder = topInventory.getHolder();

        if (inventoryHolder != null) {
            if (!(inventoryHolder instanceof CraftOriginLayerMenu)) {
                return;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onOrbOfOriginInteract(PlayerInteractEvent event) {
        if (event.useItemInHand() != Event.Result.DENY) {
            Player player = event.getPlayer();
            OriginPlayer originPlayer = plugin.getOriginPlayer(player);

            if (originPlayer != null) {
                Action action = event.getAction();

                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    ItemStack itemStack = event.getItem();

                    if (itemStack != null) {
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        if (itemMeta != null) {
                            String data = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:custom_item", PersistentDataType.STRING);

                            if (data != null) {
                                if (data.equals("origins-bukkit:orb_of_origin")) {
                                    if (originPlayer.getOrigins().values().stream().anyMatch(Objects::nonNull)) {
                                        originPlayer.getOrigins().forEach((originLayer, origin) -> originPlayer.setOrigin(originLayer, null));
                                        itemStack.setAmount(itemStack.getAmount() - 1);
                                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1f, 0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

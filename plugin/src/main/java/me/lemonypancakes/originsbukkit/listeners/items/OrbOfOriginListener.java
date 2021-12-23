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
package me.lemonypancakes.originsbukkit.listeners.items;

import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * The type Orb of origin listener.
 *
 * @author LemonyPancakes
 */
public class OrbOfOriginListener implements Listener {

    private final ItemListenerHandler itemListenerHandler;

    /**
     * Gets item listener handler.
     *
     * @return the item listener handler
     */
    public ItemListenerHandler getItemListenerHandler() {
        return itemListenerHandler;
    }

    /**
     * Instantiates a new Orb of origin listener.
     *
     * @param itemListenerHandler the item listener handler
     */
    public OrbOfOriginListener(ItemListenerHandler itemListenerHandler) {
        this.itemListenerHandler = itemListenerHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        getItemListenerHandler()
                .getListenerHandler()
                .getPlugin()
                .getServer()
                .getPluginManager()
                .registerEvents(this, getItemListenerHandler()
                        .getListenerHandler()
                        .getPlugin());
    }

    /**
     * Player right click.
     *
     * @param event the event
     */
    @EventHandler
    private void playerRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (playerOrigin != null) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (itemStack != null && itemStack.isSimilar(getItemListenerHandler()
                        .getListenerHandler()
                        .getPlugin()
                        .getItemHandler()
                        .getOrbOfOrigin()
                        .getItemStack())) {
                    getItemListenerHandler()
                            .getListenerHandler()
                            .getPlugin()
                            .getStorageHandler()
                            .getOriginsPlayerData()
                            .deleteOriginsPlayerData(playerUUID);
                    player.getInventory()
                            .getItemInMainHand()
                            .setAmount(player.getInventory()
                                    .getItemInMainHand()
                                    .getAmount() - 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1f, 0);
                }
            }
        }
    }
}

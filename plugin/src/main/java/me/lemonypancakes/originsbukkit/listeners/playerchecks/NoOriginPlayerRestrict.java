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
package me.lemonypancakes.originsbukkit.listeners.playerchecks;

import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.wrappers.OriginsPlayerDataWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * The type No origin player restrict.
 *
 * @author LemonyPancakes
 */
public class NoOriginPlayerRestrict implements Listener {

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
     * Instantiates a new No origin player restrict.
     *
     * @param listenerHandler the listener handler
     */
    public NoOriginPlayerRestrict(ListenerHandler listenerHandler) {
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
     * Restrict player movement.
     *
     * @param player the player
     */
    public void restrictPlayerMovement(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, false));
    }

    /**
     * Unrestrict player movement.
     *
     * @param player the player
     */
    public void unrestrictPlayerMovement(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW);
    }

    /**
     * On no origin player damage.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = ((Player) entity).getPlayer();
            OriginPlayer originPlayer = new OriginPlayer(player);
            OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

            if (originsPlayerDataWrapper == null) {
                event.setCancelled(true);
            }
        }

    }

    /**
     * On no origin player death.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            if (player != null) {
                player.spigot().respawn();
            }
        }
    }

    /**
     * On no origin player respawn.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            getListenerHandler().getPlayerOriginChecker().openOriginPickerGui(player);
            restrictPlayerMovement(player);
        }
    }

    /**
     * On no origin player item pickup.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerItemPickup(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = ((Player) entity).getPlayer();
            OriginPlayer originPlayer = new OriginPlayer(player);
            OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

            if (originsPlayerDataWrapper == null) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * On no origin player block break.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            event.setCancelled(true);
        }
    }

    /**
     * On no origin player block place.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            event.setCancelled(true);
        }
    }

    /**
     * On no origin player interact.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            event.setCancelled(true);
        }
    }

    /**
     * On no origin player interact at entity.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            event.setCancelled(true);
        }
    }

    /**
     * On no origin player interact entity.
     *
     * @param event the event
     */
    @EventHandler
    private void onNoOriginPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            event.setCancelled(true);
        }
    }
}

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
package me.lemonypancakes.originsbukkit.listeners;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.listeners.player.*;
import me.lemonypancakes.originsbukkit.listeners.world.DayAndNightCycleListener;

public class ListenerHandler {

    private final OriginsBukkit plugin;
    private final JoinEventListener joinEventListener;
    private final QuitEventListener quitEventListener;
    private final InventoryClickEventListener inventoryClickEventListener;
    private final InventoryCloseEventListener inventoryCloseEventListener;
    private final DayAndNightCycleListener dayAndNightCycleListener;
    private final InteractEventListener interactEventListener;
    private final EntityDamageEventListener entityDamageEventListener;
    private final EntityPickupItemEventListener entityPickupItemEventListener;
    private final BlockBreakEventListener blockBreakEventListener;
    private final BlockPlaceEventListener blockPlaceEventListener;
    private final PlayerInteractEventListener playerInteractEventListener;
    private final PlayerInteractAtEntityEventListener playerInteractAtEntityEventListener;
    private final PlayerInteractEntityEventListener playerInteractEntityEventListener;

    public ListenerHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        this.joinEventListener = new JoinEventListener(this);
        this.quitEventListener = new QuitEventListener(this);
        this.inventoryClickEventListener = new InventoryClickEventListener(this);
        this.inventoryCloseEventListener = new InventoryCloseEventListener(this);
        this.dayAndNightCycleListener = new DayAndNightCycleListener(this);
        this.interactEventListener = new InteractEventListener(this);
        this.entityDamageEventListener = new EntityDamageEventListener(this);
        this.entityPickupItemEventListener = new EntityPickupItemEventListener(this);
        this.blockBreakEventListener = new BlockBreakEventListener(this);
        this.blockPlaceEventListener = new BlockPlaceEventListener(this);
        this.playerInteractEventListener = new PlayerInteractEventListener(this);
        this.playerInteractAtEntityEventListener = new PlayerInteractAtEntityEventListener(this);
        this.playerInteractEntityEventListener = new PlayerInteractEntityEventListener(this);
    }

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public JoinEventListener getJoinEventListener() {
        return joinEventListener;
    }

    public QuitEventListener getQuitEventListener() {
        return quitEventListener;
    }

    public InventoryClickEventListener getInventoryClickEventListener() {
        return inventoryClickEventListener;
    }

    public InventoryCloseEventListener getInventoryCloseEventListener() {
        return inventoryCloseEventListener;
    }

    public DayAndNightCycleListener getDayAndNightCycleListener() {
        return dayAndNightCycleListener;
    }

    public InteractEventListener getInteractEventListener() {
        return interactEventListener;
    }

    public EntityDamageEventListener getEntityDamageEventListener() {
        return entityDamageEventListener;
    }

    public EntityPickupItemEventListener getEntityPickupItemEventListener() {
        return entityPickupItemEventListener;
    }

    public BlockBreakEventListener getBlockBreakEventListener() {
        return blockBreakEventListener;
    }

    public BlockPlaceEventListener getBlockPlaceEventListener() {
        return blockPlaceEventListener;
    }

    public PlayerInteractEventListener getPlayerInteractEventListener() {
        return playerInteractEventListener;
    }

    public PlayerInteractAtEntityEventListener getPlayerInteractAtEntityEventListener() {
        return playerInteractAtEntityEventListener;
    }

    public PlayerInteractEntityEventListener getPlayerInteractEntityEventListener() {
        return playerInteractEntityEventListener;
    }
}

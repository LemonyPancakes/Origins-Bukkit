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
import me.lemonypancakes.originsbukkit.listeners.player.InventoryClickEventListener;
import me.lemonypancakes.originsbukkit.listeners.player.InventoryCloseEventListener;
import me.lemonypancakes.originsbukkit.listeners.player.JoinEventListener;
import me.lemonypancakes.originsbukkit.listeners.player.QuitEventListener;

public class ListenerHandler {

    private final OriginsBukkit plugin;
    private final JoinEventListener joinEventListener;
    private final QuitEventListener quitEventListener;
    private final InventoryClickEventListener inventoryClickEventListener;
    private final InventoryCloseEventListener inventoryCloseEventListener;

    public ListenerHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        this.joinEventListener = new JoinEventListener(this);
        this.quitEventListener = new QuitEventListener(this);
        this.inventoryClickEventListener = new InventoryClickEventListener(this);
        this.inventoryCloseEventListener = new InventoryCloseEventListener(this);
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
}

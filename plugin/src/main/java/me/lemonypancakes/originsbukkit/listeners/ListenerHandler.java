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
import me.lemonypancakes.originsbukkit.listeners.events.CustomEventListenerHandler;
import me.lemonypancakes.originsbukkit.listeners.items.ItemListenerHandler;
import me.lemonypancakes.originsbukkit.listeners.keys.KeyListener;
import me.lemonypancakes.originsbukkit.listeners.origins.OriginListenerHandler;
import me.lemonypancakes.originsbukkit.listeners.playerchecks.NoOriginPlayerRestrict;
import me.lemonypancakes.originsbukkit.listeners.playerchecks.PlayerOriginChecker;

public class ListenerHandler {

    private final OriginsBukkit plugin;
    private CustomEventListenerHandler customEventListenerHandler;
    private OriginListenerHandler originListenerHandler;
    private ItemListenerHandler itemListenerHandler;
    private NoOriginPlayerRestrict noOriginPlayerRestrict;
    private PlayerOriginChecker playerOriginChecker;
    private KeyListener keyListener;

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public CustomEventListenerHandler getCustomEventListenerHandler() {
        return customEventListenerHandler;
    }

    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    public ItemListenerHandler getItemListenerHandler() {
        return itemListenerHandler;
    }

    public NoOriginPlayerRestrict getNoOriginPlayerRestrict() {
        return noOriginPlayerRestrict;
    }

    public PlayerOriginChecker getPlayerOriginChecker() {
        return playerOriginChecker;
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public ListenerHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        customEventListenerHandler = new CustomEventListenerHandler(this);
        originListenerHandler = new OriginListenerHandler(this);
        itemListenerHandler = new ItemListenerHandler(this);
        noOriginPlayerRestrict = new NoOriginPlayerRestrict(this);
        playerOriginChecker = new PlayerOriginChecker(this);
        keyListener = new KeyListener(this);
    }
}

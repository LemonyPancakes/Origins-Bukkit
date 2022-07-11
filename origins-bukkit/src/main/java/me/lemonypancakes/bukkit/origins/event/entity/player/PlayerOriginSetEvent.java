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
package me.lemonypancakes.bukkit.origins.event.entity.player;

import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class PlayerOriginSetEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final OriginLayer originLayer;
    private final Origin oldOrigin;
    private Origin newOrigin;
    private boolean isCancelled;

    public PlayerOriginSetEvent(Player player, OriginLayer originLayer, Origin oldOrigin, Origin newOrigin) {
        this.player = player;
        this.originLayer = originLayer;
        this.oldOrigin = oldOrigin;
        this.newOrigin = newOrigin;
        this.isCancelled = false;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return this.player;
    }

    public OriginLayer getOriginLayer() {
        return originLayer;
    }

    public Origin getOldOrigin() {
        return this.oldOrigin;
    }

    public Origin getNewOrigin() {
        return newOrigin;
    }

    public void setNewOrigin(Origin newOrigin) {
        this.newOrigin = newOrigin;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}

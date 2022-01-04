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
package me.lemonypancakes.originsbukkit.api.events.player;

import me.lemonypancakes.originsbukkit.listeners.events.player.AsyncPlayerFluidInteract;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class AsyncPlayerFluidInteractEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final AsyncPlayerFluidInteract.FluidType fluidType;

    public AsyncPlayerFluidInteractEvent(Player player, AsyncPlayerFluidInteract.FluidType fluidType) {
        super(true);
        this.player = player;
        this.fluidType = fluidType;
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
        return player;
    }

    public AsyncPlayerFluidInteract.FluidType getFluidType() {
        return fluidType;
    }
}

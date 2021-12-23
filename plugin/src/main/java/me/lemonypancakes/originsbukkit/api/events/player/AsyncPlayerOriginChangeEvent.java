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

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * The type Async player origin change event.
 *
 * @author LemonyPancakes
 */
public class AsyncPlayerOriginChangeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String oldOrigin;
    private final String newOrigin;

    /**
     * Instantiates a new Async player origin change event.
     *
     * @param player    the player
     * @param oldOrigin the old origin
     * @param newOrigin the new origin
     */
    public AsyncPlayerOriginChangeEvent(Player player, String oldOrigin, String newOrigin) {
        super(true);
        this.player = player;
        this.oldOrigin = oldOrigin;
        this.newOrigin = newOrigin;
    }

    /**
     * Gets handlers.
     *
     * @return the handlers
     */
    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets new origin.
     *
     * @return the new origin
     */
    public String getNewOrigin() {
        return this.newOrigin;
    }

    /**
     * Gets old origin.
     *
     * @return the old origin
     */
    public String getOldOrigin() {
        return this.oldOrigin;
    }
}

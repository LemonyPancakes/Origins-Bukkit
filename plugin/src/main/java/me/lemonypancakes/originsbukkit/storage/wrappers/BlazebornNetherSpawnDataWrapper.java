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
package me.lemonypancakes.originsbukkit.storage.wrappers;

import java.util.UUID;

/**
 * The type Blazeborn nether spawn data wrapper.
 *
 * @author LemonyPancakes
 */
public class BlazebornNetherSpawnDataWrapper {

    private UUID playerUUID;
    private boolean firstTime;

    /**
     * Instantiates a new Blazeborn nether spawn data wrapper.
     *
     * @param playerUUID the player uuid
     * @param firstTime  the first time
     */
    public BlazebornNetherSpawnDataWrapper(UUID playerUUID, boolean firstTime) {
        this.playerUUID = playerUUID;
        this.firstTime = firstTime;
    }

    /**
     * Gets player uuid.
     *
     * @return the player uuid
     */
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Sets player uuid.
     *
     * @param playerUUID the player uuid
     */
    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    /**
     * Is first time boolean.
     *
     * @return the boolean
     */
    public boolean isFirstTime() {
        return firstTime;
    }

    /**
     * Sets first time.
     *
     * @param firstTime the first time
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}

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
 * The type Phantom ability toggle data wrapper.
 *
 * @author LemonyPancakes
 */
public class PhantomAbilityToggleDataWrapper {

    private UUID playerUUID;
    private boolean isToggled;

    /**
     * Instantiates a new Phantom ability toggle data wrapper.
     *
     * @param playerUUID the player uuid
     * @param isToggled  the is toggled
     */
    public PhantomAbilityToggleDataWrapper(UUID playerUUID, boolean isToggled) {
        this.playerUUID = playerUUID;
        this.isToggled = isToggled;
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
     * Is toggled boolean.
     *
     * @return the boolean
     */
    public boolean isToggled() {
        return isToggled;
    }

    /**
     * Sets toggled.
     *
     * @param toggled the toggled
     */
    public void setToggled(boolean toggled) {
        isToggled = toggled;
    }
}

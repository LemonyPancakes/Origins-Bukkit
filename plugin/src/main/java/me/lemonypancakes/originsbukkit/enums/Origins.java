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
package me.lemonypancakes.originsbukkit.enums;

/**
 * The enum Origins.
 */
public enum Origins {
    HUMAN("Human"),
    ENDERIAN("Enderian"),
    MERLING("Merling"),
    PHANTOM("Phantom"),
    ELYTRIAN("Elytrian"),
    BLAZEBORN("Blazeborn"),
    AVIAN("Avian"),
    ARACHNID("Arachnid"),
    SHULK("Shulk"),
    FELINE("Feline");

    private final Object value;

    /**
     * Instantiates a new Origins.
     *
     * @param value the value
     */
    Origins(final Object value) {
        this.value = value;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return (String) this.value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }
}

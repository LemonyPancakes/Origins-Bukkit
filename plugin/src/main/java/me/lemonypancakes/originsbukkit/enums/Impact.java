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

import org.bukkit.ChatColor;

public enum Impact {
    NONE(0, "none", ChatColor.GRAY),
    LOW(1, "low", ChatColor.GREEN),
    MEDIUM(2, "medium", ChatColor.YELLOW),
    HIGH(3, "high", ChatColor.RED);

    private final int impactValue;
    private final String value;
    private final ChatColor color;

    Impact(int impactValue, String value, ChatColor color) {
        this.impactValue = impactValue;
        this.value = value;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Impact{" +
                "impactValue=" + impactValue +
                ", value='" + value + '\'' +
                ", color=" + color +
                '}';
    }

    public int getImpactValue() {
        return impactValue;
    }

    public String getValue() {
        return value;
    }

    public ChatColor getColor() {
        return color;
    }
}

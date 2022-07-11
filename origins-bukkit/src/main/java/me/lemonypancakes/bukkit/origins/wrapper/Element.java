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
package me.lemonypancakes.bukkit.origins.wrapper;

import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;

public class Element<T> {

    private final Action<T> action;
    private final int weight;

    public Element(Action<T> action, int weight) {
        this.action = action;
        this.weight = weight;
    }

    public Action<T> getAction() {
        return action;
    }

    public int getWeight() {
        return weight;
    }
}

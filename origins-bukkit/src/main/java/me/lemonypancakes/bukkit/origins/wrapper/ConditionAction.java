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
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;

public class ConditionAction<T> {

    private final OriginsBukkitPlugin plugin;
    private final Condition<T> condition;
    private final Action<T> action;

    public ConditionAction(OriginsBukkitPlugin plugin, Condition<T> condition, Action<T> action) {
        this.plugin = plugin;
        this.condition = condition;
        this.action = action;
    }

    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    public Condition<T> getCondition() {
        return condition;
    }

    public Action<T> getAction() {
        return action;
    }
}

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
package me.lemonypancakes.bukkit.origins.factory.condition.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class CraftOrCondition<T> extends CraftCondition<T> {

    private Condition<T>[] conditions;

    public CraftOrCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiPredicate<JsonObject, T> biPredicate) {
        super(plugin, jsonObject, dataType, biPredicate);
        if (jsonObject != null) {
            this.conditions = plugin.getLoader().loadConditions(dataType, jsonObject.getAsJsonArray("conditions"));
            setBiPredicate(((jsonObject1, t) -> Arrays.stream(conditions).anyMatch(condition -> condition.test(t))));
        }
    }
}

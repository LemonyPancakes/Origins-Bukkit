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
package me.lemonypancakes.bukkit.origins.factory.action.meta;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.CraftAction;
import me.lemonypancakes.bukkit.origins.util.ProbabilityUtils;
import me.lemonypancakes.bukkit.origins.wrapper.Element;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class CraftChoiceAction<T> extends CraftAction<T> {

    private Element<T>[] elements;
    private int totalWeight;

    public CraftChoiceAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.elements = plugin.getLoader().loadElements(dataType, jsonObject);
            Arrays.stream(elements).forEach(element -> totalWeight = totalWeight + element.getWeight());
            setBiConsumer((jsonObject1, t) -> {
                for (Element<T> element : elements) {
                    if (ProbabilityUtils.getChance((float) (element.getWeight() / totalWeight))) {
                        element.getAction().accept(t);
                        break;
                    }
                }
            });
        }
    }
}

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

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;
import me.lemonypancakes.bukkit.origins.util.ProbabilityUtils;

import java.util.function.BiConsumer;

public class CraftChanceAction<T> extends CraftAction<T> {

    private Action<T> action;
    private Action<T> failAction;

    public CraftChanceAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.action = plugin.getLoader().loadAction(dataType, jsonObject);
            this.failAction = plugin.getLoader().loadAction(dataType, jsonObject, "fail_action");
            setBiConsumer((jsonObject1, t) -> {
                if (jsonObject1.has("chance")) {
                    if (ProbabilityUtils.getChance(jsonObject1.get("chance").getAsFloat())) {
                        action.accept(t);
                    } else {
                        failAction.accept(t);
                    }
                }
            });
        }
    }
}

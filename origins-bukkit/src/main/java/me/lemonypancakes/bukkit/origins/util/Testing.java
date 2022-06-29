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
package me.lemonypancakes.bukkit.origins.util;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Testing<T> {

    private final DataType<T> dataType;
    private final Consumer<T> consumer;

    public Testing(DataType<T> dataType, Consumer<T> consumer) {
        this.dataType = dataType;
        this.consumer = consumer;
        BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Action<T>>>> biFunction
                = ((plugin, jsonObject) -> (dataType1 -> () -> new CraftAction<>(plugin, jsonObject, dataType1, null)));
        Action<T> action = biFunction.apply(null, null).apply(null).get();
    }

    public DataType<T> getDataType() {
        return dataType;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }
}

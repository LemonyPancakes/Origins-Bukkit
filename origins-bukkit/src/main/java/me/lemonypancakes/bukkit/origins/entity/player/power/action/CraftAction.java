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
package me.lemonypancakes.bukkit.origins.entity.player.power.action;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;

import java.util.Objects;
import java.util.function.BiConsumer;

public class CraftAction<T> implements Action<T> {

    private OriginsBukkitPlugin plugin;
    private JsonObject jsonObject;
    private DataType<T> dataType;
    private BiConsumer<JsonObject, T> biConsumer;

    public CraftAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        setPlugin(plugin);
        setJsonObject(jsonObject);
        setDataType(dataType);
        setBiConsumer(biConsumer);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {
        if (this.plugin == null) {
            this.plugin = plugin;
        }
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public DataType<T> getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(DataType<T> dataType) {
        if (this.dataType == null) {
            this.dataType = dataType;
        }
    }

    @Override
    public BiConsumer<JsonObject, T> getBiConsumer() {
        return biConsumer;
    }

    @Override
    public void setBiConsumer(BiConsumer<JsonObject, T> biConsumer) {
        this.biConsumer = biConsumer;
    }

    @Override
    public void accept(T t) {
        if (biConsumer != null) {
            biConsumer.accept(jsonObject, t);
        }
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftAction)) return false;
        CraftAction<?> that = (CraftAction<?>) itemStack;
        return Objects.equals(plugin, that.plugin) && Objects.equals(jsonObject, that.jsonObject) && Objects.equals(dataType, that.dataType) && Objects.equals(biConsumer, that.biConsumer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, dataType);
    }

    @Override
    public String toString() {
        return "CraftAction{" +
                "plugin=" + plugin +
                ", jsonObject=" + jsonObject +
                ", dataType=" + dataType +
                ", biConsumer=" + biConsumer +
                '}';
    }
}

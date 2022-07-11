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
package me.lemonypancakes.bukkit.origins.entity.player.power.condition;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CraftCondition<T> implements Condition<T> {

    private OriginsBukkitPlugin plugin;
    private JsonObject jsonObject;
    private DataType<T> dataType;
    private BiPredicate<JsonObject, T> biPredicate;
    private boolean inverted;

    public CraftCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiPredicate<JsonObject, T> biPredicate) {
        setPlugin(plugin);
        setJsonObject(jsonObject);
        setDataType(dataType);
        setBiPredicate(biPredicate);
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
        if (jsonObject != null) {
            if (jsonObject.has("inverted")) {
                this.inverted = jsonObject.get("inverted").getAsBoolean();
            }
        }
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
    public BiPredicate<JsonObject, T> getBiPredicate() {
        return biPredicate;
    }

    @Override
    public void setBiPredicate(BiPredicate<JsonObject, T> biPredicate) {
        this.biPredicate = biPredicate;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public boolean test(T t) {
        if (biPredicate == null) {
            return true;
        }
        if (inverted) {
            return !biPredicate.test(jsonObject, t);
        } else {
            return biPredicate.test(jsonObject, t);
        }
    }

    @Override
    public Predicate<T> and(@Nonnull Predicate<? super T> other) {
        return Condition.super.and(other);
    }

    @Override
    public Predicate<T> negate() {
        return Condition.super.negate();
    }

    @Override
    public Predicate<T> or(@Nonnull Predicate<? super T> other) {
        return Condition.super.or(other);
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftCondition)) return false;
        CraftCondition<?> that = (CraftCondition<?>) itemStack;
        return inverted == that.inverted && Objects.equals(plugin, that.plugin) && Objects.equals(jsonObject, that.jsonObject) && Objects.equals(dataType, that.dataType) && Objects.equals(biPredicate, that.biPredicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, dataType);
    }

    @Override
    public String toString() {
        return "CraftCondition{" +
                "plugin=" + plugin +
                ", jsonObject=" + jsonObject +
                ", dataType=" + dataType +
                ", biPredicate=" + biPredicate +
                ", inverted=" + inverted +
                '}';
    }
}

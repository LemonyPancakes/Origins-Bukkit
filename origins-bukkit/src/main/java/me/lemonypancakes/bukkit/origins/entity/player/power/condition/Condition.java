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
import me.lemonypancakes.bukkit.origins.Identifiable;
import me.lemonypancakes.bukkit.origins.JsonObjectHolder;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.data.DataTypeHolder;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.util.Identifier;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Condition<T> extends OriginsBukkitPluginHolder, Predicate<T>, DataTypeHolder<T>, JsonObjectHolder {

    BiPredicate<JsonObject, T> getBiPredicate();

    void setBiPredicate(BiPredicate<JsonObject, T> biPredicate);

    boolean isInverted();

    final class Factory<T> implements Identifiable, DataTypeHolder<T> {

        private Identifier identifier;
        private DataType<T> dataType;
        private Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Condition<T>>>>> function;

        public Factory(Identifier identifier, DataType<T> dataType, Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Condition<T>>>>> function) {
            setIdentifier(identifier);
            setDataType(dataType);
            setFunction(function);
        }

        public Condition<T> create(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
            return function.apply(plugin).apply(jsonObject).apply(dataType).get();
        }

        @Override
        public Identifier getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(Identifier identifier) {
            if (this.identifier == null) {
                this.identifier = identifier;
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

        public Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Condition<T>>>>> getFunction() {
            return function;
        }

        public void setFunction(Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Condition<T>>>>> function) {
            if (this.function == null) {
                this.function = function;
            }
        }

        @Override
        public boolean equals(Object itemStack) {
            if (this == itemStack) return true;
            if (!(itemStack instanceof Factory)) return false;
            Factory<?> factory = (Factory<?>) itemStack;
            return Objects.equals(identifier, factory.identifier) && Objects.equals(dataType, factory.dataType) && Objects.equals(function, factory.function);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier, dataType, function);
        }

        @Override
        public String toString() {
            return "Factory{" +
                    "identifier=" + identifier +
                    ", dataType=" + dataType +
                    ", function=" + function +
                    '}';
        }
    }
}

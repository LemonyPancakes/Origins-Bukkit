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
package me.lemonypancakes.bukkit.origins.entity.player.power;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Identifiable;
import me.lemonypancakes.bukkit.origins.JsonObjectHolder;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.ConditionHolder;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PowerSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Power extends OriginsBukkitPluginHolder, Identifiable, JsonObjectHolder, ConditionHolder<Entity>, Listener {

    String getName();

    void setName(String name);

    String[] getDescription();

    void setDescription(String[] description);

    boolean isHidden();

    void setHidden(boolean hidden);

    int getLoadingPriority();

    void setLoadingPriority(int loadingPriority);

    Set<Player> getMembers();

    void addMember(Player player, PowerSource powerSource);

    void removeMember(Player player, PowerSource powerSource);

    void forceRemoveMember(Player player);

    boolean hasMember(Player player);

    boolean isActive(Player player);

    final class Factory implements Identifiable {

        private Identifier identifier;
        private Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> function;

        public Factory(Identifier identifier, Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> function) {
            setIdentifier(identifier);
            setFunction(function);
        }

        public Power create(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
            return function.apply(plugin).apply(identifier).apply(jsonObject).get();
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

        public Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> getFunction() {
            return function;
        }

        public void setFunction(Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> function) {
            if (this.function == null) {
                this.function = function;
            }
        }

        @Override
        public boolean equals(Object itemStack) {
            if (this == itemStack) return true;
            if (!(itemStack instanceof Factory)) return false;
            Factory factory = (Factory) itemStack;
            return Objects.equals(identifier, factory.identifier) && Objects.equals(function, factory.function);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier, function);
        }

        @Override
        public String toString() {
            return "Factory{" +
                    "identifier=" + identifier +
                    ", function=" + function +
                    '}';
        }
    }
}

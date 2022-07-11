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
package me.lemonypancakes.bukkit.origins.factory.condition;

import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;

public class FluidConditions {

    public FluidConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "empty"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, fluid) -> {
            if (fluid != null) {
                return fluid.getType() == Material.CAULDRON;
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_tag"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, fluid) -> {
            if (fluid != null) {
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "still"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, fluid) -> {
            if (fluid != null) {
                return fluid.getType() == Material.WATER || fluid.getType() == Material.LAVA;
            }
            return false;
        })));
    }
}

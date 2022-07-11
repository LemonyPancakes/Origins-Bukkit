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
package me.lemonypancakes.bukkit.origins.factory.action;

import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.CraftAction;
import me.lemonypancakes.bukkit.origins.factory.action.meta.*;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class BlockActions {

    public BlockActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAndAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftChanceAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftChoiceAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftDelayAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftIfElseAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftIfElseListAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftNothingAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_block"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                if (jsonObject.has("block")) {
                    block.setType(Material.valueOf(jsonObject.get("block").getAsString()));
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "bonemeal"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, block) -> block.applyBoneMeal(BlockFace.SELF))));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "set_block"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                if (jsonObject.has("block")) {
                    block.setType(Material.valueOf(jsonObject.get("block").getAsString()));
                }
            }
        })));
    }
}

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

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Comparison;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class BlockConditions {

    public BlockConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "adjacent"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new Adjacent(p, j, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attachable"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.getType().isSolid();
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "blast_resistance"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getType().getBlastResistance(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.getType() == Material.valueOf(jsonObject.get("block").getAsString());
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "match_blocks"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                if (jsonObject.has("blocks")) {
                    Material[] blocks = new Gson().fromJson(jsonObject.get("blocks"), Material[].class);

                    if (blocks != null) {
                        return Arrays.asList(blocks).contains(block.getType());
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "exposed_to_sky"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.getLightFromSky() >= 15;
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fluid"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new Fluid(p, j, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "hardness"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getType().getHardness(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "height"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getY(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "light_blocking"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.getType().isOccluding();
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "light_level"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                String lightType = null;
                byte toCompare = 0;

                if (jsonObject.has("light_type")) {
                    lightType = jsonObject.get("light_type").getAsString();
                }
                if (lightType != null) {
                    switch (lightType.toLowerCase()) {
                        case "sky":
                            toCompare = block.getLightFromSky();
                            break;
                        case "block":
                            toCompare = block.getLightFromBlocks();
                            break;
                    }
                } else {
                    toCompare = (byte) Math.max(block.getLightFromSky(), block.getLightFromBlocks());
                }
                return Comparison.parseComparison(jsonObject).compare(toCompare, jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "movement_blocking"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.getType().isSolid();
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "replaceable"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.isLiquid();
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "slipperiness"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getType().getSlipperiness(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "water_loggable"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, block) -> {
            if (block != null) {
                return block.getBlockData() instanceof Waterlogged;
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "offset"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new Meta.Offset(p, j, null)));
    }

    public static class Adjacent extends CraftCondition<Block> {

        private Condition<Block> adjacentCondition;

        public Adjacent(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Block> biPredicate) {
            super(plugin, jsonObject, DataType.BLOCK, biPredicate);
            if (jsonObject != null) {
                this.adjacentCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "adjacent_condition");
                setBiPredicate((jsonObject1, block) -> {
                    if (block != null) {
                        return Comparison.parseComparison(jsonObject1).compare(count(block), jsonObject1);
                    }
                    return false;
                });
            }
        }

        public int count(Block block) {
            int count = 0;

            for (BlockFace value : BlockFace.values()) {
                if (value.ordinal() >= 6) {
                    break;
                }
                if (adjacentCondition.test(block.getRelative(value))) {
                    count++;
                }
            }
            return count;
        }
    }

    public static class Fluid extends CraftCondition<Block> {

        private Condition<Block> condition;

        public Fluid(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Block> biPredicate) {
            super(plugin, jsonObject, DataType.BLOCK, biPredicate);
            if (jsonObject != null) {
                this.condition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject);
                setBiPredicate((jsonObject1, block) -> {
                    if (block != null) {
                        return condition.test(block);
                    }
                    return false;
                });
            }
        }
    }

    public static class Meta {

        public static class Offset extends CraftCondition<Block> {

            private Condition<Block> condition;

            public Offset(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Block> biPredicate) {
                super(plugin, jsonObject, DataType.BLOCK, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject);
                    setBiPredicate((jsonObject1, block) -> {
                        if (block != null) {
                            int x = 0;
                            int y = 0;
                            int z = 0;

                            if (jsonObject1.has("x")) {
                                x = jsonObject1.get("x").getAsInt();
                            }
                            if (jsonObject1.has("y")) {
                                y = jsonObject1.get("y").getAsInt();
                            }
                            if (jsonObject1.has("z")) {
                                z = jsonObject1.get("z").getAsInt();
                            }
                            return condition.test(block.getLocation().add(x, y, z).getBlock());
                        }
                        return false;
                    });
                }
            }
        }
    }
}

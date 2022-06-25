package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.originsbukkit.util.Comparison;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class BlockConditions {

    public BlockConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftOrCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "adjacent"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new Adjacent(plugin1, jsonObject, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attachable"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.getType().isSolid();
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "blast_resistance"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getType().getBlastResistance(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.getType() == Material.valueOf(jsonObject1.get("block").getAsString());
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "match_blocks"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                if (jsonObject1.has("blocks")) {
                    Material[] blocks = new Gson().fromJson(jsonObject1.get("blocks"), Material[].class);

                    if (blocks != null) {
                        return Arrays.asList(blocks).contains(block.getType());
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "exposed_to_sky"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.getLightFromSky() >= 15;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fluid"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new Fluid(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "hardness"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getType().getHardness(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "height"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject).compare(block.getY(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "light_blocking"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.getType().isOccluding();
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "light_level"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                String lightType = null;
                byte toCompare = 0;

                if (jsonObject1.has("light_type")) {
                    lightType = jsonObject1.get("light_type").getAsString();
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
                return Comparison.parseComparison(jsonObject1).compare(toCompare, jsonObject1);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "movement_blocking"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.getType().isSolid();
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "replaceable"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.isLiquid();
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "slipperiness"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return Comparison.parseComparison(jsonObject1).compare(block.getType().getSlipperiness(), jsonObject1);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "water_loggable"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                return block.getBlockData() instanceof Waterlogged;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "offset"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.Offset(plugin1, jsonObject, null)));
    }

    public static class Adjacent extends CraftCondition<Block> {

        private Condition<Block> adjacentCondition;

        public Adjacent(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Block> biPredicate) {
            super(plugin, jsonObject, DataType.BLOCK, biPredicate);
            if (jsonObject != null) {
                this.adjacentCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "adjacent_condition");
                setBiPredicate((jsonObject1, block) -> {
                    if (block != null) {
                        return Comparison.parseComparison(jsonObject).compare(count(block), jsonObject);
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

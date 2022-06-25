package me.lemonypancakes.originsbukkit.factory.condition;

import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Material;

public class FluidConditions {

    public FluidConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftOrCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "empty"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, fluid) -> {
            if (fluid != null) {
                return fluid.getType() == Material.CAULDRON;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_tag"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, fluid) -> {
            if (fluid != null) {
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "still"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, fluid) -> {
            if (fluid != null) {
                return fluid.getType() == Material.WATER || fluid.getType() == Material.LAVA;
            }
            return false;
        })));
    }
}

package me.lemonypancakes.bukkit.origins.factory.condition;

import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;

public class FluidConditions {

    public FluidConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "empty"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, fluid) -> {
            if (fluid != null) {
                return fluid.getType() == Material.CAULDRON;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_tag"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, fluid) -> {
            if (fluid != null) {
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "still"), DataType.BLOCK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, fluid) -> {
            if (fluid != null) {
                return fluid.getType() == Material.WATER || fluid.getType() == Material.LAVA;
            }
            return false;
        })));
    }
}

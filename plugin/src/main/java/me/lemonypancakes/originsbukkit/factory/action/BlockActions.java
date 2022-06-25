package me.lemonypancakes.originsbukkit.factory.action;

import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.factory.action.meta.*;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class BlockActions {

    public BlockActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftChanceAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftChoiceAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftDelayAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftIfElseAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftIfElseListAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftNothingAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_block"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                if (jsonObject1.has("block")) {
                    block.setType(Material.valueOf(jsonObject1.get("block").getAsString()));
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "bonemeal"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> block.applyBoneMeal(BlockFace.SELF))));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "set_block"), DataType.BLOCK, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, block) -> {
            if (block != null) {
                if (jsonObject1.has("block")) {
                    block.setType(Material.valueOf(jsonObject1.get("block").getAsString()));
                }
            }
        })));
    }
}

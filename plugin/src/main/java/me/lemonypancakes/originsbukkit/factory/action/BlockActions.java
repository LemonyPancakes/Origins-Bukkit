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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BLOCK, new CraftAndAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.BLOCK, new CraftChanceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.BLOCK, new CraftChoiceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.BLOCK, new CraftDelayAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.BLOCK, new CraftIfElseAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.BLOCK, new CraftIfElseListAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.BLOCK, new CraftNothingAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_block"), DataType.BLOCK, new CraftAction<>(plugin, null, (jsonObject, block) -> {
            if (block != null) {
                if (jsonObject.has("block")) {
                    block.setType(Material.valueOf(jsonObject.get("block").getAsString()));
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "bonemeal"), DataType.BLOCK, new CraftAction<>(plugin, null, (jsonObject, block) -> block.applyBoneMeal(BlockFace.SELF))));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "set_block"), DataType.BLOCK, new CraftAction<>(plugin, null, (jsonObject, block) -> {
            if (block != null) {
                if (jsonObject.has("block")) {
                    block.setType(Material.valueOf(jsonObject.get("block").getAsString()));
                }
            }
        })));
    }
}

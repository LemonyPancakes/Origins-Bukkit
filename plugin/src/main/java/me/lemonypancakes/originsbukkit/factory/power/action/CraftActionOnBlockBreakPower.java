package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class CraftActionOnBlockBreakPower extends CraftPower {

    private Action<Entity> entityAction;
    private Action<Block> blockAction;
    private Condition<Block> blockCondition;
    private boolean onlyWhenHarvested = true;

    public CraftActionOnBlockBreakPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
        this.blockAction = plugin.getLoader().loadAction(DataType.BLOCK, jsonObject, "block_action");
        this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
        if (jsonObject.has("only_when_harvested")) {
            this.onlyWhenHarvested = jsonObject.get("only_when_harvested").getAsBoolean();
        }
    }

    public CraftActionOnBlockBreakPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftActionOnBlockBreakPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Block block = event.getBlock();

            if (onlyWhenHarvested) {
                if (!event.isDropItems()) {
                    return;
                }
            }
            if (getCondition().test(player) && blockCondition.test(block)) {
                entityAction.accept(player);
                blockAction.accept(block);
            }
        }
    }
}

package me.lemonypancakes.bukkit.origins.factory.power.modify;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class CraftModifyHarvestPower extends CraftPower {

    private Condition<Block> blockCondition;
    private boolean allow = true;

    public CraftModifyHarvestPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
        if (jsonObject.has("allow")) {
            this.allow = jsonObject.get("allow").getAsBoolean();
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Block block = event.getBlock();

            if (getCondition().test(player) && blockCondition.test(block)) {
                if (allow) {
                    block.breakNaturally();
                } else {
                    event.setDropItems(false);
                }
            }
        }
    }
}

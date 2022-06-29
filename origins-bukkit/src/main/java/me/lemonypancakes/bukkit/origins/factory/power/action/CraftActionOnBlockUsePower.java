package me.lemonypancakes.bukkit.origins.factory.power.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftInteractionPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class CraftActionOnBlockUsePower extends CraftInteractionPower {

    private me.lemonypancakes.bukkit.origins.Action<Entity> entityAction;
    private me.lemonypancakes.bukkit.origins.Action<Block> blockAction;
    private Condition<Block> blockCondition;
    private BlockFace[] directions;

    public CraftActionOnBlockUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
        this.blockAction = plugin.getLoader().loadAction(DataType.BLOCK, jsonObject, "block_action");
        this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
        if (jsonObject.has("directions")) {
            this.directions = new Gson().fromJson(jsonObject.get("directions"), BlockFace[].class);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();

                if (directions != null) {
                    if (!Arrays.asList(directions).contains(event.getBlockFace())) {
                        return;
                    }
                }
                if (getHands() != null) {
                    if (!Arrays.asList(getHands()).contains(event.getHand())) {
                        return;
                    }
                }
                if (getCondition().test(player) && blockCondition.test(block) && getItemCondition().test(event.getItem())) {
                    entityAction.accept(player);
                    blockAction.accept(block);
                    if (getResultStack() != null) {
                        player.getInventory().addItem(getResultStack());
                        getResultItemAction().accept(getResultStack());
                    }
                    getHeldItemAction().accept(event.getItem());
                }
            }
        }
    }
}

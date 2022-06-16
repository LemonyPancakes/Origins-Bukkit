package me.lemonypancakes.originsbukkit.factory.power.prevent;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CraftPreventItemUsePower extends CraftPower {

    private Condition<ItemStack> itemCondition;

    public CraftPreventItemUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.itemCondition = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "item_condition");
    }

    public CraftPreventItemUsePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftPreventItemUsePower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (!event.isBlockInHand()) {
                    if (getCondition().test(player) && itemCondition.test(event.getItem())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

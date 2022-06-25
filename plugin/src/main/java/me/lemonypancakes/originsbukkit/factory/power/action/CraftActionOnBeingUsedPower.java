package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.factory.power.CraftInteractionPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CraftActionOnBeingUsedPower extends CraftInteractionPower {

    private Action<BiEntity> biEntityAction;
    private Condition<BiEntity> biEntityCondition;

    public CraftActionOnBeingUsedPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getMembers().contains(player)) {
                Player whoClicked = event.getPlayer();
                BiEntity biEntity = new BiEntity(whoClicked, player);
                ItemStack heldItem = whoClicked.getItemInUse();

                if (getHands() != null) {
                    if (!Arrays.asList(getHands()).contains(event.getHand())) {
                        return;
                    }
                }
                if (getCondition().test(player) && biEntityCondition.test(biEntity) && getItemCondition().test(heldItem)) {
                    biEntityAction.accept(biEntity);
                    if (getResultStack() != null) {
                        whoClicked.getInventory().addItem(getResultStack());
                        getResultItemAction().accept(getResultStack());
                    }
                    getHeldItemAction().accept(heldItem);
                }
            }
        }
    }
}

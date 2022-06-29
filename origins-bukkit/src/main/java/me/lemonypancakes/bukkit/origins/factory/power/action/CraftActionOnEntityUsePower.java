package me.lemonypancakes.bukkit.origins.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftInteractionPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CraftActionOnEntityUsePower extends CraftInteractionPower {

    private Action<BiEntity> biEntityAction;
    private Condition<BiEntity> biEntityCondition;

    public CraftActionOnEntityUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Entity entity = event.getRightClicked();
            BiEntity biEntity = new BiEntity(player, entity);
            ItemStack heldItem = player.getItemInUse();

            if (getHands() != null) {
                if (!Arrays.asList(getHands()).contains(event.getHand())) {
                    return;
                }
            }
            if (getCondition().test(player) && biEntityCondition.test(biEntity) && getItemCondition().test(heldItem)) {
                biEntityAction.accept(biEntity);
                if (getResultStack() != null) {
                    player.getInventory().addItem(getResultStack());
                    getResultItemAction().accept(getResultStack());
                }
                getHeldItemAction().accept(heldItem);
            }
        }
    }
}

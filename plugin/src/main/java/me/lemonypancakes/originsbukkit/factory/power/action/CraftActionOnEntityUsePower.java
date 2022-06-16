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

public class CraftActionOnEntityUsePower extends CraftInteractionPower {

    private Action<BiEntity> biEntityAction;
    private Condition<BiEntity> biEntityCondition;

    public CraftActionOnEntityUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
    }

    public CraftActionOnEntityUsePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftActionOnEntityUsePower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
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

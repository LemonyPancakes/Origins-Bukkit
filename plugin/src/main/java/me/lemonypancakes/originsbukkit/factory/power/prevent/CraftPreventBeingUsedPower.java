package me.lemonypancakes.originsbukkit.factory.power.prevent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import me.lemonypancakes.originsbukkit.wrapper.ItemStackWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CraftPreventBeingUsedPower extends CraftPower {

    private Action<BiEntity> biEntityAction;
    private Condition<BiEntity> biEntityCondition;
    private Condition<ItemStack> itemCondition;
    private EquipmentSlot[] hands;
    private ItemStack resultStack;
    private Action<ItemStack> heldItemAction;
    private Action<ItemStack> resultItemAction;

    public CraftPreventBeingUsedPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
        this.itemCondition = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "item_condition");
        if (jsonObject.has("hands")) {
            this.hands = new Gson().fromJson(jsonObject.get("hands"), EquipmentSlot[].class);
        }
        this.resultStack = new ItemStackWrapper(jsonObject.getAsJsonObject("result_stack")).getItemStack();
        this.heldItemAction = plugin.getLoader().loadAction(DataType.ITEM_STACK, jsonObject, "held_item_action");
        this.resultItemAction = plugin.getLoader().loadAction(DataType.ITEM_STACK, jsonObject, "result_item_action");
    }

    public CraftPreventBeingUsedPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftPreventBeingUsedPower(plugin, identifier, jsonObject);
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

                if (hands != null) {
                    if (!Arrays.asList(hands).contains(event.getHand())) {
                        return;
                    }
                }
                if (getCondition().test(player) && biEntityCondition.test(biEntity) && itemCondition.test(heldItem)) {
                    event.setCancelled(true);
                    biEntityAction.accept(biEntity);
                    if (resultStack != null) {
                        whoClicked.getInventory().addItem(resultStack);
                        resultItemAction.accept(resultStack);
                    }
                    heldItemAction.accept(heldItem);
                }
            }
        }
    }
}

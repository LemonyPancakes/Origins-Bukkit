package me.lemonypancakes.bukkit.origins.factory.power;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.ItemStackWrapper;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CraftInteractionPower extends CraftPower {

    private EquipmentSlot[] hands;
    private Condition<ItemStack> itemCondition;
    private Action<ItemStack> heldItemAction;
    private ItemStack resultStack;
    private Action<ItemStack> resultItemAction;

    public CraftInteractionPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("hands")) {
            this.hands = new Gson().fromJson(jsonObject.get("hands"), EquipmentSlot[].class);
        }
        this.itemCondition = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "item_condition");
        this.heldItemAction = plugin.getLoader().loadAction(DataType.ITEM_STACK, jsonObject, "held_item_action");
        this.resultStack = new ItemStackWrapper(jsonObject.getAsJsonObject("result_stack")).getItemStack();
        this.resultItemAction = plugin.getLoader().loadAction(DataType.ITEM_STACK, jsonObject, "result_item_action");
    }

    public EquipmentSlot[] getHands() {
        return hands;
    }

    public Condition<ItemStack> getItemCondition() {
        return itemCondition;
    }

    public Action<ItemStack> getHeldItemAction() {
        return heldItemAction;
    }

    public ItemStack getResultStack() {
        return resultStack;
    }

    public Action<ItemStack> getResultItemAction() {
        return resultItemAction;
    }
}

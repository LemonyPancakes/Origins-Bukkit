/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.factory.power;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
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

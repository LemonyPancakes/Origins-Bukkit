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
package me.lemonypancakes.bukkit.origins.factory.power.prevent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import me.lemonypancakes.bukkit.origins.wrapper.ItemStackWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CraftPreventBeingUsedPower extends CraftPower {

    private final Action<BiEntity> biEntityAction;
    private final Condition<BiEntity> biEntityCondition;
    private final Condition<ItemStack> itemCondition;
    private EquipmentSlot[] hands;
    private final ItemStack resultStack;
    private final Action<ItemStack> heldItemAction;
    private final Action<ItemStack> resultItemAction;

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

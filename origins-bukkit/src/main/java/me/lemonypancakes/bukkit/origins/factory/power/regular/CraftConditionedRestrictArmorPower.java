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
package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.armorequipevent.ArmorEquipEvent;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class CraftConditionedRestrictArmorPower extends CraftPower {

    private final Condition<ItemStack> head;
    private final Condition<ItemStack> chest;
    private final Condition<ItemStack> legs;
    private final Condition<ItemStack> feet;

    public CraftConditionedRestrictArmorPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.head = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "head");
        this.chest = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "chest");
        this.legs = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "legs");
        this.feet = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "feet");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onArmorEquipEvent(ArmorEquipEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            if (getCondition().test(player)) {
                switch (event.getType()) {
                    case HELMET:
                        if (head.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                    case CHESTPLATE:
                        if (chest.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                    case LEGGINGS:
                        if (legs.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                    case BOOTS:
                        if (feet.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                }
            }
        }
    }
}

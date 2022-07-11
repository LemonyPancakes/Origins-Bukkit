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
package me.lemonypancakes.bukkit.origins.factory.action;

import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.CraftAction;
import me.lemonypancakes.bukkit.origins.factory.action.meta.*;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemActions {

    public ItemActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftAndAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftChanceAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftChoiceAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftDelayAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftIfElseAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftIfElseListAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftNothingAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "consume"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                if (jsonObject.has("amount")) {
                    itemStack.setAmount(itemStack.getAmount() - jsonObject.get("amount").getAsInt());
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "damage"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta != null) {
                    if (itemMeta instanceof Damageable) {
                        Damageable damageable = (Damageable) itemMeta;
                        boolean ignoreUnbreaking = false;
                        int amount = 0;

                        if (jsonObject.has("ignore_unbreaking")) {
                            ignoreUnbreaking = jsonObject.get("ignore_unbreaking").getAsBoolean();
                        }
                        if (jsonObject.has("amount")) {
                            amount = jsonObject.get("amount").getAsInt();
                        }
                        if (ignoreUnbreaking) {
                            if (itemStack.containsEnchantment(Enchantment.DURABILITY)) {
                                amount = amount * itemStack.getEnchantmentLevel(Enchantment.DURABILITY);
                            }
                        }
                        if (damageable.getDamage() < itemStack.getType().getMaxDurability()) {
                            damageable.setDamage(damageable.getDamage() + amount);
                            itemStack.setItemMeta(itemMeta);
                        } else {
                            itemStack.setAmount(0);
                        }
                    }
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "modify"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {

            }
        })));
    }
}

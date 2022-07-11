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
package me.lemonypancakes.bukkit.origins.wrapper;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackWrapper {

    private ItemStack itemStack;

    public ItemStackWrapper(JsonObject jsonObject) {
        if (jsonObject != null) {
            ItemStack itemStack = null;

            if (jsonObject.has("item")) {
                Material material = Material.valueOf(jsonObject.get("item").getAsString());
                itemStack = new ItemStack(material);
            }
            if (itemStack != null) {
                if (jsonObject.has("amount")) {
                    itemStack.setAmount(jsonObject.get("amount").getAsInt());
                }
                this.itemStack = itemStack;
            }
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}

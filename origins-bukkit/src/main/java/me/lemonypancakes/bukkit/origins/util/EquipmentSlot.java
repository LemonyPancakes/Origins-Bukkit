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
package me.lemonypancakes.bukkit.origins.util;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.NoSuchElementException;

public enum EquipmentSlot {
    MAIN_HAND("mainhand") {

        @Override
        public ItemStack getItemStack(PlayerInventory playerInventory) {
            return playerInventory.getItemInMainHand();
        }
    },
    OFF_HAND("offhand") {

        @Override
        public ItemStack getItemStack(PlayerInventory playerInventory) {
            return playerInventory.getItemInOffHand();
        }
    },
    HEAD("head") {

        @Override
        public ItemStack getItemStack(PlayerInventory playerInventory) {
            return playerInventory.getHelmet();
        }
    },
    CHEST("chest") {

        @Override
        public ItemStack getItemStack(PlayerInventory playerInventory) {
            return playerInventory.getChestplate();
        }
    },
    LEGS("legs") {

        @Override
        public ItemStack getItemStack(PlayerInventory playerInventory) {
            return playerInventory.getLeggings();
        }
    },
    FEET("feet") {

        @Override
        public ItemStack getItemStack(PlayerInventory playerInventory) {
            return playerInventory.getBoots();
        }
    };

    private final String equipmentSlot;

    EquipmentSlot(String equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }

    public String getEquipmentSlot() {
        return equipmentSlot;
    }

    public static EquipmentSlot parseEquipmentSlot(String equipmentSlot) {
        for (EquipmentSlot es : values()) {
            if (es.equipmentSlot.equals(equipmentSlot)) {
                return es;
            }
        }
        throw new NoSuchElementException(String.format("Unknown equipment slot [%s]", equipmentSlot));
    }

    public static EquipmentSlot parseEquipmentSlot(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.has("equipment_slot")) {
                String equipmentSlot = jsonObject.get("equipment_slot").getAsString();

                if (equipmentSlot != null) {
                    for (EquipmentSlot es : values()) {
                        if (es.equipmentSlot.equals(equipmentSlot)) {
                            return es;
                        }
                    }
                    throw new NoSuchElementException(String.format("Unknown comparison [%s]", equipmentSlot));
                }
            }
        }
        return null;
    }

    public abstract ItemStack getItemStack(PlayerInventory playerInventory);
}

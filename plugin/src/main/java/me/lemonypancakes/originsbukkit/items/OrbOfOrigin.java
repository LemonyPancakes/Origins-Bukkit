/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
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
package me.lemonypancakes.originsbukkit.items;

import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.util.BukkitPersistentDataUtils;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class OrbOfOrigin {

    private final ItemHandler itemHandler;
    private ItemStack originBall;
    private ShapedRecipe originBallRecipe;

    public ItemHandler getItemHandler() {
        return this.itemHandler;
    }

    public ItemStack getItemStack() {
        return this.originBall;
    }

    public ShapedRecipe getRecipe() {
        return this.originBallRecipe;
    }

    public OrbOfOrigin(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
        init();
    }

    private void init() {
        ItemStack itemStack;

        if (Config.CUSTOM_ITEM_ORB_OF_ORIGIN_MATERIAL.toMaterial() == null) {
            itemStack = new ItemStack(Material.MAGMA_CREAM);
        } else {
            itemStack = new ItemStack(Config.CUSTOM_ITEM_ORB_OF_ORIGIN_MATERIAL.toMaterial());
        }
        ItemMeta itemMeta = itemStack.getItemMeta();


        if (itemMeta != null) {
            BukkitPersistentDataUtils.setPersistentData(
                    itemMeta,
                    "origins-bukkit:custom_item",
                    PersistentDataType.STRING,
                    "origins-bukkit:orb_of_origin"
            );
            itemMeta.setLore(Arrays.asList(Config.CUSTOM_ITEM_ORB_OF_ORIGIN_LORE.toStringArray()));
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setUnbreakable(true);
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.setDisplayName(ChatUtils.format(Config.CUSTOM_ITEM_ORB_OF_ORIGIN_DISPLAY_NAME.toString()));
            itemStack.setItemMeta(itemMeta);
        }

        originBall = itemStack;

        NamespacedKey namespacedKey = NamespacedKey.fromString("origins-bukkit:orb_of_origin");

        if (namespacedKey != null) {
            ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, originBall);

            List<String> shape = Arrays.asList(Config.CUSTOM_ITEM_ORB_OF_ORIGIN_RECIPE_SHAPE.toStringArray());

            shapedRecipe.shape(
                    shape.get(0),
                    shape.get(1),
                    shape.get(2)
            );

            ConfigurationSection ingredients = Config.CUSTOM_ITEM_ORB_OF_ORIGIN_RECIPE_INGREDIENTS.getConfigurationSection();

            if (ingredients != null) {
                ingredients.getKeys(false).forEach((key -> {
                    Material material = Material.valueOf((String) ingredients.get(String.valueOf(key.charAt(0))));

                    shapedRecipe.setIngredient(key.charAt(0), material);
                }));
            }

            originBallRecipe = shapedRecipe;

            itemHandler.getItems().add(String.valueOf(getRecipe().getKey()));
            itemHandler.getItems().add(String.valueOf(getRecipe().getKey()).split(":")[1]);
            getItemHandler().getPlugin().getServer().addRecipe(shapedRecipe);
        }
    }
}

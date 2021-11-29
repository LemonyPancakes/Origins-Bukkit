/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 SwagPannekaker
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
package me.swagpancakes.originsbukkit.items;

import me.swagpancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The type Origin ball.
 *
 * @author SwagPannekaker
 */
public class OriginBall {

    private final ItemHandler itemHandler;
    private ItemStack originBall;
    private ShapedRecipe originBallRecipe;

    /**
     * Gets item handler.
     *
     * @return the item handler
     */
    public ItemHandler getItemHandler() {
        return this.itemHandler;
    }

    /**
     * Gets item stack.
     *
     * @return the item stack
     */
    public ItemStack getItemStack() {
        return this.originBall;
    }

    /**
     * Gets recipe.
     *
     * @return the recipe
     */
    public ShapedRecipe getRecipe() {
        return this.originBallRecipe;
    }

    /**
     * Instantiates a new Origin ball.
     *
     * @param itemHandler the item handler
     */
    public OriginBall(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        ItemStack itemStack = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setUnbreakable(true);
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.setDisplayName(ChatUtils.format("&6Origin Ball"));
            itemStack.setItemMeta(itemMeta);
        }

        originBall = itemStack;

        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("origin_ball"), itemStack);
        shapedRecipe.shape(
                "Z",
                "Z",
                "Z");
        shapedRecipe.setIngredient('Z', Material.DIRT);

        originBallRecipe = shapedRecipe;

        itemHandler.getItems().add(String.valueOf(getRecipe().getKey()));
        itemHandler.getItems().add(String.valueOf(getRecipe().getKey()).split(":")[1]);
        Bukkit.getServer().addRecipe(shapedRecipe);
    }
}

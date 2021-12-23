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

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * The type Arachnid cobweb.
 *
 * @author LemonyPancakes
 */
public class ArachnidCobweb {
    
    private final ItemHandler itemHandler;
    private ItemStack cobweb;
    private ShapelessRecipe cobwebRecipe;

    /**
     * Gets item handler.
     *
     * @return the item handler
     */
    public ItemHandler getItemHandler() {
        return itemHandler;
    }

    /**
     * Gets item stack.
     *
     * @return the item stack
     */
    public ItemStack getItemStack() {
        return cobweb;
    }

    /**
     * Gets recipe.
     *
     * @return the recipe
     */
    public ShapelessRecipe getRecipe() {
        return cobwebRecipe;
    }

    /**
     * Instantiates a new Arachnid cobweb.
     *
     * @param itemHandler the item handler
     */
    public ArachnidCobweb(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        cobweb = new ItemStack(Material.COBWEB);

        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(NamespacedKey.minecraft("arachnid_cobweb"), cobweb);

        shapelessRecipe.addIngredient(2, Material.STRING);
        getItemHandler().getPlugin().getServer().addRecipe(shapelessRecipe);
    }
}

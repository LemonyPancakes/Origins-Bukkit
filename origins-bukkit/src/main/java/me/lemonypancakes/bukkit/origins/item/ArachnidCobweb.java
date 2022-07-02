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
package me.lemonypancakes.bukkit.origins.item;

import me.lemonypancakes.bukkit.origins.OriginItem;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class ArachnidCobweb implements OriginItem {

    private Identifier identifier = new Identifier(Identifier.ORIGINS_BUKKIT, "arachnid_cobweb");
    private final ItemStack itemStack;
    private final Recipe recipe;

    public ArachnidCobweb() {
        itemStack = new ItemStack(Material.COBWEB);
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(identifier.toNameSpacedKey(), itemStack);

        shapelessRecipe.addIngredient(2, Material.STRING);
        recipe = shapelessRecipe;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }
}

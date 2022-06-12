package me.lemonypancakes.originsbukkit;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface OriginItem extends Identifiable {

    ItemStack getItemStack();

    Recipe getRecipe();
}

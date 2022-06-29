package me.lemonypancakes.bukkit.origins.data;

import me.lemonypancakes.bukkit.origins.OriginItem;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftOriginItem implements OriginItem {

    private Identifier identifier;
    private ItemStack itemStack;

    public CraftOriginItem(Identifier identifier) {
        this.identifier = identifier;
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
        return null;
    }
}

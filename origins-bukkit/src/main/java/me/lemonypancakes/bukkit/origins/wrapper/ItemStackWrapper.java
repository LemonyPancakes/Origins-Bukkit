package me.lemonypancakes.bukkit.origins.wrapper;

import com.google.gson.JsonObject;
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

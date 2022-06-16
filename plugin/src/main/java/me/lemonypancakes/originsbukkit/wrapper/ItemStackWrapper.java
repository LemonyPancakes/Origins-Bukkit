package me.lemonypancakes.originsbukkit.wrapper;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackWrapper {

    private ItemStack itemStack;

    public ItemStackWrapper(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.has("item")) {
                Material material = Material.valueOf(jsonObject.get("item").getAsString());
                int amount = 1;
                ItemStack itemStack = new ItemStack(material);
                itemStack.setAmount(amount);
                this.itemStack = itemStack;
            }
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}

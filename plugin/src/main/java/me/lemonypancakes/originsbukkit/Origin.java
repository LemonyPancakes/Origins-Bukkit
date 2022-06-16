package me.lemonypancakes.originsbukkit;

import me.lemonypancakes.originsbukkit.util.Impact;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Origin extends JsonObjectHolder, Identifiable {

    String getDisplayName();

    void setDisplayName(String displayName);

    String[] getDescription();

    void setDescription(String[] description);

    Impact getImpact();

    void setImpact(Impact impact);

    ItemStack getIcon();

    void setIcon(ItemStack icon);

    String[] getAuthors();

    void setAuthors(String[] authors);

    List<Power> getPowers();

    void setPowers(List<Power> powers);

    Inventory getInventoryGUI();

    void setInventoryGUI(Inventory inventory);
}

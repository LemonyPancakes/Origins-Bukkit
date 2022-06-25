package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.armorequipevent.ArmorEquipEvent;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftLightArmorPower extends CraftPower {

    private final List<Material> restrictedArmorTypes = new ArrayList<>(Arrays.asList(Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS));

    public CraftLightArmorPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onArmorEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            ItemStack newArmorPiece = event.getNewArmorPiece();

            if (restrictedArmorTypes.contains(newArmorPiece.getType())) {
                event.setCancelled(true);
            }
        }
    }
}

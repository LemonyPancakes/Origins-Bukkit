package me.lemonypancakes.bukkit.origins.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CraftNoShieldPower extends CraftPower {

    public CraftNoShieldPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                ItemStack itemStack = event.getItem();

                if (itemStack != null) {
                    Material material = itemStack.getType();

                    if (material == Material.SHIELD) {
                        World world = player.getWorld();
                        Location location = player.getLocation();
                        EquipmentSlot equipmentSlot = event.getHand();
                        PlayerInventory playerInventory = player.getInventory();

                        if (equipmentSlot == EquipmentSlot.HAND) {
                            playerInventory.remove(itemStack);
                            world.dropItemNaturally(location, itemStack);
                        } else if (equipmentSlot == EquipmentSlot.OFF_HAND) {
                            playerInventory.setItemInOffHand(null);
                            world.dropItemNaturally(location, itemStack);
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}

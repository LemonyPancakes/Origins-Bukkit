package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.armorequipevent.ArmorEquipEvent;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class CraftConditionedRestrictArmorPower extends CraftPower {

    private Condition<ItemStack> head;
    private Condition<ItemStack> chest;
    private Condition<ItemStack> legs;
    private Condition<ItemStack> feet;

    public CraftConditionedRestrictArmorPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.head = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "head");
        this.chest = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "chest");
        this.legs = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "legs");
        this.feet = plugin.getLoader().loadCondition(DataType.ITEM_STACK, jsonObject, "feet");
    }

    public CraftConditionedRestrictArmorPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftConditionedRestrictArmorPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onArmorEquipEvent(ArmorEquipEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            if (getCondition().test(player)) {
                switch (event.getType()) {
                    case HELMET:
                        if (head.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                    case CHESTPLATE:
                        if (chest.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                    case LEGGINGS:
                        if (legs.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                    case BOOTS:
                        if (feet.test(event.getNewArmorPiece())) {
                            event.setCancelled(true);
                        }
                        break;
                }
            }
        }
    }
}

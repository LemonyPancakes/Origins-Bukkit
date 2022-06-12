package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.armorequipevent.ArmorEquipEvent;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class CraftConditionedRestrictArmorPower extends CraftPower {

    public CraftConditionedRestrictArmorPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftConditionedRestrictArmorPower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onArmorEquipEvent(ArmorEquipEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Temp temp = new CraftTemp();

            temp.set(DataType.ENTITY, player);
            if (testAndAccept(temp)) {
                event.setCancelled(true);
            }
        }
    }
}

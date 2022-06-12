package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.BiEntity;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftActionWhenHitPower extends CraftPower {

    public CraftActionWhenHitPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftActionWhenHitPower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getMembers().contains(player)) {
                Entity damager = event.getDamager();
                Temp temp = new CraftTemp();

                temp.set(DataType.ENTITY, player);
                temp.set(DataType.BI_ENTITY, new BiEntity(damager, player));
                testAndAccept(temp);
            }
        }
    }
}

package me.lemonypancakes.bukkit.origins.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftDamageFromSnowballsPower extends CraftPower {

    public CraftDamageFromSnowballsPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity targetEntity = event.getEntity();

        if (targetEntity instanceof Player) {
            Player targetPlayer = (Player) targetEntity;

            if (getMembers().contains(targetPlayer)) {
                Entity actorEntity = event.getDamager();

                if (actorEntity instanceof Snowball) {
                    double baseDamage = event.getDamage();

                    event.setDamage(baseDamage + 1.5);
                }
            }
        }
    }
}

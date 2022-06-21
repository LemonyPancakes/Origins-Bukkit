package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftAerialCombatantPower extends CraftPower {

    public CraftAerialCombatantPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    public CraftAerialCombatantPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftAerialCombatantPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity actorEntity = event.getDamager();

        if (actorEntity instanceof Player) {
            Player actorPlayer = (Player) actorEntity;

            if (getMembers().contains(actorPlayer)) {
                double baseDamage = event.getDamage();

                if (actorPlayer.isGliding()) {
                    double additionalDamage = baseDamage * 0.5;

                    event.setDamage(baseDamage + additionalDamage);
                }
            }
        }
    }
}

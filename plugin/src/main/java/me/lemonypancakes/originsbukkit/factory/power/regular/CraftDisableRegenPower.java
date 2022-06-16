package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class CraftDisableRegenPower extends CraftPower {

    public CraftDisableRegenPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    public CraftDisableRegenPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftDisableRegenPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getMembers().contains(player)) {
                if (getCondition().test(player)) {
                    EntityRegainHealthEvent.RegainReason regainReason = event.getRegainReason();

                    if (regainReason == EntityRegainHealthEvent.RegainReason.SATIATED || regainReason == EntityRegainHealthEvent.RegainReason.REGEN) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

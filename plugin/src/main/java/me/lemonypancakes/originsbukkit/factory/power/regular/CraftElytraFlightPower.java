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
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftElytraFlightPower extends CraftPower {

    public CraftElytraFlightPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (!player.isGliding()) {
                        player.setGliding(getCondition().test(player));
                    }
                });
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
    }

    public CraftElytraFlightPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftElytraFlightPower(plugin, identifier, jsonObject);
    }

    @Override
    protected void onMemberAdd(Player player) {
        player.setGliding(getCondition().test(player));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getMembers().contains(player)) {
                if (getCondition().test(player)) {
                    if (!event.isGliding()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

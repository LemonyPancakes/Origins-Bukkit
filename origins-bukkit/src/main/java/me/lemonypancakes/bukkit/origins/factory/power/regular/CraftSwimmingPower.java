package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftSwimmingPower extends CraftPower {

    public CraftSwimmingPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
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

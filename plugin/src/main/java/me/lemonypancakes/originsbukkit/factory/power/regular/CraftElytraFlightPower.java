package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftElytraFlightPower extends CraftPower {

    public CraftElytraFlightPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    getMembers().forEach(player -> {
                        if (!player.isGliding()) {
                            Temp temp = new CraftTemp();

                            temp.set(DataType.ENTITY, player);
                            if (testAndAccept(temp)) {
                                player.setGliding(true);
                            }
                        }
                    });
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftElytraFlightPower(plugin, identifier, jsonObject, false);
    }

    @Override
    protected void onMemberAdd(Player player) {
        Temp temp = new CraftTemp();

        temp.set(DataType.ENTITY, player);
        if (testAndAccept(temp)) {
            player.setGliding(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getMembers().contains(player)) {
                if (!event.isGliding()) {
                    Temp temp = new CraftTemp();

                    temp.set(DataType.ENTITY, player);
                    if (testAndAccept(temp)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

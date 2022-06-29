package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftCreativeFlightPower extends CraftPower {

    public CraftCreativeFlightPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (getCondition().test(player)) {
                        if (!player.isFlying()) {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        }
                    } else {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1L);
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
    }
}

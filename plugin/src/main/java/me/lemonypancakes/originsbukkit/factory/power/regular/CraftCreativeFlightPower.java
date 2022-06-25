package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
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

package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftFreezePower extends CraftPower {

    public CraftFreezePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (getCondition().test(player)) {
                        int freezeTicks = player.getFreezeTicks();

                        player.setFreezeTicks(Math.min((freezeTicks + 3), 143));
                    }
                });
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
    }

    public CraftFreezePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftFreezePower(plugin, identifier, jsonObject);
    }
}

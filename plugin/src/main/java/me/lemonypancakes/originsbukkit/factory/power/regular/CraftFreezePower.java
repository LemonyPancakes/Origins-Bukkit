package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftFreezePower extends CraftPower {

    public CraftFreezePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    getMembers().forEach(player -> {
                        Temp temp = new CraftTemp();

                        temp.set(DataType.ENTITY, player);
                        if (testAndAccept(temp)) {
                            int freezeTicks = player.getFreezeTicks();

                            player.setFreezeTicks(Math.min((freezeTicks + 3), 143));
                        }
                    });
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftFreezePower(plugin, identifier, jsonObject, false);
    }
}

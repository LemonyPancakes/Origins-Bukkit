package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftParticlePower extends CraftPower {

    private Particle particle;
    private int frequency;

    public CraftParticlePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                if (jsonObject.has("particle")) {
                    this.particle = Particle.valueOf(jsonObject.get("particle").getAsString());
                }
                if (jsonObject.has("frequency")) {
                    this.frequency = jsonObject.get("frequency").getAsInt();
                }
            }
            new BukkitRunnable() {

                @Override
                public void run() {
                    getMembers().forEach(player -> {

                    });
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftParticlePower(plugin, identifier, jsonObject, false);
    }
}

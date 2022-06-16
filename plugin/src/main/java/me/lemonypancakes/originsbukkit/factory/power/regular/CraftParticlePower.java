package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftParticlePower extends CraftPower {

    private Particle particle;
    private int frequency = 1;

    public CraftParticlePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("particle")) {
            this.particle = Particle.valueOf(jsonObject.get("particle").getAsString());
        }
        if (jsonObject.has("frequency")) {
            this.frequency = jsonObject.get("frequency").getAsInt();
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (getCondition().test(player)) {
                        player.getWorld().spawnParticle(particle, player.getLocation(), 1);
                    }
                });
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, frequency);
    }

    public CraftParticlePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftParticlePower(plugin, identifier, jsonObject);
    }
}

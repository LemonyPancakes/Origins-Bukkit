package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftParticlePower extends CraftPower {

    private Particle particle;
    private int frequency = 1;
    private int count = 1;
    private double offsetX = 0;
    private double offsetY = 0;
    private double offsetZ = 0;
    private double extra = 0;

    public CraftParticlePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("particle")) {
            this.particle = Particle.valueOf(jsonObject.get("particle").getAsString());
        }
        if (jsonObject.has("frequency")) {
            this.frequency = jsonObject.get("frequency").getAsInt();
        }
        if (jsonObject.has("count")) {
            this.count = jsonObject.get("count").getAsInt();
        }
        if (jsonObject.has("offset_x")) {
            this.offsetX = jsonObject.get("offset_x").getAsDouble();
        }
        if (jsonObject.has("offset_y")) {
            this.offsetY = jsonObject.get("offset_y").getAsDouble();
        }
        if (jsonObject.has("offset_z")) {
            this.offsetZ = jsonObject.get("offset_z").getAsDouble();
        }
        if (jsonObject.has("extra")) {
            this.extra = jsonObject.get("extra").getAsDouble();
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (getCondition().test(player)) {
                        player.getWorld().spawnParticle(particle, player.getLocation(), count, offsetX, offsetY, offsetZ, extra);
                    }
                });
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, frequency);
    }
}

package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftTogglePower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftClimbingPower extends CraftTogglePower {

    private double climbingSpeed = 0.175;

    public CraftClimbingPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("climbing_speed")) {
            this.climbingSpeed = jsonObject.get("climbing_speed").getAsDouble();
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (isToggled(player)) {
                        if (player.isSneaking() && !player.isGliding()) {
                            if (getCondition().test(player)) {
                                player.setVelocity(player.getVelocity().setY(climbingSpeed));
                            }
                        }
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1L);
    }
}

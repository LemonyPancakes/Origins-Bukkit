package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftClaustrophobiaPower extends CraftPower {

    public CraftClaustrophobiaPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    Location location = player.getLocation();
                    Location locationA = new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ());
                    Location locationB = new Location(location.getWorld(), location.getX(), location.getY() + 2, location.getZ());
                    Location locationC = new Location(location.getWorld(), location.getX(), location.getY() + 3, location.getZ());

                    if (location.getBlock().getType().isSolid() || locationA.getBlock().getType().isSolid() || locationB.getBlock().getType().isSolid() || locationC.getBlock().getType().isSolid()) {
                        PotionEffect currentWeaknessPotionEffect = player.getPotionEffect(PotionEffectType.WEAKNESS);
                        PotionEffect currentSlowPotionEffect = player.getPotionEffect(PotionEffectType.SLOW);

                        if (currentWeaknessPotionEffect != null && currentSlowPotionEffect != null) {
                            if (currentWeaknessPotionEffect.getDuration() < 3600 && currentSlowPotionEffect.getDuration() < 3600) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, currentWeaknessPotionEffect.getDuration() + 2, 0));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, currentSlowPotionEffect.getDuration() + 2, 0));
                            }
                        } else {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 2, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 0));
                        }
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1L);
    }
}

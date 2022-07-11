/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.factory.power.temporary;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
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

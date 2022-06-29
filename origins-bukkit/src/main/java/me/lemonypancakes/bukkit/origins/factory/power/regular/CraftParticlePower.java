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

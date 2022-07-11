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

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftSwimmingPower extends CraftPower {

    public CraftSwimmingPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (!player.isGliding()) {
                        player.setGliding(getCondition().test(player));
                    }
                });
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
    }

    @Override
    protected void onMemberAdd(Player player) {
        player.setGliding(getCondition().test(player));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (hasMember(player)) {
                if (getCondition().test(player)) {
                    if (!event.isGliding()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

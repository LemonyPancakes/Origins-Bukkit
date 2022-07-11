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
package me.lemonypancakes.bukkit.origins.factory.power.action;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class CraftActionOverTimePower extends CraftPower {

    private int interval = 20;
    private final Action<Entity> entityAction;
    private final Action<Entity> risingAction;
    private final Action<Entity> fallingAction;
    private final Set<Player> active = new HashSet<>();

    public CraftActionOverTimePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("interval")) {
            interval = jsonObject.get("interval").getAsInt();
        }
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
        this.risingAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "rising_action");
        this.fallingAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "falling_action");
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (getCondition().test(player)) {
                        if (!active.contains(player)) {
                            active.add(player);
                            risingAction.accept(player);
                        }
                        entityAction.accept(player);
                    } else {
                        if (active.contains(player)) {
                            fallingAction.accept(player);
                            active.remove(player);
                        }
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, interval);
    }

    @Override
    protected void onMemberRemove(Player player) {
        active.remove(player);
    }
}

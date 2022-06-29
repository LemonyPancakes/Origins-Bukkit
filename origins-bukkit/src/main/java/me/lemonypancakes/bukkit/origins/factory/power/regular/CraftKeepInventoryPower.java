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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CraftKeepInventoryPower extends CraftPower {

    private int[] slots;

    public CraftKeepInventoryPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (getMembers().contains(player)) {
            if (getMembers().contains(player)) {
                event.getDrops().clear();
                event.setKeepInventory(true);
            }
        }
    }
}

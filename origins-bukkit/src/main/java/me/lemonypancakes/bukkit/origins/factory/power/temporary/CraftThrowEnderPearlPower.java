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
import me.lemonypancakes.bukkit.origins.factory.power.CraftActiveWithCooldownPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Key;
import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Set;

public class CraftThrowEnderPearlPower extends CraftActiveWithCooldownPower {

    private final Set<Player> players = new HashSet<>();

    public CraftThrowEnderPearlPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @Override
    protected void onUse(Player player, Key key) {
        player.launchProjectile(EnderPearl.class);
        players.add(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                if (players.contains(player)) {
                    players.remove(player);
                    event.setCancelled(true);
                    Location getToLocation = event.getTo();

                    if (getToLocation != null) {
                        player.teleport(getToLocation);
                    }
                }
            }
        }
    }

    @Override
    protected void onMemberRemove(Player player) {
        players.remove(player);
    }
}

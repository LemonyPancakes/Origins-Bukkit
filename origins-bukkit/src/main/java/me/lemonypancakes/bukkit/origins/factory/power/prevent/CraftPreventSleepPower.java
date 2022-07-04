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
package me.lemonypancakes.bukkit.origins.factory.power.prevent;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftPreventSleepPower extends CraftPower {

    private final Condition<Block> blockCondition;
    private String message;
    private boolean setSpawnPoint;

    public CraftPreventSleepPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
        if (jsonObject.has("message")) {
            this.message = jsonObject.get("message").getAsString();
        }
        if (jsonObject.has("set_spawn_point")) {
            this.setSpawnPoint = jsonObject.get("set_spawn_point").getAsBoolean();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (!setSpawnPoint) {
                    Block block = event.getClickedBlock();

                    if (block != null) {
                        Material material = block.getType();

                        if (Tag.BEDS.isTagged(material)) {
                            if (getCondition().test(player) && blockCondition.test(block)) {
                                event.setCancelled(true);
                                ChatUtils.sendPlayerMessage(player, message);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            if (getCondition().test(player) && blockCondition.test(event.getBed())) {
                event.setCancelled(true);
                ChatUtils.sendPlayerMessage(player, message);
            }
        }
    }
}

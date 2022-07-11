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

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftInteractionPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class CraftActionOnBlockUsePower extends CraftInteractionPower {

    private final me.lemonypancakes.bukkit.origins.entity.player.power.action.Action<Entity> entityAction;
    private final me.lemonypancakes.bukkit.origins.entity.player.power.action.Action<Block> blockAction;
    private final Condition<Block> blockCondition;
    private BlockFace[] directions;

    public CraftActionOnBlockUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
        this.blockAction = plugin.getLoader().loadAction(DataType.BLOCK, jsonObject, "block_action");
        this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
        if (jsonObject.has("directions")) {
            this.directions = new Gson().fromJson(jsonObject.get("directions"), BlockFace[].class);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();

                if (directions != null) {
                    if (!Arrays.asList(directions).contains(event.getBlockFace())) {
                        return;
                    }
                }
                if (getHands() != null) {
                    if (!Arrays.asList(getHands()).contains(event.getHand())) {
                        return;
                    }
                }
                if (getCondition().test(player) && blockCondition.test(block) && getItemCondition().test(event.getItem())) {
                    entityAction.accept(player);
                    blockAction.accept(block);
                    if (getResultStack() != null) {
                        player.getInventory().addItem(getResultStack());
                        getResultItemAction().accept(getResultStack());
                    }
                    getHeldItemAction().accept(event.getItem());
                }
            }
        }
    }
}

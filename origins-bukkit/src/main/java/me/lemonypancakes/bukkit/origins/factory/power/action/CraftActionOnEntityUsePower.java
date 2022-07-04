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

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftInteractionPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CraftActionOnEntityUsePower extends CraftInteractionPower {

    private final Action<BiEntity> biEntityAction;
    private final Condition<BiEntity> biEntityCondition;

    public CraftActionOnEntityUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            Entity entity = event.getRightClicked();
            BiEntity biEntity = new BiEntity(player, entity);
            ItemStack heldItem = player.getItemInUse();

            if (getHands() != null) {
                if (!Arrays.asList(getHands()).contains(event.getHand())) {
                    return;
                }
            }
            if (getCondition().test(player) && biEntityCondition.test(biEntity) && getItemCondition().test(heldItem)) {
                biEntityAction.accept(biEntity);
                if (getResultStack() != null) {
                    player.getInventory().addItem(getResultStack());
                    getResultItemAction().accept(getResultStack());
                }
                getHeldItemAction().accept(heldItem);
            }
        }
    }
}

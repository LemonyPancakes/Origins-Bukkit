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

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class CraftPreventDeathPower extends CraftPower {

    private final Condition<Damage> damageCondition;
    private final Action<Entity> entityAction;

    public CraftPreventDeathPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (hasMember(player)) {
                if (player.getHealth() - event.getFinalDamage() <= 0) {
                    if (getCondition().test(player) && damageCondition.test(new Damage(event.getFinalDamage(), null, event.getCause()))) {
                        event.setCancelled(true);
                        entityAction.accept(player);
                    }
                }
            }
        }
    }
}

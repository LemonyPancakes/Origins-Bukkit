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
package me.lemonypancakes.bukkit.origins.factory.power.modify;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import me.lemonypancakes.bukkit.origins.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftModifyDamageDealtPower extends CraftPower {

    private final Condition<BiEntity> biEntityCondition;
    private final Condition<Damage> damageCondition;
    private final Condition<Entity> targetCondition;
    private final Action<BiEntity> biEntityAction;
    private final Action<Entity> selfAction;
    private final Action<Entity> targetAction;

    public CraftModifyDamageDealtPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.targetCondition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject, "target_condition");
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.selfAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "self_action");
        this.targetAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "target_action");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity actorEntity = event.getDamager();

        if (actorEntity instanceof Player) {
            Player actorPlayer = (Player) actorEntity;

            if (hasMember(actorPlayer)) {
                Entity targetEntity = event.getEntity();
                BiEntity biEntity = new BiEntity(actorPlayer, targetEntity);

                if (getCondition().test(actorPlayer) && biEntityCondition.test(biEntity) && damageCondition.test(new Damage(event.getFinalDamage(), actorPlayer, event.getCause())) && targetCondition.test(targetEntity)) {
                    biEntityAction.accept(biEntity);
                    selfAction.accept(actorPlayer);
                    targetAction.accept(targetEntity);
                }
            }
        }
    }
}

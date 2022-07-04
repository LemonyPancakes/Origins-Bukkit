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

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import me.lemonypancakes.bukkit.origins.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftModifyDamageTakenPower extends CraftPower {

    private final Condition<BiEntity> biEntityCondition;
    private final Condition<Damage> damageCondition;
    private final Action<BiEntity> biEntityAction;
    private final Action<Entity> selfAction;
    private final Action<Entity> attackerAction;

    public CraftModifyDamageTakenPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.selfAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "self_action");
        this.attackerAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "attacker_action");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity targetEntity = event.getEntity();

        if (targetEntity instanceof Player) {
            Player targetPlayer = (Player) targetEntity;

            if (hasMember(targetPlayer)) {
                Entity actorEntity = event.getDamager();
                BiEntity biEntity = new BiEntity(actorEntity, targetPlayer);

                if (getCondition().test(targetPlayer) && biEntityCondition.test(biEntity) && damageCondition.test(new Damage(event.getFinalDamage(), actorEntity, event.getCause()))) {
                    biEntityAction.accept(biEntity);
                    selfAction.accept(targetPlayer);
                    attackerAction.accept(actorEntity);
                }
            }
        }
    }
}

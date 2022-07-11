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
package me.lemonypancakes.bukkit.origins.factory.condition;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Comparison;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.BiPredicate;

public class DamageConditions {

    public DamageConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "amount"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, damage) -> {
            if (damage != null) {
                return Comparison.parseComparison(jsonObject).compare(damage.getAmount(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attacker"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new Attacker(p, j, null)));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fire"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, damage) -> {
            if (damage != null) {
                EntityDamageEvent.DamageCause damageCause = damage.getDamageCause();

                if (damageCause != null) {
                    return damageCause == EntityDamageEvent.DamageCause.FIRE || damageCause == EntityDamageEvent.DamageCause.FIRE_TICK || damageCause == EntityDamageEvent.DamageCause.LAVA;
                }
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "name"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, damage) -> {
            if (damage != null) {
                EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.valueOf(jsonObject.get("name").getAsString());

                if (damage.getDamageCause() != null) {
                    return damage.getDamageCause() == damageCause;
                }
            }
            return false;
        })));
        plugin.getRegistry().registerConditionFactory(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "projectile"), DataType.DAMAGE, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, damage) -> {
            if (damage != null) {
                Entity damager = damage.getEntity();

                if (damager != null) {
                    if (damager instanceof Projectile) {
                        if (!jsonObject.has("projectile")) {
                            return true;
                        }
                        Projectile projectile = (Projectile) damager;

                        return projectile.getType() == EntityType.valueOf(jsonObject.get("projectile").getAsString());
                    }
                }
            }
            return false;
        })));
    }

    public static class Attacker extends CraftCondition<Damage> {

        private Condition<Entity> condition;

        public Attacker(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Damage> biPredicate) {
            super(plugin, jsonObject, DataType.DAMAGE, biPredicate);
            if (jsonObject != null) {
                this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
                setBiPredicate(((jsonObject1, damage) -> {
                    if (damage != null) {
                        Entity damager = damage.getEntity();

                        if (damager != null) {
                            if (condition == null) {
                                return true;
                            }
                            return condition.test(damager);
                        }
                    }
                    return false;
                }));
            }
        }
    }
}

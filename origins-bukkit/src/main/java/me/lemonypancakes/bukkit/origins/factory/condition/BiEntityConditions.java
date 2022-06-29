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

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Comparison;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.util.RayTraceResult;

import java.util.function.BiPredicate;

public class BiEntityConditions {

    public BiEntityConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attack_target"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (actor instanceof Monster && target instanceof LivingEntity) {
                        Monster monster = (Monster) actor;
                        LivingEntity livingEntity = (LivingEntity) target;

                        return monster.getTarget() != null && monster.getTarget() == livingEntity;
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attacker"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> false)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "can_see"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (actor instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) actor;
                        RayTraceResult rayTraceResult = livingEntity.getWorld().rayTraceEntities(livingEntity.getEyeLocation(), livingEntity.getEyeLocation().getDirection(), Bukkit.getViewDistance() * 16, entity -> entity == actor);

                        if (rayTraceResult != null) {
                            return rayTraceResult.getHitEntity() != null && rayTraceResult.getHitEntity() == target;
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "distance"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    return Comparison.parseComparison(jsonObject).compare(actor.getLocation().distance(target.getLocation()), jsonObject);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "owner"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (actor instanceof AnimalTamer) {
                        AnimalTamer animalTamer = (AnimalTamer) actor;

                        if (target instanceof Tameable) {
                            Tameable tameable = (Tameable) target;

                            return tameable.getOwner() == animalTamer;
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "relative_rotation"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "riding_recursive"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "riding_root"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "riding"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    return target.getPassengers().contains(actor);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "actor_condition"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.ActorCondition(p, j, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "both"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.Both(p, j, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "either"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.Either(p, j, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invert"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.Invert(p, j, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "target_condition"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.TargetCondition(p, j, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "undirected"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.Undirected(p, j, null)));
    }

    public static class Meta {

        public static class ActorCondition extends CraftCondition<BiEntity> {

            private Condition<Entity> condition;

            public ActorCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, BiEntity> biPredicate) {
                super(plugin, jsonObject, DataType.BI_ENTITY, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
                    setBiPredicate(((jsonObject1, biEntity) -> {
                        if (biEntity != null) {
                            Entity actor = biEntity.getActor();

                            if (actor != null) {
                                return condition.test(biEntity.getActor());
                            }
                        }
                        return false;
                    }));
                }
            }
        }

        public static class Both extends CraftCondition<BiEntity> {

            private Condition<Entity> condition;

            public Both(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, BiEntity> biPredicate) {
                super(plugin, jsonObject, DataType.BI_ENTITY, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
                    setBiPredicate(((jsonObject1, biEntity) -> {
                        if (biEntity != null) {
                            Entity actor = biEntity.getActor();
                            Entity target = biEntity.getTarget();

                            if (actor != null && target != null) {
                                return condition.test(biEntity.getActor()) && condition.test(biEntity.getTarget());
                            }
                        }
                        return false;
                    }));
                }
            }
        }

        public static class Either extends CraftCondition<BiEntity> {

            private Condition<Entity> condition;

            public Either(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, BiEntity> biPredicate) {
                super(plugin, jsonObject, DataType.BI_ENTITY, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
                    setBiPredicate(((jsonObject1, biEntity) -> {
                        if (biEntity != null) {
                            Entity actor = biEntity.getActor();
                            Entity target = biEntity.getTarget();

                            if (actor != null && target != null) {
                                return condition.test(biEntity.getActor()) || condition.test(biEntity.getTarget());
                            }
                        }
                        return false;
                    }));
                }
            }
        }

        public static class Invert extends CraftCondition<BiEntity> {

            private Condition<BiEntity> condition;

            public Invert(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, BiEntity> biPredicate) {
                super(plugin, jsonObject, DataType.BI_ENTITY, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject);
                    setBiPredicate(((jsonObject1, biEntity) -> {
                        if (biEntity != null) {
                            Entity actor = biEntity.getActor();
                            Entity target = biEntity.getTarget();

                            if (actor != null && target != null) {
                                return condition.test(new BiEntity(biEntity.getTarget(), biEntity.getActor()));
                            }
                        }
                        return false;
                    }));
                }
            }
        }

        public static class TargetCondition extends CraftCondition<BiEntity> {

            private Condition<Entity> condition;

            public TargetCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, BiEntity> biPredicate) {
                super(plugin, jsonObject, DataType.BI_ENTITY, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
                    setBiPredicate(((jsonObject1, biEntity) -> {
                        if (biEntity != null) {
                            Entity target = biEntity.getTarget();

                            if (target != null) {
                                return condition.test(biEntity.getTarget());
                            }
                        }
                        return false;
                    }));
                }
            }
        }

        public static class Undirected extends CraftCondition<BiEntity> {

            private Condition<BiEntity> condition;

            public Undirected(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, BiEntity> biPredicate) {
                super(plugin, jsonObject, DataType.BI_ENTITY, biPredicate);
                if (jsonObject != null) {
                    this.condition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject);
                    setBiPredicate(((jsonObject1, biEntity) -> {
                        if (biEntity != null) {
                            Entity actor = biEntity.getActor();
                            Entity target = biEntity.getTarget();

                            if (actor != null && target != null) {
                                return condition.test(biEntity) || condition.test(new BiEntity(biEntity.getTarget(), biEntity.getActor()));
                            }
                        }
                        return false;
                    }));
                }
            }
        }
    }
}

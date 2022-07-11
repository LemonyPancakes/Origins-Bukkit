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
package me.lemonypancakes.bukkit.origins.factory.action;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.CraftAction;
import me.lemonypancakes.bukkit.origins.factory.action.meta.*;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.function.BiConsumer;

public class BiEntityActions {

    public BiEntityActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAndAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftChanceAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftChoiceAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftDelayAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftIfElseAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftIfElseListAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftNothingAction<>(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_velocity"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    Vector vector = target.getVelocity();
                    float x = 0;
                    float y = 0;
                    float z = 0;
                    boolean setVelocity = false;

                    if (jsonObject.has("x")) {
                        x = jsonObject.get("x").getAsFloat();
                    }
                    if (jsonObject.has("y")) {
                        y = jsonObject.get("y").getAsFloat();
                    }
                    if (jsonObject.has("z")) {
                        z = jsonObject.get("z").getAsFloat();
                    }
                    if (jsonObject.has("set_velocity")) {
                        setVelocity = jsonObject.get("set_velocity").getAsBoolean();
                    }
                    if (setVelocity) {
                        target.setVelocity(new Vector(x, y, z));
                    } else {
                        target.setVelocity(new Vector(vector.getX() + x, vector.getY() + y, vector.getZ() + z));
                    }
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "damage"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    Damageable damageable = (Damageable) target;
                    double amount = 0;

                    if (jsonObject.has("amount")) {
                        amount = jsonObject.get("amount").getAsDouble();
                    }
                    damageable.damage(amount);
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "mount"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    target.addPassenger(actor);
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "set_in_love"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (target instanceof Animals) {
                        Animals animals = (Animals) target;
                        int duration = 600;

                        if (jsonObject.has("duration")) {
                            duration = jsonObject.get("duration").getAsInt();
                        }
                        animals.setLoveModeTicks(duration);
                    }
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "tame"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (actor instanceof AnimalTamer) {
                        AnimalTamer animalTamer = (AnimalTamer) actor;

                        if (target instanceof Tameable) {
                            Tameable tameable = (Tameable) target;

                            tameable.setTamed(true);
                            tameable.setOwner(animalTamer);
                        }
                    }
                }
            }
        })));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "actor_action"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.ActorAction(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invert"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.Invert(p, j, d, null)));
        plugin.getRegistry().registerActionFactory(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "target_action"), DataType.BI_ENTITY, (p) -> (j) -> (d) -> () -> new Meta.TargetAction(p, j, d, null)));
    }

    public static class Meta {

        public static class ActorAction extends CraftAction<BiEntity> {

            private Action<Entity> action;

            public ActorAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<BiEntity> dataType, BiConsumer<JsonObject, BiEntity> biConsumer) {
                super(plugin, jsonObject, dataType, biConsumer);
                if (jsonObject != null) {
                    this.action = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject);
                    setBiConsumer((jsonObject1, biEntity) -> action.accept(biEntity.getActor()));
                }
            }
        }

        public static class Invert extends CraftAction<BiEntity> {

            private Action<BiEntity> action;

            public Invert(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<BiEntity> dataType, BiConsumer<JsonObject, BiEntity> biConsumer) {
                super(plugin, jsonObject, dataType, biConsumer);
                if (jsonObject != null) {
                    this.action = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject);
                    setBiConsumer((jsonObject1, biEntity) -> action.accept(new BiEntity(biEntity.getTarget(), biEntity.getActor())));
                }
            }
        }

        public static class TargetAction extends CraftAction<BiEntity> {

            private Action<Entity> action;

            public TargetAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<BiEntity> dataType, BiConsumer<JsonObject, BiEntity> biConsumer) {
                super(plugin, jsonObject, dataType, biConsumer);
                if (jsonObject != null) {
                    this.action = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject);
                    setBiConsumer((jsonObject1, biEntity) -> action.accept(biEntity.getTarget()));
                }
            }
        }
    }
}

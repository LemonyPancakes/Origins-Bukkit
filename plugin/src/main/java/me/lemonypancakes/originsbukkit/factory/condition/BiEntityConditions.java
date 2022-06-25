package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.originsbukkit.util.Comparison;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.util.RayTraceResult;

import java.util.function.BiPredicate;

public class BiEntityConditions {

    public BiEntityConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftOrCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attack_target"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attacker"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> false)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "can_see"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "distance"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    return Comparison.parseComparison(jsonObject1).compare(actor.getLocation().distance(target.getLocation()), jsonObject1);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "owner"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "relative_rotation"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "riding_recursive"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "riding_root"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "riding"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    return target.getPassengers().contains(actor);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "actor_condition"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.ActorCondition(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "both"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.Both(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "either"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.Either(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invert"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.Invert(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "target_condition"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.TargetCondition(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "undirected"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.Undirected(plugin, null, null)));
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

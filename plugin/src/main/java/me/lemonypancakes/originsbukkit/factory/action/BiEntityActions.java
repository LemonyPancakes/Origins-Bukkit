package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.factory.action.meta.*;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.function.BiConsumer;

public class BiEntityActions {

    public BiEntityActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftChanceAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftChoiceAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftDelayAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftIfElseAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftIfElseListAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftNothingAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_velocity"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    Vector vector = target.getVelocity();
                    float x = 0;
                    float y = 0;
                    float z = 0;
                    boolean setVelocity = false;

                    if (jsonObject1.has("x")) {
                        x = jsonObject1.get("x").getAsFloat();
                    }
                    if (jsonObject1.has("y")) {
                        y = jsonObject1.get("y").getAsFloat();
                    }
                    if (jsonObject1.has("z")) {
                        z = jsonObject1.get("z").getAsFloat();
                    }
                    if (jsonObject1.has("set_velocity")) {
                        setVelocity = jsonObject1.get("set_velocity").getAsBoolean();
                    }
                    if (setVelocity) {
                        target.setVelocity(new Vector(x, y, z));
                    } else {
                        target.setVelocity(new Vector(vector.getX() + x, vector.getY() + y, vector.getZ() + z));
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "damage"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    Damageable damageable = (Damageable) target;
                    double amount = 0;

                    if (jsonObject1.has("amount")) {
                        amount = jsonObject1.get("amount").getAsDouble();
                    }
                    damageable.damage(amount);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "mount"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    target.addPassenger(actor);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "set_in_love"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (target instanceof Animals) {
                        Animals animals = (Animals) target;
                        int duration = 600;

                        if (jsonObject1.has("duration")) {
                            duration = jsonObject1.get("duration").getAsInt();
                        }
                        animals.setLoveModeTicks(duration);
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "tame"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, biEntity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "actor_action"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.ActorAction(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invert"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.Invert(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "target_action"), DataType.BI_ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new Meta.TargetAction(plugin1, jsonObject, dataType, null)));
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

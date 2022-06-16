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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BI_ENTITY, new CraftAndAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.BI_ENTITY, new CraftChanceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.BI_ENTITY, new CraftChoiceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.BI_ENTITY, new CraftDelayAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.BI_ENTITY, new CraftIfElseAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.BI_ENTITY, new CraftIfElseListAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.BI_ENTITY, new CraftNothingAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_velocity"), DataType.BI_ENTITY, new CraftAction<>(plugin, null, (jsonObject, biEntity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "damage"), DataType.BI_ENTITY, new CraftAction<>(plugin, null, (jsonObject, biEntity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "mount"), DataType.BI_ENTITY, new CraftAction<>(plugin, null, (jsonObject, biEntity) -> {
            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    target.addPassenger(actor);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "set_in_love"), DataType.BI_ENTITY, new CraftAction<>(plugin, null, (jsonObject, biEntity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "tame"), DataType.BI_ENTITY, new CraftAction<>(plugin, null, (jsonObject, biEntity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "actor_action"), DataType.BI_ENTITY, new Meta.ActorAction(plugin, null, DataType.BI_ENTITY, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invert"), DataType.BI_ENTITY, new Meta.Invert(plugin, null, DataType.BI_ENTITY, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "target_action"), DataType.BI_ENTITY, new Meta.TargetAction(plugin, null, DataType.BI_ENTITY, null)));
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

            @Override
            public Action<BiEntity> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<BiEntity> dataType) {
                return new ActorAction(plugin, jsonObject, dataType, getBiConsumer());
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

            @Override
            public Action<BiEntity> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<BiEntity> dataType) {
                return new Invert(plugin, jsonObject, dataType, getBiConsumer());
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

            @Override
            public Action<BiEntity> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<BiEntity> dataType) {
                return new TargetAction(plugin, jsonObject, dataType, getBiConsumer());
            }
        }
    }
}

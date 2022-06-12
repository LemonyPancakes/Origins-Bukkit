package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.BiEntity;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class BiEntityActions {

    public BiEntityActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "add_velocity"), new CraftAction(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

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
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "damage"), new CraftAction(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

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
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "mount"), new CraftAction(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    target.addPassenger(actor);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "set_in_love"), new CraftAction(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

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
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "tame"), new CraftAction(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

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
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "actor_action"), new Meta.ActorAction(plugin, null, (jsonObject, temp) -> {

        })));
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "invert"), new Meta.Invert(plugin, null, (jsonObject, temp) -> {

        })));
        plugin.getRegistry().register(new Action.Factory(new Identifier(OriginsBukkit.KEY, "target_action"), new Meta.TargetAction(plugin, null, (jsonObject, temp) -> {

        })));
    }

    public static class Meta {

        public static class ActorAction extends CraftAction {

            private Action[] actions;

            public ActorAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiConsumer<JsonObject, Temp> biConsumer) {
                super(plugin, jsonObject, biConsumer);
                if (jsonObject != null) {
                    this.actions = OriginsBukkit.getLoader().loadActions(jsonObject.getAsJsonArray("action"));
                    setBiConsumer((jsonObject1, temp) -> {
                        BiEntity biEntity = temp.get(DataType.BI_ENTITY);

                        if (biEntity != null) {
                            temp.set(DataType.ENTITY, biEntity.getActor());
                            Arrays.stream(actions).forEach(action -> action.accept(temp));
                        }
                    });
                }
            }

            @Override
            public void setJsonObject(JsonObject jsonObject) {
                super.setJsonObject(jsonObject);
                actions = OriginsBukkit.getLoader().loadActions(getJsonObject());
            }
        }

        public static class Invert extends CraftAction {

            private Action[] actions;

            public Invert(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiConsumer<JsonObject, Temp> biConsumer) {
                super(plugin, jsonObject, biConsumer);
                if (jsonObject != null) {
                    this.actions = OriginsBukkit.getLoader().loadActions(jsonObject.getAsJsonArray("action"));
                    setBiConsumer(((jsonObject1, temp) -> {
                        BiEntity biEntity = temp.get(DataType.BI_ENTITY);

                        if (biEntity != null) {
                            Temp temp1 = new CraftTemp();

                            temp1.set(DataType.BI_ENTITY, new BiEntity(biEntity.getTarget(), biEntity.getActor()));
                            Arrays.stream(actions).forEach(action -> action.accept(temp1));
                        }
                    }));
                }
            }

            @Override
            public Action newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
                return new Invert(plugin, jsonObject, null);
            }
        }

        public static class TargetAction extends CraftAction {

            private Action[] actions;

            public TargetAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiConsumer<JsonObject, Temp> biConsumer) {
                super(plugin, jsonObject, biConsumer);
                if (jsonObject != null) {
                    this.actions = OriginsBukkit.getLoader().loadActions(jsonObject.getAsJsonArray("action"));
                    setBiConsumer(((jsonObject1, temp) -> {
                        BiEntity biEntity = temp.get(DataType.BI_ENTITY);

                        if (biEntity != null) {
                            temp.set(DataType.ENTITY, biEntity.getTarget());
                            Arrays.stream(actions).forEach(action -> action.accept(temp));
                        }
                    }));
                }
            }

            @Override
            public void setJsonObject(JsonObject jsonObject) {
                super.setJsonObject(jsonObject);
                actions = OriginsBukkit.getLoader().loadActions(getJsonObject());
            }
        }
    }
}

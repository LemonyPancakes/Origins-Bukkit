package me.lemonypancakes.originsbukkit.factory.listener.special;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.TempContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.api.events.world.WorldDayAndNightCycleEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.Random;

public class WorldDayAndNightCycle extends ListenerPowerContainer {

    private String worldToListen = "world";

    public WorldDayAndNightCycle(Identifier identifier,
                                 JsonObject jsonObject,
                                 Action<?>[] actions,
                                 Condition<?> condition,
                                 boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
        if (!isFactory()) {
            if (jsonObject != null) {
                if (jsonObject.has("fields")) {
                    JsonObject fields = jsonObject.getAsJsonObject("fields");

                    if (fields != null) {
                        if (fields.has("world_to_listen")) {
                            this.worldToListen = fields.get("world_to_listen").getAsString();
                        }
                    }
                }
            }
        }
    }

    public WorldDayAndNightCycle(Identifier identifier,
                                 JsonObject jsonObject,
                                 Action<?>[] actions,
                                 boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public WorldDayAndNightCycle(Identifier identifier,
                                 JsonObject jsonObject,
                                 boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public WorldDayAndNightCycle(Identifier identifier,
                                 boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public WorldDayAndNightCycle(boolean isFactory) {
        this(null, isFactory);
    }

    public WorldDayAndNightCycle() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new WorldDayAndNightCycle(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new WorldDayAndNightCycle(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new WorldDayAndNightCycle(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new WorldDayAndNightCycle(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new WorldDayAndNightCycle();
    }

    @Override
    protected <T> void onInvoke(T t) {
        if (t instanceof Temp) {
            Player player = ((Temp) t).getPlayer();

            if (getPlayersToListen().contains(player)) {
                World world = Bukkit.getWorld(worldToListen);

                if (world != null) {
                    WorldDayAndNightCycleEvent.Time time;

                    if (world.getTime() > 0 && world.getTime() < 13000) {
                        time = WorldDayAndNightCycleEvent.Time.DAY;
                    } else {
                        time = WorldDayAndNightCycleEvent.Time.NIGHT;
                    }
                    WorldDayAndNightCycleEvent nightCycleEvent = new WorldDayAndNightCycleEvent(world, time);
                    Temp temp = new TempContainer();

                    temp.setPlayer(player);
                    temp.setEvent(nightCycleEvent);
                    if (getCondition() != null) {
                        if (getCondition().test(temp)) {
                            if (getActions() != null) {
                                if (getProbability() != null) {
                                    Random random = new Random();
                                    int probability = random.nextInt(getProbability());

                                    if (isInvertProbability()) {
                                        if (probability == 0) {
                                            return;
                                        }
                                    } else {
                                        if (probability != 0) {
                                            return;
                                        }
                                    }
                                }
                                Arrays.stream(getActions()).forEach(
                                        action -> action.accept(temp)
                                );
                            }
                        }
                    } else {
                        if (getActions() != null) {
                            if (getProbability() != null) {
                                Random random = new Random();
                                int probability = random.nextInt(getProbability());

                                if (isInvertProbability()) {
                                    if (probability == 0) {
                                        return;
                                    }
                                } else {
                                    if (probability != 0) {
                                        return;
                                    }
                                }
                            }
                            Arrays.stream(getActions()).forEach(
                                    action -> action.accept(temp)
                            );
                        }
                    }
                }
            }
        }
    }

    public static Power getFactory() {
        return new WorldDayAndNightCycle(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/special/world_day_and_night_cycle"
                ), true
        );
    }

    @EventHandler
    private void onDayAndNightCycle(WorldDayAndNightCycleEvent event) {
        if (event.getWorld().getName().equals(worldToListen)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (getPlayersToListen().contains(player)) {
                    Temp temp = new TempContainer();

                    temp.setPlayer(player);
                    temp.setEvent(event);
                    if (getCondition() != null) {
                        if (getCondition().test(temp)) {
                            if (getActions() != null) {
                                if (getProbability() != null) {
                                    Random random = new Random();
                                    int probability = random.nextInt(getProbability());

                                    if (isInvertProbability()) {
                                        if (probability == 0) {
                                            return;
                                        }
                                    } else {
                                        if (probability != 0) {
                                            return;
                                        }
                                    }
                                }
                                Arrays.stream(getActions()).forEach(
                                        action -> action.accept(temp)
                                );
                            }
                        }
                    } else {
                        if (getActions() != null) {
                            if (getProbability() != null) {
                                Random random = new Random();
                                int probability = random.nextInt(getProbability());

                                if (isInvertProbability()) {
                                    if (probability == 0) {
                                        return;
                                    }
                                } else {
                                    if (probability != 0) {
                                        return;
                                    }
                                }
                            }
                            Arrays.stream(getActions()).forEach(
                                    action -> action.accept(temp)
                            );
                        }
                    }
                }
            }
        }
    }
}

package me.lemonypancakes.originsbukkit.api.data.container.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.SchedulerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.util.Storage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

@SuppressWarnings({"unchecked", "unused"})
public class SinglePowerContainer implements Power {

    private Identifier identifier;
    private JsonObject jsonObject;
    private Action<?>[] actions;
    private Condition<?> condition;

    private Boolean isAsync;

    private boolean isTicking = false;

    private int tickRate = 4;

    public SinglePowerContainer(Identifier identifier,
                                JsonObject jsonObject,
                                Action<?>[] actions,
                                Condition<?> condition) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.actions = actions;
        this.condition = condition;
        if (jsonObject != null) {
            if (jsonObject.has("async")) {
                this.isAsync = jsonObject.get("async").getAsBoolean();
            }
            if (jsonObject.has("ticking")) {
                this.isTicking = jsonObject.get("ticking").getAsBoolean();
            }
            if (jsonObject.has("tick_rate")) {
                this.tickRate = jsonObject.get("tick_rate").getAsInt();
            }
        }
    }

    public SinglePowerContainer(Identifier identifier,
                                JsonObject jsonObject,
                                Action<?>[] actions) {
        this(identifier, jsonObject, actions, null);
    }

    public SinglePowerContainer(Identifier identifier,
                                JsonObject jsonObject) {
        this(identifier, jsonObject, null);
    }

    public SinglePowerContainer(Identifier identifier) {
        this(identifier, null);
    }

    public SinglePowerContainer() {
        this(null);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        if (jsonObject != null) {
            if (jsonObject.has("async")) {
                this.isAsync = jsonObject.get("async").getAsBoolean();
            }
            if (jsonObject.has("ticking")) {
                this.isTicking = jsonObject.get("ticking").getAsBoolean();
            }
            if (jsonObject.has("tick_rate")) {
                this.tickRate = jsonObject.get("tick_rate").getAsInt();
            }
        }
    }

    @Override
    public <T> Action<T>[] getActions() {
        return (Action<T>[]) actions;
    }

    @Override
    public void setActions(Action<?>[] action) {
        this.actions = action;
    }

    @Override
    public <T> Condition<T> getCondition() {
        return (Condition<T>) condition;
    }

    @Override
    public <T> void setCondition(Condition<T> condition) {
        this.condition = condition;
    }

    @Override
    public <T> void invoke(T t) {
        if (this.isAsync != null) {
            if (!this.isAsync) {
                if (getCondition() != null) {
                    if (!this.isTicking) {
                        if (getCondition().test(t)) {
                            for (Action<Object> action : getActions()) {
                                action.accept(t);
                            }
                        }
                    } else {
                        if (t instanceof Temp) {
                            Temp temp = (Temp) t;
                            Player player = temp.getPlayer();

                            Storage.removeSchedulerDataFromStorage(player);
                            BukkitTask tick = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (player.isOnline()) {
                                        if (getCondition().test(temp)) {
                                            for (Action<Object> action : getActions()) {
                                                action.accept(temp);
                                            }
                                        }
                                    } else {
                                        Storage.removeSchedulerDataFromStorage(player);
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                            Storage.addDataToStorage(
                                    player,
                                    new SchedulerContainer(
                                            getIdentifier(),
                                            tick
                                    )
                            );
                        }
                    }
                } else {
                    if (!this.isTicking) {
                        for (Action<Object> action : getActions()) {
                            action.accept(t);
                        }
                    } else {
                        if (t instanceof Temp) {
                            Temp temp = (Temp) t;
                            Player player = temp.getPlayer();

                            Storage.removeSchedulerDataFromStorage(player);
                            BukkitTask tick = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (player.isOnline()) {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(temp);
                                        }
                                    } else {
                                        Storage.removeSchedulerDataFromStorage(player);
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                            Storage.addDataToStorage(
                                    player,
                                    new SchedulerContainer(
                                            getIdentifier(),
                                            tick
                                    )
                            );
                        }
                    }
                }
            } else {
                Bukkit.getScheduler()
                        .runTaskAsynchronously(
                                OriginsBukkit.getPlugin(),
                                bukkitTask -> {
                                    if (getCondition() != null) {
                                        if (!this.isTicking) {
                                            if (getCondition().test(t)) {
                                                for (Action<Object> action : getActions()) {
                                                    action.accept(t);
                                                }
                                            }
                                        } else {
                                            if (t instanceof Temp) {
                                                Temp temp = (Temp) t;
                                                Player player = temp.getPlayer();

                                                Storage.removeSchedulerDataFromStorage(player);
                                                BukkitTask tick = new BukkitRunnable() {

                                                    @Override
                                                    public void run() {
                                                        if (player.isOnline()) {
                                                            if (getCondition().test(temp)) {
                                                                for (Action<Object> action : getActions()) {
                                                                    action.accept(temp);
                                                                }
                                                            }
                                                        } else {
                                                            Storage.removeSchedulerDataFromStorage(player);
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimerAsynchronously(
                                                        OriginsBukkit.getPlugin(),
                                                        0,
                                                        tickRate
                                                );
                                                Storage.addDataToStorage(
                                                        player,
                                                        new SchedulerContainer(
                                                                getIdentifier(),
                                                                tick
                                                        )
                                                );
                                            }
                                        }
                                    } else {
                                        if (!this.isTicking) {
                                            for (Action<Object> action : getActions()) {
                                                action.accept(t);
                                            }
                                        } else {
                                            if (t instanceof Temp) {
                                                Temp temp = (Temp) t;
                                                Player player = temp.getPlayer();

                                                Storage.removeSchedulerDataFromStorage(player);
                                                BukkitTask tick = new BukkitRunnable() {

                                                    @Override
                                                    public void run() {
                                                        if (player.isOnline()) {
                                                            for (Action<Object> action : getActions()) {
                                                                action.accept(temp);
                                                            }
                                                        } else {
                                                            Storage.removeSchedulerDataFromStorage(player);
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimerAsynchronously(
                                                        OriginsBukkit.getPlugin(),
                                                        0,
                                                        tickRate
                                                );
                                                Storage.addDataToStorage(
                                                        player,
                                                        new SchedulerContainer(
                                                                getIdentifier(),
                                                                tick
                                                        )
                                                );
                                            }
                                        }
                                    }
                                });
            }
        } else {
            if (getCondition() != null) {
                if (!this.isTicking) {
                    if (getCondition().test(t)) {
                        for (Action<Object> action : getActions()) {
                            action.accept(t);
                        }
                    }
                } else {
                    if (t instanceof Temp) {
                        Temp temp = (Temp) t;
                        Player player = temp.getPlayer();

                        Storage.removeSchedulerDataFromStorage(player);
                        BukkitTask tick = new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (player.isOnline()) {
                                    if (getCondition().test(temp)) {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(temp);
                                        }
                                    }
                                } else {
                                    Storage.removeSchedulerDataFromStorage(player);
                                    cancel();
                                }
                            }
                        }.runTaskTimer(
                                OriginsBukkit.getPlugin(),
                                0,
                                tickRate
                        );
                        Storage.addDataToStorage(
                                player,
                                new SchedulerContainer(
                                        getIdentifier(),
                                        tick
                                )
                        );
                    }
                }
            } else {
                if (!this.isTicking) {
                    for (Action<Object> action : getActions()) {
                        action.accept(t);
                    }
                } else {
                    if (t instanceof Temp) {
                        Temp temp = (Temp) t;
                        Player player = temp.getPlayer();

                        Storage.removeSchedulerDataFromStorage(player);
                        BukkitTask tick = new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (player.isOnline()) {
                                    for (Action<Object> action : getActions()) {
                                        action.accept(temp);
                                    }
                                } else {
                                    Storage.removeSchedulerDataFromStorage(player);
                                    cancel();
                                }
                            }
                        }.runTaskTimer(
                                OriginsBukkit.getPlugin(),
                                0,
                                tickRate
                        );
                        Storage.addDataToStorage(
                                player,
                                new SchedulerContainer(
                                        getIdentifier(),
                                        tick
                                )
                        );
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "SinglePowerContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", actions=" + Arrays.toString(actions) +
                ", condition=" + condition +
                ", isAsync=" + isAsync +
                ", isTicking=" + isTicking +
                ", tickRate=" + tickRate +
                '}';
    }
}
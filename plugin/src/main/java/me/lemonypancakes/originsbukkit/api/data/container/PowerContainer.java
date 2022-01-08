package me.lemonypancakes.originsbukkit.api.data.container;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "unused"})
public class PowerContainer implements Power {

    private Identifier identifier;
    private JsonObject jsonObject;
    private Action<?>[] actions;
    private Condition<?> condition;

    private Boolean isAsync;

    private boolean isTicking = false;

    private int tickRate = 4;

    private final Map<Object, Object> buffer = new HashMap<>();

    public PowerContainer(Identifier identifier,
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

    public PowerContainer(Identifier identifier,
                          JsonObject jsonObject,
                          Action<?>[] actions) {
        this(identifier, jsonObject, actions, null);
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

    public PowerContainer(Identifier identifier,
                          JsonObject jsonObject) {
        this(identifier, jsonObject, null);
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

    public PowerContainer(Identifier identifier) {
        this(identifier, null);
        this.identifier = identifier;
    }

    public PowerContainer() {}

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
                        if (t instanceof Player) {
                            Player player = (Player) t;

                            if (buffer.containsKey(player.getUniqueId())) {
                                ((BukkitTask) buffer.get(player.getUniqueId())).cancel();
                            }
                            BukkitTask tick = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (player.isOnline()) {
                                        if (getCondition().test(player)) {
                                            for (Action<Object> action : getActions()) {
                                                action.accept(player);
                                            }
                                        }
                                    } else {
                                        buffer.remove(player.getUniqueId());
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                            buffer.put(player.getUniqueId(), tick);
                        } else {
                            if (!buffer.containsKey(t)) {
                                buffer.put(t, t);

                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        if (getCondition().test(t)) {
                                            for (Action<Object> action : getActions()) {
                                                action.accept(t);
                                            }
                                        }
                                    }
                                }.runTaskTimer(
                                        OriginsBukkit.getPlugin(),
                                        0,
                                        tickRate
                                );
                            }
                        }
                    }
                } else {
                    if (!this.isTicking) {
                        for (Action<Object> action : getActions()) {
                            action.accept(t);
                        }
                    } else {
                        if (t instanceof Player) {
                            Player player = (Player) t;

                            if (buffer.containsKey(player.getUniqueId())) {
                                ((BukkitTask) buffer.get(player.getUniqueId())).cancel();
                            }
                            BukkitTask tick = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (player.isOnline()) {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(player);
                                        }
                                    } else {
                                        buffer.remove(player.getUniqueId());
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                            buffer.put(player.getUniqueId(), tick);
                        } else {
                            if (!buffer.containsKey(t)) {
                                buffer.put(t, t);

                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(t);
                                        }
                                    }
                                }.runTaskTimer(
                                        OriginsBukkit.getPlugin(),
                                        0,
                                        tickRate
                                );
                            }
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
                                            if (t instanceof Player) {
                                                Player player = (Player) t;

                                                if (buffer.containsKey(player.getUniqueId())) {
                                                    ((BukkitTask) buffer.get(player.getUniqueId())).cancel();
                                                }
                                                BukkitTask tick = new BukkitRunnable() {

                                                    @Override
                                                    public void run() {
                                                        if (player.isOnline()) {
                                                            if (getCondition().test(player)) {
                                                                for (Action<Object> action : getActions()) {
                                                                    action.accept(player);
                                                                }
                                                            }
                                                        } else {
                                                            buffer.remove(player.getUniqueId());
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimerAsynchronously(
                                                        OriginsBukkit.getPlugin(),
                                                        0,
                                                        tickRate
                                                );
                                                buffer.put(player.getUniqueId(), tick);
                                            } else {
                                                if (!buffer.containsKey(t)) {
                                                    buffer.put(t, t);

                                                    new BukkitRunnable() {

                                                        @Override
                                                        public void run() {
                                                            if (getCondition().test(t)) {
                                                                for (Action<Object> action : getActions()) {
                                                                    action.accept(t);
                                                                }
                                                            }
                                                        }
                                                    }.runTaskTimerAsynchronously(
                                                            OriginsBukkit.getPlugin(),
                                                            0,
                                                            tickRate
                                                    );
                                                }
                                            }
                                        }
                                    } else {
                                        if (!this.isTicking) {
                                            for (Action<Object> action : getActions()) {
                                                action.accept(t);
                                            }
                                        } else {
                                            if (t instanceof Player) {
                                                Player player = (Player) t;

                                                if (buffer.containsKey(player.getUniqueId())) {
                                                    ((BukkitTask) buffer.get(player.getUniqueId())).cancel();
                                                }
                                                BukkitTask tick = new BukkitRunnable() {

                                                    @Override
                                                    public void run() {
                                                        if (player.isOnline()) {
                                                            for (Action<Object> action : getActions()) {
                                                                action.accept(player);
                                                            }
                                                        } else {
                                                            buffer.remove(player.getUniqueId());
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimerAsynchronously(
                                                        OriginsBukkit.getPlugin(),
                                                        0,
                                                        tickRate
                                                );
                                                buffer.put(player.getUniqueId(), tick);
                                            } else {
                                                if (!buffer.containsKey(t)) {
                                                    buffer.put(t, t);

                                                    new BukkitRunnable() {

                                                        @Override
                                                        public void run() {
                                                            for (Action<Object> action : getActions()) {
                                                                action.accept(t);
                                                            }
                                                        }
                                                    }.runTaskTimerAsynchronously(
                                                            OriginsBukkit.getPlugin(),
                                                            0,
                                                            tickRate
                                                    );
                                                }
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
                    if (t instanceof Player) {
                        Player player = (Player) t;

                        if (buffer.containsKey(player.getUniqueId())) {
                            ((BukkitTask) buffer.get(player.getUniqueId())).cancel();
                        }
                        BukkitTask tick = new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (player.isOnline()) {
                                    if (getCondition().test(player)) {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(player);
                                        }
                                    }
                                } else {
                                    buffer.remove(player.getUniqueId());
                                    cancel();
                                }
                            }
                        }.runTaskTimer(
                                OriginsBukkit.getPlugin(),
                                0,
                                tickRate
                        );
                        buffer.put(player.getUniqueId(), tick);
                    } else {
                        if (!buffer.containsKey(t)) {
                            buffer.put(t, t);

                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (getCondition().test(t)) {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(t);
                                        }
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                        }
                    }
                }
            } else {
                if (!this.isTicking) {
                    for (Action<Object> action : getActions()) {
                        action.accept(t);
                    }
                } else {
                    if (t instanceof Player) {
                        Player player = (Player) t;

                        if (buffer.containsKey(player.getUniqueId())) {
                            ((BukkitTask) buffer.get(player.getUniqueId())).cancel();
                        }
                        BukkitTask tick = new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (player.isOnline()) {
                                    for (Action<Object> action : getActions()) {
                                        action.accept(player);
                                    }
                                } else {
                                    buffer.remove(player.getUniqueId());
                                    cancel();
                                }
                            }
                        }.runTaskTimer(
                                OriginsBukkit.getPlugin(),
                                0,
                                tickRate
                        );
                        buffer.put(player.getUniqueId(), tick);
                    } else {
                        if (!buffer.containsKey(t)) {
                            buffer.put(t, t);

                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    for (Action<Object> action : getActions()) {
                                        action.accept(t);
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "PowerContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", actions=" + Arrays.toString(actions) +
                ", condition=" + condition +
                ", isAsync=" + isAsync +
                ", isTicking=" + isTicking +
                ", tickRate=" + tickRate +
                ", buffer=" + buffer +
                '}';
    }
}

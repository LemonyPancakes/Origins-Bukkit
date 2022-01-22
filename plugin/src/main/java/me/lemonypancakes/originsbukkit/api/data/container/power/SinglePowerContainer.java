package me.lemonypancakes.originsbukkit.api.data.container.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.SchedulerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"unchecked", "unused"})
public class SinglePowerContainer implements Power {

    private Identifier identifier;
    private JsonObject jsonObject;
    private Action<?>[] actions;
    private Condition<?> condition;

    private Boolean isAsync;

    private Integer probability;

    private boolean invertProbability = false;
    private boolean isTicking = false;

    private int tickRate = 4;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

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
            if (jsonObject.has("action_probability")) {
                this.probability = jsonObject.get("action_probability").getAsInt();
            }
            if (jsonObject.has("invert_action_probability")) {
                this.invertProbability = jsonObject.get("invert_action_probability").getAsBoolean();
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
            if (jsonObject.has("invert_action_probability")) {
                this.invertProbability = jsonObject.get("invert_action_probability").getAsBoolean();
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
        if (this.probability != null) {
            Random random = new Random();
            int probability = random.nextInt(this.probability);

            if (this.invertProbability) {
                if (probability == 0) {
                    return;
                }
            } else {
                if (probability != 0) {
                    return;
                }
            }
        }
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

                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                    .getSchedulers()
                                    .removeByIdentifier(
                                            getIdentifier()
                                    );
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
                                        ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                .getSchedulers()
                                                .removeByIdentifier(
                                                        getIdentifier()
                                                );
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                    .getSchedulers()
                                    .add(
                                            new SchedulerContainer(
                                                    getIdentifier(), tick
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

                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                    .getSchedulers()
                                    .removeByIdentifier(
                                            getIdentifier()
                                    );
                            BukkitTask tick = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    if (player.isOnline()) {
                                        for (Action<Object> action : getActions()) {
                                            action.accept(temp);
                                        }
                                    } else {
                                        ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                .getSchedulers()
                                                .removeByIdentifier(
                                                        getIdentifier()
                                                );
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(
                                    OriginsBukkit.getPlugin(),
                                    0,
                                    tickRate
                            );
                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                    .getSchedulers()
                                    .add(
                                            new SchedulerContainer(
                                                    getIdentifier(), tick
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

                                                ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                        .getSchedulers()
                                                        .removeByIdentifier(
                                                                getIdentifier()
                                                        );
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
                                                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                                    .getSchedulers()
                                                                    .removeByIdentifier(
                                                                            getIdentifier()
                                                                    );
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimerAsynchronously(
                                                        OriginsBukkit.getPlugin(),
                                                        0,
                                                        tickRate
                                                );
                                                ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                        .getSchedulers()
                                                        .add(
                                                                new SchedulerContainer(
                                                                        getIdentifier(), tick
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

                                                ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                        .getSchedulers()
                                                        .removeByIdentifier(
                                                                getIdentifier()
                                                        );
                                                BukkitTask tick = new BukkitRunnable() {

                                                    @Override
                                                    public void run() {
                                                        if (player.isOnline()) {
                                                            for (Action<Object> action : getActions()) {
                                                                action.accept(temp);
                                                            }
                                                        } else {
                                                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                                    .getSchedulers()
                                                                    .removeByIdentifier(
                                                                            getIdentifier()
                                                                    );
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimerAsynchronously(
                                                        OriginsBukkit.getPlugin(),
                                                        0,
                                                        tickRate
                                                );
                                                ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                                        .getSchedulers()
                                                        .add(
                                                                new SchedulerContainer(
                                                                        getIdentifier(), tick
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

                        ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                .getSchedulers()
                                .removeByIdentifier(
                                        getIdentifier()
                                );
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
                                    ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                            .getSchedulers()
                                            .removeByIdentifier(
                                                    getIdentifier()
                                            );
                                    cancel();
                                }
                            }
                        }.runTaskTimer(
                                OriginsBukkit.getPlugin(),
                                0,
                                tickRate
                        );
                        ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                .getSchedulers()
                                .add(
                                        new SchedulerContainer(
                                                getIdentifier(), tick
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

                        ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                .getSchedulers()
                                .removeByIdentifier(
                                        getIdentifier()
                                );
                        BukkitTask tick = new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (player.isOnline()) {
                                    for (Action<Object> action : getActions()) {
                                        action.accept(temp);
                                    }
                                } else {
                                    ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                            .getSchedulers()
                                            .removeByIdentifier(
                                                    getIdentifier()
                                            );
                                    cancel();
                                }
                            }
                        }.runTaskTimer(
                                OriginsBukkit.getPlugin(),
                                0,
                                tickRate
                        );
                        ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                .getSchedulers()
                                .add(
                                        new SchedulerContainer(
                                                getIdentifier(), tick
                                        )
                                );
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinglePowerContainer)) return false;
        SinglePowerContainer that = (SinglePowerContainer) o;
        return isTicking == that.isTicking && tickRate == that.tickRate && Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Arrays.equals(getActions(), that.getActions()) && Objects.equals(getCondition(), that.getCondition()) && Objects.equals(isAsync, that.isAsync);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getIdentifier(), getJsonObject(), getCondition(), isAsync, isTicking, tickRate);
        result = 31 * result + Arrays.hashCode(getActions());
        return result;
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

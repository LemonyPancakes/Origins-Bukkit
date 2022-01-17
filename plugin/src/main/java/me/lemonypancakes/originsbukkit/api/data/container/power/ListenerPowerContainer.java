package me.lemonypancakes.originsbukkit.api.data.container.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "unused"})
public class ListenerPowerContainer implements Power, Listener {

    private Identifier identifier;
    private JsonObject jsonObject;
    private Action<?>[] actions;
    private Condition<?> condition;

    private final boolean isFactory;

    private boolean setCancelled = false;

    private final Set<Player> playersToListen = new HashSet<>();

    public ListenerPowerContainer(Identifier identifier,
                                  JsonObject jsonObject,
                                  Action<?>[] actions,
                                  Condition<?> condition,
                                  boolean isFactory) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.actions = actions;
        this.condition = condition;
        this.isFactory = isFactory;
        if (!isFactory) {
            if (jsonObject != null) {
                JsonObject fields = jsonObject.getAsJsonObject("fields");

                if (fields != null) {
                    if (fields.has("set_cancelled")) {
                        this.setCancelled = fields.get("set_cancelled").getAsBoolean();
                    }
                }
            }
            Bukkit.getPluginManager().registerEvents(this, OriginsBukkit.getPlugin());
        }
    }

    public ListenerPowerContainer(Identifier identifier,
                                  JsonObject jsonObject,
                                  Action<?>[] actions,
                                  boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ListenerPowerContainer(Identifier identifier,
                                  JsonObject jsonObject,
                                  boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ListenerPowerContainer(Identifier identifier,
                                  boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ListenerPowerContainer(boolean isFactory) {
        this(null, isFactory);
    }

    public ListenerPowerContainer() {
        this(false);
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
    public boolean isFactory() {
        return isFactory;
    }

    @Override
    public boolean isSetCancelled() {
        return setCancelled;
    }

    @Override
    public Set<Player> getPlayersToListen() {
        return playersToListen;
    }

    @Override
    public Power newInstance(Identifier identifier, JsonObject jsonObject, Action<?>[] actions, Condition<?> condition) {
        return new ListenerPowerContainer(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier, JsonObject jsonObject, Action<?>[] actions) {
        return new ListenerPowerContainer(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier, JsonObject jsonObject) {
        return new ListenerPowerContainer(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ListenerPowerContainer(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ListenerPowerContainer();
    }

    @Override
    public <T> void invoke(T t) {
        if (t instanceof Player) {
            getPlayersToListen().forEach((player -> {
                if (t.toString().equals(player.toString())) {
                    getPlayersToListen().remove(player);
                }
            }));
            getPlayersToListen().add((Player) t);
            onInvoke(t);
        }
    }

    @Override
    public <T> void onInvoke(T t) {}

    public static Power getFactory() {
        return new ListenerPowerContainer(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/dummy"
                ), true
        );
    }

    @Override
    public String toString() {
        return "ListenerPowerContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", actions=" + Arrays.toString(actions) +
                ", condition=" + condition +
                ", isFactory=" + isFactory +
                ", playersToListen=" + playersToListen +
                '}';
    }
}

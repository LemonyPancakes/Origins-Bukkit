package me.lemonypancakes.originsbukkit.api.data.container.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unchecked", "unused"})
public class ListenerPowerContainer implements Power, Listener {

    private Identifier identifier;
    private JsonObject jsonObject;
    private Action<?>[] actions;
    private Condition<?> condition;

    private final boolean isFactory;

    private Integer probability;

    private boolean invertProbability = false;
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
                if (jsonObject.has("fields")) {
                    JsonObject fields = jsonObject.getAsJsonObject("fields");

                    if (fields != null) {
                        if (fields.has("set_cancelled")) {
                            this.setCancelled = fields.get("set_cancelled").getAsBoolean();
                        }
                        if (fields.has("action_probability")) {
                            this.probability = fields.get("action_probability").getAsInt();
                        }
                        if (fields.has("invert_action_probability")) {
                            this.invertProbability = fields.get("invert_action_probability").getAsBoolean();
                        }
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
        if (jsonObject != null) {
            if (jsonObject.has("fields")) {
                JsonObject fields = jsonObject.getAsJsonObject("fields");

                if (fields != null) {
                    if (fields.has("set_cancelled")) {
                        this.setCancelled = fields.get("set_cancelled").getAsBoolean();
                    }
                    if (fields.has("action_probability")) {
                        this.probability = fields.get("action_probability").getAsInt();
                    }
                    if (fields.has("invert_action_probability")) {
                        this.invertProbability = fields.get("invert_action_probability").getAsBoolean();
                    }
                }
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
    public boolean isFactory() {
        return isFactory;
    }

    public Integer getProbability() {
        return probability;
    }

    public boolean isInvertProbability() {
        return invertProbability;
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
        if (t instanceof Temp) {
            Player player = ((Temp) t).getPlayer();
            getPlayersToListen().forEach((p -> {
                if (player.toString().equals(p.toString())) {
                    getPlayersToListen().remove(p);
                }
            }));
            getPlayersToListen().add(player);
        }
        onInvoke(t);
    }

    protected <T> void onInvoke(T t) {}

    @Override
    public void unlisten(Player player) {
        getPlayersToListen().forEach(p -> {
            if (player.toString().equals(p.toString())) {
                getPlayersToListen().remove(p);
                onUnlisten(p);
            }
        });
    }

    protected void onUnlisten(Player player) {}

    public static Power getFactory() {
        return new ListenerPowerContainer(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/dummy"
                ), true
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListenerPowerContainer)) return false;
        ListenerPowerContainer that = (ListenerPowerContainer) o;
        return isFactory() == that.isFactory() && isSetCancelled() == that.isSetCancelled() && Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Arrays.equals(getActions(), that.getActions()) && Objects.equals(getCondition(), that.getCondition()) && Objects.equals(getPlayersToListen(), that.getPlayersToListen());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getIdentifier(), getJsonObject(), getCondition(), isFactory(), isSetCancelled(), getPlayersToListen());
        result = 31 * result + Arrays.hashCode(getActions());
        return result;
    }

    @Override
    public String toString() {
        return "ListenerPowerContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", actions=" + Arrays.toString(actions) +
                ", condition=" + condition +
                ", isFactory=" + isFactory +
                ", setCancelled=" + setCancelled +
                ", playersToListen=" + playersToListen +
                '}';
    }
}

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

@SuppressWarnings("unchecked")
public class AbilityPowerContainer implements Power, Ability, Listener {

    private Identifier identifier;
    private JsonObject jsonObject;
    private Action<?>[] actions;
    private Condition<?> condition;

    private final boolean isFactory;

    private final Set<Player> playersToListen = new HashSet<>();

    public AbilityPowerContainer(Identifier identifier,
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
            Bukkit.getPluginManager().registerEvents(this, OriginsBukkit.getPlugin());
        }
    }

    public AbilityPowerContainer(Identifier identifier,
                                 JsonObject jsonObject,
                                 Action<?>[] actions,
                                 boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public AbilityPowerContainer(Identifier identifier,
                                 JsonObject jsonObject,
                                 boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public AbilityPowerContainer(Identifier identifier,
                                 boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public AbilityPowerContainer(boolean isFactory) {
        this(null, isFactory);
    }

    public AbilityPowerContainer() {
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

    public <T> Action<T>[] getActions() {
        return (Action<T>[]) actions;
    }

    @Override
    public void setActions(Action<?>[] actions) {
        this.actions = actions;
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
        return false;
    }

    @Override
    public Set<Player> getPlayersToListen() {
        return playersToListen;
    }

    @Override
    public Power newInstance(Identifier identifier, JsonObject jsonObject, Action<?>[] actions, Condition<?> condition) {
        return null;
    }

    @Override
    public Power newInstance(Identifier identifier, JsonObject jsonObject, Action<?>[] actions) {
        return null;
    }

    @Override
    public Power newInstance(Identifier identifier, JsonObject jsonObject) {
        return null;
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return null;
    }

    @Override
    public Power newInstance() {
        return null;
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
                        OriginsBukkit.KEY, "ability/dummy"
                ), true
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbilityPowerContainer)) return false;
        AbilityPowerContainer that = (AbilityPowerContainer) o;
        return isFactory() == that.isFactory() && Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Arrays.equals(getActions(), that.getActions()) && Objects.equals(getCondition(), that.getCondition()) && Objects.equals(getPlayersToListen(), that.getPlayersToListen());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getIdentifier(), getJsonObject(), getCondition(), isFactory(), getPlayersToListen());
        result = 31 * result + Arrays.hashCode(getActions());
        return result;
    }

    @Override
    public String toString() {
        return "AbilityPowerContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", actions=" + Arrays.toString(actions) +
                ", condition=" + condition +
                ", isFactory=" + isFactory +
                ", playersToListen=" + playersToListen +
                '}';
    }
}

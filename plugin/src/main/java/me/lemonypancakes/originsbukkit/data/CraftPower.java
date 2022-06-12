package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CraftPower implements Power, Listener {

    private final OriginsBukkitPlugin plugin;
    private Identifier identifier;
    private JsonObject jsonObject;
    private Action[] actions;
    private Condition[] conditions;
    private Probability probability;
    private final boolean isFactory;
    private final Set<Player> members = new HashSet<>();

    public CraftPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        this.plugin = plugin;
        setIdentifier(identifier);
        setJsonObject(jsonObject);
        setActions(plugin.getLoader().loadActions(jsonObject));
        setConditions(plugin.getLoader().loadConditions(jsonObject));
        this.isFactory = isFactory;
        if (!isFactory) {
            Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
        }
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
            if (jsonObject.has("probability")) {
                JsonObject probability = jsonObject.get("probability").getAsJsonObject();

                if (probability != null) {
                    Probability probabilityContainer = new CraftProbability();

                    if (probability.has("min")) {
                        probabilityContainer.setMin(probability.get("min").getAsInt());
                    }
                    if (probability.has("max")) {
                        probabilityContainer.setMax(probability.get("max").getAsInt());
                    }

                    if (probabilityContainer.getMin() > 0 && probabilityContainer.getMax() > 0) {
                        this.probability = probabilityContainer;
                    }
                }
            }
        }
    }

    @Override
    public Action[] getActions() {
        return actions;
    }

    @Override
    public void setActions(Action[] actions) {
        this.actions = actions;
    }

    @Override
    public Condition[] getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(Condition[] conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean isFactory() {
        return isFactory;
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin,
                             Identifier identifier,
                             JsonObject jsonObject) {
        return new CraftPower(plugin, identifier, jsonObject, false);
    }

    @Override
    public Set<Player> getMembers() {
        return new HashSet<>(members);
    }

    @Override
    public void addMember(Player player) {
        members.add(player);
        onMemberAdd(player);
    }

    protected void onMemberAdd(Player player) {}

    @Override
    public void removeMember(Player player) {
        members.remove(player);
        onMemberRemove(player);
    }

    protected void onMemberRemove(Player player) {}

    @Override
    public boolean hasMember(Player player) {
        return members.contains(player);
    }

    @Override
    public boolean testAndAccept(Temp temp) {
        if (probability != null) {
            if (!probability.generateProbability()) {
                return false;
            }
        }
        if (getConditions() != null) {
            if (!Arrays.stream(getConditions()).allMatch(condition -> condition.test(temp))) {
                return false;
            }
        }
        if (getActions() != null) {
            Arrays.stream(getActions()).forEach(action -> action.accept(temp));

            return true;
        }
        return false;
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftPower)) return false;
        CraftPower that = (CraftPower) o;
        return isFactory() == that.isFactory() && Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Arrays.equals(getActions(), that.getActions()) && Arrays.equals(getConditions(), that.getConditions()) && Objects.equals(probability, that.probability) && Objects.equals(getMembers(), that.getMembers());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getPlugin(), getIdentifier(), getJsonObject(), probability, isFactory(), getMembers());
        result = 31 * result + Arrays.hashCode(getActions());
        result = 31 * result + Arrays.hashCode(getConditions());
        return result;
    }

    @Override
    public String toString() {
        return "CraftPower{" +
                "plugin=" + plugin +
                ", identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", actions=" + Arrays.toString(actions) +
                ", conditions=" + Arrays.toString(conditions) +
                ", probability=" + probability +
                ", isFactory=" + isFactory +
                ", members=" + members +
                '}';
    }
}

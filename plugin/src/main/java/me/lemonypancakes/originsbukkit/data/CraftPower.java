package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CraftPower implements Power, Listener {

    private final OriginsBukkitPlugin plugin;
    private Identifier identifier;
    private JsonObject jsonObject;
    private Condition<Entity> condition;
    private final Set<Player> members = new HashSet<>();

    public CraftPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        this.plugin = plugin;
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    public CraftPower(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
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
    public Condition<Entity> getCondition() {
        return condition;
    }

    @Override
    public void setCondition(Condition<Entity> condition) {
        this.condition = condition;
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
    public boolean isActive(Player player) {
        if (getMembers().contains(player)) {
            return getCondition().test(player);
        }
        return false;
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin,
                             Identifier identifier,
                             JsonObject jsonObject) {
        return new CraftPower(plugin, identifier, jsonObject);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftPower)) return false;
        CraftPower that = (CraftPower) itemStack;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getCondition(), that.getCondition()) && Objects.equals(getMembers(), that.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), getIdentifier(), getJsonObject(), getCondition(), getMembers());
    }

    @Override
    public String toString() {
        return "CraftPower{" +
                "plugin=" + plugin +
                ", identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", condition=" + condition +
                ", members=" + members +
                '}';
    }
}

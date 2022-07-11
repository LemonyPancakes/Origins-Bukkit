/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.entity.player.power;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PowerSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class CraftPower implements Power {

    private OriginsBukkitPlugin plugin;
    private Identifier identifier;
    private JsonObject jsonObject;
    private Condition<Entity> condition;
    private String name;
    private String[] description;
    private boolean hidden;
    private int loadingPriority;
    private final Map<Player, Set<PowerSource>> map = new HashMap<>();
    private final Set<Player> members = new HashSet<>();

    public CraftPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        setPlugin(plugin);
        setIdentifier(identifier);
        setJsonObject(jsonObject);
        setCondition(plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject));
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {
        if (this.plugin == null) {
            this.plugin = plugin;
        }
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.identifier == null) {
            this.identifier = identifier;
        }
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        if (this.jsonObject == null) {
            this.jsonObject = jsonObject;
            if (jsonObject != null) {
                if (jsonObject.has("name")) {
                    this.name = jsonObject.get("name").getAsString();
                }
                if (jsonObject.has("description")) {
                    this.description = new Gson().fromJson(jsonObject.get("description"), String[].class);
                }
                if (jsonObject.has("hidden")) {
                    this.hidden = jsonObject.get("hidden").getAsBoolean();
                }
                if (jsonObject.has("loading_priority")) {
                    this.loadingPriority = jsonObject.get("loading_priority").getAsInt();
                }
            }
        }
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String[] getDescription() {
        return description;
    }

    @Override
    public void setDescription(String[] description) {
        this.description = description;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int getLoadingPriority() {
        return loadingPriority;
    }

    @Override
    public void setLoadingPriority(int loadingPriority) {
        this.loadingPriority = loadingPriority;
    }

    @Override
    public Set<Player> getMembers() {
        return Collections.unmodifiableSet(new HashSet<>(members));
    }

    @Override
    public void addMember(Player player, PowerSource powerSource) {
        if (!map.containsKey(player)) {
            map.put(player, new LinkedHashSet<>());
            members.add(player);
            onMemberAdd(player);
        }
        Set<PowerSource> powerSources = map.get(player);

        if (powerSources != null) {
            powerSources.add(powerSource);
        }
    }

    protected void onMemberAdd(Player player) {}

    @Override
    public void removeMember(Player player, PowerSource powerSource) {
        if (map.containsKey(player)) {
            Set<PowerSource> powerSources = map.get(player);

            if (powerSources != null) {
                powerSources.remove(powerSource);
            }
            if (powerSources == null || powerSources.isEmpty()) {
                forceRemoveMember(player);
            }
        }
    }

    @Override
    public void forceRemoveMember(Player player) {
        if (map.containsKey(player)) {
            map.remove(player);
            members.remove(player);
            onMemberRemove(player);
        }
    }

    protected void onMemberRemove(Player player) {}

    @Override
    public boolean hasMember(Player player) {
        return members.contains(player);
    }

    @Override
    public boolean isActive(Player player) {
        if (members.contains(player)) {
            return getCondition().test(player);
        }
        return false;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftPower)) return false;
        CraftPower that = (CraftPower) itemStack;
        return hidden == that.hidden && loadingPriority == that.loadingPriority && Objects.equals(plugin, that.plugin) && Objects.equals(identifier, that.identifier) && Objects.equals(jsonObject, that.jsonObject) && Objects.equals(condition, that.condition) && Objects.equals(name, that.name) && Arrays.equals(description, that.description) && Objects.equals(map, that.map) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, identifier, jsonObject);
    }

    @Override
    public String toString() {
        return "CraftPower{" +
                "plugin=" + plugin +
                ", identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", condition=" + condition +
                ", name='" + name + '\'' +
                ", description=" + Arrays.toString(description) +
                ", hidden=" + hidden +
                ", loadingPriority=" + loadingPriority +
                ", map=" + map +
                ", members=" + members +
                '}';
    }
}

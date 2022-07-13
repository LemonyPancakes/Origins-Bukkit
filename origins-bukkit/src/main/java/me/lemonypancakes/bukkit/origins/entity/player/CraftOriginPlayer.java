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
package me.lemonypancakes.bukkit.origins.entity.player;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonParser;
import me.lemonypancakes.bukkit.origins.data.storage.Storage;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.event.entity.player.PlayerOriginSetEvent;
import me.lemonypancakes.bukkit.origins.menu.CraftOriginLayerMenu;
import me.lemonypancakes.bukkit.origins.menu.Menu;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.registry.Registry;
import me.lemonypancakes.bukkit.origins.util.AsyncCatcher;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PowerSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CraftOriginPlayer implements OriginPlayer {

    private OriginsBukkitPlugin plugin;
    private Player player;
    private final Schedulers schedulers;
    private final Map<OriginLayer, Origin> origins = new LinkedHashMap<>();
    private final Map<Power, Set<PowerSource>> powers = new HashMap<>();
    private JsonObject metadata = new JsonObject();
    private boolean hasOriginBefore;

    public CraftOriginPlayer(OriginsBukkitPlugin plugin, Player player) {
        setPlugin(plugin);
        setPlayer(player);
        this.schedulers = new Schedulers();
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
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        if (this.player == null) {
            this.player = player;
        }
    }

    @Override
    public Origin getOrigin(OriginLayer originLayer) {
        return origins.get(originLayer);
    }

    @Override
    public Map<OriginLayer, Origin> getOrigins() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(origins));
    }

    @Override
    public PlayerOriginSetEvent setOrigin(OriginLayer originLayer, Origin newOrigin) {
        AsyncCatcher.catchAsync("Asynchronous origin set");
        if (originLayer != null) {
            Origin oldOrigin = getOrigin(originLayer);
            PlayerOriginSetEvent playerOriginSetEvent = new PlayerOriginSetEvent(player, originLayer, oldOrigin, newOrigin);

            Bukkit.getPluginManager().callEvent(playerOriginSetEvent);
            if (!playerOriginSetEvent.isCancelled()) {
                newOrigin = playerOriginSetEvent.getNewOrigin();

                if (originLayer.hasOrigin(newOrigin) || newOrigin == null) {
                    if (oldOrigin != null) {
                        List<Power> powers = oldOrigin.getPowers();

                        if (powers != null) {
                            powers.forEach(power -> removePower(power, new PowerSource(oldOrigin.getIdentifier())));
                        }
                    }
                    origins.put(originLayer, newOrigin);
                    if (newOrigin != null) {
                        Origin finalNewOrigin = newOrigin;
                        List<Power> powers = finalNewOrigin.getPowers();

                        if (powers != null) {
                            powers.forEach(power -> addPower(power, new PowerSource(finalNewOrigin.getIdentifier())));
                        }
                        if (origins.entrySet().stream().allMatch(originLayerOriginEntry -> originLayerOriginEntry.getValue() != null)) {
                            hasOriginBefore = true;
                        }
                    }
                    new BukkitRunnable() {

                        @Override
                        public void run () {
                            compute();
                        }
                    }.runTask(plugin.getJavaPlugin());
                }
            }
            return playerOriginSetEvent;
        }
        return null;
    }

    @Override
    public Set<PowerSource> getPowerSources(Power power) {
        return Collections.unmodifiableSet(new HashSet<>(powers.get(power) != null ? powers.get(power) : new HashSet<>()));
    }

    @Override
    public Map<Power, Set<PowerSource>> getPowers() {
        return Collections.unmodifiableMap(new HashMap<>(powers));
    }

    @Override
    public void addPower(Power power, PowerSource powerSource) {
        AsyncCatcher.catchAsync("Asynchronous power add");
        if (!powers.containsKey(power)) {
            powers.put(power, new HashSet<>());
            power.addMember(player, powerSource);
        }
        Set<PowerSource> powerSources = powers.get(power);

        if (powerSources != null) {
            powerSources.add(powerSource);
        }
    }

    @Override
    public void removePower(Power power, PowerSource powerSource) {
        AsyncCatcher.catchAsync("Asynchronous power remove");
        if (powers.containsKey(power)) {
            Set<PowerSource> powerSources = powers.get(power);

            if (powerSources != null) {
                powerSources.remove(powerSource);
                power.removeMember(player, powerSource);
            }
            if (powerSources == null || powerSources.isEmpty()) {
                forceRemovePower(power);
            }
        }
    }

    @Override
    public void forceRemovePower(Power power) {
        AsyncCatcher.catchAsync("Asynchronous power remove");
        if (powers.containsKey(power)) {
            powers.remove(power);
            power.forceRemoveMember(player);
        }
    }

    @Override
    public Schedulers getSchedulers() {
        return schedulers;
    }

    @Override
    public void refresh() {
        AsyncCatcher.catchAsync("Async origin player refresh");
        Registry registry = plugin.getRegistry();

        if (registry != null) {
            Collection<OriginLayer> originLayers = registry.getRegisteredOriginLayers();

            if (originLayers != null) {
                originLayers.forEach(originLayer -> {
                    if (originLayer.isEnabled() && !originLayer.isHidden()) {
                        origins.put(originLayer, null);
                    }
                });
            }
            Storage storage = plugin.getStorage();

            if (storage != null) {
                Map<String, Object> data = storage.getData(player);

                if (data != null) {
                    Map<String, Object> deserializedData = OriginPlayer.deserialize(data);
                    Object metadata = deserializedData.get("metadata");

                    if (metadata != null) {
                        this.metadata = (JsonObject) metadata;
                    }
                    hasOriginBefore = (boolean) deserializedData.get("hasOriginBefore");
                    Object originObject = deserializedData.get("origin");

                    if (originObject != null) {
                        @SuppressWarnings("unchecked")
                        Map<Identifier, Identifier> origins = (Map<Identifier, Identifier>) originObject;

                        origins.forEach((originLayerIdentifier, originIdentifier) -> {
                            OriginLayer originLayer = registry.getRegisteredOriginLayer(originLayerIdentifier);

                            if (originLayer != null) {
                                Origin origin = registry.getRegisteredOrigin(originIdentifier);

                                if (origin != null) {
                                    if (originLayer.hasOrigin(origin)) {
                                        setOrigin(originLayer, origin);
                                    }
                                }
                            }
                        });
                    }
                    Object powerObject = deserializedData.get("power");

                    if (powerObject != null) {
                        @SuppressWarnings("unchecked")
                        Map<Identifier, Set<Identifier>> powers = (Map<Identifier, Set<Identifier>>) powerObject;

                        powers.forEach((powerIdentifier, powerSources) -> {
                            Power power = registry.getRegisteredPower(powerIdentifier);

                            if (power != null) {
                                powerSources.forEach(powerSource -> addPower(power, new PowerSource(powerSource)));
                            }
                        });
                    }
                }
            }
            compute();
        }
    }

    @Override
    public JsonObject getMetadata() {
        return metadata;
    }

    @Override
    public boolean hasOriginBefore() {
        return hasOriginBefore;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("UUID", player.getUniqueId());
        data.put("name", player.getName());
        JsonObject origins = new JsonObject();

        this.origins.forEach((originLayer, origin) -> {
            if (originLayer != null) {
                Identifier originLayerIdentifier = originLayer.getIdentifier();

                if (originLayerIdentifier != null) {
                    if (origin != null) {
                        Identifier originIdentifier = origin.getIdentifier();

                        if (originIdentifier != null) {
                            origins.addProperty(originLayerIdentifier.toString(), originIdentifier.toString());
                        }
                    }
                }
            }
        });
        data.put("origin", origins.toString());
        JsonObject powers = new JsonObject();

        this.powers.forEach((power, powerSources) -> {
            if (power != null) {
                Identifier powerIdentifier = power.getIdentifier();

                if (powerIdentifier != null) {
                    Set<String> set = new LinkedHashSet<>();

                    powerSources.forEach(powerSource -> {
                        Identifier powerSourceIdentifier = powerSource.getIdentifier();

                        if (powerSourceIdentifier != null) {
                            set.add(powerSourceIdentifier.toString());
                        }
                    });
                    powers.add(powerIdentifier.toString(), JsonParser.parseString(new Gson().toJson(set.toArray())));
                }
            }
        });
        data.put("power", powers.toString());
        JsonObject metadata = new JsonObject();

        if (this.metadata != null) {
            metadata = this.metadata;
        }
        data.put("metadata", metadata.toString());
        data.put("hasOriginBefore", hasOriginBefore);

        return data;
    }

    private void compute() {
        for (Map.Entry<OriginLayer, Origin> originLayerOriginEntry : origins.entrySet()) {
            OriginLayer originLayer = originLayerOriginEntry.getKey();

            if (originLayer != null) {
                if (originLayer.isEnabled() && !originLayer.isHidden()) {
                    Origin origin = originLayerOriginEntry.getValue();

                    if (origin == null) {
                        Menu menu = originLayer.getMenu();

                        if (menu != null) {
                            InventoryView inventoryView = player.getOpenInventory();
                            Inventory topInventory = inventoryView.getTopInventory();
                            InventoryHolder inventoryHolder = topInventory.getHolder();

                            if (inventoryHolder != null) {
                                if (inventoryHolder instanceof CraftOriginLayerMenu) {
                                    CraftOriginLayerMenu craftOriginLayerMenu = (CraftOriginLayerMenu) inventoryHolder;

                                    craftOriginLayerMenu.addExemption(player);
                                    if (craftOriginLayerMenu != menu) {
                                        menu.open(player);
                                        craftOriginLayerMenu.removeExemption(player);
                                    }
                                } else {
                                    if (inventoryHolder != menu) {
                                        menu.open(player);
                                    }
                                }
                            } else {
                                menu.open(player);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOriginPlayer)) return false;
        CraftOriginPlayer that = (CraftOriginPlayer) itemStack;
        return hasOriginBefore == that.hasOriginBefore && Objects.equals(plugin, that.plugin) && Objects.equals(player, that.player) && Objects.equals(schedulers, that.schedulers) && Objects.equals(origins, that.origins) && Objects.equals(powers, that.powers) && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, player);
    }

    @Override
    public String toString() {
        return "CraftOriginPlayer{" +
                "plugin=" + plugin +
                ", player=" + player +
                ", schedulers=" + schedulers +
                ", origins=" + origins +
                ", powers=" + powers +
                ", metadata=" + metadata +
                ", hasOriginBefore=" + hasOriginBefore +
                '}';
    }
}

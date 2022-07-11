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
package me.lemonypancakes.bukkit.origins.registry;

import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.item.OriginItem;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;

public class CraftRegistry implements Registry {

    private OriginsBukkitPlugin plugin;
    private final Map<DataType<?>, Map<Identifier, Action.Factory<?>>> registeredActionFactories = new LinkedHashMap<>();
    private final Map<DataType<?>, Map<Identifier, Condition.Factory<?>>> registeredConditionFactories = new LinkedHashMap<>();
    private final Map<Identifier, Origin> registeredOrigins = new LinkedHashMap<>();
    private final Map<Identifier, OriginLayer> registeredOriginLayers = new LinkedHashMap<>();
    private final Map<Identifier, Power> registeredPowers = new LinkedHashMap<>();
    private final Map<Identifier, Power.Factory> registeredPowerFactories = new LinkedHashMap<>();
    private final Map<Identifier, OriginItem> registeredOriginItems = new LinkedHashMap<>();

    public CraftRegistry(OriginsBukkitPlugin plugin) {
        setPlugin(plugin);
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
    public void registerActionFactory(Action.Factory<?> factory) {
        DataType<?> dataType = factory.getDataType();

        if (!registeredActionFactories.containsKey(dataType)) {
            registeredActionFactories.put(dataType, new HashMap<>());
        }
        Map<Identifier, Action.Factory<?>> identifierFactoryMap = registeredActionFactories.get(dataType);

        if (identifierFactoryMap != null) {
            Identifier identifier = factory.getIdentifier();

            if (!identifierFactoryMap.containsKey(identifier)) {
                identifierFactoryMap.put(identifier, factory);
            }
        }
    }

    @Override
    public void registerConditionFactory(Condition.Factory<?> factory) {
        DataType<?> dataType = factory.getDataType();

        if (!registeredConditionFactories.containsKey(dataType)) {
            registeredConditionFactories.put(dataType, new HashMap<>());
        }
        Map<Identifier, Condition.Factory<?>> identifierFactoryMap = registeredConditionFactories.get(dataType);

        if (identifierFactoryMap != null) {
            Identifier identifier = factory.getIdentifier();

            if (!identifierFactoryMap.containsKey(identifier)) {
                identifierFactoryMap.put(identifier, factory);
            }
        }
    }

    @Override
    public void registerOrigin(Origin origin) {
        Identifier identifier = origin.getIdentifier();

        if (!registeredOrigins.containsKey(identifier)) {
            registeredOrigins.put(identifier, origin);
            Map<Identifier, Origin> map = registeredOrigins.entrySet().stream().sorted(Comparator.comparingInt(i -> i.getValue().getOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            registeredOrigins.clear();
            registeredOrigins.putAll(map);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "An origin with the identifier '" + origin.getIdentifier() + "' has already been registered.");
        }
    }

    @Override
    public void registerOriginLayer(OriginLayer originLayer) {
        Identifier identifier = originLayer.getIdentifier();

        if (identifier != null) {
            if (!registeredOriginLayers.containsKey(identifier)) {
                registeredOriginLayers.put(identifier, originLayer);
            } else {
                if (originLayer.isReplace()) {
                    registeredOriginLayers.put(identifier, originLayer);
                } else {
                    OriginLayer originLayerFromMap = registeredOriginLayers.get(identifier);

                    if (originLayerFromMap != null) {
                        originLayer.getOrigins().forEach(originLayerFromMap::addOrigin);
                    }
                }
            }
            Map<Identifier, OriginLayer> map = registeredOriginLayers.entrySet().stream().sorted(Comparator.comparingInt(i -> i.getValue().getOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            registeredOriginLayers.clear();
            registeredOriginLayers.putAll(map);
        }
    }

    @Override
    public void registerPower(Power power) {
        Identifier identifier = power.getIdentifier();

        if (!registeredPowers.containsKey(identifier)) {
            registeredPowers.put(identifier, power);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "A power with the identifier '" + power.getIdentifier() + "' has already been registered.");
        }
    }

    @Override
    public void registerPowerFactory(Power.Factory factory) {
        Identifier identifier = factory.getIdentifier();

        if (!registeredPowerFactories.containsKey(identifier)) {
            registeredPowerFactories.put(identifier, factory);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "A power factory with the identifier '" + factory.getIdentifier() + "' has already been registered.");
        }
    }

    @Override
    public void registerOriginItem(OriginItem originItem) {
        Identifier identifier = originItem.getIdentifier();

        if (!registeredOriginItems.containsKey(identifier)) {
            if (originItem.getRecipe() != null) {
                Bukkit.addRecipe(originItem.getRecipe());
            }
            registeredOriginItems.put(identifier, originItem);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "An origin item with the identifier '" + originItem.getIdentifier() + "' has already been registered.");
        }
    }

    @Override
    public <T> void unregisterActionFactory(DataType<T> dataType, Identifier identifier) {
        registeredActionFactories.get(dataType).remove(identifier);
    }

    @Override
    public <T> void unregisterConditionFactory(DataType<T> dataType, Identifier identifier) {
        registeredConditionFactories.remove(dataType).remove(identifier);
    }

    @Override
    public void unregisterOrigin(Identifier identifier) {
        registeredOrigins.remove(identifier);
    }

    @Override
    public void unregisterOriginLayer(Identifier identifier) {
        registeredOriginLayers.remove(identifier);
    }

    @Override
    public void unregisterPower(Identifier identifier) {
        registeredPowers.remove(identifier);
    }

    @Override
    public void unregisterPowerFactory(Identifier identifier) {
        registeredPowerFactories.remove(identifier);
    }

    @Override
    public void unregisterOriginItem(Identifier identifier) {
        if (registeredOriginItems.containsKey(identifier)) {
            OriginItem originItem = registeredOriginItems.get(identifier);

            if (originItem != null) {
                if (originItem.getRecipe() != null) {
                    Bukkit.removeRecipe(identifier.toNameSpacedKey());
                }
                registeredOriginItems.remove(identifier);
            }
        }
    }

    @Override @SuppressWarnings("unchecked")
    public <T> Action.Factory<T> getRegisteredActionFactory(DataType<T> dataType, Identifier identifier) {
        Map<Identifier, Action.Factory<?>> identifierFactoryMap = registeredActionFactories.get(dataType);

        if (identifierFactoryMap != null) {
            return (Action.Factory<T>) identifierFactoryMap.get(identifier);
        }
        return null;
    }

    @Override @SuppressWarnings("unchecked")
    public <T> Condition.Factory<T> getRegisteredConditionFactory(DataType<T> dataType, Identifier identifier) {
        Map<Identifier, Condition.Factory<?>> identifierFactoryMap = registeredConditionFactories.get(dataType);

        if (identifierFactoryMap != null) {
            return (Condition.Factory<T>) identifierFactoryMap.get(identifier);
        }
        return null;
    }

    @Override
    public Origin getRegisteredOrigin(Identifier identifier) {
        return registeredOrigins.get(identifier);
    }

    @Override
    public OriginLayer getRegisteredOriginLayer(Identifier identifier) {
        return registeredOriginLayers.get(identifier);
    }

    @Override
    public Power getRegisteredPower(Identifier identifier) {
        return registeredPowers.get(identifier);
    }

    @Override
    public Power.Factory getRegisteredPowerFactory(Identifier identifier) {
        return registeredPowerFactories.get(identifier);
    }

    @Override
    public OriginItem getRegisteredOriginItem(Identifier identifier) {
        return registeredOriginItems.get(identifier);
    }

    @Override
    public Set<DataType<?>> getRegisteredActionFactoriesKeySet() {
        return registeredActionFactories.keySet();
    }

    @Override
    public Set<DataType<?>> getRegisteredConditionFactoriesKeySet() {
        return registeredConditionFactories.keySet();
    }

    @Override
    public Set<Identifier> getRegisteredOriginsKeySet() {
        return registeredOrigins.keySet();
    }

    @Override
    public Set<Identifier> getRegisteredOriginLayersKeySet() {
        return null;
    }

    @Override
    public Set<Identifier> getRegisteredPowersKeySet() {
        return registeredPowers.keySet();
    }

    @Override
    public Set<Identifier> getRegisteredPowerFactoriesKeySet() {
        return registeredPowerFactories.keySet();
    }

    @Override
    public Set<Identifier> getRegisteredOriginItemsKeySet() {
        return registeredOriginItems.keySet();
    }

    @Override
    public Collection<Map<Identifier, Action.Factory<?>>> getRegisteredActionFactories() {
        return registeredActionFactories.values();
    }

    @Override
    public Collection<Map<Identifier, Condition.Factory<?>>> getRegisteredConditionFactories() {
        return registeredConditionFactories.values();
    }

    @Override
    public Collection<Origin> getRegisteredOrigins() {
        return registeredOrigins.values();
    }

    @Override
    public Collection<OriginLayer> getRegisteredOriginLayers() {
        return registeredOriginLayers.values();
    }

    @Override
    public Collection<Power> getRegisteredPowers() {
        return registeredPowers.values();
    }

    @Override
    public Collection<Power.Factory> getRegisteredPowerFactories() {
        return registeredPowerFactories.values();
    }

    @Override
    public Collection<OriginItem> getRegisteredOriginItems() {
        return registeredOriginItems.values();
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftRegistry)) return false;
        CraftRegistry that = (CraftRegistry) itemStack;
        return Objects.equals(plugin, that.plugin) && Objects.equals(registeredActionFactories, that.registeredActionFactories) && Objects.equals(registeredConditionFactories, that.registeredConditionFactories) && Objects.equals(registeredOrigins, that.registeredOrigins) && Objects.equals(registeredOriginLayers, that.registeredOriginLayers) && Objects.equals(registeredPowers, that.registeredPowers) && Objects.equals(registeredPowerFactories, that.registeredPowerFactories) && Objects.equals(registeredOriginItems, that.registeredOriginItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin);
    }

    @Override
    public String toString() {
        return "CraftRegistry{" +
                "plugin=" + plugin +
                ", registeredActionFactories=" + registeredActionFactories +
                ", registeredConditionFactories=" + registeredConditionFactories +
                ", registeredOrigins=" + registeredOrigins +
                ", registeredOriginLayers=" + registeredOriginLayers +
                ", registeredPowers=" + registeredPowers +
                ", registeredPowerFactories=" + registeredPowerFactories +
                ", registeredOriginItems=" + registeredOriginItems +
                '}';
    }
}

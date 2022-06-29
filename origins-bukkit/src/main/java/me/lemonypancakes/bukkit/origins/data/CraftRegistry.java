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
package me.lemonypancakes.bukkit.origins.data;

import me.lemonypancakes.bukkit.origins.*;
import me.lemonypancakes.bukkit.origins.data.storage.other.Misc;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class CraftRegistry implements Registry {

    private final OriginsBukkitPlugin plugin;
    private final Map<DataType<?>, Map<Identifier, Action.Factory<?>>> actionFactoryMap = new HashMap<>();
    private final Map<DataType<?>, Map<Identifier, Condition.Factory<?>>> conditionFactoryMap = new HashMap<>();
    private final Map<Identifier, Origin> originMap = new HashMap<>();
    private final Map<Identifier, Power> powerMap = new HashMap<>();
    private final Map<Identifier, Power.Factory> powerFactoryMap = new HashMap<>();
    private final Map<Identifier, OriginItem> originItemMap = new HashMap<>();
    private final List<Origin> origins = new ArrayList<>();

    public CraftRegistry(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public void register(Action.Factory<?> factory) {
        DataType<?> dataType = factory.getDataType();

        if (!actionFactoryMap.containsKey(dataType)) {
            actionFactoryMap.put(dataType, new HashMap<>());
        }
        Map<Identifier, Action.Factory<?>> identifierFactoryMap = actionFactoryMap.get(dataType);

        if (identifierFactoryMap != null) {
            Identifier identifier = factory.getIdentifier();

            if (!identifierFactoryMap.containsKey(identifier)) {
                identifierFactoryMap.put(identifier, factory);
            }
        }
    }

    @Override
    public void register(Condition.Factory<?> factory) {
        DataType<?> dataType = factory.getDataType();

        if (!conditionFactoryMap.containsKey(dataType)) {
            conditionFactoryMap.put(dataType, new HashMap<>());
        }
        Map<Identifier, Condition.Factory<?>> identifierFactoryMap = conditionFactoryMap.get(dataType);

        if (identifierFactoryMap != null) {
            Identifier identifier = factory.getIdentifier();

            if (!identifierFactoryMap.containsKey(identifier)) {
                identifierFactoryMap.put(identifier, factory);
            }
        }
    }

    @Override
    public void register(Origin origin) {
        Identifier identifier = origin.getIdentifier();

        if (!originMap.containsKey(identifier)) {
            originMap.put(identifier, origin);
            origins.add(origin);
            origins.sort(Comparator.comparing(Origin::getImpact));
            List<Inventory> inventoryList = new ArrayList<>();

            for (Origin origin1 : origins) {
                inventoryList.add(origin1.getInventoryGUI());
            }
            Misc.GUIS.clear();
            Misc.GUIS.addAll(inventoryList);
            if (!origin.getIdentifier().toString().equals("origins-bukkit:dummy_origin")) {
                Misc.ORIGINS_AS_STRING.add(origin.getIdentifier().toString());
            }
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "An origin with the identifier '" + origin.getIdentifier() + "' already exists.");
        }
    }

    @Override
    public void register(Power power) {
        Identifier identifier = power.getIdentifier();

        if (!powerMap.containsKey(identifier)) {
            powerMap.put(identifier, power);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "A power with the identifier '" + power.getIdentifier() + "' already exists.");
        }
    }

    @Override
    public void register(Power.Factory factory) {
        Identifier identifier = factory.getIdentifier();

        if (!powerFactoryMap.containsKey(identifier)) {
            powerFactoryMap.put(identifier, factory);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "A power factory with the identifier '" + factory.getIdentifier() + "' already exists.");
        }
    }

    @Override
    public void register(OriginItem originItem) {
        Identifier identifier = originItem.getIdentifier();

        if (!originItemMap.containsKey(identifier)) {
            if (originItem.getRecipe() != null) {
                Bukkit.addRecipe(originItem.getRecipe());
            }
            originItemMap.put(identifier, originItem);
        } else {
            ChatUtils.sendConsoleMessage(ChatUtils.Type.WARNING, "An origin item with the identifier '" + originItem.getIdentifier() + "' already exists.");
        }
    }

    @Override
    public <T> void unregisterActionFactory(DataType<T> dataType, Identifier identifier) {
        actionFactoryMap.get(dataType).remove(identifier);
    }

    @Override
    public <T> void unregisterConditionFactory(DataType<T> dataType, Identifier identifier) {
        conditionFactoryMap.remove(dataType).remove(identifier);
    }

    @Override
    public void unregisterOrigin(Identifier identifier) {
        originMap.remove(identifier);
    }

    @Override
    public void unregisterPower(Identifier identifier) {
        powerMap.remove(identifier);
    }

    @Override
    public void unregisterPowerFactory(Identifier identifier) {
        powerFactoryMap.remove(identifier);
    }

    @Override
    public void unregisterOriginItem(Identifier identifier) {
        if (originItemMap.containsKey(identifier)) {
            OriginItem originItem = originItemMap.get(identifier);

            if (originItem != null) {
                if (originItem.getRecipe() != null) {
                    Bukkit.removeRecipe(identifier.toNameSpacedKey());
                }
                originItemMap.remove(identifier);
            }
        }
    }

    @Override
    public <T> boolean hasActionFactory(DataType<T> dataType, Identifier identifier) {
        Map<Identifier, Action.Factory<?>> identifierFactoryMap = actionFactoryMap.get(dataType);

        if (identifierFactoryMap != null) {
            return identifierFactoryMap.containsKey(identifier);
        }
        return false;
    }

    @Override
    public <T> boolean hasConditionFactory(DataType<T> dataType, Identifier identifier) {
        Map<Identifier, Condition.Factory<?>> identifierFactoryMap = conditionFactoryMap.get(dataType);

        if (identifierFactoryMap != null) {
            return identifierFactoryMap.containsKey(identifier);
        }
        return false;
    }

    @Override
    public boolean hasOrigin(Identifier identifier) {
        return originMap.containsKey(identifier);
    }

    @Override
    public boolean hasPower(Identifier identifier) {
        return powerMap.containsKey(identifier);
    }

    @Override
    public boolean hasPowerFactory(Identifier identifier) {
        return powerFactoryMap.containsKey(identifier);
    }

    @Override
    public boolean hasOriginItem(Identifier identifier) {
        return originItemMap.containsKey(identifier);
    }

    @Override @SuppressWarnings("unchecked")
    public <T> Action.Factory<T> getActionFactory(DataType<T> dataType, Identifier identifier) {
        Map<Identifier, Action.Factory<?>> identifierFactoryMap = actionFactoryMap.get(dataType);

        if (identifierFactoryMap != null) {
            return (Action.Factory<T>) identifierFactoryMap.get(identifier);
        }
        return null;
    }

    @Override @SuppressWarnings("unchecked")
    public <T> Condition.Factory<T> getConditionFactory(DataType<T> dataType, Identifier identifier) {
        Map<Identifier, Condition.Factory<?>> identifierFactoryMap = conditionFactoryMap.get(dataType);

        if (identifierFactoryMap != null) {
            return (Condition.Factory<T>) identifierFactoryMap.get(identifier);
        }
        return null;
    }

    @Override
    public Origin getOrigin(Identifier identifier) {
        return originMap.get(identifier);
    }

    @Override
    public Power getPower(Identifier identifier) {
        return powerMap.get(identifier);
    }

    @Override
    public Power.Factory getPowerFactory(Identifier identifier) {
        return powerFactoryMap.get(identifier);
    }

    @Override
    public OriginItem getOriginItem(Identifier identifier) {
        return originItemMap.get(identifier);
    }

    @Override
    public Set<DataType<?>> getActionFactoriesKeySet() {
        return actionFactoryMap.keySet();
    }

    @Override
    public Set<DataType<?>> getConditionFactoriesKeySet() {
        return conditionFactoryMap.keySet();
    }

    @Override
    public Set<Identifier> getOriginsKeySet() {
        return originMap.keySet();
    }

    @Override
    public Set<Identifier> getPowersKeySet() {
        return powerMap.keySet();
    }

    @Override
    public Set<Identifier> getPowerFactoriesKeySet() {
        return powerFactoryMap.keySet();
    }

    @Override
    public Set<Identifier> getOriginItemsKeySet() {
        return originItemMap.keySet();
    }

    @Override
    public Collection<Map<Identifier, Action.Factory<?>>> getActionFactories() {
        return actionFactoryMap.values();
    }

    @Override
    public Collection<Map<Identifier, Condition.Factory<?>>> getConditionFactories() {
        return conditionFactoryMap.values();
    }

    @Override
    public Collection<Origin> getOrigins() {
        return originMap.values();
    }

    @Override
    public Collection<Power> getPowers() {
        return powerMap.values();
    }

    @Override
    public Collection<Power.Factory> getPowerFactories() {
        return powerFactoryMap.values();
    }

    @Override
    public Collection<OriginItem> getOriginItems() {
        return originItemMap.values();
    }

    @Override
    public boolean isActionFactoriesEmpty() {
        return actionFactoryMap.isEmpty();
    }

    @Override
    public boolean isConditionFactoriesEmpty() {
        return conditionFactoryMap.isEmpty();
    }

    @Override
    public boolean isOriginsEmpty() {
        return originMap.isEmpty();
    }

    @Override
    public boolean isPowersEmpty() {
        return powerMap.isEmpty();
    }

    @Override
    public boolean isPowerFactoriesEmpty() {
        return powerFactoryMap.isEmpty();
    }

    @Override
    public boolean isOriginItemsEmpty() {
        return originItemMap.isEmpty();
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftRegistry)) return false;
        CraftRegistry that = (CraftRegistry) itemStack;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(actionFactoryMap, that.actionFactoryMap) && Objects.equals(conditionFactoryMap, that.conditionFactoryMap) && Objects.equals(originMap, that.originMap) && Objects.equals(powerMap, that.powerMap) && Objects.equals(powerFactoryMap, that.powerFactoryMap) && Objects.equals(originItemMap, that.originItemMap) && Objects.equals(getOrigins(), that.getOrigins());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), actionFactoryMap, conditionFactoryMap, originMap, powerMap, powerFactoryMap, originItemMap, getOrigins());
    }

    @Override
    public String toString() {
        return "CraftRegistry{" +
                "plugin=" + plugin +
                ", actionFactoryMap=" + actionFactoryMap +
                ", conditionFactoryMap=" + conditionFactoryMap +
                ", originMap=" + originMap +
                ", powerMap=" + powerMap +
                ", powerFactoryMap=" + powerFactoryMap +
                ", originItemMap=" + originItemMap +
                ", origins=" + origins +
                '}';
    }
}

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
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.util.Identifier;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Registry extends OriginsBukkitPluginHolder {

    void registerActionFactory(Action.Factory<?> actionFactory);

    void registerConditionFactory(Condition.Factory<?> conditionFactory);

    void registerOrigin(Origin origin);

    void registerOriginLayer(OriginLayer originLayer);

    void registerPower(Power power);

    void registerPowerFactory(Power.Factory powerFactory);

    void registerOriginItem(OriginItem originItem);

    <T> void unregisterActionFactory(DataType<T> dataType, Identifier identifier);

    <T> void unregisterConditionFactory(DataType<T> dataType, Identifier identifier);

    void unregisterOrigin(Identifier identifier);

    void unregisterOriginLayer(Identifier identifier);

    void unregisterPower(Identifier identifier);

    void unregisterPowerFactory(Identifier identifier);

    void unregisterOriginItem(Identifier identifier);

    <T> Action.Factory<T> getRegisteredActionFactory(DataType<T> dataType, Identifier identifier);

    <T> Condition.Factory<T> getRegisteredConditionFactory(DataType<T> dataType, Identifier identifier);

    Origin getRegisteredOrigin(Identifier identifier);

    OriginLayer getRegisteredOriginLayer(Identifier identifier);

    Power getRegisteredPower(Identifier identifier);

    Power.Factory getRegisteredPowerFactory(Identifier identifier);

    OriginItem getRegisteredOriginItem(Identifier identifier);

    Set<DataType<?>> getRegisteredActionFactoriesKeySet();

    Set<DataType<?>> getRegisteredConditionFactoriesKeySet();

    Set<Identifier> getRegisteredOriginsKeySet();

    Set<Identifier> getRegisteredOriginLayersKeySet();

    Set<Identifier> getRegisteredPowersKeySet();

    Set<Identifier> getRegisteredPowerFactoriesKeySet();

    Set<Identifier> getRegisteredOriginItemsKeySet();

    Collection<Map<Identifier, Action.Factory<?>>> getRegisteredActionFactories();

    Collection<Map<Identifier, Condition.Factory<?>>> getRegisteredConditionFactories();

    Collection<Origin> getRegisteredOrigins();

    Collection<OriginLayer> getRegisteredOriginLayers();

    Collection<Power> getRegisteredPowers();

    Collection<Power.Factory> getRegisteredPowerFactories();

    Collection<OriginItem> getRegisteredOriginItems();
}

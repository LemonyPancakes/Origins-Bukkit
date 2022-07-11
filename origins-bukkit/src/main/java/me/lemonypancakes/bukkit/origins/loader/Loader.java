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
package me.lemonypancakes.bukkit.origins.loader;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonArray;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.wrapper.ConditionAction;
import me.lemonypancakes.bukkit.origins.wrapper.Element;

import java.io.Reader;

public interface Loader extends OriginsBukkitPluginHolder {

    Origin loadOriginFromFile(Reader reader, String identifierKey, String identifierValue);

    Power loadPowerFromFile(Reader reader, String identifierKey, String identifierValue);

    OriginLayer loadOriginLayerFromFile(Reader reader, String identifierKey, String identifierValue);

    <T> Action<T> loadAction(DataType<T> dataType, JsonObject jsonObject);

    <T> Action<T> loadAction(DataType<T> dataType, JsonObject jsonObject, String memberName);

    <T> Action<T>[] loadActions(DataType<T> dataType, JsonObject jsonObject);

    <T> Action<T>[] loadActions(DataType<T> dataType, JsonObject jsonObject, String memberName);

    <T> Action<T>[] loadActions(DataType<T> dataType, JsonArray jsonArray);

    <T> Condition<T> loadCondition(DataType<T> dataType, JsonObject jsonObject);

    <T> Condition<T> loadCondition(DataType<T> dataType, JsonObject jsonObject, String memberName);

    <T> Condition<T>[] loadConditions(DataType<T> dataType, JsonObject jsonObject);

    <T> Condition<T>[] loadConditions(DataType<T> dataType, JsonObject jsonObject, String memberName);

    <T> Condition<T>[] loadConditions(DataType<T> dataType, JsonArray jsonArray);

    <T> ConditionAction<T> loadConditionAction(DataType<T> dataType, JsonObject jsonObject);

    <T> ConditionAction<T> loadConditionAction(DataType<T> dataType, JsonObject jsonObject, String conditionMemberName, String actionMemberName);

    <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonObject jsonObject);

    <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonObject jsonObject, String conditionMemberName, String actionMemberName);

    <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonArray jsonArray);

    <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonArray jsonArray, String conditionMemberName, String actionMemberName);

    <T> Element<T>[] loadElements(DataType<T> dataType, JsonObject jsonObject);
}

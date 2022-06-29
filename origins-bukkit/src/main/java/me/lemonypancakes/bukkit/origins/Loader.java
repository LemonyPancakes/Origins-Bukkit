package me.lemonypancakes.bukkit.origins;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.wrapper.ConditionAction;
import me.lemonypancakes.bukkit.origins.wrapper.Element;

import java.io.Reader;

public interface Loader extends OriginsBukkitPluginHolder {

    Origin loadOriginFromFile(Reader reader, String identifierKey, String identifierValue);

    Power loadPowerFromFile(Reader reader, String identifierKey, String identifierValue);

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

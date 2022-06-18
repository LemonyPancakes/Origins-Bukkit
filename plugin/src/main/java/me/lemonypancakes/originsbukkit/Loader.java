package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.wrapper.ConditionAction;
import me.lemonypancakes.originsbukkit.wrapper.Element;

import java.io.File;
import java.io.Reader;

public interface Loader extends OriginsBukkitPluginHolder {

    Origin loadOriginFromFile(Reader reader, File zipFileSource, String identifierValue);

    Power loadPowerFromFile(Reader reader, File zipFileSource, String identifierValue);

    Tag<?> loadTagFromFile(File tagFile, String identifierKey);

    Tag<?> loadTagFromFile(File tagFile);

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

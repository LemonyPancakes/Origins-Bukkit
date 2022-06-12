package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;

public interface Loader extends OriginsBukkitPluginHolder {

    Origin loadOriginFromFile(File originFile, String identifierKey);

    Origin loadOriginFromFile(File originFile);

    Power loadPowerFromFile(File powerFile, String identifierKey);

    Power loadPowerFromFile(File powerFile);

    Tag<?> loadTagFromFile(File tagFile, String identifierKey);

    Tag<?> loadTagFromFile(File tagFile);

    Action[] loadActions(JsonObject jsonObject);

    Action[] loadActions(JsonArray jsonArray);

    Condition[] loadConditions(JsonObject jsonObject);

    Condition[] loadConditions(JsonArray jsonArray);
}

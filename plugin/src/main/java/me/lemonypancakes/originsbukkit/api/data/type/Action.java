package me.lemonypancakes.originsbukkit.api.data.type;

import com.google.gson.JsonObject;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Action<T> extends Consumer<T> {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);

    JsonObject getJsonObject();

    void setJsonObject(JsonObject jsonObject);

    BiConsumer<JsonObject, T> getBiConsumer();

    void setBiConsumer(BiConsumer<JsonObject, T> biConsumer);
}

package me.lemonypancakes.originsbukkit.api.data.type;

import com.google.gson.JsonObject;

public interface Power {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);

    JsonObject getJsonObject();

    void setJsonObject(JsonObject jsonObject);

    <T> Action<T>[] getActions();

    void setActions(Action<?>[] action);

    <T> Condition<T> getCondition();

    <T> void setCondition(Condition<T> condition);

    <T> void invoke(T t);
}

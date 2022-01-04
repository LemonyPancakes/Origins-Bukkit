package me.lemonypancakes.originsbukkit.api.data.type;

import com.google.gson.JsonObject;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Condition<T> extends Predicate<T> {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);

    JsonObject getJsonObject();

    void setJsonObject(JsonObject jsonObject);

    BiPredicate<JsonObject, T> getBiPredicate();

    void setBiPredicate(BiPredicate<JsonObject, T> biPredicate);

    boolean isInverted();

    Condition<T> copy();

}

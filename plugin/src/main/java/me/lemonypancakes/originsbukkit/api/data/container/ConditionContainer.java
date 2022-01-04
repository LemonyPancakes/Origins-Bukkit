package me.lemonypancakes.originsbukkit.api.data.container;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ConditionContainer<T> implements Condition<T> {

    private Identifier identifier;
    private JsonObject jsonObject;
    private BiPredicate<JsonObject, T> biPredicate;

    private boolean isInverted;

    public ConditionContainer(Identifier identifier,
                              JsonObject jsonObject,
                              BiPredicate<JsonObject, T> biPredicate) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.biPredicate = biPredicate;
        if (jsonObject != null && jsonObject.has("is_inverted")) {
            this.isInverted = jsonObject.get("is_inverted").getAsBoolean();
        } else {
            this.isInverted = false;
        }
    }

    public ConditionContainer(Identifier identifier,
                              JsonObject jsonObject) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        if (jsonObject != null && jsonObject.has("is_inverted")) {
            this.isInverted = jsonObject.get("is_inverted").getAsBoolean();
        } else {
            this.isInverted = false;
        }
    }

    public ConditionContainer(Identifier identifier) {
        this.identifier = identifier;
        this.isInverted = false;
    }

    public ConditionContainer() {
        this.isInverted = false;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        if (jsonObject != null && jsonObject.has("is_inverted")) {
            this.isInverted = jsonObject.get("is_inverted").getAsBoolean();
        } else {
            this.isInverted = false;
        }
    }

    @Override
    public BiPredicate<JsonObject, T> getBiPredicate() {
        return biPredicate;
    }

    @Override
    public void setBiPredicate(BiPredicate<JsonObject, T> biPredicate) {
        this.biPredicate = biPredicate;
    }

    @Override
    public boolean isInverted() {
        return isInverted;
    }

    @Override
    public Condition<T> copy() {
        return new ConditionContainer<>(
                getIdentifier(),
                getJsonObject(),
                getBiPredicate());
    }

    @Override
    public boolean test(T t) {
        if (isInverted()) {
            return !biPredicate.test(
                    getJsonObject(),
                    t);
        } else {
            return biPredicate.test(
                    getJsonObject(),
                    t);
        }
    }

    @Override
    public Predicate<T> and(@Nonnull Predicate<? super T> other) {
        return Condition.super.and(other);
    }

    @Override
    public Predicate<T> negate() {
        return Condition.super.negate();
    }

    @Override
    public Predicate<T> or(@Nonnull Predicate<? super T> other) {
        return Condition.super.or(other);
    }

    @Override
    public String toString() {
        return "ConditionContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", biPredicate=" + biPredicate +
                ", isInverted=" + isInverted +
                '}';
    }
}

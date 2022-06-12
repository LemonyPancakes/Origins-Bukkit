package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Temp;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CraftCondition implements Condition {

    private final OriginsBukkitPlugin plugin;
    private JsonObject jsonObject;
    private BiPredicate<JsonObject, Temp> biPredicate;
    private boolean inverted;

    public CraftCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Temp> biPredicate) {
        this.plugin = plugin;
        setJsonObject(jsonObject);
        setBiPredicate(biPredicate);
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        if (jsonObject != null) {
            if (jsonObject.has("inverted")) {
                this.inverted = jsonObject.get("inverted").getAsBoolean();
            }
        }
    }

    @Override
    public BiPredicate<JsonObject, Temp> getBiPredicate() {
        return biPredicate;
    }

    @Override
    public void setBiPredicate(BiPredicate<JsonObject, Temp> biPredicate) {
        this.biPredicate = biPredicate;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public Condition newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
        return new CraftCondition(plugin, jsonObject, biPredicate);
    }

    @Override
    public boolean test(Temp temp) {
        if (getBiPredicate() == null) {
            return true;
        }
        if (isInverted()) {
            return !biPredicate.test(
                    getJsonObject(),
                    temp);
        } else {
            return biPredicate.test(
                    getJsonObject(),
                    temp);
        }
    }

    @Override
    public Predicate<Temp> and(@Nonnull Predicate<? super Temp> other) {
        return Condition.super.and(other);
    }

    @Override
    public Predicate<Temp> negate() {
        return Condition.super.negate();
    }

    @Override
    public Predicate<Temp> or(@Nonnull Predicate<? super Temp> other) {
        return Condition.super.or(other);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftCondition)) return false;
        CraftCondition that = (CraftCondition) o;
        return isInverted() == that.isInverted() && Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getBiPredicate(), that.getBiPredicate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), getJsonObject(), getBiPredicate(), isInverted());
    }

    @Override
    public String toString() {
        return "CraftCondition{" +
                "plugin=" + plugin +
                ", jsonObject=" + jsonObject +
                ", biPredicate=" + biPredicate +
                ", inverted=" + inverted +
                '}';
    }
}

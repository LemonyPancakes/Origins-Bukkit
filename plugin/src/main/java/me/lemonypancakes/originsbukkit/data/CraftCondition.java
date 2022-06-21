package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CraftCondition<T> implements Condition<T> {

    private final OriginsBukkitPlugin plugin;
    private JsonObject jsonObject;
    private final DataType<T> dataType;
    private BiPredicate<JsonObject, T> biPredicate;
    private boolean inverted;

    public CraftCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiPredicate<JsonObject, T> biPredicate) {
        this.plugin = plugin;
        this.jsonObject = jsonObject;
        if (jsonObject != null) {
            if (jsonObject.has("inverted")) {
                this.inverted = jsonObject.get("inverted").getAsBoolean();
            }
        }
        this.dataType = dataType;
        this.biPredicate = biPredicate;
    }

    public CraftCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, T> biPredicate) {
        this(plugin, jsonObject, null, biPredicate);
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
    public DataType<T> getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(DataType<T> dataType) {}

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
        return inverted;
    }

    @Override
    public Condition<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftCondition<>(plugin, jsonObject, dataType, biPredicate);
    }

    @Override
    public Condition<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
        return newInstance(plugin, jsonObject, null);
    }

    @Override
    public boolean test(T t) {
        if (biPredicate == null) {
            return true;
        }
        if (isInverted()) {
            return !biPredicate.test(getJsonObject(), t);
        } else {
            return biPredicate.test(getJsonObject(), t);
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
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftCondition)) return false;
        CraftCondition<?> that = (CraftCondition<?>) itemStack;
        return isInverted() == that.isInverted() && Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getDataType(), that.getDataType()) && Objects.equals(getBiPredicate(), that.getBiPredicate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), getJsonObject(), getDataType(), getBiPredicate(), isInverted());
    }

    @Override
    public String toString() {
        return "CraftCondition{" +
                "plugin=" + plugin +
                ", jsonObject=" + jsonObject +
                ", dataType=" + dataType +
                ", biPredicate=" + biPredicate +
                ", inverted=" + inverted +
                '}';
    }
}

package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Condition<T> extends Predicate<T>, DataTypeHolder<T>, JsonObjectHolder, OriginsBukkitPluginHolder {

    BiPredicate<JsonObject, T> getBiPredicate();

    void setBiPredicate(BiPredicate<JsonObject, T> biPredicate);

    boolean isInverted();

    Condition<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType);

    Condition<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject);

    final class Factory<T> implements Identifiable, DataTypeHolder<T> {

        private Identifier identifier;
        private DataType<T> dataType;
        private Condition<T> condition;

        public Factory(Identifier identifier, DataType<T> dataType, Condition<T> condition) {
            this.identifier = identifier;
            this.dataType = dataType;
            this.condition = condition;
        }

        public Condition<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
            return condition.newInstance(plugin, jsonObject, dataType);
        }

        public Condition<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
            return condition.newInstance(plugin, jsonObject, dataType);
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
        public DataType<T> getDataType() {
            return dataType;
        }

        @Override
        public void setDataType(DataType<T> dataType) {
            this.dataType = dataType;
        }

        public Condition<T> getCondition() {
            return condition;
        }

        public void setCondition(Condition<T> condition) {
            this.condition = condition;
        }
    }
}

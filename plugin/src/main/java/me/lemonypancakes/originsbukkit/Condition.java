package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.function.*;

public interface Condition<T> extends Predicate<T>, DataTypeHolder<T>, JsonObjectHolder, OriginsBukkitPluginHolder {

    BiPredicate<JsonObject, T> getBiPredicate();

    void setBiPredicate(BiPredicate<JsonObject, T> biPredicate);

    boolean isInverted();

    final class Factory<T> implements Identifiable, DataTypeHolder<T> {

        private Identifier identifier;
        private DataType<T> dataType;
        private BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Condition<T>>>> biFunction;

        public Factory(Identifier identifier, DataType<T> dataType, BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Condition<T>>>> biFunction) {
            this.identifier = identifier;
            this.dataType = dataType;
            this.biFunction = biFunction;
        }

        public Condition<T> create(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
            return biFunction.apply(plugin, jsonObject).apply(dataType).get();
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

        public BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Condition<T>>>> getBiFunction() {
            return biFunction;
        }

        public void setBiFunction(BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Condition<T>>>> biFunction) {
            this.biFunction = biFunction;
        }
    }
}

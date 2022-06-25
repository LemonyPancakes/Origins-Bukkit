package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.function.*;

public interface Action<T> extends Consumer<T>, DataTypeHolder<T>, JsonObjectHolder, OriginsBukkitPluginHolder {

    BiConsumer<JsonObject, T> getBiConsumer();

    void setBiConsumer(BiConsumer<JsonObject, T> biConsumer);

    final class Factory<T> implements Identifiable, DataTypeHolder<T> {

        private Identifier identifier;
        private DataType<T> dataType;
        private BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Action<T>>>> biFunction;

        public Factory(Identifier identifier, DataType<T> dataType, BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Action<T>>>> biFunction) {
            this.identifier = identifier;
            this.dataType = dataType;
            this.biFunction = biFunction;
        }

        public Action<T> create(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
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

        public BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Action<T>>>> getBiFunction() {
            return biFunction;
        }

        public void setBiFunction(BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Action<T>>>> biFunction) {
            this.biFunction = biFunction;
        }
    }
}

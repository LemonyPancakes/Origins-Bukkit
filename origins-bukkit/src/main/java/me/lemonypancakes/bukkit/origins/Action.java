package me.lemonypancakes.bukkit.origins;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.util.Identifier;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Action<T> extends OriginsBukkitPluginHolder, Consumer<T>, DataTypeHolder<T>, JsonObjectHolder {

    BiConsumer<JsonObject, T> getBiConsumer();

    void setBiConsumer(BiConsumer<JsonObject, T> biConsumer);

    final class Factory<T> implements Identifiable, DataTypeHolder<T> {

        private Identifier identifier;
        private DataType<T> dataType;
        private Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Action<T>>>>> function;

        public Factory(Identifier identifier, DataType<T> dataType, Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Action<T>>>>> function) {
            this.identifier = identifier;
            this.dataType = dataType;
            this.function = function;
        }

        public Action<T> create(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
            return function.apply(plugin).apply(jsonObject).apply(dataType).get();
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

        public Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Action<T>>>>> getFunction() {
            return function;
        }

        public void setFunction(Function<OriginsBukkitPlugin, Function<JsonObject, Function<DataType<T>, Supplier<Action<T>>>>> function) {
            this.function = function;
        }

        @Override
        public boolean equals(Object itemStack) {
            if (this == itemStack) return true;
            if (!(itemStack instanceof Factory)) return false;
            Factory<?> factory = (Factory<?>) itemStack;
            return Objects.equals(getIdentifier(), factory.getIdentifier()) && Objects.equals(getDataType(), factory.getDataType()) && Objects.equals(getFunction(), factory.getFunction());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getIdentifier(), getDataType(), getFunction());
        }

        @Override
        public String toString() {
            return "Factory{" +
                    "identifier=" + identifier +
                    ", dataType=" + dataType +
                    ", function=" + function +
                    '}';
        }
    }
}

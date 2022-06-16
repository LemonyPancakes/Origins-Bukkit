package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Action<T> extends Consumer<T>, DataTypeHolder<T>, JsonObjectHolder, OriginsBukkitPluginHolder {

    BiConsumer<JsonObject, T> getBiConsumer();

    void setBiConsumer(BiConsumer<JsonObject, T> biConsumer);

    Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType);

    Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject);

    final class Factory<T> implements Identifiable, DataTypeHolder<T> {

        private Identifier identifier;
        private DataType<T> dataType;
        private Action<T> action;

        public Factory(Identifier identifier, DataType<T> dataType, Action<T> action) {
            this.identifier = identifier;
            this.dataType = dataType;
            this.action = action;
        }

        public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
            return action.newInstance(plugin, jsonObject, dataType);
        }

        public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
            return action.newInstance(plugin, jsonObject, dataType);
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

        public Action<T> getAction() {
            return action;
        }

        public void setAction(Action<T> action) {
            this.action = action;
        }
    }
}

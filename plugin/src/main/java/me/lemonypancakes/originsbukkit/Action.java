package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Action extends Consumer<Temp>, JsonObjectHolder, OriginsBukkitPluginHolder {

    BiConsumer<JsonObject, Temp> getBiConsumer();

    void setBiConsumer(BiConsumer<JsonObject, Temp> biConsumer);

    Action newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject);

    final class Factory implements Identifiable {

        private Identifier identifier;
        private Action action;

        public Factory(Identifier identifier, Action action) {
            this.identifier = identifier;
            this.action = action;
        }

        public Action newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
            return action.newInstance(plugin, jsonObject);
        }

        @Override
        public Identifier getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(Identifier identifier) {
            this.identifier = identifier;
        }

        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }
    }
}

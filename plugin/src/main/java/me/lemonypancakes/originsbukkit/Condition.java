package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Condition extends Predicate<Temp>, JsonObjectHolder, OriginsBukkitPluginHolder {

    BiPredicate<JsonObject, Temp> getBiPredicate();

    void setBiPredicate(BiPredicate<JsonObject, Temp> biPredicate);

    boolean isInverted();

    Condition newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject);

    final class Factory implements Identifiable {

        private Identifier identifier;
        private Condition condition;

        public Factory(Identifier identifier, Condition condition) {
            this.identifier = identifier;
            this.condition = condition;
        }

        public Condition newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
            return condition.newInstance(plugin, jsonObject);
        }

        @Override
        public Identifier getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(Identifier identifier) {
            this.identifier = identifier;
        }

        public Condition getAction() {
            return condition;
        }

        public void setAction(Condition condition) {
            this.condition = condition;
        }
    }
}

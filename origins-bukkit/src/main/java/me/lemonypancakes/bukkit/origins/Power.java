package me.lemonypancakes.bukkit.origins;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Power extends OriginsBukkitPluginHolder, Identifiable, JsonObjectHolder, ConditionHolder<Entity>, Listener {

    Set<Player> getMembers();

    void addMember(Player player);

    void removeMember(Player player);

    boolean hasMember(Player player);

    boolean isActive(Player player);

    final class Factory implements Identifiable {

        private Identifier identifier;
        private Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> function;

        public Factory(Identifier identifier, Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> function) {
            this.identifier = identifier;
            this.function = function;
        }

        public Power create(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
            return function.apply(plugin).apply(identifier).apply(jsonObject).get();
        }

        @Override
        public Identifier getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(Identifier identifier) {
            this.identifier = identifier;
        }

        public Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> getFunction() {
            return function;
        }

        public void setFunction(Function<OriginsBukkitPlugin, Function<Identifier, Function<JsonObject, Supplier<Power>>>> function) {
            this.function = function;
        }

        @Override
        public boolean equals(Object itemStack) {
            if (this == itemStack) return true;
            if (!(itemStack instanceof Factory)) return false;
            Factory factory = (Factory) itemStack;
            return Objects.equals(getIdentifier(), factory.getIdentifier()) && Objects.equals(getFunction(), factory.getFunction());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getIdentifier(), getFunction());
        }

        @Override
        public String toString() {
            return "Factory{" +
                    "identifier=" + identifier +
                    ", function=" + function +
                    '}';
        }
    }
}

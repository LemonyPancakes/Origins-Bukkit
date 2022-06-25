package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Power extends Identifiable, JsonObjectHolder, ConditionHolder<Entity>, OriginsBukkitPluginHolder {

    Set<Player> getMembers();

    void addMember(Player player);

    void removeMember(Player player);

    boolean hasMember(Player player);

    boolean isActive(Player player);

    final class Factory implements Identifiable {

        private Identifier identifier;
        private BiFunction<OriginsBukkitPlugin, Identifier, Function<JsonObject, Supplier<Power>>> biFunction;

        public Factory(Identifier identifier, BiFunction<OriginsBukkitPlugin, Identifier, Function<JsonObject, Supplier<Power>>> biFunction) {
            this.identifier = identifier;
            this.biFunction = biFunction;
        }

        public Power create(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
            return biFunction.apply(plugin, identifier).apply(jsonObject).get();
        }

        @Override
        public Identifier getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(Identifier identifier) {
            this.identifier = identifier;
        }

        public BiFunction<OriginsBukkitPlugin, Identifier, Function<JsonObject, Supplier<Power>>> getBiFunction() {
            return biFunction;
        }

        public void setBiFunction(BiFunction<OriginsBukkitPlugin, Identifier, Function<JsonObject, Supplier<Power>>> biFunction) {
            this.biFunction = biFunction;
        }
    }
}

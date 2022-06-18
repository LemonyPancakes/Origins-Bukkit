package me.lemonypancakes.originsbukkit;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;

public interface Power extends Identifiable, JsonObjectHolder, ConditionHolder<Entity>, OriginsBukkitPluginHolder {

    Set<Player> getMembers();

    void addMember(Player player);

    void removeMember(Player player);

    boolean hasMember(Player player);

    boolean isActive(Player player);

    Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject);

    final class Factory implements Identifiable {

        private Identifier identifier;
        private Power power;

        public Factory(Identifier identifier, Power power) {
            this.identifier = identifier;
            this.power = power;
        }

        public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
            return power.newInstance(plugin, identifier, jsonObject);
        }

        @Override
        public Identifier getIdentifier() {
            return identifier;
        }

        @Override
        public void setIdentifier(Identifier identifier) {
            this.identifier = identifier;
        }

        public Power getPower() {
            return power;
        }

        public void setPower(Power power) {
            this.power = power;
        }
    }
}

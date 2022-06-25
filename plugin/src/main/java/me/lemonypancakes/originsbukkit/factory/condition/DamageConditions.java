package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.originsbukkit.util.Comparison;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.BiPredicate;

public class DamageConditions {

    public DamageConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new CraftOrCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "amount"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, damage) -> {
            if (damage != null) {
                return Comparison.parseComparison(jsonObject).compare(damage.getAmount(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attacker"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new Attacker(plugin, jsonObject, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fire"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, damage) -> {
            if (damage != null) {
                EntityDamageEvent.DamageCause damageCause = damage.getDamageCause();

                if (damageCause != null) {
                    return damageCause == EntityDamageEvent.DamageCause.FIRE || damageCause == EntityDamageEvent.DamageCause.FIRE_TICK || damageCause == EntityDamageEvent.DamageCause.LAVA;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "name"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, damage) -> {
            if (damage != null) {
                EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.valueOf(jsonObject1.get("name").getAsString());

                if (damage.getDamageCause() != null) {
                    return damage.getDamageCause() == damageCause;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "projectile"), DataType.DAMAGE, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, damage) -> {
            if (damage != null) {
                Entity damager = damage.getEntity();

                if (damager != null) {
                    if (damager instanceof Projectile) {
                        if (!jsonObject1.has("projectile")) {
                            return true;
                        }
                        Projectile projectile = (Projectile) damager;

                        return projectile.getType() == EntityType.valueOf(jsonObject1.get("projectile").getAsString());
                    }
                }
            }
            return false;
        })));
    }

    public static class Attacker extends CraftCondition<Damage> {

        private Condition<Entity> condition;

        public Attacker(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Damage> biPredicate) {
            super(plugin, jsonObject, DataType.DAMAGE, biPredicate);
            if (jsonObject != null) {
                this.condition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject);
                setBiPredicate(((jsonObject1, damage) -> {
                    if (damage != null) {
                        Entity damager = damage.getEntity();

                        if (damager != null) {
                            if (condition == null) {
                                return true;
                            }
                            return condition.test(damager);
                        }
                    }
                    return false;
                }));
            }
        }
    }
}

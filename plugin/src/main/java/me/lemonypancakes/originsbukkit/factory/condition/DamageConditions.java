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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.DAMAGE, new CraftAndCondition<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.DAMAGE, new CraftOrCondition<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "amount"), DataType.DAMAGE, new CraftCondition<>(plugin, null, (jsonObject, damage) -> {
            if (damage != null) {
                return Comparison.parseComparison(jsonObject).compare(damage.getAmount(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attacker"), DataType.DAMAGE, new Attacker(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fire"), DataType.DAMAGE, new CraftCondition<>(plugin, null, (jsonObject, damage) -> {
            if (damage != null) {
                EntityDamageEvent.DamageCause damageCause = damage.getDamageCause();

                if (damageCause != null) {
                    return damageCause == EntityDamageEvent.DamageCause.FIRE || damageCause == EntityDamageEvent.DamageCause.FIRE_TICK || damageCause == EntityDamageEvent.DamageCause.LAVA;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "name"), DataType.DAMAGE, new CraftCondition<>(plugin, null, (jsonObject, damage) -> {
            if (damage != null) {
                EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.valueOf(jsonObject.get("name").getAsString());

                if (damage.getDamageCause() != null) {
                    return damage.getDamageCause() == damageCause;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "projectile"), DataType.DAMAGE, new CraftCondition<>(plugin, null, (jsonObject, damage) -> {
            if (damage != null) {
                Entity damager = damage.getEntity();

                if (damager != null) {
                    if (damager instanceof Projectile) {
                        if (!jsonObject.has("projectile")) {
                            return true;
                        }
                        Projectile projectile = (Projectile) damager;

                        return projectile.getType() == EntityType.valueOf(jsonObject.get("projectile").getAsString());
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

        @Override
        public Condition<Damage> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
            return new Attacker(plugin, jsonObject, getBiPredicate());
        }
    }
}

package me.lemonypancakes.originsbukkit.factory.event;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/listener/entity_damage_event/compare/cause"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageEvent) {
                                EntityDamageEvent event = (EntityDamageEvent) temp.getEvent();

                                if (data.has("cause")) {
                                    String causeString = data.get("cause").getAsString();

                                    if (causeString != null) {
                                        EntityDamageEvent.DamageCause cause
                                                = EntityDamageEvent.DamageCause.valueOf(causeString);

                                        return event.getCause() == cause;
                                    }
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/listener/entity_damage_event/compare/damage"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageEvent) {
                                EntityDamageEvent event = (EntityDamageEvent) temp.getEvent();

                                if (data.has("damage")) {
                                    double damage = data.get("damage").getAsDouble();

                                    return event.getDamage() == damage;
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/listener/entity_damage_event/compare/final_damage"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageEvent) {
                                EntityDamageEvent event = (EntityDamageEvent) temp.getEvent();

                                if (data.has("final_damage")) {
                                    double finalDamage = data.get("final_damage").getAsDouble();

                                    return event.getFinalDamage() == finalDamage;
                                }
                            }
                            return false;
                        }
                )
        );
    }
}

package me.lemonypancakes.originsbukkit.factory.event;

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEventConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/listener/entity_damage_by_entity_event/compare/damager/entity_type"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) temp.getEvent();

                                if (data.has("damager_entity_type")) {
                                    EntityType entityType
                                            = new Gson().fromJson(
                                            data.get(
                                                    "damager_entity_type"
                                            ),
                                            EntityType.class
                                    );

                                    if (entityType != null) {
                                        return event.getDamager().getType() == entityType;
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
                                OriginsBukkit.KEY, "condition/listener/entity_damage_by_entity_event/compare/victim/entity_type"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) temp.getEvent();

                                if (data.has("victim_entity_type")) {
                                    EntityType entityType
                                            = new Gson().fromJson(
                                                    data.get(
                                                            "victim_entity_type"
                                                    ),
                                            EntityType.class
                                    );

                                    if (entityType != null) {
                                        return event.getEntity().getType() == entityType;
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
                                OriginsBukkit.KEY, "condition/listener/entity_damage_by_entity_event/compare/damage"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) temp.getEvent();

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
                                OriginsBukkit.KEY, "condition/listener/entity_damage_by_entity_event/compare/final_damage"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) temp.getEvent();

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

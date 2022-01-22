package me.lemonypancakes.originsbukkit.factory.event;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.util.MathUtils;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEventActions {

    public static void register() {
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/listener/entity_damage_by_entity_event/set_damage"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) temp.getEvent();

                                if (data.has("damage")) {
                                    double baseDamage = event.getDamage();
                                    String damage = data.get("damage")
                                            .getAsString()
                                            .replace(
                                                    "%base_damage%", String.valueOf(
                                                            baseDamage
                                                    )
                                            );
                                    double result = MathUtils.evaluate(damage);

                                    event.setDamage(result);
                                }
                            }
                        }
                )
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/listener/entity_damage_by_entity_event/set_additional_damage"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) temp.getEvent();

                                if (data.has("additional_damage")) {
                                    double baseDamage = event.getDamage();
                                    String additionalDamage = data.get("additional_damage")
                                            .getAsString()
                                            .replace(
                                                    "%base_damage%", String.valueOf(
                                                            baseDamage
                                                    )
                                            );
                                    double result = MathUtils.evaluate(additionalDamage);
                                    double totalDamage = baseDamage + result;

                                    event.setDamage(totalDamage);
                                }
                            }
                        }
                )
        );
    }
}

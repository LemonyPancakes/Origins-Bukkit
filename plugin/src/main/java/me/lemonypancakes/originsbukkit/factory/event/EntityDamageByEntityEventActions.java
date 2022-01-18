package me.lemonypancakes.originsbukkit.factory.event;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
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
                                    double damage = data.get("damage").getAsDouble();

                                    event.setDamage(damage);
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
                                    double additionalDamage = data.get("additional_damage").getAsDouble();
                                    double totalDamage = baseDamage + additionalDamage;

                                    event.setDamage(totalDamage);
                                }
                            }
                        }
                )
        );
    }
}

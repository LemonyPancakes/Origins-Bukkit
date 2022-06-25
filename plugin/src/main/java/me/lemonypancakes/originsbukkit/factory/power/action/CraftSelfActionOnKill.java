package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.factory.power.CraftCooldownPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.Damage;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftSelfActionOnKill extends CraftCooldownPower {

    private Action<Entity> entityAction;
    private Condition<Damage> damageCondition;
    private Condition<Entity> targetCondition;

    public CraftSelfActionOnKill(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.targetCondition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject, "target_condition");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity actorEntity = event.getDamager();

        if (actorEntity instanceof Player) {
            Player actorPlayer = (Player) actorEntity;

            if (getMembers().contains(actorPlayer)) {
                Entity targetEntity = event.getEntity();

                if (targetEntity instanceof Damageable) {
                    Damageable targetDamageable = (Damageable) targetEntity;

                    if (event.getFinalDamage() >= targetDamageable.getHealth()) {

                        if (getCondition().test(actorPlayer) && damageCondition.test(new Damage(event.getFinalDamage(), actorPlayer, event.getCause())) && targetCondition.test(targetDamageable)) {
                            if (!canUse(actorPlayer)) {
                                return;
                            }
                            setCooldown(actorPlayer);
                            entityAction.accept(actorPlayer);
                        }
                    }
                }
            }
        }
    }
}

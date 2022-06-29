package me.lemonypancakes.bukkit.origins.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftCooldownPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import me.lemonypancakes.bukkit.origins.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftActionWhenHitPower extends CraftCooldownPower {

    private Action<BiEntity> biEntityAction;
    private Condition<Damage> damageCondition;
    private Condition<BiEntity> biEntityCondition;

    public CraftActionWhenHitPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity targetEntity = event.getEntity();

        if (targetEntity instanceof Player) {
            Player targetPlayer = (Player) targetEntity;

            if (getMembers().contains(targetPlayer)) {
                Entity actorEntity = event.getDamager();
                BiEntity biEntity = new BiEntity(actorEntity, targetPlayer);

                if (getCondition().test(targetPlayer) && damageCondition.test(new Damage(event.getFinalDamage(), actorEntity, event.getCause())) && biEntityCondition.test(biEntity)) {
                    if (!canUse(targetPlayer)) {
                        return;
                    }
                    setCooldown(targetPlayer);
                    biEntityAction.accept(biEntity);
                }
            }
        }
    }
}

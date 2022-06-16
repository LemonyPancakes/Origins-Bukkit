package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.factory.power.CraftCooldownPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import me.lemonypancakes.originsbukkit.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftActionOnHitPower extends CraftCooldownPower {

    private Action<BiEntity> biEntityAction;
    private Condition<Damage> damageCondition;
    private Condition<BiEntity> biEntityCondition;

    public CraftActionOnHitPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
    }

    public CraftActionOnHitPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftActionOnHitPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity actorEntity = event.getDamager();

        if (actorEntity instanceof Player) {
            Player actorPlayer = (Player) actorEntity;

            if (getMembers().contains(actorPlayer)) {
                Entity targetEntity = event.getEntity();
                BiEntity biEntity = new BiEntity(actorPlayer, targetEntity);

                if (getCondition().test(actorPlayer) && damageCondition.test(new Damage(event.getFinalDamage(), actorPlayer, event.getCause())) && biEntityCondition.test(biEntity)) {
                    if (!canUse(actorPlayer)) {
                        return;
                    }
                    setCooldown(actorPlayer);
                    biEntityAction.accept(biEntity);
                }
            }
        }
    }
}

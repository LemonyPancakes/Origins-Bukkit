package me.lemonypancakes.originsbukkit.factory.power.modify;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import me.lemonypancakes.originsbukkit.wrapper.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CraftModifyDamageTakenPower extends CraftPower {

    private Condition<BiEntity> biEntityCondition;
    private Condition<Damage> damageCondition;
    private Action<BiEntity> biEntityAction;
    private Action<Entity> selfAction;
    private Action<Entity> attackerAction;

    public CraftModifyDamageTakenPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.selfAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "self_action");
        this.attackerAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "attacker_action");
    }

    public CraftModifyDamageTakenPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftModifyDamageTakenPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity targetEntity = event.getEntity();

        if (targetEntity instanceof Player) {
            Player targetPlayer = (Player) targetEntity;

            if (getMembers().contains(targetPlayer)) {
                Entity actorEntity = event.getDamager();
                BiEntity biEntity = new BiEntity(actorEntity, targetPlayer);

                if (getCondition().test(targetPlayer) && biEntityCondition.test(biEntity) && damageCondition.test(new Damage(event.getFinalDamage(), actorEntity, event.getCause()))) {
                    biEntityAction.accept(biEntity);
                    selfAction.accept(targetPlayer);
                    attackerAction.accept(actorEntity);
                }
            }
        }
    }
}

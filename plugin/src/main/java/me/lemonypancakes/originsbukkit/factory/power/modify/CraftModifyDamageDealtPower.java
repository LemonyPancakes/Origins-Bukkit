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

public class CraftModifyDamageDealtPower extends CraftPower {

    private Condition<BiEntity> biEntityCondition;
    private Condition<Damage> damageCondition;
    private Condition<Entity> targetCondition;
    private Action<BiEntity> biEntityAction;
    private Action<Entity> selfAction;
    private Action<Entity> targetAction;

    public CraftModifyDamageDealtPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        this.biEntityCondition = plugin.getLoader().loadCondition(DataType.BI_ENTITY, jsonObject, "bientity_condition");
        this.damageCondition = plugin.getLoader().loadCondition(DataType.DAMAGE, jsonObject, "damage_condition");
        this.targetCondition = plugin.getLoader().loadCondition(DataType.ENTITY, jsonObject, "target_condition");
        this.biEntityAction = plugin.getLoader().loadAction(DataType.BI_ENTITY, jsonObject, "bientity_action");
        this.selfAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "self_action");
        this.targetAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "target_action");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity actorEntity = event.getDamager();

        if (actorEntity instanceof Player) {
            Player actorPlayer = (Player) actorEntity;

            if (getMembers().contains(actorPlayer)) {
                Entity targetEntity = event.getEntity();
                BiEntity biEntity = new BiEntity(actorPlayer, targetEntity);

                if (getCondition().test(actorPlayer) && biEntityCondition.test(biEntity) && damageCondition.test(new Damage(event.getFinalDamage(), actorPlayer, event.getCause())) && targetCondition.test(targetEntity)) {
                    biEntityAction.accept(biEntity);
                    selfAction.accept(actorPlayer);
                    targetAction.accept(targetEntity);
                }
            }
        }
    }
}

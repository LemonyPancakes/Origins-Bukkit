package me.lemonypancakes.originsbukkit.factory.listener.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.TempContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;

public class ActionPlayerAttackEntity extends ListenerPowerContainer {

    public ActionPlayerAttackEntity(Identifier identifier,
                                    JsonObject jsonObject,
                                    Action<?>[] actions,
                                    Condition<?> condition,
                                    boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerAttackEntity(Identifier identifier,
                                    JsonObject jsonObject,
                                    Action<?>[] actions,
                                    boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerAttackEntity(Identifier identifier,
                                    JsonObject jsonObject,
                                    boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerAttackEntity(Identifier identifier,
                                    boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerAttackEntity(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerAttackEntity() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerAttackEntity(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerAttackEntity(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerAttackEntity(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerAttackEntity(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerAttackEntity();
    }

    public static Power getFactory() {
        return new ActionPlayerAttackEntity(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/self_attack_entity"
                ), true
        );
    }

    @EventHandler
    private void onBlockBreak(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();

        if (entity instanceof Player) {
            Player damager = (Player) entity;
            Entity victim = event.getEntity();

            if (getPlayersToListen().contains(damager)) {
                Temp temp = new TempContainer();

                temp.setPlayer(damager);
                temp.setEntity(victim);
                temp.setEvent(event);
                if (getCondition() != null) {
                    if (getCondition().test(temp)) {
                        event.setCancelled(isSetCancelled());
                        if (getActions() != null) {
                            Arrays.stream(getActions()).forEach(
                                    action -> action.accept(temp)
                            );
                        }
                    }
                } else {
                    event.setCancelled(isSetCancelled());
                    if (getActions() != null) {
                        Arrays.stream(getActions()).forEach(
                                action -> action.accept(temp)
                        );
                    }
                }
            }
        }
    }
}

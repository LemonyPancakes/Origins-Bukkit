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

public class ActionEntityAttackPlayer extends ListenerPowerContainer {

    public ActionEntityAttackPlayer(Identifier identifier,
                                    JsonObject jsonObject,
                                    Action<?>[] actions,
                                    Condition<?> condition,
                                    boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionEntityAttackPlayer(Identifier identifier,
                                    JsonObject jsonObject,
                                    Action<?>[] actions,
                                    boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionEntityAttackPlayer(Identifier identifier,
                                    JsonObject jsonObject,
                                    boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionEntityAttackPlayer(Identifier identifier,
                                    boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionEntityAttackPlayer(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionEntityAttackPlayer() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionEntityAttackPlayer(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionEntityAttackPlayer(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionEntityAttackPlayer(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionEntityAttackPlayer(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionEntityAttackPlayer();
    }

    public static Power getFactory() {
        return new ActionEntityAttackPlayer(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/entity_attack_self"
                ), true
        );
    }

    @EventHandler
    private void onBlockBreak(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player victim  = (Player) entity;
            Entity damager = event.getDamager();

            if (getPlayersToListen().contains(victim)) {
                Temp temp = new TempContainer();

                temp.setPlayer(victim);
                temp.setEntity(damager);
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

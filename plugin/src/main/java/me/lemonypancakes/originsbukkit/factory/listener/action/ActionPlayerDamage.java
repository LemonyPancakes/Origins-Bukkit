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
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Random;

public class ActionPlayerDamage extends ListenerPowerContainer {

    public ActionPlayerDamage(Identifier identifier,
                              JsonObject jsonObject,
                              Action<?>[] actions,
                              Condition<?> condition,
                              boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerDamage(Identifier identifier,
                              JsonObject jsonObject,
                              Action<?>[] actions,
                              boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerDamage(Identifier identifier,
                              JsonObject jsonObject,
                              boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerDamage(Identifier identifier,
                              boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerDamage(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerDamage() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerDamage(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerDamage(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerDamage(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerDamage(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerDamage();
    }

    public static Power getFactory() {
        return new ActionPlayerDamage(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/self_damage"
                ), true
        );
    }

    @EventHandler
    private void onSelfDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player  = (Player) entity;

            if (getPlayersToListen().contains(player)) {
                Temp temp = new TempContainer();

                temp.setPlayer(player);
                temp.setEvent(event);
                if (getCondition() != null) {
                    if (getCondition().test(temp)) {
                        event.setCancelled(isSetCancelled());
                        if (getActions() != null) {
                            if (getProbability() != null) {
                                Random random = new Random();
                                int probability = random.nextInt(getProbability());

                                if (isInvertProbability()) {
                                    if (probability == 0) {
                                        return;
                                    }
                                } else {
                                    if (probability != 0) {
                                        return;
                                    }
                                }
                            }
                            Arrays.stream(getActions()).forEach(
                                    action -> action.accept(temp)
                            );
                        }
                    }
                } else {
                    event.setCancelled(isSetCancelled());
                    if (getActions() != null) {
                        if (getProbability() != null) {
                            Random random = new Random();
                            int probability = random.nextInt(getProbability());

                            if (isInvertProbability()) {
                                if (probability == 0) {
                                    return;
                                }
                            } else {
                                if (probability != 0) {
                                    return;
                                }
                            }
                        }
                        Arrays.stream(getActions()).forEach(
                                action -> action.accept(temp)
                        );
                    }
                }
            }
        }
    }
}

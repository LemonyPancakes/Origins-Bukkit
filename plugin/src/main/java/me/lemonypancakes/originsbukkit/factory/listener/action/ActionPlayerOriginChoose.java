package me.lemonypancakes.originsbukkit.factory.listener.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.TempContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.api.events.player.PlayerOriginChooseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.Random;

public class ActionPlayerOriginChoose extends ListenerPowerContainer {

    public ActionPlayerOriginChoose(Identifier identifier,
                                    JsonObject jsonObject,
                                    Action<?>[] actions,
                                    Condition<?> condition,
                                    boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerOriginChoose(Identifier identifier,
                                    JsonObject jsonObject,
                                    Action<?>[] actions,
                                    boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerOriginChoose(Identifier identifier,
                                    JsonObject jsonObject,
                                    boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerOriginChoose(Identifier identifier,
                                    boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerOriginChoose(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerOriginChoose() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerOriginChoose(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerOriginChoose(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerOriginChoose(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerOriginChoose(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerOriginChoose();
    }

    public static Power getFactory() {
        return new ActionPlayerOriginChoose(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/origin_choose"
                ), true
        );
    }

    @EventHandler
    private void onOriginChoose(PlayerOriginChooseEvent event) {
        Player player = event.getPlayer();

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

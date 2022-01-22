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
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Arrays;
import java.util.Random;

public class ActionPlayerInteractEntity extends ListenerPowerContainer {

    public ActionPlayerInteractEntity(Identifier identifier,
                                      JsonObject jsonObject,
                                      Action<?>[] actions,
                                      Condition<?> condition,
                                      boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerInteractEntity(Identifier identifier,
                                      JsonObject jsonObject,
                                      Action<?>[] actions,
                                      boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerInteractEntity(Identifier identifier,
                                      JsonObject jsonObject,
                                      boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerInteractEntity(Identifier identifier,
                                      boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerInteractEntity(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerInteractEntity() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerInteractEntity(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerInteractEntity(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerInteractEntity(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerInteractEntity(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerInteractEntity();
    }

    public static Power getFactory() {
        return new ActionPlayerInteractEntity(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/player_interact_entity"
                ), true
        );
    }

    @EventHandler
    private void onEntityAttackSelf(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (getPlayersToListen().contains(player)) {
            Temp temp = new TempContainer();

            temp.setPlayer(player);
            temp.setEntity(entity);
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

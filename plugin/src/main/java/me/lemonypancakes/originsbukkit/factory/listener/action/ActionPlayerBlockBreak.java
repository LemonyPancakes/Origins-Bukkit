package me.lemonypancakes.originsbukkit.factory.listener.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.TempContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.Random;

public class ActionPlayerBlockBreak extends ListenerPowerContainer {

    public ActionPlayerBlockBreak(Identifier identifier,
                                  JsonObject jsonObject,
                                  Action<?>[] actions,
                                  Condition<?> condition,
                                  boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerBlockBreak(Identifier identifier,
                                  JsonObject jsonObject,
                                  Action<?>[] actions,
                                  boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerBlockBreak(Identifier identifier,
                                  JsonObject jsonObject,
                                  boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerBlockBreak(Identifier identifier,
                                  boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerBlockBreak(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerBlockBreak() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerBlockBreak(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerBlockBreak(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerBlockBreak(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerBlockBreak(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerBlockBreak();
    }

    public static Power getFactory() {
        return new ActionPlayerBlockBreak(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/block_break"
                ), true
        );
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (getPlayersToListen().contains(player)) {
            Block block = event.getBlock();
            Temp temp = new TempContainer();

            temp.setPlayer(player);
            temp.setBlock(block);
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

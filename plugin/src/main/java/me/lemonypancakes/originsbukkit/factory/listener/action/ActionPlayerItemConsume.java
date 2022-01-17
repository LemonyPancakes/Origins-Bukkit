package me.lemonypancakes.originsbukkit.factory.listener.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.TempContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ActionPlayerItemConsume extends ListenerPowerContainer {

    public ActionPlayerItemConsume(Identifier identifier,
                                   JsonObject jsonObject,
                                   Action<?>[] actions,
                                   Condition<?> condition,
                                   boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerItemConsume(Identifier identifier,
                                   JsonObject jsonObject,
                                   Action<?>[] actions,
                                   boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerItemConsume(Identifier identifier,
                                   JsonObject jsonObject,
                                   boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerItemConsume(Identifier identifier,
                                   boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerItemConsume(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerItemConsume() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerItemConsume(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerItemConsume(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerItemConsume(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerItemConsume(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerItemConsume();
    }

    @Override
    public <T> void onInvoke(T t) {}

    public static Power getFactory() {
        return new ActionPlayerItemConsume(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/item_consume"
                ), true
        );
    }

    @EventHandler
    private void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if (getPlayersToListen().contains(player)) {
            ItemStack itemStack = event.getItem();
            Temp temp = new TempContainer();

            temp.setPlayer(player);
            temp.setItemStack(itemStack);
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

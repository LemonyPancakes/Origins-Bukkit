package me.lemonypancakes.originsbukkit.factory.listener;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SoulboundItemListener extends ListenerPowerContainer {

    public SoulboundItemListener(Identifier identifier,
                                 JsonObject jsonObject,
                                 Action<?>[] actions,
                                 Condition<?> condition,
                                 boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public SoulboundItemListener(Identifier identifier,
                                 JsonObject jsonObject,
                                 Action<?>[] actions,
                                 boolean isFactory) {
        super(identifier, jsonObject, actions, isFactory);
    }

    public SoulboundItemListener(Identifier identifier,
                                 JsonObject jsonObject,
                                 boolean isFactory) {
        super(identifier, jsonObject, isFactory);
    }

    public SoulboundItemListener(Identifier identifier,
                                 boolean isFactory) {
        super(identifier, isFactory);
    }

    public SoulboundItemListener(boolean isFactory) {
        super(isFactory);
    }

    public SoulboundItemListener() {
        super();
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new SoulboundItemListener(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new SoulboundItemListener(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new SoulboundItemListener(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new SoulboundItemListener(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new SoulboundItemListener();
    }

    @Override
    public <T> void onInvoke(T t) {
        if (t instanceof Player) {
            Player player = (Player) t;

            player.sendMessage("INVOKEDDDDDD");
        }
    }

    public static Power getFactory() {
        return new SoulboundItemListener(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/soulbound_item_listener"
                ), true
        );
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (getPlayersToListen().contains(player)) {
            player.sendMessage("YOU ARE IN PLAYERS TO LISTEN");
        } else {
            player.sendMessage("YOU ARE NOT IN PLAYERS TO LISTEN SADGE...");
        }
    }
}

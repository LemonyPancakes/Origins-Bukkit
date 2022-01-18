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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ActionPlayerBlockPlace extends ListenerPowerContainer {

    public ActionPlayerBlockPlace(Identifier identifier,
                                  JsonObject jsonObject,
                                  Action<?>[] actions,
                                  Condition<?> condition,
                                  boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public ActionPlayerBlockPlace(Identifier identifier,
                                  JsonObject jsonObject,
                                  Action<?>[] actions,
                                  boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public ActionPlayerBlockPlace(Identifier identifier,
                                  JsonObject jsonObject,
                                  boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public ActionPlayerBlockPlace(Identifier identifier,
                                  boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public ActionPlayerBlockPlace(boolean isFactory) {
        this(null, isFactory);
    }

    public ActionPlayerBlockPlace() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new ActionPlayerBlockPlace(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new ActionPlayerBlockPlace(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new ActionPlayerBlockPlace(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new ActionPlayerBlockPlace(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new ActionPlayerBlockPlace();
    }

    public static Power getFactory() {
        return new ActionPlayerBlockPlace(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/action/block_place"
                ), true
        );
    }

    @EventHandler
    private void onBlockBreak(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (getPlayersToListen().contains(player)) {
            Block block = event.getBlock();
            ItemStack itemStack = event.getItemInHand();
            Temp temp = new TempContainer();

            temp.setPlayer(player);
            temp.setItemStack(itemStack);
            temp.setBlock(block);
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

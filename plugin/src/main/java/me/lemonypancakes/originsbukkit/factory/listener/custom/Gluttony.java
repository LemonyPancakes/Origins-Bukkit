package me.lemonypancakes.originsbukkit.factory.listener.custom;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Gluttony extends ListenerPowerContainer {

    public Gluttony(Identifier identifier,
                    JsonObject jsonObject,
                    Action<?>[] actions,
                    Condition<?> condition,
                    boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public Gluttony(Identifier identifier,
                    JsonObject jsonObject,
                    Action<?>[] actions,
                    boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public Gluttony(Identifier identifier,
                    JsonObject jsonObject,
                    boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public Gluttony(Identifier identifier,
                    boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public Gluttony(boolean isFactory) {
        this(null, isFactory);
    }

    public Gluttony() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new Gluttony(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new Gluttony(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new Gluttony(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new Gluttony(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new Gluttony();
    }

    @Override
    protected <T> void onInvoke(T t) {}

    public static Power getFactory() {
        return new Gluttony(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/custom/gluttony"
                ), true
        );
    }

    @EventHandler
    private void onEntityFoodLevelChange(FoodLevelChangeEvent event) {
        HumanEntity humanEntity = event.getEntity();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;

            if (getPlayersToListen().contains(player)) {
                if (player.getFoodLevel() < event.getFoodLevel()) {
                    event.setFoodLevel(player.getFoodLevel() + 2);
                }
            }
        }
    }
}

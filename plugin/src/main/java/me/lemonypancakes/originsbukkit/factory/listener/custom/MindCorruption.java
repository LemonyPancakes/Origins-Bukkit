package me.lemonypancakes.originsbukkit.factory.listener.custom;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class MindCorruption extends ListenerPowerContainer {

    public MindCorruption(Identifier identifier,
                          JsonObject jsonObject,
                          Action<?>[] actions,
                          Condition<?> condition,
                          boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public MindCorruption(Identifier identifier,
                          JsonObject jsonObject,
                          Action<?>[] actions,
                          boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public MindCorruption(Identifier identifier,
                          JsonObject jsonObject,
                          boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public MindCorruption(Identifier identifier,
                          boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public MindCorruption(boolean isFactory) {
        this(null, isFactory);
    }

    public MindCorruption() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new MindCorruption(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new MindCorruption(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new MindCorruption(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new MindCorruption(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new MindCorruption();
    }

    @Override
    protected <T> void onInvoke(T t) {}

    public static Power getFactory() {
        return new MindCorruption(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/custom/mind_corruption"
                ), true
        );
    }

    @EventHandler
    private void onEntityDamageSelf(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (entity instanceof Player && damager instanceof Player) {
            Player victim = (Player) entity;
            Player player = (Player) damager;

            if (getPlayersToListen().contains(victim)) {
                if (!getPlayersToListen().contains(player)) {
                    if (player.getFoodLevel() < 12) {
                        player.damage(6);
                    }
                }
                player.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 25));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BEE_STING, 1f, 1f);
            }
        }
    }
}

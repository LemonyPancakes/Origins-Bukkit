package me.lemonypancakes.originsbukkit.factory.listener.custom;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Otherworldly extends ListenerPowerContainer {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();

    public Otherworldly(Identifier identifier,
                        JsonObject jsonObject,
                        Action<?>[] actions,
                        Condition<?> condition,
                        boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
        if (!isFactory()) {
            init();
        }
    }

    public Otherworldly(Identifier identifier,
                        JsonObject jsonObject,
                        Action<?>[] actions,
                        boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public Otherworldly(Identifier identifier,
                        JsonObject jsonObject,
                        boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public Otherworldly(Identifier identifier,
                        boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public Otherworldly(boolean isFactory) {
        this(null, isFactory);
    }

    public Otherworldly() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new Otherworldly(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new Otherworldly(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new Otherworldly(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new Otherworldly(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new Otherworldly();
    }

    @Override
    protected <T> void onInvoke(T t) {}

    public static Power getFactory() {
        return new Otherworldly(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/custom/otherworldly"
                ), true
        );
    }

    private void init() {
        new BukkitRunnable() {

            @Override
            public void run() {
                getPlayersToListen().forEach(player ->
                        player.getNearbyEntities(4, 4 ,4 ).forEach(nearbyEntity -> {
                            if (nearbyEntity instanceof Player) {
                                Player nearbyPlayer = (Player) nearbyEntity;

                                if (!getPlayersToListen().contains(nearbyPlayer)) {
                                    nearbyPlayer.addPotionEffect(
                                            PotionEffectType.WITHER
                                                    .createEffect(
                                                            80, 0
                                                    )
                                    );
                                }
                            }
                        })
                );
            }
        }.runTaskTimer(PLUGIN, 0L, 20L);
    }
}

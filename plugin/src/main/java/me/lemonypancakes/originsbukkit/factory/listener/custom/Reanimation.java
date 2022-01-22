package me.lemonypancakes.originsbukkit.factory.listener.custom;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.SchedulerContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import me.lemonypancakes.originsbukkit.util.IdentifierUtils;
import me.lemonypancakes.originsbukkit.util.PlayerUtils;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.UUID;

public class Reanimation extends ListenerPowerContainer {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public Reanimation(Identifier identifier,
                       JsonObject jsonObject,
                       Action<?>[] actions,
                       Condition<?> condition,
                       boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
    }

    public Reanimation(Identifier identifier,
                       JsonObject jsonObject,
                       Action<?>[] actions,
                       boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public Reanimation(Identifier identifier,
                       JsonObject jsonObject,
                       boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public Reanimation(Identifier identifier,
                       boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public Reanimation(boolean isFactory) {
        this(null, isFactory);
    }

    public Reanimation() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new Reanimation(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new Reanimation(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new Reanimation(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new Reanimation(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new Reanimation();
    }

    @Override
    protected <T> void onInvoke(T t) {
        if (t instanceof Temp) {
            Temp temp = (Temp) t;
            Player player = temp.getPlayer();

            if (player != null) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (PlayerUtils.getMaxHealth(player) < 20) {
                            BukkitTask task = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    PlayerUtils.setMaxHealth(player, 20);
                                }
                            }.runTaskLaterAsynchronously(PLUGIN, 6000);
                            ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                                    .getSchedulers()
                                    .add(
                                            new SchedulerContainer(
                                                    IdentifierUtils.fromString(
                                                            "origins-bukkit:reanimation"
                                                    ),
                                                    task
                                            )
                                    );
                        }
                    }
                }.runTaskLaterAsynchronously(PLUGIN, 4L);
            }
        }
    }

    public static Power getFactory() {
        return new Reanimation(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/custom/reanimation"
                ), true
        );
    }

    @EventHandler
    private void onDeath(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getPlayersToListen().contains(player)) {
                if (event.getFinalDamage() > player.getHealth()) {
                    Random random = new Random();
                    int probability = random.nextInt(2);

                    if (probability != 0) {
                        PlayerUtils.setMaxHealth(player, 20);
                        return;
                    }

                    event.setCancelled(true);
                    PlayerUtils.setMaxHealth(
                            player,
                            PlayerUtils.getMaxHealth(player) / 2
                    );
                    PlayerUtils.heal(player, PlayerUtils.getMaxHealth(player));
                    player.damage(0.00000000001);
                    player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1f, 1f);
                    player.getWorld().spawnParticle(Particle.TOTEM, player.getLocation(), 1);
                    player.playEffect(EntityEffect.TOTEM_RESURRECT);
                    BukkitTask task = new BukkitRunnable() {

                        @Override
                        public void run() {
                            PlayerUtils.setMaxHealth(player, 20);
                        }
                    }.runTaskLaterAsynchronously(PLUGIN, 6000);
                    ORIGIN_PLAYERS.getByPlayerUUID(player.getUniqueId())
                            .getSchedulers()
                            .add(
                                    new SchedulerContainer(
                                            IdentifierUtils.fromString(
                                                    "origins-bukkit:reanimation"
                                            ),
                                            task
                                    )
                            );
                }
            }
        }
    }

    @EventHandler
    private void onEntityDamageSelf(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (entity instanceof Player && damager instanceof Player) {
            Player victim = (Player) entity;
            UUID victimUUID = victim.getUniqueId();
            Player player = (Player) damager;

            if (getPlayersToListen().contains(victim)) {
                if (ORIGIN_PLAYERS.hasPlayerUUID(victimUUID)) {
                    if (ORIGIN_PLAYERS.getByPlayerUUID(victimUUID) != null) {
                        if (PlayerUtils.getMaxHealth(player) < 20) {
                            BukkitTask task = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    PlayerUtils.setMaxHealth(victim, 20);
                                }
                            }.runTaskLaterAsynchronously(PLUGIN, 6000);
                            ORIGIN_PLAYERS.getByPlayerUUID(victimUUID)
                                    .getSchedulers()
                                    .add(
                                            new SchedulerContainer(
                                                    IdentifierUtils.fromString(
                                                            "origins-bukkit:reanimation"
                                                    ),
                                                    task
                                            )
                                    );
                        }
                    }
                }
            }
        }
    }
}

package me.lemonypancakes.originsbukkit.factory.listener.custom;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.util.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Photosynthesis extends ListenerPowerContainer {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();

    public Photosynthesis(Identifier identifier,
                          JsonObject jsonObject,
                          Action<?>[] actions,
                          Condition<?> condition,
                          boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
        if (!isFactory()) {
            init();
        }
    }

    public Photosynthesis(Identifier identifier,
                          JsonObject jsonObject,
                          Action<?>[] actions,
                          boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public Photosynthesis(Identifier identifier,
                          JsonObject jsonObject,
                          boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public Photosynthesis(Identifier identifier,
                          boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public Photosynthesis(boolean isFactory) {
        this(null, isFactory);
    }

    public Photosynthesis() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new Photosynthesis(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new Photosynthesis(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new Photosynthesis(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new Photosynthesis(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new Photosynthesis();
    }

    @Override
    protected <T> void onInvoke(T t) {}

    public static Power getFactory() {
        return new Photosynthesis(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/custom/photosynthesis"
                ), true
        );
    }

    private void init() {
        new BukkitRunnable() {

            @Override
            public void run() {
                getPlayersToListen().forEach(player -> {
                    if (player.isInWater()) {
                        if (player.getLocation().getBlock().getLightLevel() > 8 || player.getLocation().getBlock().getLightFromSky() > 8) {
                            PlayerUtils.feed(player, 2);
                            PlayerUtils.saturate(player, 2);
                        }
                    }
                });
            }
        }.runTaskTimerAsynchronously(PLUGIN, 0L, 10L);
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (getPlayersToListen().contains(player)) {
            if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();

                if (clickedBlock != null) {
                    if (clickedBlock.getType() == Material.CAKE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

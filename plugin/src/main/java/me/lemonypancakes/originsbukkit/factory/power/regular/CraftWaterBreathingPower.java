package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CraftWaterBreathingPower extends CraftPower {

    public CraftWaterBreathingPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        WrappedDataWatcher.WrappedDataWatcherObject address = new WrappedDataWatcher.WrappedDataWatcherObject(1, WrappedDataWatcher.Registry.get(Integer.class));

        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata();
                    List<WrappedWatchableObject> watchableObjects = new ArrayList<>();

                    watchableObjects.add(new WrappedWatchableObject(address, player.getRemainingAir()));

                    wrapper.setEntityID(player.getEntityId());
                    wrapper.setMetadata(watchableObjects);
                    wrapper.sendPacket(player);
                });
            }
        }.runTaskTimerAsynchronously(getPlugin().getJavaPlugin(), 0L, 1L);
        new BukkitRunnable() {
            @Override
            public void run() {
                getMembers().forEach(player -> {
                    Location eyeLocation = player.getEyeLocation();
                    Block eyeBlock = eyeLocation.getBlock();
                    Material eyeBlockMaterial = eyeBlock.getType();

                    if (eyeBlockMaterial == Material.WATER || eyeBlock.getBlockData() instanceof Waterlogged && ((Waterlogged) eyeBlock.getBlockData()).isWaterlogged()) {
                        player.setRemainingAir(Math.min((player.getRemainingAir() + 5), 303));
                    } else {
                        player.setRemainingAir(Math.max((player.getRemainingAir() - 5), -27));
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1L);
        new BukkitRunnable() {
            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (player.getRemainingAir() <= -27) {
                        player.damage(1);
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 20L);
    }

    public CraftWaterBreathingPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftWaterBreathingPower(plugin, identifier, jsonObject);
    }

}

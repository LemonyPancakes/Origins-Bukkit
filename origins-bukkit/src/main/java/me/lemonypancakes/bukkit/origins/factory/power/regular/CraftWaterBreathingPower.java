/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import me.lemonypancakes.bukkit.common.com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
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
}

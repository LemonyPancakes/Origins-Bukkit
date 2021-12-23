/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
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
package me.lemonypancakes.originsbukkit.api.wrappers;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Player air bubbles.
 *
 * @author LemonyPancakes
 */
public class PlayerAirBubbles extends BukkitRunnable {

    private final OriginsBukkit plugin;
    private final WrappedDataWatcher.WrappedDataWatcherObject ADDRESS;
    private final Player player;

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public OriginsBukkit getPlugin() {
        return plugin;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public WrappedDataWatcher.WrappedDataWatcherObject getAddress() {
        return ADDRESS;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Instantiates a new Player air bubbles.
     *
     * @param player the player
     */
    public PlayerAirBubbles(Player player) {
        this.player = player;
        this.plugin = OriginsBukkit.getPlugin();
        this.ADDRESS = new WrappedDataWatcher.WrappedDataWatcherObject(
                1, WrappedDataWatcher.Registry.get(
                        Integer.class
        ));

        runTaskTimerAsynchronously(getPlugin(), 0, 1);
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        if (getPlayer().isOnline()) {
            Object airTicks = OriginsBukkit.getPlugin()
                    .getListenerHandler()
                    .getOriginListenerHandler()
                    .getMerling()
                    .getMerlingAirTicks()
                    .get(getPlayer().getUniqueId());
            WrapperPlayServerEntityMetadata wrapper
                    = new WrapperPlayServerEntityMetadata();
            List<WrappedWatchableObject> watchableObjects
                    = new ArrayList<>();

            if (airTicks != null) {
                watchableObjects.add(
                        new WrappedWatchableObject(
                                getAddress(),
                                airTicks
                        )
                );
            } else {
                watchableObjects.add(
                        new WrappedWatchableObject(
                                getAddress(),
                                -27
                        )
                );
            }

            wrapper.setEntityID(getPlayer().getEntityId());
            wrapper.setMetadata(watchableObjects);
            wrapper.sendPacket(getPlayer());
        } else {
            cancel();
        }
    }
}

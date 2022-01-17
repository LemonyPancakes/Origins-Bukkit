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

public class PlayerAirBubbles {

    /*private final OriginsBukkit plugin;
    private final WrappedDataWatcher.WrappedDataWatcherObject ADDRESS;
    private final Player player;

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public WrappedDataWatcher.WrappedDataWatcherObject getAddress() {
        return ADDRESS;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerAirBubbles(Player player) {
        this.player = player;
        this.plugin = OriginsBukkit.getPlugin();
        this.ADDRESS = new WrappedDataWatcher.WrappedDataWatcherObject(
                1, WrappedDataWatcher.Registry.get(
                        Integer.class
        ));

        runTaskTimerAsynchronously(getPlugin(), 0, 1);
    }

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
    }*/
}

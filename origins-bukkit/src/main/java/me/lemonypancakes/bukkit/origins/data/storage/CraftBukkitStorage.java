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
package me.lemonypancakes.bukkit.origins.data.storage;

import me.lemonypancakes.bukkit.origins.data.serialization.StorageSerializable;
import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedHashMap;
import java.util.Map;

public class CraftBukkitStorage implements Storage, Listener {

    private final OriginsBukkitPlugin plugin;

    public CraftBukkitStorage(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public Map<String, Object> getData(OfflinePlayer offlinePlayer) {
        if (offlinePlayer instanceof PersistentDataHolder) {
            Map<String, Object> data = new LinkedHashMap<>();
            PersistentDataHolder persistentDataHolder = (PersistentDataHolder) offlinePlayer;

            data.put("origin", BukkitPersistentDataUtils.getPersistentData(persistentDataHolder, "origins-bukkit:origin", PersistentDataType.STRING));
            data.put("power", BukkitPersistentDataUtils.getPersistentData(persistentDataHolder, "origins-bukkit:power", PersistentDataType.STRING));
            data.put("metadata", BukkitPersistentDataUtils.getPersistentData(persistentDataHolder, "origins-bukkit:metadata", PersistentDataType.STRING));
            Integer integer = BukkitPersistentDataUtils.getPersistentData(persistentDataHolder, "origins-bukkit:has_origin_before", PersistentDataType.INTEGER);
            data.put("hasOriginBefore", integer != null && integer > 0);

            return data;
        }
        return null;
    }

    @Override
    public void saveData(OfflinePlayer offlinePlayer, StorageSerializable storageSerializable) {
        if (offlinePlayer instanceof PersistentDataHolder) {
            PersistentDataHolder persistentDataHolder = (PersistentDataHolder) offlinePlayer;
            Map<String, Object> data = storageSerializable.serialize();

            BukkitPersistentDataUtils.setPersistentData(persistentDataHolder, "origins-bukkit:origin", PersistentDataType.STRING, (String) data.get("origin"));
            BukkitPersistentDataUtils.setPersistentData(persistentDataHolder, "origins-bukkit:power", PersistentDataType.STRING, (String) data.get("power"));
            BukkitPersistentDataUtils.setPersistentData(persistentDataHolder, "origins-bukkit:metadata", PersistentDataType.STRING, (String) data.get("metadata"));
            BukkitPersistentDataUtils.setPersistentData(persistentDataHolder, "origins-bukkit:has_origin_before", PersistentDataType.INTEGER, (boolean) data.get("hasOriginBefore") ? 1 : 0);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == plugin) {
            Bukkit.getOnlinePlayers().forEach(player -> saveData(player, plugin.getOriginPlayer(player)));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = plugin.getOriginPlayer(player);

        if (originPlayer != null) {
            saveData(player, originPlayer);
        }
    }
}

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
package me.lemonypancakes.originsbukkit.storage.data;

import me.lemonypancakes.originsbukkit.storage.StorageHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShulkPlayerStorageData {

    private final StorageHandler storageHandler;
    private Map<UUID, ItemStack[]> shulkPlayerStorageData = new HashMap<>();

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public Map<UUID, ItemStack[]> getShulkPlayerStorageData() {
        return shulkPlayerStorageData;
    }

    public void setShulkPlayerStorageData(Map<UUID, ItemStack[]> shulkPlayerStorageData) {
        this.shulkPlayerStorageData = shulkPlayerStorageData;
    }

    public ShulkPlayerStorageData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    public void saveShulkPlayerStorageData(UUID playerUUID) throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                String s = File.separator;
                File shulkPlayerStorageDataFile = new File(getStorageHandler().getPlugin().getDataFolder(), s + "cache" + s + "shulkdata" + s + "inventoriesdata" + s + playerUUID + ".yml");

                if (!shulkPlayerStorageDataFile.getParentFile().exists()) {
                    shulkPlayerStorageDataFile.getParentFile().mkdirs();
                }
                if (!shulkPlayerStorageDataFile.exists()) {
                    try {
                        shulkPlayerStorageDataFile.createNewFile();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
                FileConfiguration shulkPlayerStorageData = YamlConfiguration.loadConfiguration(shulkPlayerStorageDataFile);

                for (Map.Entry<UUID, ItemStack[]> entry : getStorageHandler().getShulkPlayerStorageData().getShulkPlayerStorageData().entrySet()) {
                    if (entry.getKey().equals(playerUUID)) {
                        shulkPlayerStorageData.set("data." + entry.getKey(), entry.getValue());
                    }
                }
                try {
                    shulkPlayerStorageData.save(shulkPlayerStorageDataFile);
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }

    public void loadShulkPlayerStorageData(UUID playerUUID) throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                String s = File.separator;
                File shulkPlayerStorageDataFile = new File(getStorageHandler().getPlugin().getDataFolder(), s + "cache" + s + "shulkdata" + s + "inventoriesdata" + s + playerUUID + ".yml");

                if (!shulkPlayerStorageDataFile.getParentFile().exists()) {
                    shulkPlayerStorageDataFile.getParentFile().mkdirs();
                }
                if (!shulkPlayerStorageDataFile.exists()) {
                    try {
                        shulkPlayerStorageDataFile.createNewFile();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
                FileConfiguration shulkPlayerStorageData = YamlConfiguration.loadConfiguration(shulkPlayerStorageDataFile);
                if (shulkPlayerStorageData.contains("data")) {
                    shulkPlayerStorageData.getConfigurationSection("data").getKeys(false).forEach(key ->{
                        @SuppressWarnings("unchecked")
                        ItemStack[] contents = ((List<ItemStack>) shulkPlayerStorageData.get("data." + key)).toArray(new ItemStack[0]);
                        getStorageHandler().getShulkPlayerStorageData().getShulkPlayerStorageData().put(UUID.fromString(key), contents);
                    });
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }
}

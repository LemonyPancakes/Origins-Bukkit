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

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.storage.StorageHandler;
import me.lemonypancakes.originsbukkit.storage.wrappers.BlazebornNetherSpawnDataWrapper;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlazebornNetherSpawnData {

    private final StorageHandler storageHandler;
    private List<BlazebornNetherSpawnDataWrapper> blazebornNetherSpawnDataWrappers = new ArrayList<>();

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public List<BlazebornNetherSpawnDataWrapper> getBlazebornNetherSpawnDataWrappers() {
        return blazebornNetherSpawnDataWrappers;
    }

    public void setBlazebornNetherSpawnDataWrappers(List<BlazebornNetherSpawnDataWrapper> blazebornNetherSpawnDataWrappers) {
        this.blazebornNetherSpawnDataWrappers = blazebornNetherSpawnDataWrappers;
    }

    public BlazebornNetherSpawnData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    private void init() {
        try {
            loadBlazebornNetherSpawnData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBlazebornNetherSpawnData(UUID playerUUID, boolean firstTime) {
        if (findBlazebornNetherSpawnData(playerUUID) == null) {
            BlazebornNetherSpawnDataWrapper blazebornNetherSpawnDataWrapper = new BlazebornNetherSpawnDataWrapper(playerUUID, firstTime);
            getBlazebornNetherSpawnDataWrappers().add(blazebornNetherSpawnDataWrapper);
            try {
                saveBlazebornNetherSpawnData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public BlazebornNetherSpawnDataWrapper findBlazebornNetherSpawnData(UUID playerUUID) {
        for (BlazebornNetherSpawnDataWrapper blazebornNetherSpawnDataWrapper : getBlazebornNetherSpawnDataWrappers()) {
            if (blazebornNetherSpawnDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return blazebornNetherSpawnDataWrapper;
            }
        }
        return null;
    }

    public boolean getBlazebornNetherSpawnData(UUID playerUUID) {
        for (BlazebornNetherSpawnDataWrapper blazebornNetherSpawnDataWrapper : getBlazebornNetherSpawnDataWrappers()) {
            if (blazebornNetherSpawnDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return blazebornNetherSpawnDataWrapper.isFirstTime();
            }
        }
        return false;
    }

    public void updateBlazebornNetherSpawnData(UUID playerUUID, BlazebornNetherSpawnDataWrapper newBlazebornNetherSpawnDataWrapper) {
        if (findBlazebornNetherSpawnData(playerUUID) != null) {
            for (BlazebornNetherSpawnDataWrapper blazebornNetherSpawnDataWrapper : getBlazebornNetherSpawnDataWrappers()) {
                if (blazebornNetherSpawnDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    blazebornNetherSpawnDataWrapper.setFirstTime(newBlazebornNetherSpawnDataWrapper.isFirstTime());
                    try {
                        saveBlazebornNetherSpawnData();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
            }
        }
    }

    public void deleteBlazebornNetherSpawnData(UUID playerUUID) {
        if (findBlazebornNetherSpawnData(playerUUID) != null) {
            for (BlazebornNetherSpawnDataWrapper blazebornNetherSpawnDataWrapper : getBlazebornNetherSpawnDataWrappers()) {
                if (blazebornNetherSpawnDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    getBlazebornNetherSpawnDataWrappers().remove(blazebornNetherSpawnDataWrapper);
                    break;
                }
            }
            try {
                saveBlazebornNetherSpawnData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public void saveBlazebornNetherSpawnData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "blazeborndata" + s + "blazebornnetherspawndata" + s + "blazebornnetherspawndata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(getBlazebornNetherSpawnDataWrappers(), writer);
                    writer.flush();
                    writer.close();
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }

    public void loadBlazebornNetherSpawnData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "blazeborndata" + s + "blazebornnetherspawndata" + s + "blazebornnetherspawndata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    try {
                        Reader reader = new FileReader(file);
                        BlazebornNetherSpawnDataWrapper[] n = gson.fromJson(reader, BlazebornNetherSpawnDataWrapper[].class);
                        blazebornNetherSpawnDataWrappers = new ArrayList<>(Arrays.asList(n));
                    } catch (FileNotFoundException event) {
                        event.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }
}
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
import me.lemonypancakes.originsbukkit.storage.wrappers.ArachnidAbilityToggleDataWrapper;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ArachnidAbilityToggleData {

    private final StorageHandler storageHandler;
    private List<ArachnidAbilityToggleDataWrapper> arachnidAbilityToggleDataWrappers = new ArrayList<>();

    public List<ArachnidAbilityToggleDataWrapper> getArachnidAbilityToggleData() {
        return arachnidAbilityToggleDataWrappers;
    }

    public void setArachnidAbilityToggleData(List<ArachnidAbilityToggleDataWrapper> arachnidAbilityToggleDataWrappers) {
        this.arachnidAbilityToggleDataWrappers = arachnidAbilityToggleDataWrappers;
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public ArachnidAbilityToggleData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    private void init() {
        try {
            loadArachnidAbilityToggleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createArachnidAbilityToggleData(UUID playerUUID, boolean isToggled) {
        if (findArachnidAbilityToggleData(playerUUID) == null) {
            ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper = new ArachnidAbilityToggleDataWrapper(playerUUID, isToggled);
            getArachnidAbilityToggleData().add(arachnidAbilityToggleDataWrapper);
            try {
                saveArachnidAbilityToggleData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public ArachnidAbilityToggleDataWrapper findArachnidAbilityToggleData(UUID playerUUID) {
        for (ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper : getArachnidAbilityToggleData()) {
            if (arachnidAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return arachnidAbilityToggleDataWrapper;
            }
        }
        return null;
    }

    public boolean getArachnidAbilityToggleData(UUID playerUUID) {
        for (ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper : getArachnidAbilityToggleData()) {
            if (arachnidAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return arachnidAbilityToggleDataWrapper.isToggled();
            }
        }
        return false;
    }

    public void updateArachnidAbilityToggleData(UUID playerUUID, ArachnidAbilityToggleDataWrapper newArachnidAbilityToggleDataWrapper) {
        if (findArachnidAbilityToggleData(playerUUID) != null) {
            for (ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper : getArachnidAbilityToggleData()) {
                if (arachnidAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    arachnidAbilityToggleDataWrapper.setToggled(newArachnidAbilityToggleDataWrapper.isToggled());
                    try {
                        saveArachnidAbilityToggleData();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
            }
        }
    }

    public void deleteArachnidAbilityToggleData(UUID playerUUID) {
        if (findArachnidAbilityToggleData(playerUUID) != null) {
            for (ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper : getArachnidAbilityToggleData()) {
                if (arachnidAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    getArachnidAbilityToggleData().remove(arachnidAbilityToggleDataWrapper);
                    break;
                }
            }
            try {
                saveArachnidAbilityToggleData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public void saveArachnidAbilityToggleData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "arachniddata" + s + "arachnidabilitytoggledata" + s + "arachnidabilitytoggledata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(getArachnidAbilityToggleData(), writer);
                    writer.flush();
                    writer.close();
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }

    public void loadArachnidAbilityToggleData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "arachniddata" + s + "arachnidabilitytoggledata" + s + "arachnidabilitytoggledata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    try {
                        Reader reader = new FileReader(file);
                        ArachnidAbilityToggleDataWrapper[] n = gson.fromJson(reader, ArachnidAbilityToggleDataWrapper[].class);
                        arachnidAbilityToggleDataWrappers = new ArrayList<>(Arrays.asList(n));
                    } catch (FileNotFoundException event) {
                        event.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }
}

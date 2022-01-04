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
import me.lemonypancakes.originsbukkit.storage.wrappers.PhantomAbilityToggleDataWrapper;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PhantomAbilityToggleData {

    private final StorageHandler storageHandler;
    private List<PhantomAbilityToggleDataWrapper> phantomAbilityToggleDataWrappers = new ArrayList<>();

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public List<PhantomAbilityToggleDataWrapper> getPhantomAbilityToggleData() {
        return phantomAbilityToggleDataWrappers;
    }

    public void setPhantomAbilityToggleData(List<PhantomAbilityToggleDataWrapper> phantomAbilityToggleDataWrappers) {
        this.phantomAbilityToggleDataWrappers = phantomAbilityToggleDataWrappers;
    }

    public PhantomAbilityToggleData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    private void init() {
        try {
            loadPhantomAbilityToggleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPhantomAbilityToggleData(UUID playerUUID, boolean isToggled) {
        if (findPhantomAbilityToggleData(playerUUID) == null) {
            PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper = new PhantomAbilityToggleDataWrapper(playerUUID, isToggled);
            getPhantomAbilityToggleData().add(phantomAbilityToggleDataWrapper);
            try {
                savePhantomAbilityToggleData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public PhantomAbilityToggleDataWrapper findPhantomAbilityToggleData(UUID playerUUID) {
        for (PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper : getPhantomAbilityToggleData()) {
            if (phantomAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return phantomAbilityToggleDataWrapper;
            }
        }
        return null;
    }

    public boolean getPhantomAbilityToggleData(UUID playerUUID) {
        for (PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper : getPhantomAbilityToggleData()) {
            if (phantomAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return phantomAbilityToggleDataWrapper.isToggled();
            }
        }
        return false;
    }

    public void updatePhantomAbilityToggleData(UUID playerUUID, PhantomAbilityToggleDataWrapper newPhantomAbilityToggleDataWrapper) {
        if (findPhantomAbilityToggleData(playerUUID) != null) {
            for (PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper : getPhantomAbilityToggleData()) {
                if (phantomAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    phantomAbilityToggleDataWrapper.setToggled(newPhantomAbilityToggleDataWrapper.isToggled());
                    try {
                        savePhantomAbilityToggleData();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
            }
        }
    }

    public void deletePhantomAbilityToggleData(UUID playerUUID) {
        if (findPhantomAbilityToggleData(playerUUID) != null) {
            for (PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper : getPhantomAbilityToggleData()) {
                if (phantomAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    getPhantomAbilityToggleData().remove(phantomAbilityToggleDataWrapper);
                    break;
                }
            }
            try {
                savePhantomAbilityToggleData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public void savePhantomAbilityToggleData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "phantomdata" + s + "phantomabilitytoggledata" + s + "phantomabilitytoggledata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(getPhantomAbilityToggleData(), writer);
                    writer.flush();
                    writer.close();
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }

    public void loadPhantomAbilityToggleData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "phantomdata" + s + "phantomabilitytoggledata" + s + "phantomabilitytoggledata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    try {
                        Reader reader = new FileReader(file);
                        PhantomAbilityToggleDataWrapper[] n = gson.fromJson(reader, PhantomAbilityToggleDataWrapper[].class);
                        phantomAbilityToggleDataWrappers = new ArrayList<>(Arrays.asList(n));
                    } catch (FileNotFoundException event) {
                        event.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }
}

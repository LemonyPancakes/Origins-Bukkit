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

/**
 * The type Arachnid ability toggle data.
 *
 * @author LemonyPancakes
 */
public class ArachnidAbilityToggleData {

    private final StorageHandler storageHandler;
    private List<ArachnidAbilityToggleDataWrapper> arachnidAbilityToggleDataWrappers = new ArrayList<>();

    /**
     * Gets arachnid ability toggle data.
     *
     * @return the arachnid ability toggle data
     */
    public List<ArachnidAbilityToggleDataWrapper> getArachnidAbilityToggleData() {
        return arachnidAbilityToggleDataWrappers;
    }

    /**
     * Sets arachnid ability toggle data.
     *
     * @param arachnidAbilityToggleDataWrappers the arachnid ability toggle data wrappers
     */
    public void setArachnidAbilityToggleData(List<ArachnidAbilityToggleDataWrapper> arachnidAbilityToggleDataWrappers) {
        this.arachnidAbilityToggleDataWrappers = arachnidAbilityToggleDataWrappers;
    }

    /**
     * Gets storage handler.
     *
     * @return the storage handler
     */
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    /**
     * Instantiates a new Arachnid ability toggle data.
     *
     * @param storageHandler the storage handler
     */
    public ArachnidAbilityToggleData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        try {
            loadArachnidAbilityToggleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create arachnid ability toggle data.
     *
     * @param playerUUID the player uuid
     * @param isToggled  the is toggled
     */
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

    /**
     * Find arachnid ability toggle data arachnid ability toggle data wrapper.
     *
     * @param playerUUID the player uuid
     *
     * @return the arachnid ability toggle data wrapper
     */
    public ArachnidAbilityToggleDataWrapper findArachnidAbilityToggleData(UUID playerUUID) {
        for (ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper : getArachnidAbilityToggleData()) {
            if (arachnidAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return arachnidAbilityToggleDataWrapper;
            }
        }
        return null;
    }

    /**
     * Gets arachnid ability toggle data.
     *
     * @param playerUUID the player uuid
     *
     * @return the arachnid ability toggle data
     */
    public boolean getArachnidAbilityToggleData(UUID playerUUID) {
        for (ArachnidAbilityToggleDataWrapper arachnidAbilityToggleDataWrapper : getArachnidAbilityToggleData()) {
            if (arachnidAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return arachnidAbilityToggleDataWrapper.isToggled();
            }
        }
        return false;
    }

    /**
     * Update arachnid ability toggle data.
     *
     * @param playerUUID                          the player uuid
     * @param newArachnidAbilityToggleDataWrapper the new arachnid ability toggle data wrapper
     */
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

    /**
     * Delete arachnid ability toggle data.
     *
     * @param playerUUID the player uuid
     */
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

    /**
     * Save arachnid ability toggle data.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Load arachnid ability toggle data.
     *
     * @throws IOException the io exception
     */
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

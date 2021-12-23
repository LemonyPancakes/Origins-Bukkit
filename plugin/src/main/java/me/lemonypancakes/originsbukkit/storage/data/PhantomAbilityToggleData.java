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

/**
 * The type Phantom ability toggle data.
 *
 * @author LemonyPancakes
 */
public class PhantomAbilityToggleData {

    private final StorageHandler storageHandler;
    private List<PhantomAbilityToggleDataWrapper> phantomAbilityToggleDataWrappers = new ArrayList<>();

    /**
     * Gets storage handler.
     *
     * @return the storage handler
     */
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    /**
     * Gets phantom ability toggle data.
     *
     * @return the phantom ability toggle data
     */
    public List<PhantomAbilityToggleDataWrapper> getPhantomAbilityToggleData() {
        return phantomAbilityToggleDataWrappers;
    }

    /**
     * Sets phantom ability toggle data.
     *
     * @param phantomAbilityToggleDataWrappers the phantom ability toggle data wrappers
     */
    public void setPhantomAbilityToggleData(List<PhantomAbilityToggleDataWrapper> phantomAbilityToggleDataWrappers) {
        this.phantomAbilityToggleDataWrappers = phantomAbilityToggleDataWrappers;
    }

    /**
     * Instantiates a new Phantom ability toggle data.
     *
     * @param storageHandler the storage handler
     */
    public PhantomAbilityToggleData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        try {
            loadPhantomAbilityToggleData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create phantom ability toggle data.
     *
     * @param playerUUID the player uuid
     * @param isToggled  the is toggled
     */
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

    /**
     * Find phantom ability toggle data phantom ability toggle data wrapper.
     *
     * @param playerUUID the player uuid
     *
     * @return the phantom ability toggle data wrapper
     */
    public PhantomAbilityToggleDataWrapper findPhantomAbilityToggleData(UUID playerUUID) {
        for (PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper : getPhantomAbilityToggleData()) {
            if (phantomAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return phantomAbilityToggleDataWrapper;
            }
        }
        return null;
    }

    /**
     * Gets phantom ability toggle data.
     *
     * @param playerUUID the player uuid
     *
     * @return the phantom ability toggle data
     */
    public boolean getPhantomAbilityToggleData(UUID playerUUID) {
        for (PhantomAbilityToggleDataWrapper phantomAbilityToggleDataWrapper : getPhantomAbilityToggleData()) {
            if (phantomAbilityToggleDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return phantomAbilityToggleDataWrapper.isToggled();
            }
        }
        return false;
    }

    /**
     * Update phantom ability toggle data.
     *
     * @param playerUUID                         the player uuid
     * @param newPhantomAbilityToggleDataWrapper the new phantom ability toggle data wrapper
     */
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

    /**
     * Delete phantom ability toggle data.
     *
     * @param playerUUID the player uuid
     */
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

    /**
     * Save phantom ability toggle data.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Load phantom ability toggle data.
     *
     * @throws IOException the io exception
     */
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

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
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginChangeEvent;
import me.lemonypancakes.originsbukkit.storage.StorageHandler;
import me.lemonypancakes.originsbukkit.storage.wrappers.OriginsPlayerDataWrapper;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The type Origins player data.
 *
 * @author LemonyPancakes
 */
public class OriginsPlayerData {

    private final StorageHandler storageHandler;
    private List<OriginsPlayerDataWrapper> originsPlayerDataWrappers = new ArrayList<>();
    private boolean isOriginsPlayerDataLoaded = false;

    /**
     * Gets storage handler.
     *
     * @return the storage handler
     */
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    /**
     * Gets origins player data.
     *
     * @return the origins player data
     */
    public List<OriginsPlayerDataWrapper> getOriginsPlayerData() {
        return originsPlayerDataWrappers;
    }

    /**
     * Sets origins player data.
     *
     * @param originsPlayerDatumWrappers the origins player datum wrappers
     */
    public void setOriginsPlayerData(List<OriginsPlayerDataWrapper> originsPlayerDatumWrappers) {
        this.originsPlayerDataWrappers = originsPlayerDatumWrappers;
    }

    /**
     * Is origins player data loaded boolean.
     *
     * @return the boolean
     */
    public boolean isOriginsPlayerDataLoaded() {
        return isOriginsPlayerDataLoaded;
    }

    /**
     * Sets origins player data loaded.
     *
     * @param originsPlayerDataLoaded the origins player data loaded
     */
    public void setOriginsPlayerDataLoaded(boolean originsPlayerDataLoaded) {
        isOriginsPlayerDataLoaded = originsPlayerDataLoaded;
    }

    /**
     * Instantiates a new Origins player data.
     *
     * @param storageHandler the storage handler
     */
    public OriginsPlayerData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        try {
            loadOriginsPlayerData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create origins player data.
     *
     * @param playerUUID the player uuid
     * @param player     the player
     * @param origin     the origin
     */
    public void createOriginsPlayerData(UUID playerUUID, Player player, String origin) {
        String playerName = player.getName();

        if (findOriginsPlayerData(playerUUID) == null) {
            OriginsPlayerDataWrapper originsPlayerDataWrapper = new OriginsPlayerDataWrapper(playerUUID, playerName, origin);
            String newOrigin = originsPlayerDataWrapper.getOrigin();

            getOriginsPlayerData().add(originsPlayerDataWrapper);
            new BukkitRunnable() {

                @Override
                public void run() {
                    AsyncPlayerOriginChangeEvent asyncPlayerOriginChangeEvent = new AsyncPlayerOriginChangeEvent(player, null, newOrigin);
                    Bukkit.getPluginManager().callEvent(asyncPlayerOriginChangeEvent);
                }
            }.runTaskAsynchronously(getStorageHandler().getPlugin());
            try {
                saveOriginsPlayerData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    /**
     * Find origins player data origins player data wrapper.
     *
     * @param playerUUID the player uuid
     *
     * @return the origins player data wrapper
     */
    public OriginsPlayerDataWrapper findOriginsPlayerData(UUID playerUUID) {
        for (OriginsPlayerDataWrapper originsPlayerDataWrapper : getOriginsPlayerData()) {
            if (originsPlayerDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return originsPlayerDataWrapper;
            }
        }
        return null;
    }

    /**
     * Gets player origin.
     *
     * @param playerUUID the player uuid
     *
     * @return the player origin
     */
    public String getPlayerOrigin(UUID playerUUID) {
        for (OriginsPlayerDataWrapper originsPlayerDataWrapper : getOriginsPlayerData()) {
            if (originsPlayerDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return originsPlayerDataWrapper.getOrigin();
            }
        }
        return null;
    }

    /**
     * Update origins player data.
     *
     * @param playerUUID                  the player uuid
     * @param newOriginsPlayerDataWrapper the new origins player data wrapper
     */
    public void updateOriginsPlayerData(UUID playerUUID, OriginsPlayerDataWrapper newOriginsPlayerDataWrapper) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (findOriginsPlayerData(playerUUID) != null) {
            for (OriginsPlayerDataWrapper originsPlayerDataWrapper : getOriginsPlayerData()) {
                if (originsPlayerDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    String oldOrigin = originsPlayerDataWrapper.getOrigin();

                    originsPlayerDataWrapper.setPlayerName(newOriginsPlayerDataWrapper.getPlayerName());
                    originsPlayerDataWrapper.setOrigin(newOriginsPlayerDataWrapper.getOrigin());
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            AsyncPlayerOriginChangeEvent asyncPlayerOriginChangeEvent = new AsyncPlayerOriginChangeEvent(player, oldOrigin, newOriginsPlayerDataWrapper.getOrigin());
                            Bukkit.getPluginManager().callEvent(asyncPlayerOriginChangeEvent);
                        }
                    }.runTaskAsynchronously(getStorageHandler().getPlugin());
                    try {
                        saveOriginsPlayerData();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Delete origins player data.
     *
     * @param playerUUID the player uuid
     */
    public void deleteOriginsPlayerData(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (findOriginsPlayerData(playerUUID) != null) {
            for (OriginsPlayerDataWrapper originsPlayerDataWrapper : getOriginsPlayerData()) {
                if (originsPlayerDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    String oldOrigin = originsPlayerDataWrapper.getOrigin();

                    getOriginsPlayerData().remove(originsPlayerDataWrapper);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            AsyncPlayerOriginChangeEvent asyncPlayerOriginChangeEvent = new AsyncPlayerOriginChangeEvent(player, oldOrigin, null);
                            Bukkit.getPluginManager().callEvent(asyncPlayerOriginChangeEvent);
                        }
                    }.runTaskAsynchronously(getStorageHandler().getPlugin());
                    break;
                }
            }
            try {
                saveOriginsPlayerData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    /**
     * Save origins player data.
     *
     * @throws IOException the io exception
     */
    public void saveOriginsPlayerData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "playerorigindata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(getOriginsPlayerData(), writer);
                    writer.flush();
                    writer.close();
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }

    /**
     * Load origins player data.
     *
     * @throws IOException the io exception
     */
    public void loadOriginsPlayerData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "playerorigindata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Loading player data...");

                    try {
                        Reader reader = new FileReader(file);
                        OriginsPlayerDataWrapper[] n = gson.fromJson(reader, OriginsPlayerDataWrapper[].class);
                        originsPlayerDataWrappers = new ArrayList<>(Arrays.asList(n));
                    } catch (FileNotFoundException event) {
                        event.printStackTrace();
                    }
                    setOriginsPlayerDataLoaded(true);
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Player data loaded.");
                } else {
                    setOriginsPlayerDataLoaded(true);
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }
}

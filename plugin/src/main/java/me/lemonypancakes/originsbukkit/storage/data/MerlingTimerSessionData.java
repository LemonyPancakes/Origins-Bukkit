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
import me.lemonypancakes.originsbukkit.storage.wrappers.MerlingTimerSessionDataWrapper;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MerlingTimerSessionData {

    private final StorageHandler storageHandler;
    private List<MerlingTimerSessionDataWrapper> merlingTimerSessionDataWrappers = new ArrayList<>();

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public List<MerlingTimerSessionDataWrapper> getMerlingTimerSessionData() {
        return merlingTimerSessionDataWrappers;
    }

    public void setMerlingTimerSessionData(List<MerlingTimerSessionDataWrapper> merlingTimerSessionDataWrappers) {
        this.merlingTimerSessionDataWrappers = merlingTimerSessionDataWrappers;
    }

    public MerlingTimerSessionData(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        init();
    }

    private void init() {
        try {
            loadMerlingTimerSessionData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMerlingTimerSessionData(UUID playerUUID, int timeLeft) {
        if (findMerlingTimerSessionData(playerUUID) == null) {
            MerlingTimerSessionDataWrapper merlingTimerSessionDataWrapper = new MerlingTimerSessionDataWrapper(playerUUID, timeLeft);
            getMerlingTimerSessionData().add(merlingTimerSessionDataWrapper);
            try {
                saveMerlingTimerSessionData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public MerlingTimerSessionDataWrapper findMerlingTimerSessionData(UUID playerUUID) {
        for (MerlingTimerSessionDataWrapper merlingTimerSessionDataWrapper : getMerlingTimerSessionData()) {
            if (merlingTimerSessionDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return merlingTimerSessionDataWrapper;
            }
        }
        return null;
    }

    public int getMerlingTimerSessionDataTimeLeft(UUID playerUUID) {
        for (MerlingTimerSessionDataWrapper merlingTimerSessionDataWrapper : getMerlingTimerSessionData()) {
            if (merlingTimerSessionDataWrapper.getPlayerUUID().equals(playerUUID)) {
                return merlingTimerSessionDataWrapper.getTimeLeft();
            }
        }
        return 0;
    }

    public void updateMerlingTimerSessionData(UUID playerUUID, MerlingTimerSessionDataWrapper newMerlingTimerSessionDataWrapper) {
        if (findMerlingTimerSessionData(playerUUID) != null) {
            for (MerlingTimerSessionDataWrapper merlingTimerSessionDataWrapper : getMerlingTimerSessionData()) {
                if (merlingTimerSessionDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    merlingTimerSessionDataWrapper.setTimeLeft(newMerlingTimerSessionDataWrapper.getTimeLeft());
                    try {
                        saveMerlingTimerSessionData();
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
            }
        }
    }

    public void deleteMerlingTimerSessionData(UUID playerUUID) {
        if (findMerlingTimerSessionData(playerUUID) != null) {
            for (MerlingTimerSessionDataWrapper merlingTimerSessionDataWrapper : getMerlingTimerSessionData()) {
                if (merlingTimerSessionDataWrapper.getPlayerUUID().equals(playerUUID)) {
                    getMerlingTimerSessionData().remove(merlingTimerSessionDataWrapper);
                    break;
                }
            }
            try {
                saveMerlingTimerSessionData();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
    }

    public void saveMerlingTimerSessionData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "merlingdata" + s + "merlingtimersessiondata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(getMerlingTimerSessionData(), writer);
                    writer.flush();
                    writer.close();
                } catch (IOException event) {
                    event.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }

    public void loadMerlingTimerSessionData() throws IOException {

        new BukkitRunnable() {

            @Override
            public void run() {
                Gson gson = new Gson();
                String s = File.separator;
                File file = new File(getStorageHandler().getPlugin().getDataFolder().getAbsolutePath() + s + "cache" + s + "merlingdata" + s + "merlingtimersessiondata.json");

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    try {
                        Reader reader = new FileReader(file);
                        MerlingTimerSessionDataWrapper[] n = gson.fromJson(reader, MerlingTimerSessionDataWrapper[].class);
                        merlingTimerSessionDataWrappers = new ArrayList<>(Arrays.asList(n));
                    } catch (FileNotFoundException event) {
                        event.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(getStorageHandler().getPlugin());
    }
}

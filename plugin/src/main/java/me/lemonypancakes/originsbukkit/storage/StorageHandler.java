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
package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.storage.data.*;

public class StorageHandler {

    private final OriginsBukkit plugin;
    private ArachnidAbilityToggleData arachnidAbilityToggleData;
    private MerlingTimerSessionData merlingTimerSessionData;
    private OriginsPlayerData originsPlayerData;
    private PhantomAbilityToggleData phantomAbilityToggleData;
    private ShulkPlayerStorageData shulkPlayerStorageData;
    private BlazebornNetherSpawnData blazebornNetherSpawnData;
    private ElytrianClaustrophobiaTimerData elytrianClaustrophobiaTimerData;

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public ArachnidAbilityToggleData getArachnidAbilityToggleData() {
        return arachnidAbilityToggleData;
    }

    public MerlingTimerSessionData getMerlingTimerSessionData() {
        return merlingTimerSessionData;
    }

    public OriginsPlayerData getOriginsPlayerData() {
        return originsPlayerData;
    }

    public PhantomAbilityToggleData getPhantomAbilityToggleData() {
        return phantomAbilityToggleData;
    }

    public ShulkPlayerStorageData getShulkPlayerStorageData() {
        return shulkPlayerStorageData;
    }

    public BlazebornNetherSpawnData getBlazebornNetherSpawnData() {
        return blazebornNetherSpawnData;
    }

    public ElytrianClaustrophobiaTimerData getElytrianClaustrophobiaTimerData() {
        return elytrianClaustrophobiaTimerData;
    }

    public StorageHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        arachnidAbilityToggleData = new ArachnidAbilityToggleData(this);
        merlingTimerSessionData = new MerlingTimerSessionData(this);
        originsPlayerData = new OriginsPlayerData(this);
        phantomAbilityToggleData = new PhantomAbilityToggleData(this);
        shulkPlayerStorageData = new ShulkPlayerStorageData(this);
        blazebornNetherSpawnData = new BlazebornNetherSpawnData(this);
        elytrianClaustrophobiaTimerData = new ElytrianClaustrophobiaTimerData(this);
    }
}

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

/**
 * The type Storage handler.
 *
 * @author LemonyPancakes
 */
public class StorageHandler {

    private final OriginsBukkit plugin;
    private ArachnidAbilityToggleData arachnidAbilityToggleData;
    private MerlingTimerSessionData merlingTimerSessionData;
    private OriginsPlayerData originsPlayerData;
    private PhantomAbilityToggleData phantomAbilityToggleData;
    private ShulkPlayerStorageData shulkPlayerStorageData;
    private BlazebornNetherSpawnData blazebornNetherSpawnData;
    private ElytrianClaustrophobiaTimerData elytrianClaustrophobiaTimerData;

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public OriginsBukkit getPlugin() {
        return plugin;
    }

    /**
     * Gets arachnid ability toggle data.
     *
     * @return the arachnid ability toggle data
     */
    public ArachnidAbilityToggleData getArachnidAbilityToggleData() {
        return arachnidAbilityToggleData;
    }

    /**
     * Gets merling timer session data.
     *
     * @return the merling timer session data
     */
    public MerlingTimerSessionData getMerlingTimerSessionData() {
        return merlingTimerSessionData;
    }

    /**
     * Gets origins player data.
     *
     * @return the origins player data
     */
    public OriginsPlayerData getOriginsPlayerData() {
        return originsPlayerData;
    }

    /**
     * Gets phantom ability toggle data.
     *
     * @return the phantom ability toggle data
     */
    public PhantomAbilityToggleData getPhantomAbilityToggleData() {
        return phantomAbilityToggleData;
    }

    /**
     * Gets shulk player storage data.
     *
     * @return the shulk player storage data
     */
    public ShulkPlayerStorageData getShulkPlayerStorageData() {
        return shulkPlayerStorageData;
    }

    /**
     * Gets blazeborn nether spawn data.
     *
     * @return the blazeborn nether spawn data
     */
    public BlazebornNetherSpawnData getBlazebornNetherSpawnData() {
        return blazebornNetherSpawnData;
    }

    /**
     * Gets elytrian claustrophobia timer data.
     *
     * @return the elytrian claustrophobia timer data
     */
    public ElytrianClaustrophobiaTimerData getElytrianClaustrophobiaTimerData() {
        return elytrianClaustrophobiaTimerData;
    }

    /**
     * Instantiates a new Storage handler.
     *
     * @param plugin the plugin
     */
    public StorageHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    /**
     * Init.
     */
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

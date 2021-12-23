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
package me.lemonypancakes.originsbukkit.api.wrappers;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.storage.wrappers.*;
import org.bukkit.entity.Player;

/**
 * The type Origin player.
 *
 * @author LemonyPancakes
 */
@SuppressWarnings("unused")
public class OriginPlayer {

    private final Player player;

    /**
     * Instantiates a new Origin player.
     *
     * @param player the player
     */
    public OriginPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Create origins player data.
     *
     * @param origin the origin
     */
    public void createOriginsPlayerData(String origin) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .createOriginsPlayerData(
                        player.getUniqueId(),
                        player,
                        origin);
    }

    /**
     * Find origins player data origins player data wrapper.
     *
     * @return the origins player data wrapper
     */
    public OriginsPlayerDataWrapper findOriginsPlayerData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .findOriginsPlayerData(player.getUniqueId());
    }

    /**
     * Gets origin.
     *
     * @return the origin
     */
    public String getOrigin() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .getPlayerOrigin(player.getUniqueId());
    }

    /**
     * Update origins player data.
     *
     * @param newOriginsPlayerDataWrapper the new origins player data wrapper
     */
    public void updateOriginsPlayerData(OriginsPlayerDataWrapper newOriginsPlayerDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .updateOriginsPlayerData(player.getUniqueId(),
                        new OriginsPlayerDataWrapper(
                                newOriginsPlayerDataWrapper.getPlayerUUID(),
                                newOriginsPlayerDataWrapper.getPlayerName(),
                                newOriginsPlayerDataWrapper.getOrigin()));
    }

    /**
     * Delete origins player data.
     */
    public void deleteOriginsPlayerData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .deleteOriginsPlayerData(player.getUniqueId());
    }

    /**
     * Create arachnid ability toggle data.
     *
     * @param isToggled the is toggled
     */
    public void createArachnidAbilityToggleData(boolean isToggled) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .createArachnidAbilityToggleData(
                        player.getUniqueId(),
                        isToggled);
    }

    /**
     * Find arachnid ability toggle data arachnid ability toggle data wrapper.
     *
     * @return the arachnid ability toggle data wrapper
     */
    public ArachnidAbilityToggleDataWrapper findArachnidAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .findArachnidAbilityToggleData(player.getUniqueId());
    }

    /**
     * Gets arachnid ability toggle data.
     *
     * @return the arachnid ability toggle data
     */
    public boolean getArachnidAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .getArachnidAbilityToggleData(player.getUniqueId());
    }

    /**
     * Update arachnid ability toggle data.
     *
     * @param newArachnidAbilityToggleDataWrapper the new arachnid ability toggle data wrapper
     */
    public void updateArachnidAbilityToggleData(ArachnidAbilityToggleDataWrapper newArachnidAbilityToggleDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .updateArachnidAbilityToggleData(player.getUniqueId(),
                        new ArachnidAbilityToggleDataWrapper(
                                newArachnidAbilityToggleDataWrapper.getPlayerUUID(),
                                newArachnidAbilityToggleDataWrapper.isToggled()));
    }

    /**
     * Delete arachnid ability toggle data.
     */
    public void deleteArachnidAbilityToggleData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .deleteArachnidAbilityToggleData(player.getUniqueId());
    }

    /**
     * Create merling timer session data.
     *
     * @param timeLeft the time left
     */
    public void createMerlingTimerSessionData(int timeLeft) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .createMerlingTimerSessionData(
                        player.getUniqueId(),
                        timeLeft);
    }

    /**
     * Find merling timer session data merling timer session data wrapper.
     *
     * @return the merling timer session data wrapper
     */
    public MerlingTimerSessionDataWrapper findMerlingTimerSessionData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .findMerlingTimerSessionData(player.getUniqueId());
    }

    /**
     * Gets merling timer session data time left.
     *
     * @return the merling timer session data time left
     */
    public int getMerlingTimerSessionDataTimeLeft() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .getMerlingTimerSessionDataTimeLeft(player.getUniqueId());
    }

    /**
     * Update merling timer session data.
     *
     * @param newMerlingTimerSessionDataWrapper the new merling timer session data wrapper
     */
    public void updateMerlingTimerSessionData(MerlingTimerSessionDataWrapper newMerlingTimerSessionDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .updateMerlingTimerSessionData(player.getUniqueId(),
                        new MerlingTimerSessionDataWrapper(
                                newMerlingTimerSessionDataWrapper.getPlayerUUID(),
                                newMerlingTimerSessionDataWrapper.getTimeLeft()));
    }

    /**
     * Delete merling timer session data.
     */
    public void deleteMerlingTimerSessionData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .deleteMerlingTimerSessionData(player.getUniqueId());
    }

    /**
     * Create phantom ability toggle data.
     *
     * @param isToggled the is toggled
     */
    public void createPhantomAbilityToggleData(boolean isToggled) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .createPhantomAbilityToggleData(
                        player.getUniqueId(),
                        isToggled);
    }

    /**
     * Find phantom ability toggle data phantom ability toggle data wrapper.
     *
     * @return the phantom ability toggle data wrapper
     */
    public PhantomAbilityToggleDataWrapper findPhantomAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .findPhantomAbilityToggleData(player.getUniqueId());
    }

    /**
     * Gets phantom ability toggle data.
     *
     * @return the phantom ability toggle data
     */
    public boolean getPhantomAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .getPhantomAbilityToggleData(player.getUniqueId());
    }

    /**
     * Update phantom ability toggle data.
     *
     * @param newPhantomAbilityToggleDataWrapper the new phantom ability toggle data wrapper
     */
    public void updatePhantomAbilityToggleData(PhantomAbilityToggleDataWrapper newPhantomAbilityToggleDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .updatePhantomAbilityToggleData(player.getUniqueId(),
                        new PhantomAbilityToggleDataWrapper(
                                newPhantomAbilityToggleDataWrapper.getPlayerUUID(),
                                newPhantomAbilityToggleDataWrapper.isToggled()));
    }

    /**
     * Delete phantom ability toggle data.
     */
    public void deletePhantomAbilityToggleData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .deletePhantomAbilityToggleData(player.getUniqueId());
    }

    /**
     * Create blazeborn nether spawn data.
     *
     * @param firstTime the first time
     */
    public void createBlazebornNetherSpawnData(boolean firstTime) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .createBlazebornNetherSpawnData(
                        player.getUniqueId(),
                        firstTime);
    }

    /**
     * Find blazeborn nether spawn data blazeborn nether spawn data wrapper.
     *
     * @return the blazeborn nether spawn data wrapper
     */
    public BlazebornNetherSpawnDataWrapper findBlazebornNetherSpawnData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .findBlazebornNetherSpawnData(player.getUniqueId());
    }

    /**
     * Gets blazeborn nether spawn data.
     *
     * @return the blazeborn nether spawn data
     */
    public boolean getBlazebornNetherSpawnData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .getBlazebornNetherSpawnData(player.getUniqueId());
    }

    /**
     * Update blazeborn nether spawn data.
     *
     * @param newBlazebornNetherSpawnDataWrapper the new blazeborn nether spawn data wrapper
     */
    public void updateBlazebornNetherSpawnData(BlazebornNetherSpawnDataWrapper newBlazebornNetherSpawnDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .updateBlazebornNetherSpawnData(player.getUniqueId(),
                        new BlazebornNetherSpawnDataWrapper(
                                newBlazebornNetherSpawnDataWrapper.getPlayerUUID(),
                                newBlazebornNetherSpawnDataWrapper.isFirstTime()));
    }

    /**
     * Delete blazeborn nether spawn data.
     */
    public void deleteBlazebornNetherSpawnData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .deleteBlazebornNetherSpawnData(player.getUniqueId());
    }

    /**
     * Create elytrian claustrophobia timer data.
     *
     * @param timerTimeLeft          the timer time left
     * @param claustrophobiaTimeLeft the claustrophobia time left
     */
    public void createElytrianClaustrophobiaTimerData(int timerTimeLeft, int claustrophobiaTimeLeft) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .createElytrianClaustrophobiaTimerData(
                        player.getUniqueId(),
                        timerTimeLeft,
                        claustrophobiaTimeLeft);
    }

    /**
     * Find elytrian claustrophobia timer data elytrian claustrophobia timer data wrapper.
     *
     * @return the elytrian claustrophobia timer data wrapper
     */
    public ElytrianClaustrophobiaTimerDataWrapper findElytrianClaustrophobiaTimerData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .findElytrianClaustrophobiaTimerData(player.getUniqueId());
    }

    /**
     * Gets elytrian claustrophobia timer data.
     *
     * @return the elytrian claustrophobia timer data
     */
    public int getElytrianClaustrophobiaTimerData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .getElytrianClaustrophobiaTimerData(player.getUniqueId());
    }

    /**
     * Update elytrian claustrophobia timer data.
     *
     * @param newElytrianClaustrophobiaTimerDataWrapper the new elytrian claustrophobia timer data wrapper
     */
    public void updateElytrianClaustrophobiaTimerData(ElytrianClaustrophobiaTimerDataWrapper newElytrianClaustrophobiaTimerDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .updateElytrianClaustrophobiaTimerData(player.getUniqueId(),
                        new ElytrianClaustrophobiaTimerDataWrapper(
                        newElytrianClaustrophobiaTimerDataWrapper.getPlayerUUID(),
                        newElytrianClaustrophobiaTimerDataWrapper.getTimerTimeLeft(),
                        newElytrianClaustrophobiaTimerDataWrapper.getClaustrophobiaTimeLeft()));
    }

    /**
     * Delete elytrian claustrophobia timer data.
     */
    public void deleteElytrianClaustrophobiaTimerData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .deleteElytrianClaustrophobiaTimerData(player.getUniqueId());
    }
}

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

@SuppressWarnings("unused")
public class OriginPlayer {

    private final Player player;

    public OriginPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void createOriginsPlayerData(String origin) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .createOriginsPlayerData(
                        player.getUniqueId(),
                        player,
                        origin);
    }

    public OriginsPlayerDataWrapper findOriginsPlayerData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .findOriginsPlayerData(player.getUniqueId());
    }

    public String getOrigin() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .getPlayerOrigin(player.getUniqueId());
    }

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

    public void deleteOriginsPlayerData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOriginsPlayerData()
                .deleteOriginsPlayerData(player.getUniqueId());
    }

    public void createArachnidAbilityToggleData(boolean isToggled) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .createArachnidAbilityToggleData(
                        player.getUniqueId(),
                        isToggled);
    }

    public ArachnidAbilityToggleDataWrapper findArachnidAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .findArachnidAbilityToggleData(player.getUniqueId());
    }

    public boolean getArachnidAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .getArachnidAbilityToggleData(player.getUniqueId());
    }

    public void updateArachnidAbilityToggleData(ArachnidAbilityToggleDataWrapper newArachnidAbilityToggleDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .updateArachnidAbilityToggleData(player.getUniqueId(),
                        new ArachnidAbilityToggleDataWrapper(
                                newArachnidAbilityToggleDataWrapper.getPlayerUUID(),
                                newArachnidAbilityToggleDataWrapper.isToggled()));
    }

    public void deleteArachnidAbilityToggleData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getArachnidAbilityToggleData()
                .deleteArachnidAbilityToggleData(player.getUniqueId());
    }

    public void createMerlingTimerSessionData(int timeLeft) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .createMerlingTimerSessionData(
                        player.getUniqueId(),
                        timeLeft);
    }

    public MerlingTimerSessionDataWrapper findMerlingTimerSessionData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .findMerlingTimerSessionData(player.getUniqueId());
    }

    public int getMerlingTimerSessionDataTimeLeft() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .getMerlingTimerSessionDataTimeLeft(player.getUniqueId());
    }

    public void updateMerlingTimerSessionData(MerlingTimerSessionDataWrapper newMerlingTimerSessionDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .updateMerlingTimerSessionData(player.getUniqueId(),
                        new MerlingTimerSessionDataWrapper(
                                newMerlingTimerSessionDataWrapper.getPlayerUUID(),
                                newMerlingTimerSessionDataWrapper.getTimeLeft()));
    }

    public void deleteMerlingTimerSessionData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getMerlingTimerSessionData()
                .deleteMerlingTimerSessionData(player.getUniqueId());
    }

    public void createPhantomAbilityToggleData(boolean isToggled) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .createPhantomAbilityToggleData(
                        player.getUniqueId(),
                        isToggled);
    }

    public PhantomAbilityToggleDataWrapper findPhantomAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .findPhantomAbilityToggleData(player.getUniqueId());
    }

    public boolean getPhantomAbilityToggleData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .getPhantomAbilityToggleData(player.getUniqueId());
    }

    public void updatePhantomAbilityToggleData(PhantomAbilityToggleDataWrapper newPhantomAbilityToggleDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .updatePhantomAbilityToggleData(player.getUniqueId(),
                        new PhantomAbilityToggleDataWrapper(
                                newPhantomAbilityToggleDataWrapper.getPlayerUUID(),
                                newPhantomAbilityToggleDataWrapper.isToggled()));
    }

    public void deletePhantomAbilityToggleData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPhantomAbilityToggleData()
                .deletePhantomAbilityToggleData(player.getUniqueId());
    }

    public void createBlazebornNetherSpawnData(boolean firstTime) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .createBlazebornNetherSpawnData(
                        player.getUniqueId(),
                        firstTime);
    }

    public BlazebornNetherSpawnDataWrapper findBlazebornNetherSpawnData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .findBlazebornNetherSpawnData(player.getUniqueId());
    }

    public boolean getBlazebornNetherSpawnData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .getBlazebornNetherSpawnData(player.getUniqueId());
    }

    public void updateBlazebornNetherSpawnData(BlazebornNetherSpawnDataWrapper newBlazebornNetherSpawnDataWrapper) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .updateBlazebornNetherSpawnData(player.getUniqueId(),
                        new BlazebornNetherSpawnDataWrapper(
                                newBlazebornNetherSpawnDataWrapper.getPlayerUUID(),
                                newBlazebornNetherSpawnDataWrapper.isFirstTime()));
    }

    public void deleteBlazebornNetherSpawnData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getBlazebornNetherSpawnData()
                .deleteBlazebornNetherSpawnData(player.getUniqueId());
    }

    public void createElytrianClaustrophobiaTimerData(int timerTimeLeft, int claustrophobiaTimeLeft) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .createElytrianClaustrophobiaTimerData(
                        player.getUniqueId(),
                        timerTimeLeft,
                        claustrophobiaTimeLeft);
    }

    public ElytrianClaustrophobiaTimerDataWrapper findElytrianClaustrophobiaTimerData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .findElytrianClaustrophobiaTimerData(player.getUniqueId());
    }

    public int getElytrianClaustrophobiaTimerData() {
        return OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .getElytrianClaustrophobiaTimerData(player.getUniqueId());
    }

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

    public void deleteElytrianClaustrophobiaTimerData() {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getElytrianClaustrophobiaTimerData()
                .deleteElytrianClaustrophobiaTimerData(player.getUniqueId());
    }
}

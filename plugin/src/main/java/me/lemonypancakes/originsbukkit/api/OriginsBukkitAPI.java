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
package me.lemonypancakes.originsbukkit.api;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.storage.wrappers.OriginsPlayerDataWrapper;
import org.bukkit.entity.Player;

import java.util.UUID;

public class OriginsBukkitAPI {

    public String getPlayerOrigin(UUID playerUUID) {
        return OriginsBukkit.getPlugin().getStorageHandler().getOriginsPlayerData().getPlayerOrigin(playerUUID);
    }

    public void createOriginsPlayerData(UUID playerUUID, Player player, String origin) {
        OriginsBukkit.getPlugin().getStorageHandler().getOriginsPlayerData().createOriginsPlayerData(playerUUID, player, origin);
    }

    public OriginsPlayerDataWrapper findOriginsPlayerData(UUID playerUUID) {
        return OriginsBukkit.getPlugin().getStorageHandler().getOriginsPlayerData().findOriginsPlayerData(playerUUID);
    }

    public void deleteOriginsPlayerData(UUID playerUUID) {
        OriginsBukkit.getPlugin().getStorageHandler().getOriginsPlayerData().deleteOriginsPlayerData(playerUUID);
    }

    public void updateOriginsPlayerData(UUID playerUUID, OriginsPlayerDataWrapper newOriginsPlayerDataWrapper) {
        OriginsBukkit.getPlugin().getStorageHandler().getOriginsPlayerData().updateOriginsPlayerData(playerUUID, newOriginsPlayerDataWrapper);
    }
}

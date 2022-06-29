/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
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
package me.lemonypancakes.bukkit.origins.data.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.Storage;
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class CraftBukkitStorage implements Storage {

    private final OriginsBukkitPlugin plugin;

    public CraftBukkitStorage(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public Identifier getOrigin(OfflinePlayer offlinePlayer) {
        String string = BukkitPersistentDataUtils.getPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING);

        if (string != null) {
            if (string.contains(":")) {
                return Identifier.fromString(string);
            }
            BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING, "null");
        }
        return null;
    }

    @Override
    public void setOrigin(OfflinePlayer offlinePlayer, Identifier identifier) {
        if (identifier == null) {
            BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING, "null");
            return;
        }
        BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING, identifier.toString());
    }

    @Override
    public boolean hasOriginPlayerData(OfflinePlayer offlinePlayer) {
        return BukkitPersistentDataUtils.hasPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING);
    }

    @Override
    public void wipeOriginPlayerData(OfflinePlayer offlinePlayer) {
        BukkitPersistentDataUtils.removePersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"));
    }

    @Override
    public JsonObject getMetadata(OfflinePlayer offlinePlayer) {
        String string = BukkitPersistentDataUtils.getPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "metadata"), PersistentDataType.STRING);

        if (string != null) {
            return new Gson().fromJson(string, JsonObject.class);
        }
        BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "metadata"), PersistentDataType.STRING, "{}");
        return new Gson().fromJson("{}", JsonObject.class);
    }
}

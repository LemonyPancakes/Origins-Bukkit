package me.lemonypancakes.originsbukkit.data.fetcher;

import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.OriginPlayerDataFetcher;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.util.BukkitPersistentDataUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class BukkitOriginPlayerDataFetcher implements OriginPlayerDataFetcher {

    @Override
    public Identifier getOrigin(OfflinePlayer offlinePlayer) {
        String string = BukkitPersistentDataUtils.getPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(OriginsBukkit.KEY, "origin"), PersistentDataType.STRING);

        if (string != null) {
            if (string.contains(":")) {
                return Identifier.fromString(string);
            }
            BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(OriginsBukkit.KEY, "origin"), PersistentDataType.STRING, "null");
        }
        return null;
    }

    @Override
    public void setOrigin(OfflinePlayer offlinePlayer, Identifier identifier) {
        if (identifier == null) {
            BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(OriginsBukkit.KEY, "origin"), PersistentDataType.STRING, "null");
            return;
        }
        BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(OriginsBukkit.KEY, "origin"), PersistentDataType.STRING, identifier.toString());
    }

    @Override
    public boolean hasOriginPlayerData(OfflinePlayer offlinePlayer) {
        return !BukkitPersistentDataUtils.hasPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(OriginsBukkit.KEY, "origin"), PersistentDataType.STRING);
    }

    @Override
    public void wipeOriginPlayerData(OfflinePlayer offlinePlayer) {
        BukkitPersistentDataUtils.removePersistentData((PersistentDataHolder) offlinePlayer, new Identifier(OriginsBukkit.KEY, "origin"));
    }
}

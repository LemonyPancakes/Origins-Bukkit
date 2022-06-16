package me.lemonypancakes.originsbukkit;

import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.OfflinePlayer;

public interface Storage {

    Identifier getOrigin(OfflinePlayer offlinePlayer);

    void setOrigin(OfflinePlayer offlinePlayer, Identifier identifier);

    boolean hasOriginPlayerData(OfflinePlayer offlinePlayer);

    void wipeOriginPlayerData(OfflinePlayer offlinePlayer);
}

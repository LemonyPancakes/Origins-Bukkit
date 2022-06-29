package me.lemonypancakes.bukkit.origins;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.OfflinePlayer;

public interface Storage extends OriginsBukkitPluginHolder {

    Identifier getOrigin(OfflinePlayer offlinePlayer);

    void setOrigin(OfflinePlayer offlinePlayer, Identifier identifier);

    boolean hasOriginPlayerData(OfflinePlayer offlinePlayer);

    void wipeOriginPlayerData(OfflinePlayer offlinePlayer);

    JsonObject getMetadata(OfflinePlayer offlinePlayer);
}

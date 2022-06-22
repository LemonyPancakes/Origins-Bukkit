package me.lemonypancakes.originsbukkit.data.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Storage;
import me.lemonypancakes.originsbukkit.util.BukkitPersistentDataUtil;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class CraftBukkitStorage implements Storage {

    @Override
    public Identifier getOrigin(OfflinePlayer offlinePlayer) {
        String string = BukkitPersistentDataUtil.getPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING);

        if (string != null) {
            if (string.contains(":")) {
                return Identifier.fromString(string);
            }
            BukkitPersistentDataUtil.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING, "null");
        }
        return null;
    }

    @Override
    public void setOrigin(OfflinePlayer offlinePlayer, Identifier identifier) {
        if (identifier == null) {
            BukkitPersistentDataUtil.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING, "null");
            return;
        }
        BukkitPersistentDataUtil.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING, identifier.toString());
    }

    @Override
    public boolean hasOriginPlayerData(OfflinePlayer offlinePlayer) {
        return BukkitPersistentDataUtil.hasPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), PersistentDataType.STRING);
    }

    @Override
    public void wipeOriginPlayerData(OfflinePlayer offlinePlayer) {
        BukkitPersistentDataUtil.removePersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "origin"));
    }

    @Override
    public JsonObject getMetadata(OfflinePlayer offlinePlayer) {
        String string = BukkitPersistentDataUtil.getPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "metadata"), PersistentDataType.STRING);

        if (string != null) {
            return new Gson().fromJson(string, JsonObject.class);
        }
        BukkitPersistentDataUtil.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "metadata"), PersistentDataType.STRING, "{}");
        return new Gson().fromJson("{}", JsonObject.class);
    }
}

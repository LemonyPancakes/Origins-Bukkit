package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.api.data.type.OriginPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OriginPlayers {

    private final StorageHandler storageHandler;

    public OriginPlayers(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    private final Map<UUID, OriginPlayer> originPlayerMap = new HashMap<>();

    public void add(OriginPlayer originPlayer) {
        if (!originPlayerMap.containsKey(originPlayer.getPlayerUUID())) {
            originPlayerMap.put(originPlayer.getPlayerUUID(), originPlayer);
        }
    }

    public void add(OriginPlayer originPlayer, boolean override) {
        if (originPlayerMap.containsKey(originPlayer.getPlayerUUID())) {
            if (override) {
                removeByPlayerUUID(originPlayer.getPlayerUUID());
                add(originPlayer);
            }
        } else {
            add(originPlayer);
        }
    }

    public void removeByPlayerUUID(UUID playerUUID) {
        if (originPlayerMap.containsKey(playerUUID)) {
            originPlayerMap.get(playerUUID).unlistenAndDestroy();
            originPlayerMap.remove(playerUUID);
        }
    }

    public void removeByValue(OriginPlayer originPlayer) {
        for (Map.Entry<UUID, OriginPlayer> entry : originPlayerMap.entrySet()) {
            if (entry.getValue().equals(originPlayer)) {
                entry.getValue().unlistenAndDestroy();
                originPlayerMap.remove(entry.getKey());
                break;
            }
        }
    }

    public OriginPlayer getByPlayerUUID(UUID playerUUID) {
        if (originPlayerMap.containsKey(playerUUID)) {
            return originPlayerMap.get(playerUUID);
        }
        return null;
    }

    public OriginPlayer getByValue(OriginPlayer originPlayer) {
        for (Map.Entry<UUID, OriginPlayer> entry : originPlayerMap.entrySet()) {
            if (entry.getValue().equals(originPlayer)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean hasPlayerUUID(UUID playerUUID) {
        return originPlayerMap.containsKey(playerUUID);
    }

    public boolean hasValue(OriginPlayer originPlayer) {
        return originPlayerMap.containsValue(originPlayer);
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}

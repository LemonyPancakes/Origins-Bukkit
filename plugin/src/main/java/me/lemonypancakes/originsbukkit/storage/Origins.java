package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;

import java.util.HashMap;
import java.util.Map;

public class Origins {

    private final StorageHandler storageHandler;

    public Origins(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    private final Map<Identifier, Origin> originMap = new HashMap<>();

    public void add(Origin origin) {
        if (!originMap.containsKey(origin.getIdentifier())) {
            originMap.put(origin.getIdentifier(), origin);
        }
    }

    public void add(Origin origin, boolean override) {
        if (originMap.containsKey(origin.getIdentifier())) {
            if (override) {
                originMap.replace(origin.getIdentifier(), origin);
            }
        } else {
            add(origin);
        }
    }

    public void removeByIdentifier(Identifier identifier) {
        originMap.remove(identifier);
    }

    public void removeByValue(Origin origin) {
        for (Map.Entry<Identifier, Origin> entry : originMap.entrySet()) {
            if (entry.getValue().equals(origin)) {
                originMap.remove(entry.getKey());
                break;
            }
        }
    }

    public Origin getByIdentifier(Identifier identifier) {
        if (originMap.containsKey(identifier)) {
            return originMap.get(identifier);
        }
        return null;
    }

    public Origin getByValue(Origin origin) {
        for (Map.Entry<Identifier, Origin> entry : originMap.entrySet()) {
            if (entry.getValue().equals(origin)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean hasIdentifier(Identifier identifier) {
        return originMap.containsKey(identifier);
    }

    public boolean hasValue(Origin origin) {
        return originMap.containsValue(origin);
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}

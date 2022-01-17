package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;

import java.util.HashMap;
import java.util.Map;

public class Powers {

    private final StorageHandler storageHandler;

    public Powers(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    private final Map<Identifier, Power> powerMap = new HashMap<>();

    public void add(Power power) {
        if (!powerMap.containsKey(power.getIdentifier())) {
            powerMap.put(power.getIdentifier(), power);
        }
    }

    public void add(Power power, boolean override) {
        if (powerMap.containsKey(power.getIdentifier())) {
            if (override) {
                powerMap.replace(power.getIdentifier(), power);
            }
        } else {
            add(power);
        }
    }

    public void removeByIdentifier(Identifier identifier) {
        powerMap.remove(identifier);
    }

    public void removeByValue(Power power) {
        for (Map.Entry<Identifier, Power> entry : powerMap.entrySet()) {
            if (entry.getValue().equals(power)) {
                powerMap.remove(entry.getKey());
                break;
            }
        }
    }

    public Power getByIdentifier(Identifier identifier) {
        if (powerMap.containsKey(identifier)) {
            return powerMap.get(identifier);
        }
        return null;
    }

    public Power getByValue(Power power) {
        for (Map.Entry<Identifier, Power> entry : powerMap.entrySet()) {
            if (entry.getValue().equals(power)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean hasIdentifier(Identifier identifier) {
        return powerMap.containsKey(identifier);
    }

    public boolean hasValue(Power power) {
        return powerMap.containsValue(power);
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}

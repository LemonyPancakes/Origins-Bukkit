package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Conditions {

    private final StorageHandler storageHandler;

    public Conditions(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    private final Map<Identifier, Condition<?>> conditionMap = new HashMap<>();

    public <T> void add(Condition<T> condition) {
        if (!conditionMap.containsKey(condition.getIdentifier())) {
            conditionMap.put(condition.getIdentifier(), condition);
        }
    }

    public <T> void add(Condition<T> condition, boolean override) {
        if (conditionMap.containsKey(condition.getIdentifier())) {
            if (override) {
                conditionMap.replace(condition.getIdentifier(), condition);
            }
        } else {
            add(condition);
        }
    }

    public void removeByIdentifier(Identifier identifier) {
        conditionMap.remove(identifier);
    }

    public <T> void removeByValue(Condition<T> condition) {
        for (Map.Entry<Identifier, Condition<?>> entry : conditionMap.entrySet()) {
            if (entry.getValue().equals(condition)) {
                conditionMap.remove(entry.getKey());
                break;
            }
        }
    }

    public <T> Condition<T> getByIdentifier(Identifier identifier) {
        if (conditionMap.containsKey(identifier)) {
            return (Condition<T>) conditionMap.get(identifier);
        }
        return null;
    }

    public <T> Condition<T> getByValue(Condition<T> condition) {
        for (Map.Entry<Identifier, Condition<?>> entry : conditionMap.entrySet()) {
            if (entry.getValue().equals(condition)) {
                return (Condition<T>) entry.getValue();
            }
        }
        return null;
    }

    public boolean hasIdentifier(Identifier identifier) {
        return conditionMap.containsKey(identifier);
    }

    public <T> boolean hasValue(Condition<T> condition) {
        return conditionMap.containsValue(condition);
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}

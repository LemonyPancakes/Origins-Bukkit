package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Actions {

    private final StorageHandler storageHandler;

    public Actions(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    private final Map<Identifier, Action<?>> actionMap = new HashMap<>();

    public <T> void add(Action<T> action) {
        if (!actionMap.containsKey(action.getIdentifier())) {
            actionMap.put(action.getIdentifier(), action);
        }
    }

    public <T> void add(Action<T> action, boolean override) {
        if (actionMap.containsKey(action.getIdentifier())) {
            if (override) {
                actionMap.replace(action.getIdentifier(), action);
            }
        } else {
            add(action);
        }
    }

    public void removeByIdentifier(Identifier identifier) {
        actionMap.remove(identifier);
    }

    public <T> void removeByValue(Action<T> action) {
        for (Map.Entry<Identifier, Action<?>> entry : actionMap.entrySet()) {
            if (entry.getValue().equals(action)) {
                actionMap.remove(entry.getKey());
                break;
            }
        }
    }

    public <T> Action<T> getByIdentifier(Identifier identifier) {
        if (actionMap.containsKey(identifier)) {
            return (Action<T>) actionMap.get(identifier);
        }
        return null;
    }

    public <T> Action<T> getByValue(Action<T> action) {
        for (Map.Entry<Identifier, Action<?>> entry : actionMap.entrySet()) {
            if (entry.getValue().equals(action)) {
                return (Action<T>) entry.getValue();
            }
        }
        return null;
    }

    public boolean hasIdentifier(Identifier identifier) {
        return actionMap.containsKey(identifier);
    }

    public <T> boolean hasValue(Action<T> action) {
        return actionMap.containsValue(action);
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}

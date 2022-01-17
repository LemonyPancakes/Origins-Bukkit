package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Tag;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Tags {

    private final StorageHandler storageHandler;

    public Tags(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    private final Map<Identifier, Tag<?>> tagMap = new HashMap<>();

    public <T> void add(Tag<T> tag) {
        if (!tagMap.containsKey(tag.getIdentifier())) {
            tagMap.put(tag.getIdentifier(), tag);
        }
    }

    public <T> void add(Tag<T> tag, boolean override) {
        if (tagMap.containsKey(tag.getIdentifier())) {
            if (override) {
                tagMap.replace(tag.getIdentifier(), tag);
            }
        } else {
            add(tag);
        }
    }

    public void removeByIdentifier(Identifier identifier) {
        tagMap.remove(identifier);
    }

    public <T> void removeByValue(Tag<T> tag) {
        for (Map.Entry<Identifier, Tag<?>> entry : tagMap.entrySet()) {
            if (entry.getValue().equals(tag)) {
                tagMap.remove(entry.getKey());
                break;
            }
        }
    }

    public <T> Tag<T> getByIdentifier(Identifier identifier) {
        if (tagMap.containsKey(identifier)) {
            return (Tag<T>) tagMap.get(identifier);
        }
        return null;
    }

    public <T> Tag<T> getByValue(Tag<T> tag) {
        for (Map.Entry<Identifier, Tag<?>> entry : tagMap.entrySet()) {
            if (entry.getValue().equals(tag)) {
                return (Tag<T>) entry.getValue();
            }
        }
        return null;
    }

    public boolean hasIdentifier(Identifier identifier) {
        return tagMap.containsKey(identifier);
    }

    public <T> boolean hasValue(Tag<T> tag) {
        return tagMap.containsValue(tag);
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}

package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class BukkitPersistentDataUtils {

    public static <T, Z> Z getPersistentData(PersistentDataHolder persistentDataHolder,
                                             String string,
                                             PersistentDataType<T, Z> dataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(string);

        if (namespacedKey != null) {
            if (dataContainer.has(namespacedKey, dataType)) {
                return dataContainer.get(namespacedKey, dataType);
            }
        }
        return null;
    }

    public static <T, Z> void setPersistentData(PersistentDataHolder persistentDataHolder,
                                                String string,
                                                PersistentDataType<T, Z> dataType,
                                                Z z) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(string);

        if (namespacedKey != null) {
            dataContainer.set(namespacedKey, dataType, z);
        }
    }

    public static <T, Z> Z getPersistentData(PersistentDataHolder persistentDataHolder,
                                             NamespacedKey namespacedKey,
                                             PersistentDataType<T, Z> dataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();

        if (namespacedKey != null) {
            if (dataContainer.has(namespacedKey, dataType)) {
                return dataContainer.get(namespacedKey, dataType);
            }
        }
        return null;
    }

    public static <T, Z> void setPersistentData(PersistentDataHolder persistentDataHolder,
                                                NamespacedKey namespacedKey,
                                                PersistentDataType<T, Z> dataType,
                                                Z z) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();

        if (namespacedKey != null) {
            dataContainer.set(namespacedKey, dataType, z);
        }
    }

    public static <T, Z> Z getPersistentData(PersistentDataHolder persistentDataHolder,
                                             Identifier identifier,
                                             PersistentDataType<T, Z> dataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(identifier.getIdentifier());

        if (namespacedKey != null) {
            if (dataContainer.has(namespacedKey, dataType)) {
                return dataContainer.get(namespacedKey, dataType);
            }
        }
        return null;
    }

    public static <T, Z> void setPersistentData(PersistentDataHolder persistentDataHolder,
                                                Identifier identifier,
                                                PersistentDataType<T, Z> dataType,
                                                Z z) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(identifier.getIdentifier());

        if (namespacedKey != null) {
            dataContainer.set(namespacedKey, dataType, z);
        }
    }
}

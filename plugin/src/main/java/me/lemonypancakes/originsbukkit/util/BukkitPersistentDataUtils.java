package me.lemonypancakes.originsbukkit.util;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public final class BukkitPersistentDataUtils {

    private BukkitPersistentDataUtils() {}

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

    public static void removePersistentData(PersistentDataHolder persistentDataHolder,
                                            String string) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(string);

        if (namespacedKey != null) {
            dataContainer.remove(namespacedKey);
        }
    }

    public static <T, Z> boolean hasPersistentData(PersistentDataHolder persistentDataHolder,
                                            String string,
                                            PersistentDataType<T, Z> persistentDataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(string);

        if (namespacedKey != null) {
            return dataContainer.has(namespacedKey, persistentDataType);
        }
        return false;
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

    public static void removePersistentData(PersistentDataHolder persistentDataHolder,
                                            NamespacedKey namespacedKey) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();

        if (namespacedKey != null) {
            dataContainer.remove(namespacedKey);
        }
    }

    public static <T, Z> boolean hasPersistentData(PersistentDataHolder persistentDataHolder,
                                                   NamespacedKey namespacedKey,
                                                   PersistentDataType<T, Z> persistentDataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();

        if (namespacedKey != null) {
            return dataContainer.has(namespacedKey, persistentDataType);
        }
        return false;
    }

    public static <T, Z> Z getPersistentData(PersistentDataHolder persistentDataHolder,
                                             Identifier identifier,
                                             PersistentDataType<T, Z> dataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(identifier.toString());

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
        NamespacedKey namespacedKey = NamespacedKey.fromString(identifier.toString());

        if (namespacedKey != null) {
            dataContainer.set(namespacedKey, dataType, z);
        }
    }

    public static void removePersistentData(PersistentDataHolder persistentDataHolder,
                                            Identifier identifier) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(identifier.toString());

        if (namespacedKey != null) {
            dataContainer.remove(namespacedKey);
        }
    }

    public static <T, Z> boolean hasPersistentData(PersistentDataHolder persistentDataHolder,
                                                   Identifier identifier,
                                                   PersistentDataType<T, Z> persistentDataType) {
        PersistentDataContainer dataContainer = persistentDataHolder.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(identifier.toString());

        if (namespacedKey != null) {
            return dataContainer.has(namespacedKey, persistentDataType);
        }
        return false;
    }
}

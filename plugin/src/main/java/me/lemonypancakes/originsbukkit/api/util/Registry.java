package me.lemonypancakes.originsbukkit.api.util;

import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.util.Storage;

public final class Registry {

    public static void register(Origin origin) {
        Storage.addDataToStorage(origin);
    }

    public static void register(Power power) {
        Storage.addDataToStorage(power);
    }

    public static <T> void register(Action<T> action) {
        Storage.addDataToStorage(action);
    }

    public static <T> void register(Condition<T> condition) {
        Storage.addDataToStorage(condition);
    }

    public static <T> void register(Tag<T> tag) {
        Storage.addDataToStorage(tag);
    }

    public static void unregister(Origin origin) {
        Storage.removeDataFromStorage(origin);
    }

    public static void unregister(Power power) {
        Storage.removeDataFromStorage(power);
    }

    public static <T> void unregister(Action<T> action) {
        Storage.removeDataFromStorage(action);
    }

    public static <T> void unregister(Condition<T> condition) {
        Storage.removeDataFromStorage(condition);
    }

    public static <T> void unregister(Tag<T> tag) {
        Storage.removeDataFromStorage(tag);
    }
}

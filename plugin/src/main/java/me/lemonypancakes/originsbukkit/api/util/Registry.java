package me.lemonypancakes.originsbukkit.api.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.storage.Misc;

public final class Registry {

    public static void register(Origin origin) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOrigins()
                .add(origin);
        Misc.GUIS.add(origin.getInventoryGUI());
        Misc.ORIGINS_AS_STRING.add(origin.getIdentifier().getIdentifier());
    }

    public static void register(Power power) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPowers()
                .add(power);
    }

    public static <T> void register(Action<T> action) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getActions()
                .add(action);
    }

    public static <T> void register(Condition<T> condition) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getConditions()
                .add(condition);
    }

    public static <T> void register(Tag<T> tag) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getTags()
                .add(tag);
    }

    public static void unregister(Origin origin) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOrigins()
                .removeByIdentifier(
                        origin.getIdentifier()
                );
    }

    public static void unregister(Power power) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getPowers()
                .removeByIdentifier(
                        power.getIdentifier()
                );
    }

    public static <T> void unregister(Action<T> action) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getActions()
                .removeByIdentifier(
                        action.getIdentifier()
                );
    }

    public static <T> void unregister(Condition<T> condition) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getConditions()
                .removeByIdentifier(
                        condition.getIdentifier()
                );
    }

    public static <T> void unregister(Tag<T> tag) {
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getTags()
                .removeByIdentifier(
                        tag.getIdentifier()
                );
    }
}

package me.lemonypancakes.originsbukkit.api.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.storage.Misc;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Registry {

    private static final List<Origin> ORIGINS = new ArrayList<>();

    public static void register(Origin origin) {
        ORIGINS.add(origin);
        OriginsBukkit.getPlugin()
                .getStorageHandler()
                .getOrigins()
                .add(origin);
        ORIGINS.sort(Comparator.comparing(Origin::getImpact));
        List<Inventory> inventoryList = new ArrayList<>();

        for (Origin origin1 : ORIGINS) {
            inventoryList.add(origin1.getInventoryGUI());
        }
        Misc.GUIS.clear();
        Misc.GUIS.addAll(inventoryList);
        if (!origin.getIdentifier().getIdentifier().equals("origins-bukkit:dummy_origin")) {
            Misc.ORIGINS_AS_STRING.add(origin.getIdentifier().getIdentifier());
        }
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

package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class Storage {

    private static final Logger LOGGER = OriginsBukkit.getPlugin().getLogger();

    private static final Map<Identifier, Origin> ORIGINS_DATA = new HashMap<>();
    private static final Map<Identifier, Power> POWERS_DATA = new HashMap<>();
    private static final Map<Identifier, Action<?>> ACTIONS_DATA = new HashMap<>();
    private static final Map<Identifier, Condition<?>> CONDITIONS_DATA = new HashMap<>();
    private static final Map<Player, Origin> PLAYER_ORIGINS_DATA = new HashMap<>();
    private static final Map<Identifier, Origin> CACHED_ORIGINS_DATA = new HashMap<>();

    public static Map<Identifier, Origin> getOriginsData() {
        return ORIGINS_DATA;
    }

    public static Map<Identifier, Power> getPowersData() {
        return POWERS_DATA;
    }

    public static Map<Identifier, Action<?>> getActionsData() {
        return ACTIONS_DATA;
    }

    public static Map<Identifier, Condition<?>> getConditionsData() {
        return CONDITIONS_DATA;
    }

    public static Map<Player, Origin> getPlayerOriginsData() {
        return PLAYER_ORIGINS_DATA;
    }

    public static Map<Identifier, Origin> getCachedOriginsData() {
        return CACHED_ORIGINS_DATA;
    }

    public static void addDataToStorage(Origin origin) {
        if (!Catcher.catchDuplicateFromMap(
                ORIGINS_DATA, origin.getIdentifier())) {
            ORIGINS_DATA.put(origin.getIdentifier(), origin);
        } else {
            LOGGER.warning("log");
        }
    }

    public static void addDataToStorage(Power power) {
        if (!Catcher.catchDuplicateFromMap(
                POWERS_DATA, power.getIdentifier())) {
            POWERS_DATA.put(power.getIdentifier(), power);
        } else {
            LOGGER.warning("log");
        }
    }

    public static <T> void addDataToStorage(Action<T> action) {
        if (!Catcher.catchDuplicateFromMap(
                ACTIONS_DATA, action.getIdentifier())) {
            ACTIONS_DATA.put(action.getIdentifier(), action);
        } else {
            LOGGER.warning("log");
        }
    }

    public static <T> void addDataToStorage(Condition<T> condition) {
        if (!Catcher.catchDuplicateFromMap(
                CONDITIONS_DATA, condition.getIdentifier())) {
            CONDITIONS_DATA.put(condition.getIdentifier(), condition);
        } else {
            LOGGER.warning("log");
        }
    }

    public static void addDataToStorage(Player player, Origin origin) {
        if (!Catcher.catchDuplicateFromMap(
                PLAYER_ORIGINS_DATA, player)) {
            PLAYER_ORIGINS_DATA.put(player, origin);
        } else {
            for (Map.Entry<Player, Origin> entry : PLAYER_ORIGINS_DATA.entrySet()) {
                Player key = entry.getKey();

                String duplicate
                        = String.valueOf(
                        Catcher.getDuplicateFromMap(
                                PLAYER_ORIGINS_DATA,
                                player
                        )
                );

                if (duplicate != null) {
                    if (key.toString().equals(duplicate)) {
                        PLAYER_ORIGINS_DATA.remove(key);
                        PLAYER_ORIGINS_DATA.put(player, origin);
                    }
                }
            }
        }
    }

    public static void removeDataFromStorage(Origin origin) {
        if (ORIGINS_DATA.containsKey(origin.getIdentifier())) {
            ORIGINS_DATA.remove(origin.getIdentifier());
        } else {
            LOGGER.warning("log");
        }
    }

    public static void removeDataFromStorage(Power power) {
        if (POWERS_DATA.containsKey(power.getIdentifier())) {
            POWERS_DATA.remove(power.getIdentifier());
        } else {
            LOGGER.warning("log");
        }
    }

    public static <T> void removeDataFromStorage(Action<T> action) {
        if (ACTIONS_DATA.containsKey(action.getIdentifier())) {
            ACTIONS_DATA.remove(action.getIdentifier());
        } else {
            LOGGER.warning("log");
        }
    }

    public static <T> void removeDataFromStorage(Condition<T> condition) {
        if (CONDITIONS_DATA.containsKey(condition.getIdentifier())) {
            CONDITIONS_DATA.remove(condition.getIdentifier());
        } else {
            LOGGER.warning("log");
        }
    }

    public static void removeDataFromStorage(Player player) {
        if (PLAYER_ORIGINS_DATA.containsKey(player)) {
            PLAYER_ORIGINS_DATA.remove(player);
        } else {
            LOGGER.warning("log");
        }
    }
}

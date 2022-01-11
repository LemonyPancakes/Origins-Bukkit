package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class Storage {

    private static final Map<Identifier, Origin> ORIGINS_DATA = new HashMap<>();
    private static final Map<Identifier, Power> POWERS_DATA = new HashMap<>();
    private static final Map<Identifier, Action<?>> ACTIONS_DATA = new HashMap<>();
    private static final Map<Identifier, Condition<?>> CONDITIONS_DATA = new HashMap<>();
    private static final Map<Identifier, Tag<?>> TAGS_DATA = new HashMap<>();
    private static final Map<Player, Origin> PLAYER_ORIGINS_DATA = new HashMap<>();
    private static final Map<Player, Scheduler> PLAYER_SCHEDULER_DATA = new HashMap<>();
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

    public static Map<Identifier, Tag<?>> getTagsData() {
        return TAGS_DATA;
    }

    public static Map<Player, Origin> getPlayerOriginsData() {
        return PLAYER_ORIGINS_DATA;
    }

    public static Map<Player, Scheduler> getPlayerSchedulerData() {
        return PLAYER_SCHEDULER_DATA;
    }

    public static Map<Identifier, Origin> getCachedOriginsData() {
        return CACHED_ORIGINS_DATA;
    }

    public static void addDataToStorage(Origin origin) {
        if (!Catcher.catchDuplicateFromMap(
                ORIGINS_DATA, origin.getIdentifier())) {
            ORIGINS_DATA.put(origin.getIdentifier(), origin);
        } else {
            Message.sendConsoleMessage(
                    "&6[Origins-Bukkit] Warning. Tried to register duplicate origin &e\""
                            + origin.getIdentifier().getIdentifier()
                            + "\""
            );
        }
    }

    public static void addDataToStorage(Power power) {
        if (!Catcher.catchDuplicateFromMap(
                POWERS_DATA, power.getIdentifier())) {
            POWERS_DATA.put(power.getIdentifier(), power);
        } else {
            Message.sendConsoleMessage(
                    "&6[Origins-Bukkit] Warning. Tried to register duplicate power &e\""
                            + power.getIdentifier().getIdentifier()
                            + "\""
            );
        }
    }

    public static <T> void addDataToStorage(Action<T> action) {
        if (!Catcher.catchDuplicateFromMap(
                ACTIONS_DATA, action.getIdentifier())) {
            ACTIONS_DATA.put(action.getIdentifier(), action);
        } else {
            Message.sendConsoleMessage(
                    "&6[Origins-Bukkit] Warning. Tried to register duplicate action &e\""
                            + action.getIdentifier().getIdentifier()
                            + "\""
            );
        }
    }

    public static <T> void addDataToStorage(Condition<T> condition) {
        if (!Catcher.catchDuplicateFromMap(
                CONDITIONS_DATA, condition.getIdentifier())) {
            CONDITIONS_DATA.put(condition.getIdentifier(), condition);
        } else {
            Message.sendConsoleMessage(
                    "&6[Origins-Bukkit] Warning. Tried to register duplicate condition &e\""
                            + condition.getIdentifier().getIdentifier()
                            + "\""
            );
        }
    }

    public static <T> void addDataToStorage(Tag<T> tag) {
        if (!Catcher.catchDuplicateFromMap(
                TAGS_DATA, tag.getIdentifier())) {
            TAGS_DATA.put(tag.getIdentifier(), tag);
        } else {
            Message.sendConsoleMessage(
                    "&6[Origins-Bukkit] Warning. Tried to register duplicate listener &e\""
                            + tag.getIdentifier().getIdentifier()
                            + "\""
            );
        }
    }

    public static void addDataToStorage(Player player, Scheduler scheduler) {
        if (!Catcher.catchDuplicateFromMap(
                PLAYER_SCHEDULER_DATA, scheduler)) {
            PLAYER_SCHEDULER_DATA.put(player, scheduler);
        } else {
            for (Map.Entry<Player, Scheduler> entry : PLAYER_SCHEDULER_DATA.entrySet()) {
                Player key = entry.getKey();

                String duplicate
                        = String.valueOf(
                        Catcher.getDuplicateFromMap(
                                PLAYER_SCHEDULER_DATA,
                                player
                        )
                );

                if (duplicate != null) {
                    if (key.toString().equals(duplicate)) {
                        PLAYER_SCHEDULER_DATA.get(key).getBukkitTask().cancel();
                        PLAYER_SCHEDULER_DATA.remove(key);
                        PLAYER_SCHEDULER_DATA.put(player, scheduler);
                    }
                }
            }
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
        ORIGINS_DATA.forEach((key, value) -> {
            if (Catcher.catchDuplicate(key, origin.getIdentifier())) {
                ORIGINS_DATA.remove(key);
            }
        });
    }

    public static void removeDataFromStorage(Power power) {
        POWERS_DATA.forEach((key, value) -> {
            if (Catcher.catchDuplicate(key, power.getIdentifier())) {
                POWERS_DATA.remove(key);
            }
        });
    }

    public static <T> void removeDataFromStorage(Action<T> action) {
        ACTIONS_DATA.forEach((key, value) -> {
            if (Catcher.catchDuplicate(key, action.getIdentifier())) {
                ACTIONS_DATA.remove(key);
            }
        });
    }

    public static <T> void removeDataFromStorage(Condition<T> condition) {
        CONDITIONS_DATA.forEach((key, value) -> {
            if (Catcher.catchDuplicate(key, condition.getIdentifier())) {
                CONDITIONS_DATA.remove(key);
            }
        });
    }

    public static <T> void removeDataFromStorage(Tag<T> tag) {
        TAGS_DATA.forEach((key, value) -> {
            if (Catcher.catchDuplicate(key, tag.getIdentifier())) {
                TAGS_DATA.remove(key);
            }
        });
    }

    public static void removeDataFromStorage(Player player) {
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
                }
            }
        }
    }

    public static void removeSchedulerDataFromStorage(Player player) {
        for (Map.Entry<Player, Scheduler> entry : PLAYER_SCHEDULER_DATA.entrySet()) {
            Player key = entry.getKey();

            String duplicate
                    = String.valueOf(
                    Catcher.getDuplicateFromMap(
                            PLAYER_SCHEDULER_DATA,
                            player
                    )
            );

            if (duplicate != null) {
                if (key.toString().equals(duplicate)) {
                    PLAYER_SCHEDULER_DATA.get(key).getBukkitTask().cancel();
                    PLAYER_SCHEDULER_DATA.remove(key);
                }
            }
        }
    }

    public static Tag<?> get(Identifier identifier) {
        return Catcher.getDuplicateFromMap(TAGS_DATA, identifier);
    }
}

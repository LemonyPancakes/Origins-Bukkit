package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class OriginPlayerContainer implements OriginPlayer {

    private final UUID playerUUID;
    private final Schedulers schedulers;

    private Origin origin;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();

    public OriginPlayerContainer(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.schedulers = new Schedulers();
        Player player = getPlayer();
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(OriginsBukkit.getPlugin(), "origin");
        String identifierString = dataContainer.get(namespacedKey, PersistentDataType.STRING);

        if (identifierString != null) {
            Identifier identifier = new IdentifierContainer(
                    identifierString.split(":")[0],
                    identifierString.split(":")[1]
            );

            Origin origin = PLUGIN.getStorageHandler().getOrigins().getByIdentifier(identifier);

            if (origin != null) {
                setOrigin(origin);
            }
            Temp temp = new TempContainer();

            temp.setPlayer(player);
            if (getOrigin() != null) {
                getOrigin().getPowers().forEach(
                        power -> power.invoke(temp)
                );
            }
        }
    }


    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Player getPlayer() {
        if (Bukkit.getPlayer(playerUUID) != null) {
            return Bukkit.getPlayer(playerUUID);
        }
        return (Player) Bukkit.getOfflinePlayer(playerUUID);
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        if (this.origin != null) {
            getSchedulers().destroy();
        }
        this.origin = origin;
    }

    public Schedulers getSchedulers() {
        return schedulers;
    }

    public static class Schedulers {

        private final Map<Identifier, Scheduler> schedulerMap = new HashMap<>();

        public void add(Scheduler scheduler) {
            if (schedulerMap.containsKey(scheduler.getIdentifier())) {
                schedulerMap.get(scheduler.getIdentifier()).getBukkitTask().cancel();
                schedulerMap.remove(scheduler.getIdentifier());
            }
            schedulerMap.put(scheduler.getIdentifier(), scheduler);
        }

        public void removeByIdentifier(Identifier identifier) {
            if (schedulerMap.containsKey(identifier)) {
                schedulerMap.get(identifier).getBukkitTask().cancel();
                schedulerMap.remove(identifier);
            }
        }

        public void removeByValue(Scheduler scheduler) {
            for (Map.Entry<Identifier, Scheduler> entry : schedulerMap.entrySet()) {
                if (entry.getValue().equals(scheduler)) {
                    schedulerMap.get(entry.getKey()).getBukkitTask().cancel();
                    schedulerMap.remove(entry.getKey());
                    break;
                }
            }
        }

        public Scheduler getByIdentifier(Identifier identifier) {
            if (schedulerMap.containsKey(identifier)) {
                return schedulerMap.get(identifier);
            }
            return null;
        }

        public Scheduler getByValue(Scheduler scheduler) {
            for (Map.Entry<Identifier, Scheduler> entry : schedulerMap.entrySet()) {
                if (entry.getValue().equals(scheduler)) {
                    return entry.getValue();
                }
            }
            return null;
        }

        public boolean hasIdentifier(Identifier identifier) {
            return schedulerMap.containsKey(identifier);
        }

        public boolean hasValue(Scheduler scheduler) {
            return schedulerMap.containsValue(scheduler);
        }

        public void destroy() {
            for (Map.Entry<Identifier, Scheduler> entry : schedulerMap.entrySet()) {
                removeByIdentifier(entry.getKey());
            }
        }
    }
}

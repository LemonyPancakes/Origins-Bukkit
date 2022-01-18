package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.storage.Origins;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OriginPlayerContainer implements OriginPlayer {

    private final UUID playerUUID;
    private final Schedulers schedulers;

    private Origin origin;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Origins ORIGINS = PLUGIN.getStorageHandler().getOrigins();

    public OriginPlayerContainer(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.schedulers = new Schedulers();
        PersistentDataContainer dataContainer = getPlayer().getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(OriginsBukkit.getPlugin(), "origin");

        if (dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            String identifierString = dataContainer.get(
                    namespacedKey,
                    PersistentDataType.STRING
            );

            if (identifierString != null) {
                Identifier identifier = new IdentifierContainer(
                        identifierString.split(":")[0],
                        identifierString.split(":")[1]
                );

                if (ORIGINS.hasIdentifier(identifier)) {
                    Origin origin = ORIGINS.getByIdentifier(identifier);

                    if (origin != null) {
                        setOrigin(origin);
                    }
                } else {
                    dataContainer.remove(namespacedKey);
                    Misc.VIEWERS.put(getPlayerUUID(), 0);
                    getPlayer().openInventory(Misc.GUIS.get(0));
                    ChatUtils.sendPlayerMessage(
                            getPlayer(),
                            "&cYour origin (&e\"" + identifierString + "\"&c) doesn't exist so we pruned your player data."
                    );
                }
            }
        } else {
            Misc.VIEWERS.put(getPlayerUUID(), 0);
            getPlayer().openInventory(Misc.GUIS.get(0));
        }
    }

    @Override
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    @Override
    public Player getPlayer() {
        if (Bukkit.getPlayer(playerUUID) != null) {
            return Bukkit.getPlayer(playerUUID);
        }
        return (Player) Bukkit.getOfflinePlayer(playerUUID);
    }

    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(Origin origin) {
        if (this.origin != null) {
            unlistenAndDestroy();
        }
        this.origin = origin;
        PersistentDataContainer dataContainer = getPlayer().getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(OriginsBukkit.getPlugin(), "origin");

        if (origin != null) {
            dataContainer.set(
                    namespacedKey,
                    PersistentDataType.STRING,
                    origin.getIdentifier().getIdentifier()
            );
            if (origin.getPowers() != null) {
                Temp temp = new TempContainer();

                temp.setPlayer(getPlayer());
                origin.getPowers().forEach(
                        power -> power.invoke(temp)
                );
            }
            if (Misc.VIEWERS.containsKey(getPlayerUUID())) {
                Misc.VIEWERS.remove(getPlayerUUID());
                getPlayer().closeInventory();
            }
        } else {
            if (dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
                dataContainer.remove(namespacedKey);
            }
            Misc.VIEWERS.put(getPlayerUUID(), 0);
            getPlayer().openInventory(Misc.GUIS.get(0));
        }
    }

    @Override
    public Schedulers getSchedulers() {
        return schedulers;
    }

    @Override
    public void unlisten() {
        if (getOrigin() != null) {
            if (getOrigin().getPowers() != null) {
                getOrigin().getPowers().forEach(power -> {
                    if (power instanceof ListenerPowerContainer) {
                        ListenerPowerContainer listenerPowerContainer = (ListenerPowerContainer) power;

                        listenerPowerContainer.unlisten(getPlayer());
                    }
                });
            }
        }
    }

    @Override
    public void unlistenAndDestroy() {
        unlisten();
        getSchedulers().destroy();
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

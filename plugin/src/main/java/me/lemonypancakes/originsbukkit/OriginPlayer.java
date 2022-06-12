package me.lemonypancakes.originsbukkit;

import me.lemonypancakes.originsbukkit.data.CraftOriginPlayer;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public interface OriginPlayer extends OriginsBukkitPluginHolder {

    UUID getUUID();

    Player getPlayer();

    Origin getOrigin();

    void setOrigin(Origin origin);

    Schedulers getSchedulers();

    void unlisten();

    void unlistenAndDestroy();

    final class Schedulers {

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CraftOriginPlayer.Schedulers)) return false;
            CraftOriginPlayer.Schedulers that = (CraftOriginPlayer.Schedulers) o;
            return Objects.equals(schedulerMap, that.schedulerMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(schedulerMap);
        }

        @Override
        public String toString() {
            return "Schedulers{" +
                    "schedulerMap=" + schedulerMap +
                    '}';
        }
    }
}

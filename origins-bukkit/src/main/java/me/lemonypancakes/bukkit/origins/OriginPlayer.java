/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins;

import me.lemonypancakes.bukkit.origins.data.CraftOriginPlayer;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface OriginPlayer extends OriginsBukkitPluginHolder, Metadatable {

    Player getPlayer();

    Origin getOrigin();

    void setOrigin(Origin origin);

    Schedulers getSchedulers();

    void unlisten();

    void unlistenAndDestroy();

    void refresh();

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

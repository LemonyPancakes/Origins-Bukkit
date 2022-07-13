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
package me.lemonypancakes.bukkit.origins.entity.player;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Metadatable;
import me.lemonypancakes.bukkit.origins.data.serialization.StorageSerializable;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.event.entity.player.PlayerOriginSetEvent;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.scheduler.Scheduler;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PowerSource;
import org.bukkit.entity.Player;

import java.util.*;

public interface OriginPlayer extends OriginsBukkitPluginHolder, Metadatable, StorageSerializable {

    Player getPlayer();

    void setPlayer(Player player);

    Origin getOrigin(OriginLayer originLayer);

    Map<OriginLayer, Origin> getOrigins();

    PlayerOriginSetEvent setOrigin(OriginLayer originLayer, Origin newOrigin);

    Set<PowerSource> getPowerSources(Power power);

    Map<Power, Set<PowerSource>> getPowers();

    void addPower(Power power, PowerSource powerSource);

    void removePower(Power power, PowerSource powerSource);

    void forceRemovePower(Power power);

    Schedulers getSchedulers();

    void refresh();

    boolean hasOriginBefore();

    static Map<String, Object> deserialize(Map<String, Object> map) {
        if (map != null) {
            Map<String, Object> data = new LinkedHashMap<>();
            Gson gson = new Gson();

            data.put("UUID", null);
            data.put("name", null);
            data.put("origin", null);
            data.put("power", null);
            data.put("metadata", null);
            data.put("hasOriginBefore", false);
            if (map.containsKey("UUID")) {
                data.put("UUID", UUID.fromString((String) map.get("UUID")));
            }
            data.put("name", map.getOrDefault("name", null));
            if (map.containsKey("origin")) {
                String string = (String) map.get("origin");

                if (string == null || string.isEmpty() || !string.startsWith("{") && !string.endsWith("}")) {
                    string = "{}";
                }
                JsonObject origin = gson.fromJson(string, JsonObject.class);
                Map<Identifier, Identifier> origins = new LinkedHashMap<>();

                if (origin != null) {
                    origin.entrySet().forEach(entry -> {
                        String key = entry.getKey();
                        String value = entry.getValue().getAsString();

                        if (key.contains(":") && value.contains(":")) {
                            origins.put(Identifier.fromString(key), Identifier.fromString(value));
                        }
                    });
                }
                data.put("origin", origins);
            }
            if (map.containsKey("power")) {
                String string = (String) map.get("power");

                if (string == null || string.isEmpty() || !string.startsWith("{") && !string.endsWith("}")) {
                    string = "{}";
                }
                JsonObject power = gson.fromJson(string, JsonObject.class);
                Map<Identifier, Set<Identifier>> powers = new HashMap<>();

                if (power != null) {
                    power.entrySet().forEach(entry -> {
                        String key = entry.getKey();
                        String[] value = gson.fromJson(entry.getValue(), String[].class);
                        Set<Identifier> set = new HashSet<>();

                        if (key.contains(":")) {
                            Arrays.stream(value).forEach(s -> {
                                if (s.contains(":")) {
                                    set.add(Identifier.fromString(s));
                                }
                            });
                            powers.put(Identifier.fromString(key), set);
                        }
                    });
                }
                data.put("power", powers);
            }
            if (map.containsKey("metadata")) {
                data.put("metadata", gson.fromJson((String) map.get("metadata"), JsonObject.class));
            }
            if (map.containsKey("hasOriginBefore")) {
                data.put("hasOriginBefore", map.get("hasOriginBefore"));
            }

            return data;
        }
        return null;
    }

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

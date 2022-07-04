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
package me.lemonypancakes.bukkit.origins.factory.power.modify;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CraftModifyPlayerSpawnPower extends CraftPower {

    private World dimension;
    private Biome biome;
    private StructureType structure;
    private String spawnStrategy;
    private String dimensionDistanceMultiplier;

    public CraftModifyPlayerSpawnPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("dimension")) {
            this.dimension = Bukkit.getWorld(jsonObject.get("dimension").getAsString());
        }
        if (jsonObject.has("biome")) {
            this.biome = Biome.valueOf(jsonObject.get("biome").getAsString());
        }
        if (jsonObject.has("structure")) {
            this.structure = StructureType.getStructureTypes().get(jsonObject.get("structure").getAsString());
        }
        if (jsonObject.has("spawn_strategy")) {
            this.spawnStrategy = jsonObject.get("spawn_strategy").getAsString();
        }
        if (jsonObject.has("dimension_distance_multiplier")) {
            this.dimensionDistanceMultiplier = jsonObject.get("dimension_distance_multiplier").getAsString();
        }
    }

    @Override
    protected void onMemberAdd(Player player) {
        if (!getPlugin().getStorage().hasOriginPlayerData(player)) {
            player.teleport(generateLocation());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (hasMember(player)) {
            if (player.getBedSpawnLocation() == null) {
                event.setRespawnLocation(generateLocation());
            }
        }
    }

    private Location generateLocation() {
        Location location = dimension.getSpawnLocation();
        Integer spawnRadius = dimension.getGameRuleValue(GameRule.SPAWN_RADIUS);

        if (spawnRadius != null) {
            for (int x = -spawnRadius; x < spawnRadius; x++) {
                for (int y = -spawnRadius; y < spawnRadius; y++) {
                    for (int z = -spawnRadius; z < spawnRadius; z++) {
                        Location newLocation = new Location(location.getWorld(), location.getX() + x + 0.5, location.getY() + y, location.getZ() + z + 0.5);

                        if (newLocation.getX() < 0) {
                            newLocation.subtract(1, 0, 0);
                        }
                        if (newLocation.getZ() < 0) {
                            newLocation.subtract(0, 0, 1);
                        }
                        if (isLocationSafe(newLocation) && isLocationBiome(newLocation)) {
                            return newLocation;
                        }
                    }
                }
            }
        }
        return location;
    }
    
    private boolean isLocationSafe(Location location) {
        Material head = new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()).getBlock().getType();
        Material body = location.getBlock().getType();
        Material ground = new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ()).getBlock().getType();

        return ground.isSolid() && body.isAir() && head.isAir();
    }
    
    private boolean isLocationBiome(Location location) {
        if (this.biome != null) {
            Chunk chunk = location.getChunk();
            ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot(false, true, false);

            return chunkSnapshot.getBiome(location.getBlockX() % 16, location.getBlockY(), location.getBlockZ() % 16) == biome;
        }
        return true;
    }
}

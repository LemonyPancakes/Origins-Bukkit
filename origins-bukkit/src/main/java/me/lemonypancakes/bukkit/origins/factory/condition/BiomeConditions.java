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
package me.lemonypancakes.bukkit.origins.factory.condition;

import com.google.gson.Gson;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Comparison;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.block.Biome;

public class BiomeConditions {

    public BiomeConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BIOME, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BIOME, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "category"), DataType.BIOME, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biome) -> {
            if (biome != null) {
                Biome category = null;

                if (jsonObject.has("category")) {
                    category = new Gson().fromJson(jsonObject.get("category"), Biome.class);
                }
                if (category != null) {
                    return biome.getEntity().getLocation().getBlock().getBiome() == category;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "high_humidity"), DataType.BIOME, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biome) -> biome.getEntity().getLocation().getBlock().getHumidity() > 3)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "precipitation"), DataType.BIOME, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biome) -> false)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "temperature"), DataType.BIOME, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, biome) -> Comparison.parseComparison(jsonObject).compare(biome.getEntity().getLocation().getBlock().getTemperature(), jsonObject))));
    }
}

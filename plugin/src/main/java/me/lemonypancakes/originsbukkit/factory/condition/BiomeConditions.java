package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.originsbukkit.util.Comparison;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.block.Biome;

public class BiomeConditions {

    public BiomeConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BIOME, new CraftAndCondition<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BIOME, new CraftOrCondition<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "category"), DataType.BIOME, new CraftCondition<>(plugin, null, (jsonObject, biome) -> {
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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "high_humidity"), DataType.BIOME, new CraftCondition<>(plugin, null, (jsonObject, biome) -> biome.getEntity().getLocation().getBlock().getHumidity() > 3)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "precipitation"), DataType.BIOME, new CraftCondition<>(plugin, null, (jsonObject, biome) -> false)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "temperature"), DataType.BIOME, new CraftCondition<>(plugin, null, (jsonObject, biome) -> Comparison.parseComparison(jsonObject).compare(biome.getEntity().getLocation().getBlock().getTemperature(), jsonObject))));
    }
}

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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.BIOME, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.BIOME, (plugin1, jsonObject) -> (dataType) -> () -> new CraftOrCondition<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "category"), DataType.BIOME, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biome) -> {
            if (biome != null) {
                Biome category = null;

                if (jsonObject1.has("category")) {
                    category = new Gson().fromJson(jsonObject1.get("category"), Biome.class);
                }
                if (category != null) {
                    return biome.getEntity().getLocation().getBlock().getBiome() == category;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "high_humidity"), DataType.BIOME, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biome) -> biome.getEntity().getLocation().getBlock().getHumidity() > 3)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "precipitation"), DataType.BIOME, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biome) -> false)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "temperature"), DataType.BIOME, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, biome) -> Comparison.parseComparison(jsonObject1).compare(biome.getEntity().getLocation().getBlock().getTemperature(), jsonObject1))));
    }
}

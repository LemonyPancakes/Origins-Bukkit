package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;

public class BlockConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/is_passable"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isPassable();
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/is_empty"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isEmpty();
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/is_indirectly_powered"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isBlockIndirectlyPowered();
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/is_powered"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isBlockPowered();
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/is_liquid"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isLiquid();
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/compare_block/type"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("block_type")) {
                                Material blockType
                                        = new Gson().fromJson(
                                                data.get(
                                                        "block_type"
                                                ),
                                        Material.class
                                );

                                if (blockType != null) {
                                    return location.getBlock().getType() == blockType;
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/compare_biome"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("biome")) {
                                Biome biome = new Gson().fromJson(
                                        data.get(
                                                "biome"
                                        ),
                                        Biome.class
                                );

                                if (biome != null) {
                                    return location.getBlock().getBiome() == biome;
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/compare_block_power"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("block_power")) {
                                int blockPower = data.get("block_power").getAsInt();

                                return location.getBlock().getBlockPower() == blockPower;
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/compare_humidity"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("humidity")) {
                                double humidity = data.get("humidity").getAsDouble();

                                return location.getBlock().getHumidity() == humidity;
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/compare_temperature"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("temperature")) {
                                double temperature = data.get("temperature").getAsDouble();

                                return location.getBlock().getTemperature() == temperature;
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/block/compare_light_level"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getBlock().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("light_level")) {
                                byte lightLevel = data.get("light_level").getAsByte();

                                return location.getBlock().getLightLevel() == lightLevel;
                            }
                            return false;
                        }
                )
        );
    }

    private static Location locationOffset(Location location, JsonObject offset) {
        if (offset != null) {
            return new Location(
                    location.getWorld(),
                    location.getX() + offset.get("x").getAsDouble(),
                    location.getY() + offset.get("y").getAsDouble(),
                    location.getZ() + offset.get("z").getAsDouble());
        }
        return location;
    }
}

package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;

public class BlockActions {

    public static void register() {
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/block/set_biome"
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
                                    location.getBlock().setBiome(biome);
                                }
                            }
                        }
                )
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/block/set_type"
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
                                    location.getBlock().setType(blockType);
                                }
                            }
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

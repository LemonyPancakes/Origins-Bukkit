package me.lemonypancakes.originsbukkit.factory.condition;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import me.lemonypancakes.originsbukkit.storage.Origins;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EntityConditions {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Origins ORIGINS = PLUGIN.getStorageHandler().getOrigins();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/entity/is_in_shade"
                        ), null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                Location location = entity.getLocation();

                                return (!(location.getBlockY() > entity.getWorld().getHighestBlockAt(location).getLocation().getBlockY()));
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare/origin"
                        ), null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    UUID playerUUID = player.getUniqueId();

                                    if (data.has("origin")) {
                                        String originString = data.get("origin").getAsString();

                                        if (originString.contains(":")) {
                                            if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                                                Origin origin = ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).getOrigin();

                                                if (origin != null) {
                                                    return originString.equals(origin.getIdentifier().getIdentifier());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return false;
                        })
        );
    }
}

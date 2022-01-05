package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public final class PlayerConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_online"
                        ), null,
                        (data, player) -> player.isOnline())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_in_water"
                        ), null,
                        (data, player) -> player.isInWater())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_player_time_relative"
                        ), null,
                        (data, player) -> player.isPlayerTimeRelative())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_flying"
                        ), null,
                        (data, player) -> player.isFlying())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_health_scaled"
                        ), null,
                        (data, player) -> player.isHealthScaled())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_sleeping_ignored"
                        ), null,
                        (data, player) -> player.isSleepingIgnored())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_sleeping"
                        ), null,
                        (data, player) -> player.isSleeping())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_banned"
                        ), null,
                        (data, player) -> player.isBanned())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_blocking"
                        ), null,
                        (data, player) -> player.isBlocking())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_climbing"
                        ), null,
                        (data, player) -> player.isClimbing())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_collidable"
                        ), null,
                        (data, player) -> player.isCollidable())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_conversing"
                        ), null,
                        (data, player) -> player.isConversing())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_custom_name_visible"
                        ), null,
                        (data, player) -> player.isCustomNameVisible())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_dead"
                        ), null,
                        (data, player) -> player.isDead())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_empty"
                        ), null,
                        (data, player) -> player.isEmpty())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_frozen"
                        ), null,
                        (data, player) -> player.isFrozen())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_gliding"
                        ), null,
                        (data, player) -> player.isGliding())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_glowing"
                        ), null,
                        (data, player) -> player.isGlowing())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_hand_raised"
                        ), null,
                        (data, player) -> player.isHandRaised())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_inside_vehicle"
                        ), null,
                        (data, player) -> player.isInsideVehicle())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_invisible"
                        ), null,
                        (data, player) -> player.isInvisible())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_invulnerable"
                        ), null,
                        (data, player) -> player.isInvulnerable())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_leashed"
                        ), null,
                        (data, player) -> player.isLeashed())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_op"
                        ), null,
                        (data, player) -> player.isOp())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_riptiding"
                        ), null,
                        (data, player) -> player.isRiptiding())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_silent"
                        ), null,
                        (data, player) -> player.isSilent())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_swimming"
                        ), null,
                        (data, player) -> player.isSwimming())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_visual_fire"
                        ), null,
                        (data, player) -> player.isVisualFire())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_whitelisted"
                        ), null,
                        (data, player) -> player.isWhitelisted())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_permission"
                        ), null,
                        (data, player) -> {
                            if (data.has("permission")) {
                                String permission = data.get("permission").getAsString();

                                if (permission != null) {
                                    return player.hasPermission(permission);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_cooldown"
                        ), null,
                        (data, player) -> {
                            if (data.has("material")) {
                                Material material
                                        = new Gson().fromJson(
                                                data.get(
                                                        "material"
                                                ),
                                        Material.class
                                );

                                if (material != null) {
                                    return player.hasCooldown(material);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_played_before"
                        ), null,
                        (data, player) -> player.hasPlayedBefore())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_discovered_recipe"
                        ), null,
                        (data, player) -> {
                            if (data.has("recipe")) {
                                NamespacedKey recipe
                                        = NamespacedKey.fromString(
                                                data.get(
                                                        "recipe"
                                                ).getAsString()
                                );

                                if (recipe != null) {
                                    return player.hasDiscoveredRecipe(recipe);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/"
                        ), null,
                        (data, player) -> player.hasGravity())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/"
                        ), null,
                        (data, player) -> {
                            if (data.has("type")) {
                                PotionEffectType type
                                        = PotionEffectType.getByName(
                                                data.get(
                                                        "type"
                                                ).getAsString()
                                );

                                if (type != null) {
                                    return player.hasPotionEffect(type);
                                }
                            }
                            return false;
                        })
        );
    }
}

package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.enums.Operator;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
                                OriginsBukkit.KEY, "condition/player/has_gravity"
                        ), null,
                        (data, player) -> player.hasGravity())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_potion_effect"
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
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_attribute_value"
                        ), null,
                        (data, player) -> {
                            if (data.has("attribute")) {
                                JsonObject attribute = data.getAsJsonObject("attribute");

                                if (attribute != null) {
                                    Attribute type = null;
                                    Double value = null;

                                    if (attribute.has("type")) {
                                        type = new Gson().fromJson(
                                                attribute.get(
                                                        "type"
                                                ),
                                                Attribute.class
                                        );
                                    }
                                    if (attribute.has("value")) {
                                        value = attribute.get("value").getAsDouble();
                                    }
                                    if (type != null && value != null) {
                                        AttributeInstance attributeInstance
                                                = player.getAttribute(type);

                                        if (attributeInstance != null) {
                                            if (data.has("operator")) {
                                                String operator = data.get("operator").getAsString();

                                                if (operator != null) {
                                                    return Operator.parseOperator(operator)
                                                            .apply(
                                                                    attributeInstance.getValue(),
                                                                    value
                                                            );
                                                }
                                            }
                                            return attributeInstance.getValue() == value;
                                        }
                                    }
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_attribute_base_value"
                        ), null,
                        (data, player) -> {
                            if (data.has("attribute")) {
                                JsonObject attribute = data.getAsJsonObject("attribute");

                                if (attribute != null) {
                                    Attribute type = null;
                                    Double baseValue = null;

                                    if (attribute.has("type")) {
                                        type = new Gson().fromJson(
                                                attribute.get(
                                                        "type"
                                                ),
                                                Attribute.class
                                        );
                                    }
                                    if (attribute.has("base_value")) {
                                        baseValue = attribute.get("base_value").getAsDouble();
                                    }
                                    if (type != null && baseValue != null) {
                                        AttributeInstance attributeInstance
                                                = player.getAttribute(type);

                                        if (attributeInstance != null) {
                                            if (data.has("operator")) {
                                                String operator = data.get("operator").getAsString();

                                                if (operator != null) {
                                                    return Operator.parseOperator(operator)
                                                            .apply(
                                                                    attributeInstance.getBaseValue(),
                                                                    baseValue
                                                            );
                                                }
                                            }
                                            return attributeInstance.getBaseValue() == baseValue;
                                        }
                                    }
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_ping"
                        ), null,
                        (data, player) -> {
                            if (data.has("ping")) {
                                int ping = data.get("ping").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getPing(),
                                                        ping
                                                );
                                    }
                                }
                                return player.getPing() == ping;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_world"
                        ), null,
                        (data, player) -> {
                            if (data.has("world")) {
                                String world = data.get("world").getAsString();

                                if (world != null) {
                                    return player.getWorld().getName().equals(world);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_health"
                        ), null,
                        (data, player) -> {
                            if (data.has("health")) {
                                double health = data.get("health").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getHealth(),
                                                        health
                                                );
                                    }
                                }
                                return player.getHealth() == health;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_health_scale"
                        ), null,
                        (data, player) -> {
                            if (data.has("health_scale")) {
                                double healthScale = data.get("health_scale").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getHealthScale(),
                                                        healthScale
                                                );
                                    }
                                }
                                return player.getHealthScale() == healthScale;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_game_mode"
                        ), null,
                        (data, player) -> {
                            if (data.has("mode")) {
                                GameMode mode
                                        = new Gson().fromJson(
                                        data.get(
                                                "mode"
                                        ),
                                        GameMode.class
                                );

                                if (mode != null) {
                                    return player.getGameMode() == mode;
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_allow_flight"
                        ), null,
                        (data, player) -> {
                            if (data.has("flight")) {
                                boolean flight = data.get("flight").getAsBoolean();

                                return player.getAllowFlight() == flight;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_advancement_progress/is_done"
                        ), null,
                        (data, player) -> {
                            if (data.has("advancement")) {
                                JsonObject advancement = data.getAsJsonObject("advancement");

                                if (advancement != null) {
                                    String type;
                                    Advancement playerAdvancement = null;
                                    Boolean isDone = null;

                                    if (advancement.has("type")) {
                                        type = advancement.get("type").getAsString();

                                        if (type != null) {
                                            playerAdvancement
                                                    = Bukkit.getAdvancement(
                                                    NamespacedKey.minecraft(
                                                            type
                                                    )
                                            );
                                        }
                                    }
                                    if (advancement.has("is_done")) {
                                        isDone = advancement.get("is_done").getAsBoolean();
                                    }
                                    if (playerAdvancement != null && isDone != null) {
                                        return player.getAdvancementProgress(playerAdvancement)
                                                .isDone() == isDone;
                                    }
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_client_view_distance"
                        ), null,
                        (data, player) -> {
                            if (data.has("view_distance")) {
                                int viewDistance = data.get("view_distance").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getClientViewDistance(),
                                                        viewDistance
                                                );
                                    }
                                }
                                return player.getClientViewDistance() == viewDistance;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_display_name"
                        ), null,
                        (data, player) -> {
                            if (data.has("display_name")) {
                                String displayName = data.get("display_name").getAsString();

                                if (displayName != null) {
                                    return player.getDisplayName().equals(displayName);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_exp"
                        ), null,
                        (data, player) -> {
                            if (data.has("exp")) {
                                float exp = data.get("exp").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getExp(),
                                                        exp
                                                );
                                    }
                                }
                                return player.getExp() == exp;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_fly_speed"
                        ), null,
                        (data, player) -> {
                            if (data.has("fly_speed")) {
                                float flySpeed = data.get("fly_speed").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getFlySpeed(),
                                                        flySpeed
                                                );
                                    }
                                }
                                return player.getFlySpeed() == flySpeed;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_level"
                        ), null,
                        (data, player) -> {
                            if (data.has("level")) {
                                int level = data.get("level").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getLevel(),
                                                        level
                                                );
                                    }
                                }
                                return player.getLevel() == level;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_locale"
                        ), null,
                        (data, player) -> {
                            if (data.has("locale")) {
                                String locale = data.get("locale").getAsString();

                                if (locale != null) {
                                    return player.getLocale().equals(locale);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_time"
                        ), null,
                        (data, player) -> {
                            if (data.has("time")) {
                                long time = data.get("time").getAsLong();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getPlayerTime(),
                                                        time
                                                );
                                    }
                                }
                                return player.getPlayerTime() == time;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_time_offset"
                        ), null,
                        (data, player) -> {
                            if (data.has("time_offset")) {
                                long timeOffset = data.get("time_offset").getAsLong();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getPlayerTimeOffset(),
                                                        timeOffset
                                                );
                                    }
                                }
                                return player.getPlayerTimeOffset() == timeOffset;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_weather"
                        ), null,
                        (data, player) -> {
                            if (data.has("weather_type")) {
                                WeatherType weatherType
                                        = new Gson().fromJson(
                                        data.get(
                                                "weather_type"
                                        ),
                                        WeatherType.class
                                );

                                if (weatherType != null) {
                                    return player.getPlayerWeather() == weatherType;
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_total_experience"
                        ), null,
                        (data, player) -> {
                            if (data.has("total_experience")) {
                                int totalExperience = data.get("total_experience").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getTotalExperience(),
                                                        totalExperience
                                                );
                                    }
                                }
                                return player.getTotalExperience() == totalExperience;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_walk_speed"
                        ), null,
                        (data, player) -> {
                            if (data.has("walk_speed")) {
                                float walkSpeed = data.get("walk_speed").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getWalkSpeed(),
                                                        walkSpeed
                                                );
                                    }
                                }
                                return player.getWalkSpeed() == walkSpeed;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_absorption_amount"
                        ), null,
                        (data, player) -> {
                            if (data.has("absorption_amount")) {
                                double absorptionAmount = data.get("absorption_amount").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getAbsorptionAmount(),
                                                        absorptionAmount
                                                );
                                    }
                                }
                                return player.getAbsorptionAmount() == absorptionAmount;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_active_potion_effects_count"
                        ), null,
                        (data, player) -> {
                            if (data.has("count")) {
                                int count = data.get("count").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getActivePotionEffects().size(),
                                                        count
                                                );
                                    }
                                }
                                return player.getActivePotionEffects().size() == count;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_arrow_cooldown"
                        ), null,
                        (data, player) -> {
                            if (data.has("arrow_cooldown")) {
                                int arrowCooldown = data.get("arrow_cooldown").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getArrowCooldown(),
                                                        arrowCooldown
                                                );
                                    }
                                }
                                return player.getArrowCooldown() == arrowCooldown;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_arrows_in_body"
                        ), null,
                        (data, player) -> {
                            if (data.has("arrows_in_body")) {
                                int arrowsInBody = data.get("arrows_in_body").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getArrowsInBody(),
                                                        arrowsInBody
                                                );
                                    }
                                }
                                return player.getArrowsInBody() == arrowsInBody;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_attack_cooldown"
                        ), null,
                        (data, player) -> {
                            if (data.has("attack_cooldown")) {
                                float attackCooldown = data.get("attack_cooldown").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getAttackCooldown(),
                                                        attackCooldown
                                                );
                                    }
                                }
                                return player.getAttackCooldown() == attackCooldown;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/can_pickup_items"
                        ), null,
                        (data, player) -> player.getCanPickupItems())
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_custom_name"
                        ), null,
                        (data, player) -> {
                            if (data.has("custom_name")) {
                                String customName = data.get("custom_name").getAsString();

                                if (player.getCustomName() != null && customName != null) {
                                    return player.getCustomName().equals(customName);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_discovered_recipes_count"
                        ), null,
                        (data, player) -> {
                            if (data.has("count")) {
                                int count = data.get("count").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getDiscoveredRecipes().size(),
                                                        count
                                                );
                                    }
                                }
                                return player.getDiscoveredRecipes().size() == count;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_exhaustion"
                        ), null,
                        (data, player) -> {
                            if (data.has("exhaustion")) {
                                float exhaustion = data.get("exhaustion").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getExhaustion(),
                                                        exhaustion
                                                );
                                    }
                                }
                                return player.getExhaustion() == exhaustion;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_eye_height"
                        ), null,
                        (data, player) -> {
                            if (data.has("eye_height")) {
                                double eyeHeight = data.get("eye_height").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getEyeHeight(),
                                                        eyeHeight
                                                );
                                    }
                                }
                                return player.getEyeHeight() == eyeHeight;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_exp_to_level"
                        ), null,
                        (data, player) -> {
                            if (data.has("level")) {
                                int level = data.get("level").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getExpToLevel(),
                                                        level
                                                );
                                    }
                                }
                                return player.getExpToLevel() == level;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_fire_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("fire_ticks")) {
                                int fireTicks = data.get("fire_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getFireTicks(),
                                                        fireTicks
                                                );
                                    }
                                }
                                return player.getFireTicks() == fireTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_max_fire_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("max_fire_ticks")) {
                                int maxFireTicks = data.get("max_fire_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getMaxFireTicks(),
                                                        maxFireTicks
                                                );
                                    }
                                }
                                return player.getMaxFireTicks() == maxFireTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_fall_distance"
                        ), null,
                        (data, player) -> {
                            if (data.has("fall_distance")) {
                                float fallDistance = data.get("fall_distance").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getFallDistance(),
                                                        fallDistance
                                                );
                                    }
                                }
                                return player.getFallDistance() == fallDistance;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_food_level"
                        ), null,
                        (data, player) -> {
                            if (data.has("food_level")) {
                                int foodLevel = data.get("food_level").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getFoodLevel(),
                                                        foodLevel
                                                );
                                    }
                                }
                                return player.getFoodLevel() == foodLevel;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_freeze_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("freeze_ticks")) {
                                int freezeTicks = data.get("freeze_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getFreezeTicks(),
                                                        freezeTicks
                                                );
                                    }
                                }
                                return player.getFreezeTicks() == freezeTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_max_freeze_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("max_freeze_ticks")) {
                                int maxFreezeTicks = data.get("max_freeze_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getMaxFreezeTicks(),
                                                        maxFreezeTicks
                                                );
                                    }
                                }
                                return player.getMaxFreezeTicks() == maxFreezeTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_height"
                        ), null,
                        (data, player) -> {
                            if (data.has("height")) {
                                double height = data.get("height").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getHeight(),
                                                        height
                                                );
                                    }
                                }
                                return player.getHeight() == height;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_last_damage"
                        ), null,
                        (data, player) -> {
                            if (data.has("last_damage")) {
                                double lastDamage = data.get("last_damage").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getLastDamage(),
                                                        lastDamage
                                                );
                                    }
                                }
                                return player.getLastDamage() == lastDamage;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_maximum_air"
                        ), null,
                        (data, player) -> {
                            if (data.has("maximum_air")) {
                                int maximumAir = data.get("maximum_air").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getMaximumAir(),
                                                        maximumAir
                                                );
                                    }
                                }
                                return player.getMaximumAir() == maximumAir;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_remaining_air"
                        ), null,
                        (data, player) -> {
                            if (data.has("remaining_air")) {
                                int remainingAir = data.get("remaining_air").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getRemainingAir(),
                                                        remainingAir
                                                );
                                    }
                                }
                                return player.getRemainingAir() == remainingAir;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_maximum_no_damage_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("maximum_no_damage_ticks")) {
                                int ticks = data.get("maximum_no_damage_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getMaximumNoDamageTicks(),
                                                        ticks
                                                );
                                    }
                                }
                                return player.getMaximumNoDamageTicks() == ticks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_name"
                        ), null,
                        (data, player) -> {
                            if (data.has("name")) {
                                String name = data.get("name").getAsString();

                                if (name != null) {
                                    return player.getName().equals(name);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_no_damage_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("no_damage_ticks")) {
                                int noDamageTicks = data.get("no_damage_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getNoDamageTicks(),
                                                        noDamageTicks
                                                );
                                    }
                                }
                                return player.getNoDamageTicks() == noDamageTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_saturated_regen_rate"
                        ), null,
                        (data, player) -> {
                            if (data.has("saturated_regen_rate")) {
                                int saturatedRegenRate = data.get("saturated_regen_rate").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getSaturatedRegenRate(),
                                                        saturatedRegenRate
                                                );
                                    }
                                }
                                return player.getSaturatedRegenRate() == saturatedRegenRate;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_saturation"
                        ), null,
                        (data, player) -> {
                            if (data.has("saturation")) {
                                float saturation = data.get("saturation").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getSaturation(),
                                                        saturation
                                                );
                                    }
                                }
                                return player.getSaturation() == saturation;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_sleep_ticks"
                        ), null,
                        (data, player) -> {
                            if (data.has("sleep_ticks")) {
                                int sleepTicks = data.get("sleep_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getSleepTicks(),
                                                        sleepTicks
                                                );
                                    }
                                }
                                return player.getSleepTicks() == sleepTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_starvation_rate"
                        ), null,
                        (data, player) -> {
                            if (data.has("starvation_rate")) {
                                int starvationRate = data.get("starvation_rate").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getStarvationRate(),
                                                        starvationRate
                                                );
                                    }
                                }
                                return player.getStarvationRate() == starvationRate;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_ticks_lived"
                        ), null,
                        (data, player) -> {
                            if (data.has("ticks_lived")) {
                                int ticksLived = data.get("ticks_lived").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getTicksLived(),
                                                        ticksLived
                                                );
                                    }
                                }
                                return player.getTicksLived() == ticksLived;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_unsaturated_regen_rate"
                        ), null,
                        (data, player) -> {
                            if (data.has("unsaturated_regen_rate")) {
                                int unsaturatedRegenRate = data.get("unsaturated_regen_rate").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getUnsaturatedRegenRate(),
                                                        unsaturatedRegenRate
                                                );
                                    }
                                }
                                return player.getUnsaturatedRegenRate() == unsaturatedRegenRate;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_x_velocity"
                        ), null,
                        (data, player) -> {
                            if (data.has("x_velocity")) {
                                double xVelocity = data.get("x_velocity").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getVelocity().getX(),
                                                        xVelocity
                                                );
                                    }
                                }
                                return player.getVelocity().getX() == xVelocity;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_y_velocity"
                        ), null,
                        (data, player) -> {
                            if (data.has("y_velocity")) {
                                double yVelocity = data.get("y_velocity").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getVelocity().getY(),
                                                        yVelocity
                                                );
                                    }
                                }
                                return player.getVelocity().getY() == yVelocity;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_z_velocity"
                        ), null,
                        (data, player) -> {
                            if (data.has("z_velocity")) {
                                double zVelocity = data.get("z_velocity").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        player.getVelocity().getZ(),
                                                        zVelocity
                                                );
                                    }
                                }
                                return player.getVelocity().getZ() == zVelocity;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("type")) {
                                Material type
                                        = new Gson().fromJson(
                                                data.get(
                                                        "type"
                                                ),
                                        Material.class
                                );

                                if (type != null) {
                                    return location.getBlock().getType() == type;
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_solid"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isSolid();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_block"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isBlock();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_air"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isAir();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_burnable"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isBurnable();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_edible"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isEdible();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_flammable"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isFlammable();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_fuel"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isFuel();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_interactable"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isInteractable();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_item"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isItem();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_occluding"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isOccluding();
                        })
        );
        Registry.register(
                new ConditionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_record"
                        ), null,
                        (data, player) -> {
                            Location location = locationOffset(
                                    player.getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isRecord();
                        })
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

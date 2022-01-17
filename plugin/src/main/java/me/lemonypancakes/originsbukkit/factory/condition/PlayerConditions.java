package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.enums.Operator;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.potion.PotionEffectType;

public final class PlayerConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_online"
                        ), null,
                        (data, temp) -> temp.getPlayer().isOnline())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_in_water"
                        ), null,
                        (data, temp) -> temp.getPlayer().isInWater())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_player_time_relative"
                        ), null,
                        (data, temp) -> temp.getPlayer().isPlayerTimeRelative())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_flying"
                        ), null,
                        (data, temp) -> temp.getPlayer().isFlying())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_health_scaled"
                        ), null,
                        (data, temp) -> temp.getPlayer().isHealthScaled())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_sleeping_ignored"
                        ), null,
                        (data, temp) -> temp.getPlayer().isSleepingIgnored())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_sleeping"
                        ), null,
                        (data, temp) -> temp.getPlayer().isSleeping())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_banned"
                        ), null,
                        (data, temp) -> temp.getPlayer().isBanned())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_blocking"
                        ), null,
                        (data, temp) -> temp.getPlayer().isBlocking())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_climbing"
                        ), null,
                        (data, temp) -> temp.getPlayer().isClimbing())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_collidable"
                        ), null,
                        (data, temp) -> temp.getPlayer().isCollidable())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_conversing"
                        ), null,
                        (data, temp) -> temp.getPlayer().isConversing())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_custom_name_visible"
                        ), null,
                        (data, temp) -> temp.getPlayer().isCustomNameVisible())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_dead"
                        ), null,
                        (data, temp) -> temp.getPlayer().isDead())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_empty"
                        ), null,
                        (data, temp) -> temp.getPlayer().isEmpty())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_frozen"
                        ), null,
                        (data, temp) -> temp.getPlayer().isFrozen())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_gliding"
                        ), null,
                        (data, temp) -> temp.getPlayer().isGliding())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_glowing"
                        ), null,
                        (data, temp) -> temp.getPlayer().isGlowing())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_hand_raised"
                        ), null,
                        (data, temp) -> temp.getPlayer().isHandRaised())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_inside_vehicle"
                        ), null,
                        (data, temp) -> temp.getPlayer().isInsideVehicle())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_invisible"
                        ), null,
                        (data, temp) -> temp.getPlayer().isInvisible())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_invulnerable"
                        ), null,
                        (data, temp) -> temp.getPlayer().isInvulnerable())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_leashed"
                        ), null,
                        (data, temp) -> temp.getPlayer().isLeashed())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_op"
                        ), null,
                        (data, temp) -> temp.getPlayer().isOp())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_riptiding"
                        ), null,
                        (data, temp) -> temp.getPlayer().isRiptiding())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_silent"
                        ), null,
                        (data, temp) -> temp.getPlayer().isSilent())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_swimming"
                        ), null,
                        (data, temp) -> temp.getPlayer().isSwimming())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_visual_fire"
                        ), null,
                        (data, temp) -> temp.getPlayer().isVisualFire())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/is_whitelisted"
                        ), null,
                        (data, temp) -> temp.getPlayer().isWhitelisted())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_permission"
                        ), null,
                        (data, temp) -> {
                            if (data.has("permission")) {
                                String permission = data.get("permission").getAsString();

                                if (permission != null) {
                                    return temp.getPlayer().hasPermission(permission);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_cooldown"
                        ), null,
                        (data, temp) -> {
                            if (data.has("material")) {
                                Material material
                                        = new Gson().fromJson(
                                        data.get(
                                                "material"
                                        ),
                                        Material.class
                                );

                                if (material != null) {
                                    return temp.getPlayer().hasCooldown(material);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_played_before"
                        ), null,
                        (data, temp) -> temp.getPlayer().hasPlayedBefore())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_discovered_recipe"
                        ), null,
                        (data, temp) -> {
                            if (data.has("recipe")) {
                                NamespacedKey recipe
                                        = NamespacedKey.fromString(
                                        data.get(
                                                "recipe"
                                        ).getAsString()
                                );

                                if (recipe != null) {
                                    return temp.getPlayer().hasDiscoveredRecipe(recipe);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_gravity"
                        ), null,
                        (data, temp) -> temp.getPlayer().hasGravity())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/has_potion_effect_type"
                        ), null,
                        (data, temp) -> {
                            if (data.has("potion_effect_type")) {
                                PotionEffectType type
                                        = PotionEffectType.getByName(
                                        data.get(
                                                "potion_effect_type"
                                        ).getAsString()
                                );

                                if (type != null) {
                                    return temp.getPlayer().hasPotionEffect(type);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_attribute_value"
                        ), null,
                        (data, temp) -> {
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
                                                = temp.getPlayer().getAttribute(type);

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
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_attribute_base_value"
                        ), null,
                        (data, temp) -> {
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
                                                = temp.getPlayer().getAttribute(type);

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
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_ping"
                        ), null,
                        (data, temp) -> {
                            if (data.has("ping")) {
                                int ping = data.get("ping").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getPing(),
                                                        ping
                                                );
                                    }
                                }
                                return temp.getPlayer().getPing() == ping;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_world"
                        ), null,
                        (data, temp) -> {
                            if (data.has("world")) {
                                String world = data.get("world").getAsString();

                                if (world != null) {
                                    return temp.getPlayer().getWorld().getName().equals(world);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_health"
                        ), null,
                        (data, temp) -> {
                            if (data.has("health")) {
                                double health = data.get("health").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getHealth(),
                                                        health
                                                );
                                    }
                                }
                                return temp.getPlayer().getHealth() == health;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_health_scale"
                        ), null,
                        (data, temp) -> {
                            if (data.has("health_scale")) {
                                double healthScale = data.get("health_scale").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getHealthScale(),
                                                        healthScale
                                                );
                                    }
                                }
                                return temp.getPlayer().getHealthScale() == healthScale;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_game_mode"
                        ), null,
                        (data, temp) -> {
                            if (data.has("mode")) {
                                GameMode mode
                                        = new Gson().fromJson(
                                        data.get(
                                                "mode"
                                        ),
                                        GameMode.class
                                );

                                if (mode != null) {
                                    return temp.getPlayer().getGameMode() == mode;
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_allow_flight"
                        ), null,
                        (data, temp) -> {
                            if (data.has("flight")) {
                                boolean flight = data.get("flight").getAsBoolean();

                                return temp.getPlayer().getAllowFlight() == flight;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_advancement_progress/is_done"
                        ), null,
                        (data, temp) -> {
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
                                        return temp.getPlayer().getAdvancementProgress(playerAdvancement)
                                                .isDone() == isDone;
                                    }
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_client_view_distance"
                        ), null,
                        (data, temp) -> {
                            if (data.has("view_distance")) {
                                int viewDistance = data.get("view_distance").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getClientViewDistance(),
                                                        viewDistance
                                                );
                                    }
                                }
                                return temp.getPlayer().getClientViewDistance() == viewDistance;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_display_name"
                        ), null,
                        (data, temp) -> {
                            if (data.has("display_name")) {
                                String displayName = data.get("display_name").getAsString();

                                if (displayName != null) {
                                    return temp.getPlayer().getDisplayName().equals(displayName);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_exp"
                        ), null,
                        (data, temp) -> {
                            if (data.has("exp")) {
                                float exp = data.get("exp").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getExp(),
                                                        exp
                                                );
                                    }
                                }
                                return temp.getPlayer().getExp() == exp;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_fly_speed"
                        ), null,
                        (data, temp) -> {
                            if (data.has("fly_speed")) {
                                float flySpeed = data.get("fly_speed").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getFlySpeed(),
                                                        flySpeed
                                                );
                                    }
                                }
                                return temp.getPlayer().getFlySpeed() == flySpeed;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_level"
                        ), null,
                        (data, temp) -> {
                            if (data.has("level")) {
                                int level = data.get("level").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getLevel(),
                                                        level
                                                );
                                    }
                                }
                                return temp.getPlayer().getLevel() == level;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_locale"
                        ), null,
                        (data, temp) -> {
                            if (data.has("locale")) {
                                String locale = data.get("locale").getAsString();

                                if (locale != null) {
                                    return temp.getPlayer().getLocale().equals(locale);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_time"
                        ), null,
                        (data, temp) -> {
                            if (data.has("time")) {
                                long time = data.get("time").getAsLong();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getPlayerTime(),
                                                        time
                                                );
                                    }
                                }
                                return temp.getPlayer().getPlayerTime() == time;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_time_offset"
                        ), null,
                        (data, temp) -> {
                            if (data.has("time_offset")) {
                                long timeOffset = data.get("time_offset").getAsLong();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getPlayerTimeOffset(),
                                                        timeOffset
                                                );
                                    }
                                }
                                return temp.getPlayer().getPlayerTimeOffset() == timeOffset;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_weather"
                        ), null,
                        (data, temp) -> {
                            if (data.has("weather_type")) {
                                WeatherType weatherType
                                        = new Gson().fromJson(
                                        data.get(
                                                "weather_type"
                                        ),
                                        WeatherType.class
                                );

                                if (weatherType != null) {
                                    return temp.getPlayer().getPlayerWeather() == weatherType;
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_total_experience"
                        ), null,
                        (data, temp) -> {
                            if (data.has("total_experience")) {
                                int totalExperience = data.get("total_experience").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getTotalExperience(),
                                                        totalExperience
                                                );
                                    }
                                }
                                return temp.getPlayer().getTotalExperience() == totalExperience;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_walk_speed"
                        ), null,
                        (data, temp) -> {
                            if (data.has("walk_speed")) {
                                float walkSpeed = data.get("walk_speed").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getWalkSpeed(),
                                                        walkSpeed
                                                );
                                    }
                                }
                                return temp.getPlayer().getWalkSpeed() == walkSpeed;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_absorption_amount"
                        ), null,
                        (data, temp) -> {
                            if (data.has("absorption_amount")) {
                                double absorptionAmount = data.get("absorption_amount").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getAbsorptionAmount(),
                                                        absorptionAmount
                                                );
                                    }
                                }
                                return temp.getPlayer().getAbsorptionAmount() == absorptionAmount;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_active_potion_effects_count"
                        ), null,
                        (data, temp) -> {
                            if (data.has("count")) {
                                int count = data.get("count").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getActivePotionEffects().size(),
                                                        count
                                                );
                                    }
                                }
                                return temp.getPlayer().getActivePotionEffects().size() == count;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_arrow_cooldown"
                        ), null,
                        (data, temp) -> {
                            if (data.has("arrow_cooldown")) {
                                int arrowCooldown = data.get("arrow_cooldown").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getArrowCooldown(),
                                                        arrowCooldown
                                                );
                                    }
                                }
                                return temp.getPlayer().getArrowCooldown() == arrowCooldown;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_arrows_in_body"
                        ), null,
                        (data, temp) -> {
                            if (data.has("arrows_in_body")) {
                                int arrowsInBody = data.get("arrows_in_body").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getArrowsInBody(),
                                                        arrowsInBody
                                                );
                                    }
                                }
                                return temp.getPlayer().getArrowsInBody() == arrowsInBody;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_attack_cooldown"
                        ), null,
                        (data, temp) -> {
                            if (data.has("attack_cooldown")) {
                                float attackCooldown = data.get("attack_cooldown").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getAttackCooldown(),
                                                        attackCooldown
                                                );
                                    }
                                }
                                return temp.getPlayer().getAttackCooldown() == attackCooldown;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/can_pickup_items"
                        ), null,
                        (data, temp) -> temp.getPlayer().getCanPickupItems())
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_custom_name"
                        ), null,
                        (data, temp) -> {
                            if (data.has("custom_name")) {
                                String customName = data.get("custom_name").getAsString();

                                if (temp.getPlayer().getCustomName() != null && customName != null) {
                                    return temp.getPlayer().getCustomName().equals(customName);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_discovered_recipes_count"
                        ), null,
                        (data, temp) -> {
                            if (data.has("count")) {
                                int count = data.get("count").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getDiscoveredRecipes().size(),
                                                        count
                                                );
                                    }
                                }
                                return temp.getPlayer().getDiscoveredRecipes().size() == count;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_exhaustion"
                        ), null,
                        (data, temp) -> {
                            if (data.has("exhaustion")) {
                                float exhaustion = data.get("exhaustion").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getExhaustion(),
                                                        exhaustion
                                                );
                                    }
                                }
                                return temp.getPlayer().getExhaustion() == exhaustion;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_eye_height"
                        ), null,
                        (data, temp) -> {
                            if (data.has("eye_height")) {
                                double eyeHeight = data.get("eye_height").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getEyeHeight(),
                                                        eyeHeight
                                                );
                                    }
                                }
                                return temp.getPlayer().getEyeHeight() == eyeHeight;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_exp_to_level"
                        ), null,
                        (data, temp) -> {
                            if (data.has("level")) {
                                int level = data.get("level").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getExpToLevel(),
                                                        level
                                                );
                                    }
                                }
                                return temp.getPlayer().getExpToLevel() == level;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_fire_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("fire_ticks")) {
                                int fireTicks = data.get("fire_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getFireTicks(),
                                                        fireTicks
                                                );
                                    }
                                }
                                return temp.getPlayer().getFireTicks() == fireTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_max_fire_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("max_fire_ticks")) {
                                int maxFireTicks = data.get("max_fire_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getMaxFireTicks(),
                                                        maxFireTicks
                                                );
                                    }
                                }
                                return temp.getPlayer().getMaxFireTicks() == maxFireTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_fall_distance"
                        ), null,
                        (data, temp) -> {
                            if (data.has("fall_distance")) {
                                float fallDistance = data.get("fall_distance").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getFallDistance(),
                                                        fallDistance
                                                );
                                    }
                                }
                                return temp.getPlayer().getFallDistance() == fallDistance;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_food_level"
                        ), null,
                        (data, temp) -> {
                            if (data.has("food_level")) {
                                int foodLevel = data.get("food_level").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getFoodLevel(),
                                                        foodLevel
                                                );
                                    }
                                }
                                return temp.getPlayer().getFoodLevel() == foodLevel;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_freeze_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("freeze_ticks")) {
                                int freezeTicks = data.get("freeze_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getFreezeTicks(),
                                                        freezeTicks
                                                );
                                    }
                                }
                                return temp.getPlayer().getFreezeTicks() == freezeTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_max_freeze_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("max_freeze_ticks")) {
                                int maxFreezeTicks = data.get("max_freeze_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getMaxFreezeTicks(),
                                                        maxFreezeTicks
                                                );
                                    }
                                }
                                return temp.getPlayer().getMaxFreezeTicks() == maxFreezeTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_height"
                        ), null,
                        (data, temp) -> {
                            if (data.has("height")) {
                                double height = data.get("height").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getHeight(),
                                                        height
                                                );
                                    }
                                }
                                return temp.getPlayer().getHeight() == height;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_last_damage"
                        ), null,
                        (data, temp) -> {
                            if (data.has("last_damage")) {
                                double lastDamage = data.get("last_damage").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getLastDamage(),
                                                        lastDamage
                                                );
                                    }
                                }
                                return temp.getPlayer().getLastDamage() == lastDamage;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_maximum_air"
                        ), null,
                        (data, temp) -> {
                            if (data.has("maximum_air")) {
                                int maximumAir = data.get("maximum_air").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getMaximumAir(),
                                                        maximumAir
                                                );
                                    }
                                }
                                return temp.getPlayer().getMaximumAir() == maximumAir;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_remaining_air"
                        ), null,
                        (data, temp) -> {
                            if (data.has("remaining_air")) {
                                int remainingAir = data.get("remaining_air").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getRemainingAir(),
                                                        remainingAir
                                                );
                                    }
                                }
                                return temp.getPlayer().getRemainingAir() == remainingAir;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_maximum_no_damage_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("maximum_no_damage_ticks")) {
                                int ticks = data.get("maximum_no_damage_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getMaximumNoDamageTicks(),
                                                        ticks
                                                );
                                    }
                                }
                                return temp.getPlayer().getMaximumNoDamageTicks() == ticks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_name"
                        ), null,
                        (data, temp) -> {
                            if (data.has("name")) {
                                String name = data.get("name").getAsString();

                                if (name != null) {
                                    return temp.getPlayer().getName().equals(name);
                                }
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_no_damage_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("no_damage_ticks")) {
                                int noDamageTicks = data.get("no_damage_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getNoDamageTicks(),
                                                        noDamageTicks
                                                );
                                    }
                                }
                                return temp.getPlayer().getNoDamageTicks() == noDamageTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_saturated_regen_rate"
                        ), null,
                        (data, temp) -> {
                            if (data.has("saturated_regen_rate")) {
                                int saturatedRegenRate = data.get("saturated_regen_rate").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getSaturatedRegenRate(),
                                                        saturatedRegenRate
                                                );
                                    }
                                }
                                return temp.getPlayer().getSaturatedRegenRate() == saturatedRegenRate;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_saturation"
                        ), null,
                        (data, temp) -> {
                            if (data.has("saturation")) {
                                float saturation = data.get("saturation").getAsFloat();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getSaturation(),
                                                        saturation
                                                );
                                    }
                                }
                                return temp.getPlayer().getSaturation() == saturation;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_sleep_ticks"
                        ), null,
                        (data, temp) -> {
                            if (data.has("sleep_ticks")) {
                                int sleepTicks = data.get("sleep_ticks").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getSleepTicks(),
                                                        sleepTicks
                                                );
                                    }
                                }
                                return temp.getPlayer().getSleepTicks() == sleepTicks;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_starvation_rate"
                        ), null,
                        (data, temp) -> {
                            if (data.has("starvation_rate")) {
                                int starvationRate = data.get("starvation_rate").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getStarvationRate(),
                                                        starvationRate
                                                );
                                    }
                                }
                                return temp.getPlayer().getStarvationRate() == starvationRate;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_ticks_lived"
                        ), null,
                        (data, temp) -> {
                            if (data.has("ticks_lived")) {
                                int ticksLived = data.get("ticks_lived").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getTicksLived(),
                                                        ticksLived
                                                );
                                    }
                                }
                                return temp.getPlayer().getTicksLived() == ticksLived;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_unsaturated_regen_rate"
                        ), null,
                        (data, temp) -> {
                            if (data.has("unsaturated_regen_rate")) {
                                int unsaturatedRegenRate = data.get("unsaturated_regen_rate").getAsInt();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getUnsaturatedRegenRate(),
                                                        unsaturatedRegenRate
                                                );
                                    }
                                }
                                return temp.getPlayer().getUnsaturatedRegenRate() == unsaturatedRegenRate;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_x_velocity"
                        ), null,
                        (data, temp) -> {
                            if (data.has("x_velocity")) {
                                double xVelocity = data.get("x_velocity").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getVelocity().getX(),
                                                        xVelocity
                                                );
                                    }
                                }
                                return temp.getPlayer().getVelocity().getX() == xVelocity;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_y_velocity"
                        ), null,
                        (data, temp) -> {
                            if (data.has("y_velocity")) {
                                double yVelocity = data.get("y_velocity").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getVelocity().getY(),
                                                        yVelocity
                                                );
                                    }
                                }
                                return temp.getPlayer().getVelocity().getY() == yVelocity;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_z_velocity"
                        ), null,
                        (data, temp) -> {
                            if (data.has("z_velocity")) {
                                double zVelocity = data.get("z_velocity").getAsDouble();

                                if (data.has("operator")) {
                                    String operator = data.get("operator").getAsString();

                                    if (operator != null) {
                                        return Operator.parseOperator(operator)
                                                .apply(
                                                        temp.getPlayer().getVelocity().getZ(),
                                                        zVelocity
                                                );
                                    }
                                }
                                return temp.getPlayer().getVelocity().getZ() == zVelocity;
                            }
                            return false;
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            if (data.has("block_type")) {
                                Material type
                                        = new Gson().fromJson(
                                        data.get(
                                                "block_type"
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
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_solid"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isSolid();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_block"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isBlock();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_air"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isAir();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_burnable"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isBurnable();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_edible"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isEdible();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_flammable"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isFlammable();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_fuel"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isFuel();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_interactable"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isInteractable();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_item"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isItem();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_occluding"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isOccluding();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/type/is_record"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().getType().isRecord();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/is_powered"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isBlockPowered();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/is_liquid"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isLiquid();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/is_empty"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isEmpty();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/is_indirectly_powered"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isBlockIndirectlyPowered();
                        })
        );
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/player/compare_location/block/is_passable"
                        ), null,
                        (data, temp) -> {
                            Location location = locationOffset(
                                    temp.getPlayer().getLocation(),
                                    data.getAsJsonObject("offset")
                            );

                            return location.getBlock().isPassable();
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

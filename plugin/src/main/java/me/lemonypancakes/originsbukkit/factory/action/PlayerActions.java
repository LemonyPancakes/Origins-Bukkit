package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public final class PlayerActions {

    public static void register() {
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/send_message"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("message")) {
                                String message = data.get("message").getAsString();

                                if (message != null) {
                                    temp.getPlayer().sendMessage(message);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/send_messages"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("messages")) {
                                String[] messages
                                        = new Gson().fromJson(
                                        data.get(
                                                "messages"
                                        ),
                                        String[].class
                                );

                                if (messages != null) {
                                    temp.getPlayer().sendMessage(messages);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/damage"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("amount")) {
                                double amount = data.get("amount").getAsDouble();

                                temp.getPlayer().damage(amount);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_remaining_air"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setRemainingAir(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/add_potion_effect"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("effect")) {
                                JsonObject effect
                                        = new Gson().fromJson(
                                        data.get(
                                                "effect"
                                        ),
                                        JsonObject.class
                                );

                                if (effect != null) {
                                    PotionEffectType potionEffectType = null;
                                    Integer duration = null;
                                    Integer amplifier = null;
                                    Boolean ambient = null;
                                    Boolean particles = null;
                                    Boolean icon = null;

                                    if (effect.has("type")) {
                                        potionEffectType
                                                = PotionEffectType.getByName(
                                                effect.get(
                                                        "type"
                                                ).getAsString()
                                        );
                                    }
                                    if (effect.has("duration")) {
                                        duration = effect.get("duration").getAsInt();
                                    }
                                    if (effect.has("amplifier")) {
                                        amplifier = effect.get("amplifier").getAsInt();
                                    }
                                    if (effect.has("ambient")) {
                                        ambient = effect.get("ambient").getAsBoolean();
                                    }
                                    if (effect.has("particles")) {
                                        particles = effect.get("particles").getAsBoolean();
                                    }
                                    if (effect.has("icon")) {
                                        icon = effect.get("icon").getAsBoolean();
                                    }

                                    if (potionEffectType != null
                                            && duration != null
                                            && amplifier != null
                                            && ambient != null
                                            && particles != null
                                            && icon != null) {
                                        temp.getPlayer().addPotionEffect(
                                                new PotionEffect(
                                                        potionEffectType,
                                                        duration,
                                                        amplifier,
                                                        ambient,
                                                        particles,
                                                        icon
                                                )
                                        );
                                    } else if (potionEffectType != null
                                            && duration != null
                                            && amplifier != null
                                            && ambient != null
                                            && particles != null) {
                                        temp.getPlayer().addPotionEffect(
                                                new PotionEffect(
                                                        potionEffectType,
                                                        duration,
                                                        amplifier,
                                                        ambient,
                                                        particles
                                                )
                                        );
                                    } else if (potionEffectType != null
                                            && duration != null
                                            && amplifier != null
                                            && ambient != null) {
                                        temp.getPlayer().addPotionEffect(
                                                new PotionEffect(
                                                        potionEffectType,
                                                        duration,
                                                        amplifier,
                                                        ambient
                                                )
                                        );
                                    } else if (potionEffectType != null
                                            && duration != null
                                            && amplifier != null) {
                                        temp.getPlayer().addPotionEffect(
                                                new PotionEffect(
                                                        potionEffectType,
                                                        duration,
                                                        amplifier
                                                )
                                        );
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/add_potion_effects"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("effects")) {
                                JsonObject[] effects
                                        = new Gson().fromJson(
                                        data.get(
                                                "effects"
                                        ),
                                        JsonObject[].class
                                );

                                if (effects != null) {
                                    for (JsonObject effect : effects) {
                                        PotionEffectType potionEffectType = null;
                                        Integer duration = null;
                                        Integer amplifier = null;
                                        Boolean ambient = null;
                                        Boolean particles = null;
                                        Boolean icon = null;

                                        if (effect.has("type")) {
                                            potionEffectType
                                                    = PotionEffectType.getByName(
                                                    effect.get(
                                                            "type"
                                                    ).getAsString()
                                            );
                                        }
                                        if (effect.has("duration")) {
                                            duration = effect.get("duration").getAsInt();
                                        }
                                        if (effect.has("amplifier")) {
                                            amplifier = effect.get("amplifier").getAsInt();
                                        }
                                        if (effect.has("ambient")) {
                                            ambient = effect.get("ambient").getAsBoolean();
                                        }
                                        if (effect.has("particles")) {
                                            particles = effect.get("particles").getAsBoolean();
                                        }
                                        if (effect.has("icon")) {
                                            icon = effect.get("icon").getAsBoolean();
                                        }

                                        if (potionEffectType != null
                                                && duration != null
                                                && amplifier != null
                                                && ambient != null
                                                && particles != null
                                                && icon != null) {
                                            temp.getPlayer().addPotionEffect(
                                                    new PotionEffect(
                                                            potionEffectType,
                                                            duration,
                                                            amplifier,
                                                            ambient,
                                                            particles,
                                                            icon
                                                    )
                                            );
                                        } else if (potionEffectType != null
                                                && duration != null
                                                && amplifier != null
                                                && ambient != null
                                                && particles != null) {
                                            temp.getPlayer().addPotionEffect(
                                                    new PotionEffect(
                                                            potionEffectType,
                                                            duration,
                                                            amplifier,
                                                            ambient,
                                                            particles
                                                    )
                                            );
                                        } else if (potionEffectType != null
                                                && duration != null
                                                && amplifier != null
                                                && ambient != null) {
                                            temp.getPlayer().addPotionEffect(
                                                    new PotionEffect(
                                                            potionEffectType,
                                                            duration,
                                                            amplifier,
                                                            ambient
                                                    )
                                            );
                                        } else if (potionEffectType != null
                                                && duration != null
                                                && amplifier != null) {
                                            temp.getPlayer().addPotionEffect(
                                                    new PotionEffect(
                                                            potionEffectType,
                                                            duration,
                                                            amplifier
                                                    )
                                            );
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/remove_potion_effect_types"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("effect_types")) {
                                PotionEffectType[] potionEffectTypes
                                        = new Gson().fromJson(
                                        data.get(
                                                "effect_types"
                                        ),
                                        PotionEffectType[].class
                                );

                                if (potionEffectTypes != null) {
                                    for (PotionEffectType potionEffectType : potionEffectTypes) {
                                        temp.getPlayer().removePotionEffect(potionEffectType);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_health"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("health")) {
                                double health = data.get("health").getAsDouble();

                                temp.getPlayer().setHealth(health);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_glowing"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                temp.getPlayer().setGlowing(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_flying"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                boolean value = data.get("value").getAsBoolean();

                                temp.getPlayer().setFlying(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_fly_speed"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                temp.getPlayer().setFlySpeed(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_allow_flight"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("flight")) {
                                boolean flight = data.get("flight").getAsBoolean();

                                temp.getPlayer().setAllowFlight(flight);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_walk_speed"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                temp.getPlayer().setWalkSpeed(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_health_scale"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("scale")) {
                                double scale = data.get("scale").getAsDouble();

                                temp.getPlayer().setHealthScale(scale);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_bed_spawn_location"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("location")) {
                                Location location
                                        = new Gson().fromJson(
                                        data.get(
                                                "location"
                                        ),
                                        Location.class
                                );

                                if (location != null) {
                                    temp.getPlayer().setBedSpawnLocation(location);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_compass_target"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("location")) {
                                Location location
                                        = new Gson().fromJson(
                                        data.get(
                                                "location"
                                        ),
                                        Location.class
                                );

                                if (location != null) {
                                    temp.getPlayer().setCompassTarget(location);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_display_name"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("display_name")) {
                                String displayName = data.get("display_name").getAsString();

                                temp.getPlayer().setDisplayName(displayName);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_exp"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("exp")) {
                                float exp = data.get("exp").getAsFloat();

                                temp.getPlayer().setExp(exp);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_health_scaled"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("scale")) {
                                boolean scale = data.get("scale").getAsBoolean();

                                temp.getPlayer().setHealthScaled(scale);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_level"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("level")) {
                                int level = data.get("level").getAsInt();

                                temp.getPlayer().setLevel(level);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_time"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("time")) {
                                long time = data.get("time").getAsLong();
                                boolean relative = false;

                                if (data.has("relative")) {
                                    relative = data.get("relative").getAsBoolean();
                                }
                                temp.getPlayer().setPlayerTime(time, relative);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_weather"
                        ),
                        null,
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
                                    temp.getPlayer().setPlayerWeather(weatherType);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_resource_pack"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("resource_pack_url")) {
                                String resourcePackUrl = data.get("resource_pack_url").getAsString();

                                if (resourcePackUrl != null) {
                                    temp.getPlayer().setResourcePack(resourcePackUrl);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_scoreboard"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("scoreboard")) {
                                Scoreboard scoreboard
                                        = new Gson().fromJson(
                                        data.get(
                                                "scoreboard"
                                        ),
                                        Scoreboard.class
                                );

                                if (scoreboard != null) {
                                    temp.getPlayer().setScoreboard(scoreboard);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_sleeping_ignored"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("is_sleeping")) {
                                boolean isSleeping = data.get("is_sleeping").getAsBoolean();

                                temp.getPlayer().setSleepingIgnored(isSleeping);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_sneaking"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("sneak")) {
                                boolean sneak = data.get("sneak").getAsBoolean();

                                temp.getPlayer().setSneaking(sneak);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_sprinting"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("sprinting")) {
                                boolean sprinting = data.get("sprinting").getAsBoolean();

                                temp.getPlayer().setSprinting(sprinting);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_total_experience"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("exp")) {
                                int exp = data.get("exp").getAsInt();

                                temp.getPlayer().setTotalExperience(exp);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_absorption_amount"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("amount")) {
                                double amount = data.get("amount").getAsDouble();

                                temp.getPlayer().setAbsorptionAmount(amount);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_arrow_cooldown"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setArrowCooldown(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_arrows_in_body"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("count")) {
                                int count = data.get("count").getAsInt();

                                temp.getPlayer().setArrowsInBody(count);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_can_pickup_items"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("pickup")) {
                                boolean pickup = data.get("pickup").getAsBoolean();

                                temp.getPlayer().setCanPickupItems(pickup);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_collidable"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("collidable")) {
                                boolean collidable = data.get("collidable").getAsBoolean();

                                temp.getPlayer().setCollidable(collidable);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_cooldown"
                        ),
                        null,
                        (data, temp) -> {
                            Material material = null;
                            Integer ticks = null;

                            if (data.has("material")) {
                                material = new Gson().fromJson(
                                        data.get(
                                                "material"
                                        ),
                                        Material.class
                                );
                            }
                            if (data.has("ticks")) {
                                ticks = data.get("ticks").getAsInt();
                            }
                            if (material != null && ticks != null) {
                                temp.getPlayer().setCooldown(material, ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_custom_name"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("name")) {
                                String name = data.get("name").getAsString();

                                if (name != null) {
                                    temp.getPlayer().setCustomName(name);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_custom_name_visible"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                temp.getPlayer().setCustomNameVisible(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_exhaustion"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                temp.getPlayer().setExhaustion(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_fall_distance"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("distance")) {
                                float distance = data.get("distance").getAsFloat();

                                temp.getPlayer().setFallDistance(distance);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_fire_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setFireTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_food_level"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                int value = data.get("value").getAsInt();

                                temp.getPlayer().setFoodLevel(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_freeze_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setFreezeTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_game_mode"
                        ),
                        null,
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
                                    temp.getPlayer().setGameMode(mode);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_gliding"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("gliding")) {
                                boolean gliding = data.get("gliding").getAsBoolean();

                                temp.getPlayer().setGliding(gliding);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_gravity"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("gravity")) {
                                boolean gravity = data.get("gravity").getAsBoolean();

                                temp.getPlayer().setGravity(gravity);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_invisible"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("invisible")) {
                                boolean invisible = data.get("invisible").getAsBoolean();

                                temp.getPlayer().setInvisible(invisible);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_invulnerable"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                temp.getPlayer().setInvulnerable(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_last_damage"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("damage")) {
                                double damage = data.get("damage").getAsDouble();

                                temp.getPlayer().setLastDamage(damage);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_leash_holder"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("holder")) {
                                Entity holder
                                        = new Gson().fromJson(
                                        data.get(
                                                "holder"
                                        ),
                                        Entity.class
                                );

                                if (holder != null) {
                                    temp.getPlayer().setLeashHolder(holder);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_maximum_air"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setMaximumAir(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_maximum_no_damage_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setMaximumNoDamageTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_no_damage_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setNoDamageTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_op"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                boolean value = data.get("value").getAsBoolean();

                                temp.getPlayer().setOp(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_portal_cooldown"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("cooldown")) {
                                int cooldown = data.get("cooldown").getAsInt();

                                temp.getPlayer().setPortalCooldown(cooldown);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_rotation"
                        ),
                        null,
                        (data, temp) -> {
                            float yaw;
                            float pitch;

                            if (data.has("yaw")) {
                                yaw = data.get("yaw").getAsFloat();
                            } else {
                                yaw = 0;
                            }
                            if (data.has("pitch")) {
                                pitch = data.get("pitch").getAsFloat();
                            } else {
                                pitch = 0;
                            }
                            temp.getPlayer().setRotation(yaw, pitch);
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_saturated_regen_rate"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setSaturatedRegenRate(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_saturation"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                temp.getPlayer().setSaturation(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_silent"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                temp.getPlayer().setSilent(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_starvation_rate"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setStarvationRate(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_swimming"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("swimming")) {
                                boolean swimming = data.get("swimming").getAsBoolean();

                                temp.getPlayer().setSwimming(swimming);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_ticks_lived"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                int value = data.get("value").getAsInt();

                                temp.getPlayer().setTicksLived(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_unsaturated_regen_rate"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                temp.getPlayer().setUnsaturatedRegenRate(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_velocity"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("velocity")) {
                                Vector velocity
                                        = new Gson().fromJson(
                                        data.get("velocity"),
                                        Vector.class
                                );

                                if (velocity != null) {
                                    temp.getPlayer().setVelocity(velocity);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_visual_fire"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("fire")) {
                                boolean fire = data.get("fire").getAsBoolean();

                                temp.getPlayer().setVisualFire(fire);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_whitelisted"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("value")) {
                                boolean value = data.get("value").getAsBoolean();

                                temp.getPlayer().setWhitelisted(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_attribute_base_value"
                        ),
                        null,
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
                                            attributeInstance.setBaseValue(baseValue);
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_attributes_base_value"
                        ),
                        null,
                        (data, temp) -> {
                            if (data.has("attributes")) {
                                JsonObject[] attributes
                                        = new Gson().fromJson(
                                        data.get(
                                                "attributes"
                                        ),
                                        JsonObject[].class
                                );

                                if (attributes != null) {
                                    for (JsonObject attribute : attributes) {
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
                                                attributeInstance.setBaseValue(baseValue);
                                            }
                                        }
                                    }
                                }
                            }
                        })
        );
    }
}

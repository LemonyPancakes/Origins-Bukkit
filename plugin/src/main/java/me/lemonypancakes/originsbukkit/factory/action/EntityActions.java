package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import me.lemonypancakes.originsbukkit.storage.Origins;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class EntityActions {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Origins ORIGINS = PLUGIN.getStorageHandler().getOrigins();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public static void register() {
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/send_message"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("message")) {
                                        String message = data.get("message").getAsString();

                                        if (message != null) {
                                            ChatUtils.sendPlayerMessage(player, message);
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/send_messages"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("messages")) {
                                        String[] messages
                                                = new Gson().fromJson(
                                                data.get(
                                                        "messages"
                                                ),
                                                String[].class
                                        );

                                        if (messages != null) {
                                            player.sendMessage(ChatUtils.formatList(messages));
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/damage"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("amount")) {
                                        double amount = data.get("amount").getAsDouble();

                                        livingEntity.damage(amount);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_remaining_air"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        livingEntity.setRemainingAir(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/add_potion_effect"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

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
                                                livingEntity.addPotionEffect(
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
                                                livingEntity.addPotionEffect(
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
                                                livingEntity.addPotionEffect(
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
                                                livingEntity.addPotionEffect(
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
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/add_potion_effects"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

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
                                                    livingEntity.addPotionEffect(
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
                                                    livingEntity.addPotionEffect(
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
                                                    livingEntity.addPotionEffect(
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
                                                    livingEntity.addPotionEffect(
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
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/remove_potion_effect_types"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

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
                                                livingEntity.removePotionEffect(potionEffectType);
                                            }
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_health"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("health")) {
                                        double health = data.get("health").getAsDouble();

                                        livingEntity.setHealth(health);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_glowing"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("flag")) {
                                        boolean flag = data.get("flag").getAsBoolean();

                                        livingEntity.setGlowing(flag);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_flying"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("value")) {
                                        boolean value = data.get("value").getAsBoolean();

                                        player.setFlying(value);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_fly_speed"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("value")) {
                                        float value = data.get("value").getAsFloat();

                                        player.setFlySpeed(value);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_allow_flight"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("flight")) {
                                        boolean flight = data.get("flight").getAsBoolean();

                                        player.setAllowFlight(flight);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_walk_speed"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("value")) {
                                        float value = data.get("value").getAsFloat();

                                        player.setWalkSpeed(value);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_health_scale"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("scale")) {
                                        double scale = data.get("scale").getAsDouble();

                                        player.setHealthScale(scale);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_display_name"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("display_name")) {
                                        String displayName = data.get("display_name").getAsString();

                                        player.setDisplayName(displayName);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_exp"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("exp")) {
                                        float exp = data.get("exp").getAsFloat();

                                        player.setExp(exp);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_health_scaled"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("scale")) {
                                        boolean scale = data.get("scale").getAsBoolean();

                                        player.setHealthScaled(scale);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_level"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("level")) {
                                        int level = data.get("level").getAsInt();

                                        player.setLevel(level);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_time"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("time")) {
                                        long time = data.get("time").getAsLong();
                                        boolean relative = false;

                                        if (data.has("relative")) {
                                            relative = data.get("relative").getAsBoolean();
                                        }

                                        player.setPlayerTime(time, relative);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_weather"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("weather_type")) {
                                        WeatherType weatherType
                                                = new Gson().fromJson(
                                                data.get(
                                                        "weather_type"
                                                ),
                                                WeatherType.class
                                        );

                                        if (weatherType != null) {
                                            player.setPlayerWeather(weatherType);
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_resource_pack"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("resource_pack_url")) {
                                        String resourcePackUrl = data.get("resource_pack_url").getAsString();

                                        if (resourcePackUrl != null) {
                                            player.setResourcePack(resourcePackUrl);
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_total_experience"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("exp")) {
                                        int exp = data.get("exp").getAsInt();

                                        player.setTotalExperience(exp);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_absorption_amount"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("amount")) {
                                        double amount = data.get("amount").getAsDouble();

                                        livingEntity.setAbsorptionAmount(amount);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_exhaustion"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("value")) {
                                        float value = data.get("value").getAsFloat();

                                        player.setExhaustion(value);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_fire_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (data.has("ticks")) {
                                    int ticks = data.get("ticks").getAsInt();

                                    entity.setFireTicks(ticks);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_food_level"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("value")) {
                                        int value = data.get("value").getAsInt();

                                        player.setFoodLevel(value);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_freeze_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (data.has("ticks")) {
                                    int ticks = data.get("ticks").getAsInt();

                                    entity.setFreezeTicks(ticks);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_maximum_air"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        livingEntity.setMaximumAir(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_maximum_no_damage_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        livingEntity.setMaximumNoDamageTicks(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_no_damage_ticks"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        livingEntity.setNoDamageTicks(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_rotation"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
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

                                entity.setRotation(yaw, pitch);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_saturated_regen_rate"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        player.setSaturatedRegenRate(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_saturation"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("value")) {
                                        float value = data.get("value").getAsFloat();

                                        player.setSaturation(value);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_silent"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (data.has("flag")) {
                                    boolean flag = data.get("flag").getAsBoolean();

                                    entity.setSilent(flag);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_starvation_rate"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        player.setStarvationRate(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_unsaturated_regen_rate"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

                                    if (data.has("ticks")) {
                                        int ticks = data.get("ticks").getAsInt();

                                        player.setUnsaturatedRegenRate(ticks);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_velocity"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (data.has("velocity")) {
                                    Vector velocity
                                            = new Gson().fromJson(
                                            data.get("velocity"),
                                            Vector.class
                                    );

                                    if (velocity != null) {
                                        entity.setVelocity(velocity);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_visual_fire"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (data.has("fire")) {
                                    boolean fire = data.get("fire").getAsBoolean();

                                    entity.setVisualFire(fire);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_attribute_base_value"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

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
                                                    attributeInstance.setBaseValue(baseValue);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_attributes_base_value"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;

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
                                                            = player.getAttribute(type);

                                                    if (attributeInstance != null) {
                                                        attributeInstance.setBaseValue(baseValue);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/entity/set_origin"
                        ),
                        null,
                        (data, temp) -> {
                            Entity entity = temp.getEntity();

                            if (entity != null) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    UUID playerUUID = player.getUniqueId();

                                    if (data.has("origin")) {
                                        String originString = data.get("origin").getAsString();

                                        if (originString.contains(":")) {
                                            Identifier identifier = new IdentifierContainer(
                                                    originString.split(":")[0],
                                                    originString.split(":")[1]
                                            );

                                            if (ORIGINS.hasIdentifier(identifier)) {
                                                Origin origin = ORIGINS.getByIdentifier(identifier);

                                                if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                                                    ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(origin);
                                                } else {
                                                    ORIGIN_PLAYERS.add(new OriginPlayerContainer(playerUUID));
                                                    ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(origin);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
        );
    }
}

package me.lemonypancakes.originsbukkit.factory;

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public final class Actions {

    public static void register() {
        registerPlayerActions();
    }

    private static void registerPlayerActions() {
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/send_messages"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("messages")) {
                                String[] messages
                                        = new Gson().fromJson(
                                        data.get(
                                                "messages"
                                        ),
                                        String[].class
                                );

                                if (messages != null) {
                                    player.sendMessage(messages);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/damage"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("amount")) {
                                double amount = data.get("amount").getAsDouble();

                                player.damage(amount);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_remaining_air"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setRemainingAir(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/add_potion_effects"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("effects")) {
                                PotionEffect[] potionEffects
                                        = new Gson().fromJson(
                                        data.get(
                                                "effects"
                                        ),
                                        PotionEffect[].class
                                );

                                if (potionEffects != null) {
                                    for (PotionEffect potionEffect : potionEffects) {
                                        player.addPotionEffect(potionEffect);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/remove_potion_effect_types"
                        ),
                        null,
                        (data, player) -> {
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
                                        player.removePotionEffect(potionEffectType);
                                    }
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_health"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("health")) {
                                double health = data.get("health").getAsDouble();

                                player.setHealth(health);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_glowing"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                player.setGlowing(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_flying"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                boolean value = data.get("value").getAsBoolean();

                                player.setFlying(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_fly_speed"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                player.setFlySpeed(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_allow_flight"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("flight")) {
                                boolean flight = data.get("flight").getAsBoolean();

                                player.setAllowFlight(flight);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_walk_speed"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                player.setWalkSpeed(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_health_scale"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("scale")) {
                                double scale = data.get("scale").getAsDouble();

                                player.setHealthScale(scale);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_bed_spawn_location"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("location")) {
                                Location location
                                        = new Gson().fromJson(
                                                data.get(
                                                        "location"
                                                ),
                                        Location.class
                                );

                                if (location != null) {
                                    player.setBedSpawnLocation(location);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_compass_target"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("location")) {
                                Location location
                                        = new Gson().fromJson(
                                        data.get(
                                                "location"
                                        ),
                                        Location.class
                                );

                                if (location != null) {
                                    player.setCompassTarget(location);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_display_name"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("display_name")) {
                                String displayName = data.get("display_name").getAsString();

                                player.setDisplayName(displayName);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_exp"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("exp")) {
                                float exp = data.get("exp").getAsFloat();

                                player.setExp(exp);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_health_scaled"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("scale")) {
                                boolean scale = data.get("scale").getAsBoolean();

                                player.setHealthScaled(scale);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_level"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("level")) {
                                int level = data.get("level").getAsInt();

                                player.setLevel(level);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_time"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("time")) {
                                long time = data.get("time").getAsLong();
                                boolean relative = false;

                                if (data.has("relative")) {
                                    relative = data.get("relative").getAsBoolean();
                                }
                                player.setPlayerTime(time, relative);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_weather"
                        ),
                        null,
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
                                    player.setPlayerWeather(weatherType);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_resource_pack"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("resource_pack_url")) {
                                String resourcePackUrl = data.get("resource_pack_url").getAsString();

                                if (resourcePackUrl != null) {
                                    player.setResourcePack(resourcePackUrl);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_scoreboard"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("scoreboard")) {
                                Scoreboard scoreboard
                                        = new Gson().fromJson(
                                                data.get(
                                                        "scoreboard"
                                                ),
                                        Scoreboard.class
                                );

                                if (scoreboard != null) {
                                    player.setScoreboard(scoreboard);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_sleeping_ignored"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("is_sleeping")) {
                                boolean isSleeping = data.get("is_sleeping").getAsBoolean();

                                player.setSleepingIgnored(isSleeping);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_sneaking"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("sneak")) {
                                boolean sneak = data.get("sneak").getAsBoolean();

                                player.setSneaking(sneak);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_sprinting"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("sprinting")) {
                                boolean sprinting = data.get("sprinting").getAsBoolean();

                                player.setSprinting(sprinting);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_total_experience"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("exp")) {
                                int exp = data.get("exp").getAsInt();

                                player.setTotalExperience(exp);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_absorption_amount"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("amount")) {
                                double amount = data.get("amount").getAsDouble();

                                player.setAbsorptionAmount(amount);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_ai"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ai")) {
                                boolean ai = data.get("ai").getAsBoolean();

                                player.setAI(ai);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_arrow_cooldown"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setArrowCooldown(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_arrows_in_body"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("count")) {
                                int count = data.get("count").getAsInt();

                                player.setArrowsInBody(count);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_can_pickup_items"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("pickup")) {
                                boolean pickup = data.get("pickup").getAsBoolean();

                                player.setCanPickupItems(pickup);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_collidable"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("collidable")) {
                                boolean collidable = data.get("collidable").getAsBoolean();

                                player.setCollidable(collidable);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_cooldown"
                        ),
                        null,
                        (data, player) -> {
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
                                player.setCooldown(material, ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_custom_name"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("name")) {
                                String name = data.get("name").getAsString();

                                if (name != null) {
                                    player.setCustomName(name);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_custom_name_visible"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                player.setCustomNameVisible(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_exhaustion"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                player.setExhaustion(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_fall_distance"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("distance")) {
                                float distance = data.get("distance").getAsFloat();

                                player.setFallDistance(distance);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_fire_ticks"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setFireTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_food_level"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                int value = data.get("value").getAsInt();

                                player.setFoodLevel(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_freeze_ticks"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setFreezeTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_game_mode"
                        ),
                        null,
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
                                    player.setGameMode(mode);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_gliding"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("gliding")) {
                                boolean gliding = data.get("gliding").getAsBoolean();

                                player.setGliding(gliding);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_gravity"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("gravity")) {
                                boolean gravity = data.get("gravity").getAsBoolean();

                                player.setGravity(gravity);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_invisible"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("invisible")) {
                                boolean invisible = data.get("invisible").getAsBoolean();

                                player.setInvisible(invisible);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_invulnerable"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                player.setInvulnerable(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_last_damage"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("damage")) {
                                double damage = data.get("damage").getAsDouble();

                                player.setLastDamage(damage);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_leash_holder"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("holder")) {
                                Entity holder
                                        = new Gson().fromJson(
                                                data.get(
                                                        "holder"
                                                ),
                                        Entity.class
                                );

                                if (holder != null) {
                                    player.setLeashHolder(holder);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_maximum_air"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setMaximumAir(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_maximum_no_damage_ticks"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setMaximumNoDamageTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_no_damage_ticks"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setNoDamageTicks(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_op"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                boolean value = data.get("value").getAsBoolean();

                                player.setOp(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_portal_cooldown"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("cooldown")) {
                                int cooldown = data.get("cooldown").getAsInt();

                                player.setPortalCooldown(cooldown);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_rotation"
                        ),
                        null,
                        (data, player) -> {
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
                            player.setRotation(yaw, pitch);
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_saturated_regen_rate"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setSaturatedRegenRate(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_saturation"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                float value = data.get("value").getAsFloat();

                                player.setSaturation(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_silent"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("flag")) {
                                boolean flag = data.get("flag").getAsBoolean();

                                player.setSilent(flag);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_starvation_rate"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setStarvationRate(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_swimming"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("swimming")) {
                                boolean swimming = data.get("swimming").getAsBoolean();

                                player.setSwimming(swimming);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_ticks_lived"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                int value = data.get("value").getAsInt();

                                player.setTicksLived(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_unsaturated_regen_rate"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("ticks")) {
                                int ticks = data.get("ticks").getAsInt();

                                player.setUnsaturatedRegenRate(ticks);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_velocity"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("velocity")) {
                                Vector velocity
                                        = new Gson().fromJson(
                                                data.get("velocity"),
                                        Vector.class
                                );

                                if (velocity != null) {
                                    player.setVelocity(velocity);
                                }
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_visual_fire"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("fire")) {
                                boolean fire = data.get("fire").getAsBoolean();

                                player.setVisualFire(fire);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_whitelisted"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("value")) {
                                boolean value = data.get("value").getAsBoolean();

                                player.setWhitelisted(value);
                            }
                        })
        );
        Registry.register(
                new ActionContainer<Player>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/player/set_attribute_base_value"
                        ),
                        null,
                        (data, player) -> {
                            if (data.has("attributes")) {
                                Attribute[] attributes
                                        = new Gson().fromJson(
                                                data.get(
                                                        "attributes"
                                                ),
                                        Attribute[].class
                                );

                                if (attributes != null) {
                                }
                            }
                        })
        );
    }
}

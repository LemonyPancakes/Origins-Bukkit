package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.originsbukkit.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.originsbukkit.util.Comparison;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;

import java.util.function.BiPredicate;

public class EntityConditions {

    public EntityConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndCondition<>(plugin1, null, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftOrCondition<>(plugin1, null, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "advancement"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    NamespacedKey namespacedKey = NamespacedKey.fromString(jsonObject1.get("advancement").getAsString());

                    if (namespacedKey != null) {
                        Advancement advancement = Bukkit.getAdvancement(namespacedKey);

                        if (advancement != null) {
                            return player.getAdvancementProgress(advancement).isDone();
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "air"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return Comparison.parseComparison(jsonObject1).compare(livingEntity.getRemainingAir(), jsonObject1);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attribute"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Attributable) {
                    Attributable attributable = (Attributable) entity;
                    AttributeInstance attributeInstance = attributable.getAttribute(Attribute.valueOf(jsonObject1.get("attribute").getAsString()));

                    if (attributeInstance != null) {
                        return Comparison.parseComparison(jsonObject1).compare(attributeInstance.getValue(), jsonObject1);
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block_collision"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                double offsetX = 0;
                double offsetY = 0;
                double offsetZ = 0;

                if (jsonObject1.has("offset_x")) {
                    offsetX = jsonObject1.get("offset_x").getAsDouble();
                }
                if (jsonObject1.has("offset_y")) {
                    offsetY = jsonObject1.get("offset_y").getAsDouble();
                }
                if (jsonObject1.has("offset_z")) {
                    offsetZ = jsonObject1.get("offset_z").getAsDouble();
                }
                for (BlockFace value : BlockFace.values()) {
                    if (value.ordinal() >= 6) {
                        break;
                    }
                    Block block = entity.getLocation().getBlock().getRelative(value);
                    BoundingBox boundingBox = block.getBoundingBox().clone().expand(offsetX, offsetY, offsetZ);

                    if (boundingBox.overlaps(entity.getBoundingBox())) {
                        return true;
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "daytime"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                return entity.getWorld().getTime() > 0 && entity.getWorld().getTime() < 13000;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "entity_type"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                return entity.getType() == EntityType.valueOf(jsonObject1.get("entity_type").getAsString());
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "exists"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> entity != null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fall_distance"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                return Comparison.parseComparison(jsonObject1).compare(entity.getFallDistance(), jsonObject1);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fall_flying"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return livingEntity.isGliding();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "food_level"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    return Comparison.parseComparison(jsonObject1).compare(humanEntity.getFoodLevel(), jsonObject1);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "gamemode"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (jsonObject1.has("gamemode")) {
                        GameMode gameMode = new Gson().fromJson(jsonObject1.get("gamemode"), GameMode.class);

                        if (gameMode != null) {
                            return player.getGameMode() == gameMode;
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "health"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Damageable) {
                    Damageable damageable = (Damageable) entity;

                    return Comparison.parseComparison(jsonObject1).compare(damageable.getHealth(), jsonObject1);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_block_anywhere"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftInBlockAnywhereAction(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invisible"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return livingEntity.isInvisible() || livingEntity.hasPotionEffect(PotionEffectType.INVISIBILITY);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "living"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                return entity instanceof LivingEntity;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (jsonObject1.has("origin")) {
                        Identifier identifier = Identifier.fromString(jsonObject1.get("origin").getAsString());

                        if (identifier != null) {
                            OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                            if (originPlayer != null) {
                                Origin origin = originPlayer.getOrigin();

                                if (origin != null) {
                                    return origin.getIdentifier().equals(identifier);
                                }
                            }
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "sneaking"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return player.isSneaking();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "sprinting"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return player.isSprinting();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "status_effect"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PotionEffectType potionEffectType = null;
                    int duration = 100;
                    int amplifier = 0;
                    boolean isAmbient = false;
                    boolean showParticles = true;
                    boolean showIcon = true;

                    if (jsonObject1.has("effect")) {
                        potionEffectType = PotionEffectType.getByName(jsonObject1.get("effect").getAsString());
                    }
                    if (jsonObject1.has("duration")) {
                        duration = jsonObject1.get("duration").getAsInt();
                    }
                    if (jsonObject1.has("amplifier")) {
                        amplifier = jsonObject1.get("amplifier").getAsInt();
                    }
                    if (jsonObject1.has("is_ambient")) {
                        isAmbient = jsonObject1.get("is_ambient").getAsBoolean();
                    }
                    if (jsonObject1.has("show_particles")) {
                        showParticles = jsonObject1.get("show_particles").getAsBoolean();
                    }
                    if (jsonObject1.has("show_icon")) {
                        showIcon = jsonObject1.get("show_icon").getAsBoolean();
                    }
                    if (potionEffectType != null) {
                        return livingEntity.hasPotionEffect(potionEffectType);
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "swimming"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return livingEntity.isSwimming();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "tamed"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Tameable) {
                    Tameable tameable = (Tameable) entity;

                    return tameable.isTamed();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "time_of_day"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                return Comparison.parseComparison(jsonObject1).compare(entity.getWorld().getTime(), jsonObject1);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "xp_levels"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return Comparison.parseComparison(jsonObject1).compare(player.getExpToLevel(), jsonObject1);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "xp_points"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return Comparison.parseComparison(jsonObject1).compare(player.getTotalExperience(), jsonObject1);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_water"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftCondition<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                return entity.isInWater() || entity.getLocation().getBlock().getType() == Material.WATER_CAULDRON;
            }
            return false;
        })));
    }

    public static class CraftInBlockAnywhereAction extends CraftCondition<Entity> {

        private Condition<Block> blockCondition;

        public CraftInBlockAnywhereAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Entity> biPredicate) {
            super(plugin, jsonObject, DataType.ENTITY, biPredicate);
            if (jsonObject != null) {
                this.blockCondition = plugin.getLoader().loadCondition(DataType.BLOCK, jsonObject, "block_condition");
                setBiPredicate((jsonObject1, entity) -> Comparison.parseComparison(jsonObject1).compare(count(entity), jsonObject1));
            }
        }

        public int count(Entity entity) {
            int count = 0;

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;

                if (blockCondition.test(livingEntity.getLocation().getBlock())) {
                    count++;
                }
                if (blockCondition.test(livingEntity.getEyeLocation().getBlock())) {
                    count++;
                }
            }
            return count;
        }
    }
}

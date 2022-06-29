package me.lemonypancakes.bukkit.origins.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.*;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Comparison;
import me.lemonypancakes.bukkit.origins.util.Identifier;
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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "advancement"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    NamespacedKey namespacedKey = NamespacedKey.fromString(jsonObject.get("advancement").getAsString());

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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "air"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return Comparison.parseComparison(jsonObject).compare(livingEntity.getRemainingAir(), jsonObject);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "attribute"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Attributable) {
                    Attributable attributable = (Attributable) entity;
                    AttributeInstance attributeInstance = attributable.getAttribute(Attribute.valueOf(jsonObject.get("attribute").getAsString()));

                    if (attributeInstance != null) {
                        return Comparison.parseComparison(jsonObject).compare(attributeInstance.getValue(), jsonObject);
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block_collision"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                double offsetX = 0;
                double offsetY = 0;
                double offsetZ = 0;

                if (jsonObject.has("offset_x")) {
                    offsetX = jsonObject.get("offset_x").getAsDouble();
                }
                if (jsonObject.has("offset_y")) {
                    offsetY = jsonObject.get("offset_y").getAsDouble();
                }
                if (jsonObject.has("offset_z")) {
                    offsetZ = jsonObject.get("offset_z").getAsDouble();
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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "daytime"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                return entity.getWorld().getTime() > 0 && entity.getWorld().getTime() < 13000;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "entity_type"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                return entity.getType() == EntityType.valueOf(jsonObject.get("entity_type").getAsString());
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "exists"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> entity != null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fall_distance"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                return Comparison.parseComparison(jsonObject).compare(entity.getFallDistance(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fall_flying"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return livingEntity.isGliding();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "food_level"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    return Comparison.parseComparison(jsonObject).compare(humanEntity.getFoodLevel(), jsonObject);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "gamemode"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (jsonObject.has("gamemode")) {
                        GameMode gameMode = new Gson().fromJson(jsonObject.get("gamemode"), GameMode.class);

                        if (gameMode != null) {
                            return player.getGameMode() == gameMode;
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "health"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Damageable) {
                    Damageable damageable = (Damageable) entity;

                    return Comparison.parseComparison(jsonObject).compare(damageable.getHealth(), jsonObject);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_block_anywhere"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftInBlockAnywhereAction(p, j, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "invisible"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return livingEntity.isInvisible() || livingEntity.hasPotionEffect(PotionEffectType.INVISIBILITY);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "living"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                return entity instanceof LivingEntity;
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "origin"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (jsonObject.has("origin")) {
                        Identifier identifier = Identifier.fromString(jsonObject.get("origin").getAsString());

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
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "sneaking"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return player.isSneaking();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "sprinting"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return player.isSprinting();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "status_effect"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PotionEffectType potionEffectType = null;
                    int duration = 100;
                    int amplifier = 0;
                    boolean isAmbient = false;
                    boolean showParticles = true;
                    boolean showIcon = true;

                    if (jsonObject.has("effect")) {
                        potionEffectType = PotionEffectType.getByName(jsonObject.get("effect").getAsString());
                    }
                    if (jsonObject.has("duration")) {
                        duration = jsonObject.get("duration").getAsInt();
                    }
                    if (jsonObject.has("amplifier")) {
                        amplifier = jsonObject.get("amplifier").getAsInt();
                    }
                    if (jsonObject.has("is_ambient")) {
                        isAmbient = jsonObject.get("is_ambient").getAsBoolean();
                    }
                    if (jsonObject.has("show_particles")) {
                        showParticles = jsonObject.get("show_particles").getAsBoolean();
                    }
                    if (jsonObject.has("show_icon")) {
                        showIcon = jsonObject.get("show_icon").getAsBoolean();
                    }
                    if (potionEffectType != null) {
                        return livingEntity.hasPotionEffect(potionEffectType);
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "swimming"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    return livingEntity.isSwimming();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "tamed"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Tameable) {
                    Tameable tameable = (Tameable) entity;

                    return tameable.isTamed();
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "time_of_day"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                return Comparison.parseComparison(jsonObject).compare(entity.getWorld().getTime(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "xp_levels"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return Comparison.parseComparison(jsonObject).compare(player.getExpToLevel(), jsonObject);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "xp_points"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    return Comparison.parseComparison(jsonObject).compare(player.getTotalExperience(), jsonObject);
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "in_water"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, entity) -> {
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

package me.lemonypancakes.originsbukkit.util;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUtils {

    public static void heal(Player player, double amount) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attributeInstance != null) {
            if (player.getHealth() < attributeInstance.getBaseValue()) {
                player.setHealth(Math.min((player.getHealth() + amount), attributeInstance.getBaseValue()));
            }
        }
    }

    public static void feed(Player player, int amount) {
        if (player.getFoodLevel() < 20) {
            player.setFoodLevel(Math.min((player.getFoodLevel() + amount), 20));
        }
    }

    public static void saturate(Player player, float amount) {
        if (player.getSaturation() < 20) {
            player.setSaturation(Math.min((player.getSaturation() + amount), 20));
        }
    }

    public static void exhaust(Player player, int amount) {
        if (player.getSaturation() > 0) {
            player.setSaturation(Math.max(0, player.getSaturation() - amount));
        } else {
            if (player.getFoodLevel() > 0) {
                player.setFoodLevel(Math.max(0, player.getFoodLevel() - amount));
            }
        }
    }

    public static void setMaxHealth(Player player, double amount) {
        AttributeInstance genericMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (genericMaxHealth != null) {
            genericMaxHealth.setBaseValue(amount);
        }
        player.setHealthScale(amount);
    }

    public static double getMaxHealth(Player player) {
        AttributeInstance genericMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (genericMaxHealth != null) {
            return genericMaxHealth.getBaseValue();
        }
        return 20;
    }

    public static <T, Z> Z getPersistentData(Player player, String string, PersistentDataType<T, Z> dataType) {
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(string);

        if (namespacedKey != null) {
            if (dataContainer.has(namespacedKey, dataType)) {
                return dataContainer.get(namespacedKey, dataType);
            }
        }
        return null;
    }

    public static <T, Z> void setPersistentData(Player player, String string, PersistentDataType<T, Z> dataType, Z z) {
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        NamespacedKey namespacedKey = NamespacedKey.fromString(string);

        if (namespacedKey != null) {
            dataContainer.set(namespacedKey, dataType, z);
        }
    }

    public static void resetDefaults(Player player) {
        GameMode gameMode = player.getGameMode();
        AttributeInstance genericArmor = player.getAttribute(Attribute.GENERIC_ARMOR);
        AttributeInstance genericArmorToughness = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        AttributeInstance genericLuck = player.getAttribute(Attribute.GENERIC_LUCK);
        AttributeInstance genericAttackDamage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        AttributeInstance genericAttackKnockBack = player.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK);
        AttributeInstance genericAttackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        AttributeInstance genericFlyingSpeed = player.getAttribute(Attribute.GENERIC_FLYING_SPEED);
        AttributeInstance genericFollowRange = player.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
        AttributeInstance genericKnockBackResistance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        AttributeInstance genericMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        AttributeInstance genericMovementSpeed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        AttributeInstance horseJumpStrength = player.getAttribute(Attribute.HORSE_JUMP_STRENGTH);
        AttributeInstance zombieSpawnReinforcements = player.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS);

        if (genericArmor != null) {
            genericArmor.setBaseValue(genericArmor.getDefaultValue());
        }
        if (genericArmorToughness != null) {
            genericArmorToughness.setBaseValue(genericArmorToughness.getDefaultValue());
        }
        if (genericLuck != null) {
            genericLuck.setBaseValue(genericLuck.getDefaultValue());
        }
        if (genericAttackDamage != null) {
            genericAttackDamage.setBaseValue(genericAttackDamage.getDefaultValue());
        }
        if (genericAttackKnockBack != null) {
            genericAttackKnockBack.setBaseValue(genericAttackKnockBack.getDefaultValue());
        }
        if (genericAttackSpeed != null) {
            genericAttackSpeed.setBaseValue(genericAttackSpeed.getDefaultValue());
        }
        if (genericFlyingSpeed != null) {
            genericFlyingSpeed.setBaseValue(genericFlyingSpeed.getDefaultValue());
        }
        if (genericFollowRange != null) {
            genericFollowRange.setBaseValue(genericFollowRange.getDefaultValue());
        }
        if (genericKnockBackResistance != null) {
            genericKnockBackResistance.setBaseValue(genericKnockBackResistance.getDefaultValue());
        }
        if (genericMaxHealth != null) {
            genericMaxHealth.setBaseValue(genericMaxHealth.getDefaultValue());
        }
        if (genericMovementSpeed != null) {
            genericMovementSpeed.setBaseValue(genericMovementSpeed.getDefaultValue());
        }
        if (horseJumpStrength != null) {
            horseJumpStrength.setBaseValue(horseJumpStrength.getDefaultValue());
        }
        if (zombieSpawnReinforcements != null) {
            zombieSpawnReinforcements.setBaseValue(zombieSpawnReinforcements.getDefaultValue());
        }
        player.setHealthScale(20);
        player.setInvisible(false);
        player.setGravity(true);
        player.setGlowing(false);
        player.setFireTicks(0);
        player.setFreezeTicks(0);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }
}

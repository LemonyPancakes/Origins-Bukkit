package me.lemonypancakes.bukkit.origins.factory.condition;

import com.google.gson.Gson;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftAndCondition;
import me.lemonypancakes.bukkit.origins.factory.condition.meta.CraftOrCondition;
import me.lemonypancakes.bukkit.origins.util.Comparison;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

public class ItemConditions {

    public ItemConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftAndCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "or"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftOrCondition<>(p, j, d, null)));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "amount"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                return Comparison.parseComparison(jsonObject).compare(itemStack.getAmount(), jsonObject);
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "armor_value"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "empty"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                return itemStack.getType().isAir();
            }
            return true;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "enchantment"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                if (jsonObject.has("enchantment")) {
                    NamespacedKey namespacedKey = NamespacedKey.fromString(jsonObject.get("enchantment").getAsString());

                    if (namespacedKey != null) {
                        Enchantment enchantment = Enchantment.getByKey(namespacedKey);

                        if (enchantment != null) {
                            return Comparison.parseComparison(jsonObject).compare(itemStack.getEnchantmentLevel(enchantment), jsonObject);
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "fireproof"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                return !itemStack.getType().isFlammable() || !itemStack.getType().isBurnable();
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "food"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                return itemStack.getType().isEdible();
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "harvest_level"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "ingredient"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                Material item = null;
                Material[] items = null;

                if (jsonObject.has("item")) {
                    item = Material.valueOf(jsonObject.get("item").getAsString());
                }
                if (jsonObject.has("items")) {
                    items = new Gson().fromJson(jsonObject.get("items"), Material[].class);
                }
                return itemStack.getType() == item || items != null && Arrays.asList(items).contains(itemStack.getType());
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "meat"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nbt"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "smeltable"), DataType.ITEM_STACK, (p) -> (j) -> (d) -> () -> new CraftCondition<>(p, j, d, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                return itemStack.getType().isFuel();
            }
            return false;
        })));
    }
}

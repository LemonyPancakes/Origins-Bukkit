package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStackConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/item_stack/compare_material_type"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("material_type")) {
                                Material materialType
                                        = new Gson().fromJson(
                                                data.get(
                                                        "material_type"
                                                ),
                                        Material.class
                                );

                                if (materialType != null) {
                                    return itemStack.getType() == materialType;
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/item_stack/compare_amount"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("amount")) {
                                int amount = data.get("amount").getAsInt();

                                return itemStack.getAmount() == amount;
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/item_stack/compare_max_stack_size"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("max_stack_size")) {
                                int maxStackSize = data.get("max_stack_size").getAsInt();

                                return itemStack.getMaxStackSize() == maxStackSize;
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/item_stack/compare_enchantment/level"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("enchantment")) {
                                JsonObject enchantment = data.getAsJsonObject("enchantment");

                                if (enchantment != null) {
                                    Enchantment type = null;
                                    Integer level = null;

                                    if (enchantment.has("type")) {
                                        type = Enchantment.getByKey(
                                                NamespacedKey.fromString(
                                                        enchantment.get(
                                                                "type"
                                                        ).getAsString()
                                                )
                                        );
                                    }
                                    if (enchantment.has("level")) {
                                        level = enchantment.get("level").getAsInt();
                                    }
                                    if (type != null && level != null) {
                                        return itemStack.getEnchantmentLevel(type) == level;
                                    }
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/item_stack/contains_enchantment_type"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("enchantment_type")) {
                                Enchantment enchantmentType = Enchantment.getByKey(
                                        NamespacedKey.fromString(
                                                data.get(
                                                        "enchantment_type"
                                                ).getAsString()
                                        )
                                );

                                if (enchantmentType != null) {
                                    return itemStack.containsEnchantment(enchantmentType);
                                }
                            }
                            return false;
                        }
                )
        );
        Registry.register(
                new ConditionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/item_stack/contains_enchantment_types"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("enchantment_types")) {
                                String[] enchantmentTypes
                                        = new Gson().fromJson(
                                                data.get(
                                                        "enchantment_types"
                                                ),
                                        String[].class
                                );

                                if (enchantmentTypes != null) {
                                    List<Enchantment> enchantments = new ArrayList<>();

                                    Arrays.stream(enchantmentTypes).forEach(string -> enchantments.add(
                                            Enchantment.getByKey(
                                                    NamespacedKey.fromString(
                                                            string
                                                    )
                                            )
                                    ));
                                    for (Enchantment enchantment : enchantments) {
                                        if (!itemStack.containsEnchantment(enchantment)) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                )
        );
    }
}

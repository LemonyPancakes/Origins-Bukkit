package me.lemonypancakes.originsbukkit.factory.action;

import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.factory.action.meta.*;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemActions {

    public ItemActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ITEM_STACK, new CraftAndAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.ITEM_STACK, new CraftChanceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.ITEM_STACK, new CraftChoiceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.ITEM_STACK, new CraftDelayAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.ITEM_STACK, new CraftIfElseAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.ITEM_STACK, new CraftIfElseListAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.ITEM_STACK, new CraftNothingAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "consume"), DataType.ITEM_STACK, new CraftAction<>(plugin, null, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                if (jsonObject.has("amount")) {
                    itemStack.setAmount(itemStack.getAmount() - jsonObject.get("amount").getAsInt());
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "damage"), DataType.ITEM_STACK, new CraftAction<>(plugin, null, (jsonObject, itemStack) -> {
            if (itemStack != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta != null) {
                    if (itemMeta instanceof Damageable) {
                        Damageable damageable = (Damageable) itemMeta;
                        boolean ignoreUnbreaking = false;
                        int amount = 0;

                        if (jsonObject.has("ignore_unbreaking")) {
                            ignoreUnbreaking = jsonObject.get("ignore_unbreaking").getAsBoolean();
                        }
                        if (jsonObject.has("amount")) {
                            amount = jsonObject.get("amount").getAsInt();
                        }
                        if (ignoreUnbreaking) {
                            if (itemStack.containsEnchantment(Enchantment.DURABILITY)) {
                                amount = amount * itemStack.getEnchantmentLevel(Enchantment.DURABILITY);
                            }
                        }
                        if (damageable.getDamage() < itemStack.getType().getMaxDurability()) {
                            damageable.setDamage(damageable.getDamage() + amount);
                            itemStack.setItemMeta(itemMeta);
                        } else {
                            itemStack.setAmount(0);
                        }
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "modify"), DataType.ITEM_STACK, new CraftAction<>(plugin, null, (jsonObject, itemStack) -> {
            if (itemStack != null) {

            }
        })));
    }
}

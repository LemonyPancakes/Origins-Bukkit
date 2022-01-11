package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackActions {

    public static void register() {
        Registry.register(
                new ActionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/item_stack/set_amount"
                        ), null,
                        (data, itemStack) -> {
                            if (data.has("amount")) {
                                int amount = data.get("amount").getAsInt();

                                itemStack.setAmount(amount);
                            }
                        }
                )
        );
        Registry.register(
                new ActionContainer<ItemStack>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/item_stack/set_material_type"
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
                                    itemStack.setType(materialType);
                                }
                            }
                        }
                )
        );
    }
}

package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.Gson;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import org.bukkit.Material;

public class ItemStackActions {

    public static void register() {
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/item_stack/set_amount"
                        ), null,
                        (data, temp) -> {
                            if (data.has("amount")) {
                                int amount = data.get("amount").getAsInt();

                                temp.getItemStack().setAmount(amount);
                            }
                        }
                )
        );
        Registry.register(
                new ActionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "action/item_stack/set_material_type"
                        ), null,
                        (data, temp) -> {
                            if (data.has("material_type")) {
                                Material materialType
                                        = new Gson().fromJson(
                                        data.get(
                                                "material_type"
                                        ),
                                        Material.class
                                );

                                if (materialType != null) {
                                    temp.getItemStack().setType(materialType);
                                }
                            }
                        }
                )
        );
    }
}

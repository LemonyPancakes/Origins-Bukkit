package me.lemonypancakes.bukkit.origins.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;
import me.lemonypancakes.bukkit.origins.util.ProbabilityUtils;
import me.lemonypancakes.bukkit.origins.wrapper.Element;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class CraftChoiceAction<T> extends CraftAction<T> {

    private Element<T>[] elements;
    private int totalWeight;

    public CraftChoiceAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.elements = plugin.getLoader().loadElements(dataType, jsonObject);
            Arrays.stream(elements).forEach(element -> totalWeight = totalWeight + element.getWeight());
            setBiConsumer((jsonObject1, t) -> {
                for (Element<T> element : elements) {
                    if (ProbabilityUtils.getChance((float) (element.getWeight() / totalWeight))) {
                        element.getAction().accept(t);
                        break;
                    }
                }
            });
        }
    }
}

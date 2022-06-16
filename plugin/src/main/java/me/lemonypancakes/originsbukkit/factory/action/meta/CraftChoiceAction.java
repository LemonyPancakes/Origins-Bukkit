package me.lemonypancakes.originsbukkit.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.util.ProbabilityUtils;
import me.lemonypancakes.originsbukkit.wrapper.Element;

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
            setBiConsumer((jsonObject1, temp) -> {
                for (Element<T> element : elements) {
                    if (ProbabilityUtils.getChance((float) (element.getWeight() / totalWeight))) {
                        element.getAction().accept(temp);
                        break;
                    }
                }
            });
        }
    }

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftChoiceAction<>(plugin, jsonObject, dataType, getBiConsumer());
    }
}

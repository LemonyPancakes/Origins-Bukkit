package me.lemonypancakes.originsbukkit.factory.condition.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftCondition;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class CraftAndCondition<T> extends CraftCondition<T> {

    private Condition<T>[] conditions;

    public CraftAndCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiPredicate<JsonObject, T> biPredicate) {
        super(plugin, jsonObject, dataType, biPredicate);
        if (jsonObject != null) {
            this.conditions = plugin.getLoader().loadConditions(dataType, jsonObject.getAsJsonArray("conditions"));
            setBiPredicate(((jsonObject1, temp) -> Arrays.stream(conditions).allMatch(condition -> condition.test(temp))));
        }
    }
}

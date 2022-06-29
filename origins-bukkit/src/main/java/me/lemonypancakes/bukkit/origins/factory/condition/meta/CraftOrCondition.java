package me.lemonypancakes.bukkit.origins.factory.condition.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftCondition;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class CraftOrCondition<T> extends CraftCondition<T> {

    private Condition<T>[] conditions;

    public CraftOrCondition(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiPredicate<JsonObject, T> biPredicate) {
        super(plugin, jsonObject, dataType, biPredicate);
        if (jsonObject != null) {
            this.conditions = plugin.getLoader().loadConditions(dataType, jsonObject.getAsJsonArray("conditions"));
            setBiPredicate(((jsonObject1, t) -> Arrays.stream(conditions).anyMatch(condition -> condition.test(t))));
        }
    }
}

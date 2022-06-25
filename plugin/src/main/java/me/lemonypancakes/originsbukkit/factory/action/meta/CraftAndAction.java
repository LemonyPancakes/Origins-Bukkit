package me.lemonypancakes.originsbukkit.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class CraftAndAction<T> extends CraftAction<T> {

    private Action<T>[] actions;

    public CraftAndAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.actions = plugin.getLoader().loadActions(dataType, jsonObject.getAsJsonArray("actions"));
            setBiConsumer((jsonObject1, t) -> Arrays.stream(actions).forEach(action -> action.accept(t)));
        }
    }
}

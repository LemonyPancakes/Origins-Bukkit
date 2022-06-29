package me.lemonypancakes.bukkit.origins.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;

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

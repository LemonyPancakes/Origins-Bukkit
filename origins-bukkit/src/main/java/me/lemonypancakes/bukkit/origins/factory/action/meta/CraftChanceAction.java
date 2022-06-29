package me.lemonypancakes.bukkit.origins.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;
import me.lemonypancakes.bukkit.origins.util.ProbabilityUtils;

import java.util.function.BiConsumer;

public class CraftChanceAction<T> extends CraftAction<T> {

    private Action<T> action;
    private Action<T> failAction;

    public CraftChanceAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.action = plugin.getLoader().loadAction(dataType, jsonObject);
            this.failAction = plugin.getLoader().loadAction(dataType, jsonObject, "fail_action");
            setBiConsumer((jsonObject1, t) -> {
                if (jsonObject1.has("chance")) {
                    if (ProbabilityUtils.getChance(jsonObject1.get("chance").getAsFloat())) {
                        action.accept(t);
                    } else {
                        failAction.accept(t);
                    }
                }
            });
        }
    }
}

package me.lemonypancakes.originsbukkit.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.util.ProbabilityUtils;

import java.util.function.BiConsumer;

public class CraftChanceAction<T> extends CraftAction<T> {

    private Action<T> action;
    private Action<T> failAction;

    public CraftChanceAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.action = plugin.getLoader().loadAction(dataType, jsonObject);
            this.failAction = plugin.getLoader().loadAction(dataType, jsonObject, "fail_action");
            setBiConsumer((jsonObject1, temp) -> {
                if (jsonObject1.has("chance")) {
                    if (ProbabilityUtils.getChance(jsonObject1.get("chance").getAsFloat())) {
                        action.accept(temp);
                    } else {
                        failAction.accept(temp);
                    }
                }
            });
        }
    }

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftChanceAction<>(plugin, jsonObject, dataType, getBiConsumer());
    }
}

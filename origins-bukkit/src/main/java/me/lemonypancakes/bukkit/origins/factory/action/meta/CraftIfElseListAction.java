package me.lemonypancakes.bukkit.origins.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;
import me.lemonypancakes.bukkit.origins.wrapper.ConditionAction;

import java.util.function.BiConsumer;

public class CraftIfElseListAction<T> extends CraftAction<T> {

    private ConditionAction<T>[] conditionActions;

    public CraftIfElseListAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.conditionActions = plugin.getLoader().loadConditionActions(dataType, jsonObject);
            setBiConsumer((jsonObject1, t) -> {
                for (ConditionAction<T> conditionAction : conditionActions) {
                    if (conditionAction.getCondition().test(t)) {
                        conditionAction.getAction().accept(t);
                        break;
                    }
                }
            });
        }
    }
}

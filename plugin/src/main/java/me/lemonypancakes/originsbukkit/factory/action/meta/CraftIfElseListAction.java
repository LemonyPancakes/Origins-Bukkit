package me.lemonypancakes.originsbukkit.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.wrapper.ConditionAction;

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

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftIfElseListAction<>(plugin, jsonObject, dataType, getBiConsumer());
    }
}

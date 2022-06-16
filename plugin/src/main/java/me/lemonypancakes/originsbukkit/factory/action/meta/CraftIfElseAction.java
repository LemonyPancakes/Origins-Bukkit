package me.lemonypancakes.originsbukkit.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;

import java.util.function.BiConsumer;

public class CraftIfElseAction<T> extends CraftAction<T> {

    private Condition<T> condition;
    private Action<T> ifAction;
    private Action<T> elseAction;

    public CraftIfElseAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.condition = plugin.getLoader().loadCondition(dataType, jsonObject);
            this.ifAction = plugin.getLoader().loadAction(dataType, jsonObject, "if_action");
            this.elseAction = plugin.getLoader().loadAction(dataType, jsonObject, "else_action");
            setBiConsumer((jsonObject1, temp) -> {
                if (condition.test(temp)) {
                    ifAction.accept(temp);
                } else {
                    elseAction.accept(temp);
                }
            });
        }
    }

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftIfElseAction<>(plugin, jsonObject, dataType, getBiConsumer());
    }
}

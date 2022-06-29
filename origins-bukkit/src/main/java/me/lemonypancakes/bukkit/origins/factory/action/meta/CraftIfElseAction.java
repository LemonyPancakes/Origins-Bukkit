package me.lemonypancakes.bukkit.origins.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.Condition;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;

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
            setBiConsumer((jsonObject1, t) -> {
                if (condition.test(t)) {
                    ifAction.accept(t);
                } else {
                    elseAction.accept(t);
                }
            });
        }
    }
}

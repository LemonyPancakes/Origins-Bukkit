package me.lemonypancakes.originsbukkit.util;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Testing<T> {

    private final DataType<T> dataType;
    private final Consumer<T> consumer;

    public Testing(DataType<T> dataType, Consumer<T> consumer) {
        this.dataType = dataType;
        this.consumer = consumer;
        BiFunction<OriginsBukkitPlugin, JsonObject, Function<DataType<T>, Supplier<Action<T>>>> biFunction
                = ((plugin, jsonObject) -> (dataType1 -> () -> new CraftAction<>(plugin, jsonObject, dataType1, null)));
        Action<T> action = biFunction.apply(null, null).apply(null).get();
    }

    public DataType<T> getDataType() {
        return dataType;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }
}

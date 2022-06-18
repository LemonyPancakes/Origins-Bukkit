package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.DataType;

import java.util.function.Consumer;

public class Testing<T> {

    private final DataType<T> dataType;
    private final Consumer<T> consumer;

    public Testing(DataType<T> dataType, Consumer<T> consumer) {
        this.dataType = dataType;
        this.consumer = consumer;
    }

    public DataType<T> getDataType() {
        return dataType;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }
}

package me.lemonypancakes.originsbukkit;

public interface Temp {

    <T> T get(DataType<T> type);

    <T> void set(DataType<T> type, T t);
}

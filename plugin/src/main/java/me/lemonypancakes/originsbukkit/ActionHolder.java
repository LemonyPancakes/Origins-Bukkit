package me.lemonypancakes.originsbukkit;

public interface ActionHolder<T> {

    Action<T> getAction();

    void setAction(Action<T> action);
}

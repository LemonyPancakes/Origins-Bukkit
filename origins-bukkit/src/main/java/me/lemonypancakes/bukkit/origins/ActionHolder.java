package me.lemonypancakes.bukkit.origins;

public interface ActionHolder<T> {

    Action<T> getAction();

    void setAction(Action<T> action);
}

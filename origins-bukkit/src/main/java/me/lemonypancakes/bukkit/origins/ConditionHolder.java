package me.lemonypancakes.bukkit.origins;

public interface ConditionHolder<T> {

    Condition<T> getCondition();

    void setCondition(Condition<T> condition);
}

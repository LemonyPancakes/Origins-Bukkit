package me.lemonypancakes.originsbukkit;

public interface ConditionHolder<T> {

    Condition<T> getCondition();

    void setCondition(Condition<T> condition);
}

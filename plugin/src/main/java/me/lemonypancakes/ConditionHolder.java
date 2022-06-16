package me.lemonypancakes;

import me.lemonypancakes.originsbukkit.Condition;

public interface ConditionHolder<T> {

    Condition<T> getCondition();

    void setCondition(Condition<T> condition);
}

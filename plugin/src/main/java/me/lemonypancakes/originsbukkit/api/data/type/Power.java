package me.lemonypancakes.originsbukkit.api.data.type;

public interface Power {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);

    <T> Action<T> getAction();

    <T> void setAction(Action<T> action);

    <T> Condition<T> getCondition();

    <T> void setCondition(Condition<T> condition);
}

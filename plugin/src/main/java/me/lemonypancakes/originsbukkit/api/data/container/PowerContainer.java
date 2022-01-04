package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;

@SuppressWarnings({"unchecked", "unused"})
public class PowerContainer implements Power {

    private Identifier identifier;
    private Action<?> action;
    private Condition<?> condition;

    public PowerContainer(Identifier identifier,
                          Action<?> action,
                          Condition<?> condition) {
        this.identifier = identifier;
        this.action = action;
        this.condition = condition;
    }

    public PowerContainer(Identifier identifier,
                          Action<?> action) {
        this.identifier = identifier;
        this.action = action;
    }

    public PowerContainer(Identifier identifier) {
        this.identifier = identifier;
    }

    public PowerContainer() {}

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public <T> Action<T> getAction() {
        return (Action<T>) action;
    }

    @Override
    public <T> void setAction(Action<T> action) {
        this.action = action;
    }

    @Override
    public <T> Condition<T> getCondition() {
        return (Condition<T>) condition;
    }

    @Override
    public <T> void setCondition(Condition<T> condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "PowerContainer{" +
                "identifier=" + identifier +
                ", action=" + action +
                ", condition=" + condition +
                '}';
    }
}

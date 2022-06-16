package me.lemonypancakes.originsbukkit.wrapper;

import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.Condition;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;

public class ConditionAction<T> {

    private final OriginsBukkitPlugin plugin;
    private final Condition<T> condition;
    private final Action<T> action;

    public ConditionAction(OriginsBukkitPlugin plugin, Condition<T> condition, Action<T> action) {
        this.plugin = plugin;
        this.condition = condition;
        this.action = action;
    }

    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    public Condition<T> getCondition() {
        return condition;
    }

    public Action<T> getAction() {
        return action;
    }
}

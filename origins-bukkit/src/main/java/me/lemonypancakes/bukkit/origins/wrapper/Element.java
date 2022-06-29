package me.lemonypancakes.bukkit.origins.wrapper;

import me.lemonypancakes.bukkit.origins.Action;

public class Element<T> {

    private final Action<T> action;
    private final int weight;

    public Element(Action<T> action, int weight) {
        this.action = action;
        this.weight = weight;
    }

    public Action<T> getAction() {
        return action;
    }

    public int getWeight() {
        return weight;
    }
}

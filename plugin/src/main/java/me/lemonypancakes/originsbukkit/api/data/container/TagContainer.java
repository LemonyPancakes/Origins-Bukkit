package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Tag;

import java.util.HashSet;
import java.util.Set;

public class TagContainer<T> implements Tag<T> {

    private Identifier identifier;

    private final Set<T> values = new HashSet<>();

    public TagContainer(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public Set<T> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "TagContainer{" +
                "identifier=" + identifier +
                ", values=" + values +
                '}';
    }
}

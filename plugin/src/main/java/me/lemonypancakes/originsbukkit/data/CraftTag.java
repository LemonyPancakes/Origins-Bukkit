package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.Tag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CraftTag<T> implements Tag<T> {

    private Identifier identifier;

    private final Set<T> values = new HashSet<>();

    public CraftTag(Identifier identifier) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftTag)) return false;
        CraftTag<?> that = (CraftTag<?>) o;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getValues(), that.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getValues());
    }

    @Override
    public String toString() {
        return "TagContainer{" +
                "identifier=" + identifier +
                ", values=" + values +
                '}';
    }
}

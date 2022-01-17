package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Tag;

import java.util.HashSet;
import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagContainer)) return false;
        TagContainer<?> that = (TagContainer<?>) o;
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

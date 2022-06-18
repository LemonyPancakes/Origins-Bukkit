package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.Tag;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CraftTag<T> implements Tag<T> {

    private Identifier identifier;
    private final DataType<T> dataType;
    private Set<T> values = new HashSet<>();

    public CraftTag(Identifier identifier, DataType<T> dataType, Set<T> values) {
        this.identifier = identifier;
        this.dataType = dataType;
        this.values = values;
    }

    public CraftTag(Identifier identifier, DataType<T> dataType) {
        this.identifier = identifier;
        this.dataType = dataType;
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
    public DataType<T> getDataType() {
        return dataType;
    }

    @Override
    public Set<T> getValues() {
        return values;
    }

    @Override
    public void setValues(Set<T> values) {
        this.values = values;
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

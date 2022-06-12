package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.DataType;

import java.util.Objects;

public class CraftDataType<T> implements DataType<T> {

    private final Class<T> type;

    public CraftDataType(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftDataType)) return false;
        CraftDataType<?> that = (CraftDataType<?>) o;
        return Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }

    @Override
    public String toString() {
        return "CraftDataType{" +
                "type=" + type +
                '}';
    }
}

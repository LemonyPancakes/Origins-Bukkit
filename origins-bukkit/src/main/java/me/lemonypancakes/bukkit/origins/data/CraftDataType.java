package me.lemonypancakes.bukkit.origins.data;

import me.lemonypancakes.bukkit.origins.DataType;

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
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftDataType)) return false;
        CraftDataType<?> that = (CraftDataType<?>) itemStack;
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

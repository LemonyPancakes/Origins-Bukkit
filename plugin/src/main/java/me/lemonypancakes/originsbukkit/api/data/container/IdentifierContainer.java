package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;

import java.util.Objects;

public class IdentifierContainer implements Identifier {

    private final String key;
    private final String value;

    public IdentifierContainer(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getIdentifier() {
        return key + ":" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifierContainer)) return false;
        IdentifierContainer that = (IdentifierContainer) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }

    @Override
    public String toString() {
        return "IdentifierContainer{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

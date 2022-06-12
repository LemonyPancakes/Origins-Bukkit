package me.lemonypancakes.originsbukkit.util;

import org.bukkit.NamespacedKey;

import java.util.Objects;

public final class Identifier {

    private final String key;
    private final String value;

    public Identifier(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public NamespacedKey toNameSpacedKey() {
        return NamespacedKey.fromString(toString());
    }

    public String toUnderscored() {
        return toString().replace(":", "_").replace("-", "_").toUpperCase();
    }

    public static Identifier fromString(String string) {
        if (string.contains(":")) {
            return new Identifier(string.split(":")[0], string.split(":")[1]);
        }
        return null;
    }

    public static Identifier fromNameSpacedKey(NamespacedKey namespacedKey) {
        return fromString(namespacedKey.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identifier)) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}

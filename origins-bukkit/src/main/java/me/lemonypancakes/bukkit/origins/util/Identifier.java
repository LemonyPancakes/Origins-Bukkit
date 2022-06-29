package me.lemonypancakes.bukkit.origins.util;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Identifier {

    public static final String MINECRAFT = "minecraft";
    public static final String BUKKIT = "bukkit";
    public static final String ORIGINS_BUKKIT = "origins-bukkit";
    private static final Pattern VALID_KEY = Pattern.compile("[a-z0-9/._-]+");
    private static final Pattern VALID_VALUE = Pattern.compile("[a-z0-9/._-]+");
    private final String key;
    private final String value;

    public Identifier(String key, String value) {
        Preconditions.checkArgument(key != null && VALID_KEY.matcher(key).matches(), "Invalid identifier key. Must be [a-z0-9/._-]: %s", key);
        Preconditions.checkArgument(value != null && VALID_VALUE.matcher(value).matches(), "Invalid identifier value. Must be [a-z0-9/._-]: %s", value);
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

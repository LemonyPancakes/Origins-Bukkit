/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof Identifier)) return false;
        Identifier that = (Identifier) itemStack;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}

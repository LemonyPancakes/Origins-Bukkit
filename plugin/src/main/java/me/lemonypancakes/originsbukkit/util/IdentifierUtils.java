package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;

public class IdentifierUtils {

    public static Identifier createIdentifier(String key, String value) {
        return new IdentifierContainer(key, value);
    }

    public static Identifier fromString(String string) {
        if (string.contains(":")) {
            return new IdentifierContainer(
                    string.split(":")[0],
                    string.split(":")[1]
            );
        }
        return new IdentifierContainer("undefined", string);
    }
}

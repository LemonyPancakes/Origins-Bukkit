package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;

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
    public String toString() {
        return "IdentifierContainer{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

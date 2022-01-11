package me.lemonypancakes.originsbukkit.api.data.type;

import java.util.Set;

public interface Tag<T> {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);

    Set<T> getValues();
}

package me.lemonypancakes.originsbukkit;

import java.util.Set;

public interface Tag<T> extends Identifiable {

    Set<T> getValues();
}

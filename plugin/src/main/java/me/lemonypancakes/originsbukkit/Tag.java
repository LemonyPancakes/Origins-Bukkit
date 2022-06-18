package me.lemonypancakes.originsbukkit;

import java.util.Set;

public interface Tag<T> extends Identifiable {

    DataType<T> getDataType();

    Set<T> getValues();

    void setValues(Set<T> values);
}

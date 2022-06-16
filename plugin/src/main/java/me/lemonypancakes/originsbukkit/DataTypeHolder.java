package me.lemonypancakes.originsbukkit;

public interface DataTypeHolder<T> {

    DataType<T> getDataType();

    void setDataType(DataType<T> dataType);
}

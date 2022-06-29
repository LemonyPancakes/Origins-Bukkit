package me.lemonypancakes.bukkit.origins;

public interface DataTypeHolder<T> {

    DataType<T> getDataType();

    void setDataType(DataType<T> dataType);
}

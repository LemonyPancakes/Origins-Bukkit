package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.Temp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "unchecked"})
public class CraftTemp implements Temp {

    private final Map<DataType<Object>, Object> map = new HashMap<>();

    @Override
    public <T> T get(DataType<T> type) {
        return (T) map.get(type);
    }

    @Override
    public <T> void set(DataType<T> type, T t) {
        map.put((DataType<Object>) type, t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftTemp)) return false;
        CraftTemp craftTemp = (CraftTemp) o;
        return Objects.equals(map, craftTemp.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "CraftTemp{" +
                "map=" + map +
                '}';
    }
}

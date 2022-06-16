package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;

import java.util.Objects;
import java.util.function.BiConsumer;

public class CraftAction<T> implements Action<T> {

    private final OriginsBukkitPlugin plugin;
    private JsonObject jsonObject;
    private final DataType<T> dataType;
    private BiConsumer<JsonObject, T> biConsumer;

    public CraftAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        this.plugin = plugin;
        this.jsonObject = jsonObject;
        this.dataType = dataType;
        this.biConsumer = biConsumer;
    }

    public CraftAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiConsumer<JsonObject, T> biConsumer) {
        this(plugin, jsonObject, null, biConsumer);
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public DataType<T> getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(DataType<T> dataType) {}

    @Override
    public BiConsumer<JsonObject, T> getBiConsumer() {
        return biConsumer;
    }

    @Override
    public void setBiConsumer(BiConsumer<JsonObject, T> biConsumer) {
        this.biConsumer = biConsumer;
    }

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftAction<>(plugin, jsonObject, dataType, biConsumer);
    }

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
        return newInstance(plugin, jsonObject, null);
    }

    @Override
    public void accept(T t) {
        if (biConsumer != null) {
            biConsumer.accept(jsonObject, t);
        }
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftAction)) return false;
        CraftAction<?> that = (CraftAction<?>) o;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getBiConsumer(), that.getBiConsumer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), getJsonObject(), getBiConsumer());
    }

    @Override
    public String toString() {
        return "CraftAction{" +
                "plugin=" + plugin +
                ", jsonObject=" + jsonObject +
                ", biConsumer=" + biConsumer +
                '}';
    }
}

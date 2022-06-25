package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;

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
}

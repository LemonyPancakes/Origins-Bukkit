package me.lemonypancakes.originsbukkit.factory.action.meta;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.BiConsumer;

public class CraftDelayAction<T> extends CraftAction<T> {

    private Action<T> action;
    private int ticks;

    public CraftDelayAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType, BiConsumer<JsonObject, T> biConsumer) {
        super(plugin, jsonObject, dataType, biConsumer);
        if (jsonObject != null) {
            this.action = plugin.getLoader().loadAction(dataType, jsonObject);
            if (jsonObject.has("ticks")) {
                this.ticks = jsonObject.get("ticks").getAsInt();
            }
            setBiConsumer((jsonObject1, t) -> new BukkitRunnable() {

                @Override
                public void run() {
                    action.accept(t);
                }
            }.runTaskLater(plugin.getJavaPlugin(), ticks));
        }
    }

    @Override
    public Action<T> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<T> dataType) {
        return new CraftDelayAction<>(plugin, jsonObject, dataType, getBiConsumer());
    }
}

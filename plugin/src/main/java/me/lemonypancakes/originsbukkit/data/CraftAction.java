package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Probability;
import me.lemonypancakes.originsbukkit.Temp;

import java.util.Objects;
import java.util.function.BiConsumer;

public class CraftAction implements Action {

    private final OriginsBukkitPlugin plugin;
    private JsonObject jsonObject;
    private BiConsumer<JsonObject, Temp> biConsumer;
    private Probability probability;

    public CraftAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiConsumer<JsonObject, Temp> biConsumer) {
        this.plugin = plugin;
        setJsonObject(jsonObject);
        setBiConsumer(biConsumer);
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        if (jsonObject != null) {
            if (jsonObject.has("probability")) {
                JsonObject probability = jsonObject.get("probability").getAsJsonObject();

                if (probability != null) {
                    Probability probabilityContainer = new CraftProbability();

                    if (probability.has("min")) {
                        probabilityContainer.setMin(probability.get("min").getAsInt());
                    }
                    if (probability.has("max")) {
                        probabilityContainer.setMax(probability.get("max").getAsInt());
                    }

                    if (probabilityContainer.getMin() > 1 && probabilityContainer.getMax() > 1) {
                        this.probability = probabilityContainer;
                    }
                }
            }
        }
    }

    @Override
    public BiConsumer<JsonObject, Temp> getBiConsumer() {
        return biConsumer;
    }

    @Override
    public void setBiConsumer(BiConsumer<JsonObject, Temp> biConsumer) {
        this.biConsumer = biConsumer;
    }

    @Override
    public Action newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject) {
        return new CraftAction(plugin, jsonObject, biConsumer);
    }

    @Override
    public void accept(Temp temp) {
        if (probability != null) {
            if (!probability.generateProbability()) {
                return;
            }
        }
        getBiConsumer().accept(getJsonObject(), temp);
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
        CraftAction that = (CraftAction) o;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getBiConsumer(), that.getBiConsumer()) && Objects.equals(probability, that.probability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), getJsonObject(), getBiConsumer(), probability);
    }

    @Override
    public String toString() {
        return "CraftAction{" +
                "plugin=" + plugin +
                ", jsonObject=" + jsonObject +
                ", biConsumer=" + biConsumer +
                ", probability=" + probability +
                '}';
    }
}

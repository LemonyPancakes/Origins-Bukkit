package me.lemonypancakes.originsbukkit.api.data.container;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ActionContainer<T> implements Action<T> {

    private Identifier identifier;
    private JsonObject jsonObject;
    private BiConsumer<JsonObject, T> biConsumer;

    private boolean invertProbability = false;

    private Boolean isAsync;
    private Integer probability;

    public ActionContainer(Identifier identifier,
                          JsonObject jsonObject,
                          BiConsumer<JsonObject, T> biConsumer) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.biConsumer = biConsumer;
        if (jsonObject != null) {
            if (jsonObject.has("async")) {
                this.isAsync = jsonObject.get("async").getAsBoolean();
            }
            if(jsonObject.has("probability")) {
                this.probability = jsonObject.get("probability").getAsInt();
            }
            if (jsonObject.has("invert_action_probability")) {
                this.invertProbability = jsonObject.get("invert_action_probability").getAsBoolean();
            }
        }
    }

    public ActionContainer(Identifier identifier,
                           JsonObject jsonObject) {
        this(identifier, jsonObject, null);
    }

    public ActionContainer(Identifier identifier) {
        this(identifier, null);
    }

    public ActionContainer() {
        this(null);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        if (jsonObject != null) {
            if (jsonObject.has("async")) {
                this.isAsync = jsonObject.get("async").getAsBoolean();
            }
            if(jsonObject.has("probability")) {
                this.probability = jsonObject.get("probability").getAsInt();
            }
            if (jsonObject.has("invert_action_probability")) {
                this.invertProbability = jsonObject.get("invert_action_probability").getAsBoolean();
            }
        }
    }

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
        if (this.probability != null) {
            Random random = new Random();
            int probability = random.nextInt(this.probability);

            if (this.invertProbability) {
                if (probability == 0) {
                    return;
                }
            } else {
                if (probability != 0) {
                    return;
                }
            }
        }
        if (this.isAsync != null) {
            if (!this.isAsync) {
                Bukkit.getScheduler()
                        .runTask(
                                OriginsBukkit.getPlugin()
                                , bukkitTask ->
                                        getBiConsumer().accept(
                                                getJsonObject(),
                                                t
                                        )
                        );
            } else {
                Bukkit.getScheduler()
                        .runTaskAsynchronously(
                                OriginsBukkit.getPlugin()
                                , bukkitTask ->
                                        getBiConsumer().accept(
                                                getJsonObject(),
                                                t
                                        )
                        );
            }
        } else {
            getBiConsumer().accept(
                    getJsonObject(),
                    t
            );
        }
    }

    @Override
    public Consumer<T> andThen(@Nonnull Consumer<? super T> after) {
        return Action.super.andThen(after);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionContainer)) return false;
        ActionContainer<?> that = (ActionContainer<?>) o;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getBiConsumer(), that.getBiConsumer()) && Objects.equals(isAsync, that.isAsync) && Objects.equals(probability, that.probability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getJsonObject(), getBiConsumer(), isAsync, probability);
    }

    @Override
    public String toString() {
        return "ActionContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", biConsumer=" + biConsumer +
                ", isAsync=" + isAsync +
                ", probability=" + probability +
                '}';
    }
}

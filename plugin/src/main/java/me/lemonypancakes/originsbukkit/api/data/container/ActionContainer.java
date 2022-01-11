package me.lemonypancakes.originsbukkit.api.data.container;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ActionContainer<T> implements Action<T> {

    private Identifier identifier;
    private JsonObject jsonObject;
    private BiConsumer<JsonObject, T> biConsumer;

    private Boolean isAsync;

    public ActionContainer(Identifier identifier,
                          JsonObject jsonObject,
                          BiConsumer<JsonObject, T> biConsumer) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.biConsumer = biConsumer;
        if (jsonObject != null && jsonObject.has("async")) {
            this.isAsync = jsonObject.get("async").getAsBoolean();
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
        if (jsonObject != null && jsonObject.has("async")) {
            this.isAsync = jsonObject.get("async").getAsBoolean();
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
    public String toString() {
        return "ActionContainer{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", biConsumer=" + biConsumer +
                ", isAsync=" + isAsync +
                '}';
    }
}

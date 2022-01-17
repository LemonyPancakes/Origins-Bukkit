package me.lemonypancakes.originsbukkit.storage;

import me.lemonypancakes.originsbukkit.OriginsBukkit;

public class StorageHandler {

    private final OriginsBukkit plugin;

    private final Actions actions;
    private final Conditions conditions;
    private final Origins origins;
    private final OriginPlayers originPlayers;
    private final Powers powers;
    private final Tags tags;

    public StorageHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        this.actions = new Actions(this);
        this.conditions = new Conditions(this);
        this.origins = new Origins(this);
        this.originPlayers = new OriginPlayers(this);
        this.powers = new Powers(this);
        this.tags = new Tags(this);
    }

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public Actions getActions() {
        return actions;
    }

    public Conditions getConditions() {
        return conditions;
    }

    public Origins getOrigins() {
        return origins;
    }

    public OriginPlayers getOriginPlayers() {
        return originPlayers;
    }

    public Powers getPowers() {
        return powers;
    }

    public Tags getTags() {
        return tags;
    }
}

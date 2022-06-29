package me.lemonypancakes.bukkit.origins;

import me.lemonypancakes.bukkit.origins.util.Identifier;

public interface Identifiable {

    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}

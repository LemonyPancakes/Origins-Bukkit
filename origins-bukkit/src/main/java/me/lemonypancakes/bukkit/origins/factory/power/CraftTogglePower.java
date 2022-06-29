package me.lemonypancakes.bukkit.origins.factory.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Key;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CraftTogglePower extends CraftActivePower {

    private final Set<Player> players = new HashSet<>();

    public CraftTogglePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @Override
    protected void onUse(Player player, Key key) {
        if (players.contains(player)) {
            players.remove(player);
        } else {
            players.add(player);
        }
    }

    public boolean isToggled(Player player) {
        return players.contains(player);
    }

    @Override
    protected void onMemberRemove(Player player) {
        players.remove(player);
    }
}

package me.lemonypancakes.originsbukkit.factory.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Keyed;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.event.entity.player.PlayerKeyEvent;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class CraftActivePower extends CraftPower implements Keyed {

    public CraftActivePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    public CraftActivePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftActivePower(plugin, identifier, jsonObject);
    }

    @Override
    public Key getKey() {
        return null;
    }

    @Override
    public void setKey(Key key) {

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerSwapHandItems(PlayerKeyEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            onUse(player, event.getKey());
        }
    }

    protected void onUse(Player player, Key key) {}
}
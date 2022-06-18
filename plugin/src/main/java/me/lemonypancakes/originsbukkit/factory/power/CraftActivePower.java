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

    private Key key;

    public CraftActivePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("key")) {
            this.key = Key.valueOf(jsonObject.get("key").getAsString());
        }
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
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerKeyEvent(PlayerKeyEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Key key = event.getKey();

            if (this.key == key) {
                onUse(player, event.getKey());
            }
        }
    }

    protected void onUse(Player player, Key key) {}
}

package me.lemonypancakes.originsbukkit.factory.power;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Keyed;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.event.entity.player.PlayerKeyEvent;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class CraftActiveWithCooldownPower extends CraftCooldownPower implements Keyed {

    private Key key;

    public CraftActiveWithCooldownPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("key")) {
            this.key = Key.valueOf(jsonObject.get("key").getAsString());
        }
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
    public void onPlayerKey(PlayerKeyEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Key key = event.getKey();

            if (this.key == key) {
                if (canUse(player)) {
                    setCooldown(player);
                    onUse(player, event.getKey());
                }
            }
        }
    }

    protected void onUse(Player player, Key key) {}
}

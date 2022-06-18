package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.factory.power.CraftActiveWithCooldownPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.Key;
import org.bukkit.entity.Player;

public class CraftFireProjectilePower extends CraftActiveWithCooldownPower {

    public CraftFireProjectilePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    public CraftFireProjectilePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftFireProjectilePower(plugin, identifier, jsonObject);
    }

    @Override
    protected void onUse(Player player, Key key) {

    }
}

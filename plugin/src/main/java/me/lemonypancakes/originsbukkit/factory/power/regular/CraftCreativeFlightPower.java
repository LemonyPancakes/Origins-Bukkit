package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import org.bukkit.entity.Player;

public class CraftCreativeFlightPower extends CraftPower {

    public CraftCreativeFlightPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftCreativeFlightPower(plugin, identifier, jsonObject, false);
    }

    @Override
    protected void onMemberAdd(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
    }
}

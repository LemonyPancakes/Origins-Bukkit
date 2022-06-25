package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.factory.power.CraftActiveWithCooldownPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.Key;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;

public class CraftThrowEnderPearlPower extends CraftActiveWithCooldownPower {

    public CraftThrowEnderPearlPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @Override
    protected void onUse(Player player, Key key) {
        player.launchProjectile(EnderPearl.class);
    }
}

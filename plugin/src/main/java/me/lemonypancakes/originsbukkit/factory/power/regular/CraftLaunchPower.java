package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.factory.power.CraftActiveWithCooldownPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.Key;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CraftLaunchPower extends CraftActiveWithCooldownPower {

    private double speed;
    private Sound sound;

    public CraftLaunchPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("speed")) {
            this.speed = jsonObject.get("speed").getAsDouble();
        }
        if (jsonObject.has("sound")) {
            this.sound = Sound.valueOf(jsonObject.get("sound").getAsString());
        }
    }

    @Override
    protected void onUse(Player player, Key key) {
        if (key == Key.PRIMARY) {
            player.setVelocity(new Vector(0, speed, 0));
            player.getWorld().playSound(player.getLocation(), sound, 1f, 0f);
        }
    }
}

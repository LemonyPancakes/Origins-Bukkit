package me.lemonypancakes.originsbukkit.factory.power.modify;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class CraftModifyFallingPower extends CraftPower {

    public CraftModifyFallingPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    public CraftModifyFallingPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftModifyFallingPower(plugin, identifier, jsonObject);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Location getTo = event.getTo();
            Location getFrom = event.getFrom();

            if (getTo != null) {
                if (getTo.getY() < getFrom.getY()) {
                    player.setVelocity(new Vector(player.getVelocity().getX(), player.getVelocity().getY() * .02, player.getVelocity().getZ()));
                }
            }
        }
    }
}

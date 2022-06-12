package me.lemonypancakes.originsbukkit.factory.power.prevent;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftPreventBlockUsePower extends CraftPower {

    public CraftPreventBlockUsePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftPreventBlockUsePower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_BLOCK) {
                Temp temp = new CraftTemp();

                temp.set(DataType.ENTITY, player);
                if (testAndAccept(temp)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

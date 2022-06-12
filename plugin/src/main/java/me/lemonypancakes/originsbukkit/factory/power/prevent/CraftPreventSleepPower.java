package me.lemonypancakes.originsbukkit.factory.power.prevent;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftPreventSleepPower extends CraftPower {

    private String message;
    private boolean setSpawnPoint;

    public CraftPreventSleepPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory()) {
            if (jsonObject != null) {
                if (jsonObject.has("message")) {
                    this.message = jsonObject.get("message").getAsString();
                }
                if (jsonObject.has("set_spawn_point")) {
                    this.setSpawnPoint = jsonObject.get("set_spawn_point").getAsBoolean();
                }
            }
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftPreventSleepPower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (!setSpawnPoint) {
                    Temp temp = new CraftTemp();

                    temp.set(DataType.ENTITY, player);
                    if (testAndAccept(temp)) {
                        event.setCancelled(true);
                        if (message != null) {
                            player.sendMessage(ChatUtils.format(message));
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Temp temp = new CraftTemp();

            temp.set(DataType.ENTITY, player);
            if (testAndAccept(temp)) {
                event.setCancelled(true);
                if (message != null) {
                    player.sendMessage(ChatUtils.format(message));
                }
            }
        }
    }
}

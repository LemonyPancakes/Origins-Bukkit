package me.lemonypancakes.originsbukkit.listeners.player;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.UUID;

public class EntityPickupItemEventListener implements Listener {

    private final ListenerHandler listenerHandler;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public EntityPickupItemEventListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        Bukkit.getPluginManager().registerEvents(this, getListenerHandler().getPlugin());
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    @EventHandler
    private void onEntityPickupItem(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            UUID playerUUID = player.getUniqueId();

            if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                if (ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).getOrigin() == null) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}

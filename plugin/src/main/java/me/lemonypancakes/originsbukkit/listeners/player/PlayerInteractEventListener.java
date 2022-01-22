package me.lemonypancakes.originsbukkit.listeners.player;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class PlayerInteractEventListener implements Listener {

    private final ListenerHandler listenerHandler;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public PlayerInteractEventListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        Bukkit.getPluginManager().registerEvents(this, getListenerHandler().getPlugin());
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
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

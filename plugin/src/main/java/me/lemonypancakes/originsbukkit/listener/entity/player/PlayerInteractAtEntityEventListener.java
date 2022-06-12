package me.lemonypancakes.originsbukkit.listener.entity.player;

import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public PlayerInteractAtEntityEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (plugin.getOriginPlayer(player) != null) {
            if (plugin.getOriginPlayer(player).getOrigin() == null) {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }
}

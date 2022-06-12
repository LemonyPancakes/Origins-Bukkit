package me.lemonypancakes.originsbukkit.listener.block;

import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public BlockPlaceEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onBlockPlace(BlockPlaceEvent event) {
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

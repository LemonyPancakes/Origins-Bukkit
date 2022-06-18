package me.lemonypancakes.originsbukkit.listener.entity.player;

import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.event.entity.player.PlayerKeyEvent;
import me.lemonypancakes.originsbukkit.util.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItemsEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public PlayerSwapHandItemsEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    private void onPlayerInteractAtEntity(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking()) {
            PlayerKeyEvent playerKeyEvent = new PlayerKeyEvent(player, Key.SECONDARY);
            Bukkit.getPluginManager().callEvent(playerKeyEvent);
        } else {
            PlayerKeyEvent playerKeyEvent = new PlayerKeyEvent(player, Key.PRIMARY);
            Bukkit.getPluginManager().callEvent(playerKeyEvent);
        }
        PlayerKeyEvent playerKeyEvent = new PlayerKeyEvent(player, Key.EITHER);
        Bukkit.getPluginManager().callEvent(playerKeyEvent);
        event.setCancelled(true);
    }
}

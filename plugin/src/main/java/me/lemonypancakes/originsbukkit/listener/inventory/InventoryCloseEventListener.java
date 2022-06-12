package me.lemonypancakes.originsbukkit.listener.inventory;

import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.storage.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InventoryCloseEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public InventoryCloseEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        Player player = (Player) humanEntity;
        UUID playerUUID = player.getUniqueId();
        Inventory inventory = event.getInventory();
        Map<UUID, Integer> viewers = Misc.VIEWERS;
        List<Inventory> guis = Misc.GUIS;


        if (viewers.containsKey(playerUUID)) {
            if (guis.contains(inventory)) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!player.getOpenInventory().getTopInventory().equals(guis.get(viewers.get(playerUUID)))) {
                            player.openInventory(guis.get(viewers.get(playerUUID)));
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                }.runTaskLater(plugin.getJavaPlugin(), 1L);
            }
        }
    }
}

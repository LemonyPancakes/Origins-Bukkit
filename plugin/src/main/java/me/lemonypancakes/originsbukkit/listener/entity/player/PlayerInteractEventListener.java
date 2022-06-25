package me.lemonypancakes.originsbukkit.listener.entity.player;

import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.util.BukkitPersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteractEventListener implements Listener {

    private final OriginsBukkitPlugin plugin;

    public PlayerInteractEventListener(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (plugin.getOriginPlayer(player) != null) {
            if (plugin.getOriginPlayer(player).getOrigin() == null) {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onOrbOfOriginInteract(PlayerInteractEvent event) {
        if (event.useItemInHand() != Event.Result.DENY) {
            Player player = event.getPlayer();
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                ItemStack itemStack = event.getItem();

                if (itemStack != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    if (itemMeta != null) {
                        String data = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:custom_item", PersistentDataType.STRING);

                        if (data != null) {
                            if (data.equals("origins-bukkit:orb_of_origin")) {
                                if (plugin.getOriginPlayer(player) != null) {
                                    if (plugin.getOriginPlayer(player).getOrigin() != null) {
                                        plugin.getOriginPlayer(player).setOrigin(null);
                                        itemStack.setAmount(itemStack.getAmount() - 1);
                                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1f, 0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

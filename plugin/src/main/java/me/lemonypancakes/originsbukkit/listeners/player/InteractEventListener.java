package me.lemonypancakes.originsbukkit.listeners.player;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import me.lemonypancakes.originsbukkit.util.BukkitPersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class InteractEventListener implements Listener {

    private final ListenerHandler listenerHandler;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public InteractEventListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        Bukkit.getPluginManager().registerEvents(this, getListenerHandler().getPlugin());
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.getItem();

            if (itemStack != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta != null) {
                    String data = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:custom_item", PersistentDataType.STRING);

                    if (data != null) {
                        if (data.equals("origins-bukkit:orb_of_origin")) {
                            if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                                if (ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).getOrigin() != null) {
                                    ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(null);
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

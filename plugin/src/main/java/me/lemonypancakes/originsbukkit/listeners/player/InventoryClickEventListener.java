package me.lemonypancakes.originsbukkit.listeners.player;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.events.player.PlayerOriginChooseEvent;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import me.lemonypancakes.originsbukkit.storage.Origins;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InventoryClickEventListener implements Listener {

    private final ListenerHandler listenerHandler;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Origins ORIGINS = PLUGIN.getStorageHandler().getOrigins();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public InventoryClickEventListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        Bukkit.getPluginManager().registerEvents(this, getListenerHandler().getPlugin());
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        Player player = (Player) humanEntity;
        ItemStack clickedItem = event.getCurrentItem();
        Map<UUID, Integer> viewers = Misc.VIEWERS;
        List<Inventory> guis = Misc.GUIS;

        if (guis.contains(event.getInventory())) {

            event.setCancelled(true);
            if (clickedItem != null) {
                if (!clickedItem.getType().isAir()) {
                    if (event.getRawSlot() == 48) {
                        if (guis.indexOf(event.getClickedInventory()) != 0) {
                            player.openInventory(guis.get(guis.indexOf(event.getClickedInventory()) - 1));
                            if (viewers.containsKey(player.getUniqueId())) {
                                viewers.put(player.getUniqueId(), viewers.get(player.getUniqueId()) - 1);
                                player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                    if (event.getRawSlot() == 49) {
                        player.kickPlayer(null);
                    }
                    if (event.getRawSlot() == 50) {
                        if (!guis.get(guis.indexOf(event.getClickedInventory())).equals(guis.get(guis.size() - 1))) {
                            player.openInventory(guis.get(guis.indexOf(event.getClickedInventory()) + 1));
                            if (viewers.containsKey(player.getUniqueId())) {
                                viewers.put(player.getUniqueId(), viewers.get(player.getUniqueId()) + 1);
                                player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                    if (event.getRawSlot() == 22){
                        if (clickedItem.getItemMeta() != null) {
                            String identifierString = clickedItem.getItemMeta().getLocalizedName();
                            Identifier identifier = new IdentifierContainer(
                                    identifierString.split(":")[0],
                                    identifierString.split(":")[1]
                            );

                            if (!identifier.getIdentifier().equals("origins-bukkit:dummy_origin")) {
                                if (ORIGINS.hasIdentifier(identifier)) {
                                    UUID playerUUID = player.getUniqueId();
                                    PlayerOriginChooseEvent playerOriginChooseEvent
                                            = new PlayerOriginChooseEvent(player, identifierString);

                                    if (!playerOriginChooseEvent.isCancelled()) {
                                        player.getPersistentDataContainer().set(
                                                new NamespacedKey(OriginsBukkit.getPlugin(), "origin"),
                                                PersistentDataType.STRING,
                                                identifier.getIdentifier());
                                        if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                                            ORIGIN_PLAYERS.add(new OriginPlayerContainer(playerUUID), true);
                                        } else {
                                            ORIGIN_PLAYERS.add(new OriginPlayerContainer(playerUUID));
                                        }

                                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
                                    } else {
                                        player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                                    }
                                    Bukkit.getPluginManager().callEvent(playerOriginChooseEvent);
                                }
                            } else {
                                player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                            }
                        }
                    }
                }
            }
        }
    }
}

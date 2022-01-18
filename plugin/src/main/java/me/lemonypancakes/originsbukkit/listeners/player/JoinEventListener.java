package me.lemonypancakes.originsbukkit.listeners.player;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import me.lemonypancakes.originsbukkit.api.data.container.TempContainer;
import me.lemonypancakes.originsbukkit.api.data.type.OriginPlayer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class JoinEventListener implements Listener {

    private final ListenerHandler listenerHandler;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public JoinEventListener(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        Bukkit.getPluginManager().registerEvents(this, getListenerHandler().getPlugin());
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(OriginsBukkit.getPlugin(), "origin");

        if (!dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            Misc.VIEWERS.put(player.getUniqueId(), 0);
            player.openInventory(Misc.GUIS.get(0));
        } else {
            UUID playerUUID = player.getUniqueId();

            if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                OriginPlayer originPlayer = ORIGIN_PLAYERS.getByPlayerUUID(playerUUID);
                Temp temp = new TempContainer();

                temp.setPlayer(player);
                originPlayer.unlistenAndDestroy();
                originPlayer.getOrigin().getPowers().forEach(
                        power -> power.invoke(temp)
                );
            } else {
                ORIGIN_PLAYERS.add(
                        new OriginPlayerContainer(
                                player.getUniqueId()
                        )
                );
            }
        }
    }
}

package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.storage.Misc;
import org.bukkit.Bukkit;

public class ShutdownUtils {

    public static void checkAllOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (Misc.VIEWERS.containsKey(player.getUniqueId())) {
                player.closeInventory();
            }
        });
    }
}

package me.lemonypancakes.bukkit.origins;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public interface OriginsBukkitPlugin {

    Registry getRegistry();

    Loader getLoader();

    ProtocolManager getProtocolManager();

    OriginPlayer getOriginPlayer(Player player);

    OriginPlayer getOriginPlayer(UUID uuid);

    OriginPlayer getOriginPlayer(String name);

    JavaPlugin getJavaPlugin();

    Storage getStorage();

    List<Plugin> getExpansions();

    void disable();
}

package me.lemonypancakes.originsbukkit.plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.lemonypancakes.armorequipevent.ArmorListener;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.command.maincommand.MainCommand;
import me.lemonypancakes.originsbukkit.config.CraftConfigHandler;
import me.lemonypancakes.originsbukkit.data.CraftLoader;
import me.lemonypancakes.originsbukkit.data.CraftOriginPlayer;
import me.lemonypancakes.originsbukkit.data.CraftRegistry;
import me.lemonypancakes.originsbukkit.data.storage.CraftBukkitStorage;
import me.lemonypancakes.originsbukkit.data.storage.CraftMySQLStorage;
import me.lemonypancakes.originsbukkit.data.storage.other.Misc;
import me.lemonypancakes.originsbukkit.factory.action.BiEntityActions;
import me.lemonypancakes.originsbukkit.factory.action.BlockActions;
import me.lemonypancakes.originsbukkit.factory.action.EntityActions;
import me.lemonypancakes.originsbukkit.factory.action.ItemActions;
import me.lemonypancakes.originsbukkit.factory.condition.*;
import me.lemonypancakes.originsbukkit.item.OrbOfOrigin;
import me.lemonypancakes.originsbukkit.listener.block.BlockBreakEventListener;
import me.lemonypancakes.originsbukkit.listener.block.BlockPlaceEventListener;
import me.lemonypancakes.originsbukkit.listener.entity.EntityDamageEventListener;
import me.lemonypancakes.originsbukkit.listener.entity.EntityPickupItemEventListener;
import me.lemonypancakes.originsbukkit.listener.entity.player.PlayerInteractAtEntityEventListener;
import me.lemonypancakes.originsbukkit.listener.entity.player.PlayerInteractEntityEventListener;
import me.lemonypancakes.originsbukkit.listener.entity.player.PlayerInteractEventListener;
import me.lemonypancakes.originsbukkit.listener.entity.player.PlayerSwapHandItemsEventListener;
import me.lemonypancakes.originsbukkit.listener.inventory.InventoryClickEventListener;
import me.lemonypancakes.originsbukkit.listener.inventory.InventoryCloseEventListener;
import me.lemonypancakes.originsbukkit.listener.world.DayAndNightCycleListener;
import me.lemonypancakes.originsbukkit.metrics.Metrics;
import me.lemonypancakes.originsbukkit.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class CraftOriginsBukkitPlugin extends JavaPlugin implements OriginsBukkitPlugin {

    private final Map<UUID, OriginPlayer> originPlayers = new HashMap<>();
    private final List<Plugin> expansions = new ArrayList<>();
    private final Registry registry = new CraftRegistry(this);
    private final Loader loader = new CraftLoader(this);
    private ProtocolManager protocolManager;
    private Storage storage;

    public CraftOriginsBukkitPlugin() {
        OriginsBukkit.setPlugin(this);
    }

    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();

        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &4   ___       _       _                 ____        _    _    _ _");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &c  / _ \\ _ __(_) __ _(_)_ __  ___      | __ ) _   _| | _| | _(_) |_");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &6 | | | | '__| |/ _` | | '_ \\/ __|_____|  _ \\| | | | |/ / |/ / | __|");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &e | |_| | |  | | (_| | | | | \\__ \\_____| |_) | |_| |   <|   <| | |_");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &a  \\___/|_|  |_|\\__, |_|_| |_|___/     |____/ \\__,_|_|\\_\\_|\\_\\_|\\__|");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &b               |___/");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit]");
        StartupUtils.checkServerCompatibility(this);
        StartupUtils.checkServerDependencies(this);

        if (isEnabled()) {
            new Metrics(this, 13236);
            new BlockBreakEventListener(this);
            new BlockPlaceEventListener(this);
            new PlayerInteractAtEntityEventListener(this);
            new PlayerInteractEntityEventListener(this);
            new PlayerInteractEventListener(this);
            new PlayerSwapHandItemsEventListener(this);
            new EntityDamageEventListener(this);
            new EntityPickupItemEventListener(this);
            new InventoryClickEventListener(this);
            new InventoryCloseEventListener(this);
            new DayAndNightCycleListener(this);
            new ArmorListener(this);
            new MainCommand(this);
            new CraftConfigHandler(this);
            registry.register(new OrbOfOrigin());
            new BiEntityActions(this);
            new BlockActions(this);
            new EntityActions(this);
            new ItemActions(this);
            new BiEntityConditions(this);
            new BiomeConditions(this);
            new BlockConditions(this);
            new DamageConditions(this);
            new EntityConditions(this);
            new FluidConditions(this);
            new ItemConditions(this);
            StartupUtils.registerFactories(this);
            StartupUtils.loadExpansions(this);
            StartupUtils.registerOriginPacks(this);
            switch (Config.STORAGE_METHOD.toString()) {
                case "INTERNAL":
                    this.storage = new CraftBukkitStorage();
                    break;
                case "MY_SQL":
                    this.storage = new CraftMySQLStorage(this);
                    break;
                default:
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Unknown storage method: &e" + Config.STORAGE_METHOD + ".");
                    disable();
                    break;
            }
            Bukkit.getPluginManager().registerEvents(new Listener() {

                @EventHandler(priority = EventPriority.LOWEST)
                private void onPlayerJoin(PlayerJoinEvent event) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    OriginPlayer originPlayer = new CraftOriginPlayer(CraftOriginsBukkitPlugin.this, uuid);

                    originPlayers.put(uuid, originPlayer);
                    Origin origin = originPlayer.getOrigin();

                    if (origin != null) {
                        origin.getPowers().forEach(power -> power.addMember(player));
                    }
                }
            }, this);
            Bukkit.getPluginManager().registerEvents(new Listener() {

                @EventHandler(priority = EventPriority.LOWEST)
                private void onPlayerQuit(PlayerQuitEvent event) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();

                    originPlayers.get(uuid).unlistenAndDestroy();
                    originPlayers.remove(uuid);
                }
            }, this);
            Bukkit.getOnlinePlayers().forEach(player -> {
                UUID uuid = player.getUniqueId();
                OriginPlayer originPlayer = new CraftOriginPlayer(CraftOriginsBukkitPlugin.this, uuid);

                originPlayers.put(uuid, originPlayer);
                Origin origin = originPlayer.getOrigin();

                if (origin != null) {
                    origin.getPowers().forEach(power -> power.addMember(player));
                }
            });
            new BukkitRunnable() {

                @Override
                public void run() {
                    try {
                        UpdateChecker updateChecker = new UpdateChecker(CraftOriginsBukkitPlugin.this, 97926);
                        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Checking for updates...");

                        if (updateChecker.checkForUpdates()) {
                            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] A new update is available!");
                            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] Running on &c" + getDescription().getVersion() + " &6while latest is &a" + updateChecker.getLatestVersion() + "&6.");
                            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] &e&n" + updateChecker.getResourceURL());
                        } else {
                            ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] No updates found.");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }.runTaskLaterAsynchronously(this, 8L);

            ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Plugin has been enabled!");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (Misc.VIEWERS.containsKey(player.getUniqueId())) {
                player.closeInventory();
            }
        });
        Misc.ORIGINS_INFO_GUI.forEach((key, value) -> {
            for (int i = 0; i < value.getViewers().size(); i++) {
                HumanEntity humanEntity = value.getViewers().get(i);

                humanEntity.closeInventory();
            }
        });
        for (int i = 0; i < registry.getOriginItemsKeySet().size(); i++) {
            Bukkit.removeRecipe(((Identifier) registry.getOriginItemsKeySet().toArray()[i]).toNameSpacedKey());
        }
        originPlayers.values().forEach(OriginPlayer::unlistenAndDestroy);
        expansions.forEach(plugin -> Bukkit.getPluginManager().disablePlugin(plugin));

        ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Plugin has been disabled!");
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public Registry getRegistry() {
        return registry;
    }

    @Override
    public Loader getLoader() {
        return loader;
    }

    @Override
    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    @Override
    public OriginPlayer getOriginPlayer(Player player) {
        return originPlayers.get(player.getUniqueId());
    }

    @Override
    public OriginPlayer getOriginPlayer(UUID uuid) {
        return originPlayers.get(uuid);
    }

    @Override
    public OriginPlayer getOriginPlayer(String name) {
        Player player = Bukkit.getPlayer(name);

        if (player != null) {
            return originPlayers.get(player.getUniqueId());
        }
        return null;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public List<Plugin> getExpansions() {
        return expansions;
    }

    @Override
    public void disable() {
        setEnabled(false);
    }
}

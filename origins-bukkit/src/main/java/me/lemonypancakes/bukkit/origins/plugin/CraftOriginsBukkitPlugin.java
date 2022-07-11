/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.lemonypancakes.armorequipevent.ArmorListener;
import me.lemonypancakes.bukkit.origins.OriginsBukkit;
import me.lemonypancakes.bukkit.origins.config.ConfigHandler;
import me.lemonypancakes.bukkit.origins.data.storage.CraftBukkitStorage;
import me.lemonypancakes.bukkit.origins.data.storage.CraftMySQLStorage;
import me.lemonypancakes.bukkit.origins.data.storage.Storage;
import me.lemonypancakes.bukkit.origins.entity.player.CraftOriginPlayer;
import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.factory.action.BiEntityActions;
import me.lemonypancakes.bukkit.origins.factory.action.BlockActions;
import me.lemonypancakes.bukkit.origins.factory.action.EntityActions;
import me.lemonypancakes.bukkit.origins.factory.action.ItemActions;
import me.lemonypancakes.bukkit.origins.factory.condition.*;
import me.lemonypancakes.bukkit.origins.item.ArachnidCobweb;
import me.lemonypancakes.bukkit.origins.item.OrbOfOrigin;
import me.lemonypancakes.bukkit.origins.listener.block.BlockBreakEventListener;
import me.lemonypancakes.bukkit.origins.listener.block.BlockPlaceEventListener;
import me.lemonypancakes.bukkit.origins.listener.entity.EntityDamageEventListener;
import me.lemonypancakes.bukkit.origins.listener.entity.EntityPickupItemEventListener;
import me.lemonypancakes.bukkit.origins.listener.entity.player.PlayerInteractAtEntityEventListener;
import me.lemonypancakes.bukkit.origins.listener.entity.player.PlayerInteractEntityEventListener;
import me.lemonypancakes.bukkit.origins.listener.entity.player.PlayerInteractEventListener;
import me.lemonypancakes.bukkit.origins.listener.entity.player.PlayerSwapHandItemsEventListener;
import me.lemonypancakes.bukkit.origins.listener.inventory.InventoryClickEventListener;
import me.lemonypancakes.bukkit.origins.listener.inventory.InventoryCloseEventListener;
import me.lemonypancakes.bukkit.origins.listener.world.DayAndNightCycleListener;
import me.lemonypancakes.bukkit.origins.loader.CraftLoader;
import me.lemonypancakes.bukkit.origins.loader.Loader;
import me.lemonypancakes.bukkit.origins.menu.Menu;
import me.lemonypancakes.bukkit.origins.metrics.Metrics;
import me.lemonypancakes.bukkit.origins.registry.CraftRegistry;
import me.lemonypancakes.bukkit.origins.registry.Registry;
import me.lemonypancakes.bukkit.origins.util.*;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
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
            new ConfigHandler(this);
            registry.registerOriginItem(new OrbOfOrigin());
            registry.registerOriginItem(new ArachnidCobweb());
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
                    this.storage = new CraftBukkitStorage(this);
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
                public void onPlayerJoin(PlayerJoinEvent event) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    OriginPlayer originPlayer = new CraftOriginPlayer(CraftOriginsBukkitPlugin.this, player);

                    originPlayers.put(uuid, originPlayer);
                    removeModifiers(player);
                    originPlayer.refresh();
                }
            }, this);
            Bukkit.getPluginManager().registerEvents(new Listener() {

                @EventHandler(priority = EventPriority.HIGHEST)
                public void onPlayerQuit(PlayerQuitEvent event) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    OriginPlayer originPlayer = originPlayers.get(uuid);

                    if (originPlayer != null) {
                        originPlayer.getPowers().forEach((power, powerSources) -> power.forceRemoveMember(player));
                        originPlayer.getSchedulers().destroy();
                        removeModifiers(player);
                        originPlayers.remove(uuid);
                    }
                }
            }, this);
            Bukkit.getOnlinePlayers().forEach(player -> {
                UUID uuid = player.getUniqueId();
                OriginPlayer originPlayer = new CraftOriginPlayer(CraftOriginsBukkitPlugin.this, player);

                originPlayers.put(uuid, originPlayer);
                removeModifiers(player);
                originPlayer.refresh();
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
            }.runTaskTimerAsynchronously(this, 60L, 216000L);

            ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Plugin has been enabled!");
        }
    }

    @Override
    public void onDisable() {
        for (int i = 0; i < registry.getRegisteredOriginItemsKeySet().size(); i++) {
            Bukkit.removeRecipe(((Identifier) registry.getRegisteredOriginItemsKeySet().toArray()[i]).toNameSpacedKey());
        }
        expansions.forEach(plugin -> Bukkit.getPluginManager().disablePlugin(plugin));
        originPlayers.values().forEach(originPlayer -> {
            originPlayer.getPowers().forEach((power, powerSources) -> power.forceRemoveMember(originPlayer.getPlayer()));
            originPlayer.getSchedulers().destroy();
        });
        Bukkit.getOnlinePlayers().forEach(player -> {
            InventoryView inventoryView = player.getOpenInventory();
            Inventory inventory = inventoryView.getTopInventory();
            InventoryHolder inventoryHolder = inventory.getHolder();

            if (inventoryHolder instanceof Menu) {
                player.closeInventory();
            }
            removeModifiers(player);
        });

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

    private void removeModifiers(Player player) {
        Arrays.stream(Attribute.values()).forEach(attribute -> {
            AttributeInstance attributeInstance = player.getAttribute(attribute);

            if (attributeInstance != null) {
                attributeInstance.getModifiers().forEach(attributeInstance::removeModifier);
            }
        });
    }
}

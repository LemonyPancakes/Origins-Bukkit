/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
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
package me.lemonypancakes.originsbukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.lemonypancakes.originsbukkit.commands.CommandHandler;
import me.lemonypancakes.originsbukkit.config.ConfigHandler;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.items.ItemHandler;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.metrics.Metrics;
import me.lemonypancakes.originsbukkit.storage.StorageHandler;
import me.lemonypancakes.originsbukkit.util.*;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class OriginsBukkit extends JavaPlugin {

    private static OriginsBukkit plugin;
    private ItemHandler itemHandler;
    private UtilHandler utilHandler;
    private ProtocolManager protocolManager;
    private StorageHandler storageHandler;
    private ConfigHandler configHandler;
    private ListenerHandler listenerHandler;
    private CommandHandler commandHandler;

    public static final String KEY = "origins-bukkit";

    @Override
    public void onEnable() {
        plugin = this;

        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &4   ___       _       _                 ____        _    _    _ _");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &c  / _ \\ _ __(_) __ _(_)_ __  ___      | __ ) _   _| | _| | _(_) |_");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &6 | | | | '__| |/ _` | | '_ \\/ __|_____|  _ \\| | | | |/ / |/ / | __|");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &e | |_| | |  | | (_| | | | | \\__ \\_____| |_) | |_| |   <|   <| | |_");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &a  \\___/|_|  |_|\\__, |_|_| |_|___/     |____/ \\__,_|_|\\_\\_|\\_\\_|\\__|");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &b               |___/");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit]");
        StartupUtils.checkServerCompatibility();
        StartupUtils.checkServerDependencies();

        if (isEnabled()) {
            protocolManager = ProtocolLibrary.getProtocolManager();

            init();

            ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Plugin has been enabled!");
        }
    }

    @Override
    public void onDisable() {
        unregisterRecipes();
        ShutdownUtils.checkAllOnlinePlayers();

        ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Plugin has been disabled!");
    }

    private void init() {
        load();
        StartupUtils.registerFactories();
        StartupUtils.registerOriginPacks();
        StartupUtils.checkAllOnlinePlayers();
        startMetrics();
        checkUpdates();
    }

    private void load() {
        configHandler = new ConfigHandler(this);
        utilHandler = new UtilHandler(this);
        storageHandler = new StorageHandler(this);
        listenerHandler = new ListenerHandler(this);
        commandHandler = new CommandHandler(this);
        itemHandler = new ItemHandler(this);
    }

    private void startMetrics() {
        int serviceId = 13236;

        new BukkitRunnable() {

            @Override
            public void run() {
                Metrics metrics = new Metrics(getPlugin(), serviceId);
            }
        }.runTaskAsynchronously(this);
    }

    private void checkUpdates() {
        UpdateChecker updateChecker = new UpdateChecker(this, 97926);
        boolean checkUpdate = Config.NOTIFICATIONS_UPDATES.toBoolean();
        String pluginVersion = getDescription().getVersion();

        new BukkitRunnable(){

            @Override
            public void run(){
                if (checkUpdate) {
                    ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Checking for updates...");

                    try {
                        if (updateChecker.checkForUpdates()) {
                            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] A new update is available!");
                            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] Running on &c" + pluginVersion + " &6while latest is &a" + updateChecker.getLatestVersion() + "&6.");
                            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] &e&n" + updateChecker.getResourceURL());
                        } else {
                            ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] No updates found.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskLaterAsynchronously(this, 20 * 10);
    }

    private void unregisterRecipes() {
        getServer().removeRecipe(NamespacedKey.minecraft("orb_of_origin"));
        getServer().removeRecipe(NamespacedKey.minecraft("arachnid_cobweb"));

        ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Unregistered all item recipes.");
    }

    public void disablePlugin() {
        setEnabled(false);
    }

    public static OriginsBukkit getPlugin() {
        return plugin;
    }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }

    public UtilHandler getUtilHandler() {
        return utilHandler;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}

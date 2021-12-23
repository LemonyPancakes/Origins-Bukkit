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
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.commands.CommandHandler;
import me.lemonypancakes.originsbukkit.config.ConfigHandler;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.items.ItemHandler;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.metrics.Metrics;
import me.lemonypancakes.originsbukkit.nms.NMSHandler;
import me.lemonypancakes.originsbukkit.storage.StorageHandler;
import me.lemonypancakes.originsbukkit.storage.wrappers.OriginsPlayerDataWrapper;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import me.lemonypancakes.originsbukkit.util.ServerVersionChecker;
import me.lemonypancakes.originsbukkit.util.UpdateChecker;
import me.lemonypancakes.originsbukkit.util.UtilHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The type Origins bukkit.
 *
 * @author LemonyPancakes
 */
public final class OriginsBukkit extends JavaPlugin {

    private static OriginsBukkit plugin;
    private final Map<String, Origin> origins = new HashMap<>();
    private final List<String> originsList = new ArrayList<>();
    private final List<Inventory> originsInventoryGUI = new ArrayList<>();
    private ServerVersionChecker serverVersionChecker;
    private ItemHandler itemHandler;
    private StorageHandler storageHandler;
    private UtilHandler utilHandler;
    private NMSHandler nmsHandler;
    private ProtocolManager protocolManager;
    private ConfigHandler configHandler;
    private ListenerHandler listenerHandler;
    private CommandHandler commandHandler;

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public static OriginsBukkit getPlugin() {
        return plugin;
    }

    /**
     * Gets origins.
     *
     * @return the origins
     */
    public Map<String, Origin> getOrigins() {
        return origins;
    }

    /**
     * Gets origins list.
     *
     * @return the origins list
     */
    public List<String> getOriginsList() {
        return originsList;
    }

    /**
     * Gets origins inventory gui.
     *
     * @return the origins inventory gui
     */
    public List<Inventory> getOriginsInventoryGUI() {
        return originsInventoryGUI;
    }

    /**
     * Gets server version checker.
     *
     * @return the server version checker
     */
    public ServerVersionChecker getServerVersionChecker() {
        return serverVersionChecker;
    }

    /**
     * Gets item handler.
     *
     * @return the item handler
     */
    public ItemHandler getItemHandler() {
        return itemHandler;
    }

    /**
     * Gets storage handler.
     *
     * @return the storage handler
     */
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    /**
     * Gets util handler.
     *
     * @return the util handler
     */
    public UtilHandler getUtilHandler() {
        return utilHandler;
    }

    /**
     * Gets nms handler.
     *
     * @return the nms handler
     */
    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }

    /**
     * Gets protocol manager.
     *
     * @return the protocol manager
     */
    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * Gets listener handler.
     *
     * @return the listener handler
     */
    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    /**
     * Gets command handler.
     *
     * @return the command handler
     */
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * On enable.
     */
    @Override
    public void onEnable() {
        plugin = this;
        serverVersionChecker = new ServerVersionChecker(this);

        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &4   ___       _       _                 ____        _    _    _ _");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &c  / _ \\ _ __(_) __ _(_)_ __  ___      | __ ) _   _| | _| | _(_) |_");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &6 | | | | '__| |/ _` | | '_ \\/ __|_____|  _ \\| | | | |/ / |/ / | __|");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &e | |_| | |  | | (_| | | | | \\__ \\_____| |_) | |_| |   <|   <| | |_");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &a  \\___/|_|  |_|\\__, |_|_| |_|___/     |____/ \\__,_|_|\\_\\_|\\_\\_|\\__|");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &b               |___/");
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit]");
        checkServerCompatibility();
        checkServerDependencies();

        if (isEnabled()) {
            protocolManager = ProtocolLibrary.getProtocolManager();

            init();

            ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Plugin has been enabled!");
        }
    }

    /**
     * On disable.
     */
    @Override
    public void onDisable() {
        unregisterRecipes();
        closeAllPlayerInventory();

        for (Player player : Bukkit.getOnlinePlayers()) {
            OriginPlayer originPlayer = new OriginPlayer(player);
            OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

            if (originsPlayerDataWrapper == null) {
                player.removePotionEffect(PotionEffectType.SLOW);
            }
        }

        ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Plugin has been disabled!");
    }

    /**
     * Check server compatibility.
     */
    private void checkServerCompatibility() {
        getServerVersionChecker().checkServerSoftwareCompatibility();
        getServerVersionChecker().checkServerVersionCompatibility();
    }

    /**
     * Check server dependencies.
     */
    private void checkServerDependencies() {
        if (isEnabled()) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Checking dependencies...");
        }
        if (isEnabled()) {
            Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");

            if (protocolLib != null) {
                if (protocolLib.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] ProtocolLib found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] ProtocolLib seems to be disabled. Safely disabling plugin...");
                    disablePlugin();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (ProtocolLib). Safely disabling plugin...");
                disablePlugin();
            }
        }
        if (isEnabled()) {
            Plugin pancakeLibCore = Bukkit.getServer().getPluginManager().getPlugin("PancakeLibCore");

            if (pancakeLibCore != null) {
                if (pancakeLibCore.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] PancakeLibCore found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] PancakeLibCore seems to be disabled. Safely disabling plugin...");
                    disablePlugin();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (PancakeLibCore). Safely disabling plugin...");
                disablePlugin();
            }
        }
    }

    /**
     * Init.
     */
    private void init() {
        load();
        startMetrics();
        checkUpdates();
    }

    /**
     * Load.
     */
    private void load() {
        configHandler = new ConfigHandler(this);
        utilHandler = new UtilHandler(this);
        nmsHandler = new NMSHandler(this);
        storageHandler = new StorageHandler(this);
        listenerHandler = new ListenerHandler(this);
        commandHandler = new CommandHandler(this);
        itemHandler = new ItemHandler(this);
    }

    /**
     * Start metrics.
     */
    private void startMetrics() {
        int serviceId = 13236;

        new BukkitRunnable() {

            @Override
            public void run() {
                Metrics metrics = new Metrics(getPlugin(), serviceId);
            }
        }.runTaskAsynchronously(this);
    }

    /**
     * Check updates.
     */
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

    /**
     * Unregister recipes.
     */
    private void unregisterRecipes() {
        getServer().removeRecipe(NamespacedKey.minecraft("orb_of_origin"));
        getServer().removeRecipe(NamespacedKey.minecraft("arachnid_cobweb"));

        ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Unregistered all item recipes.");
    }

    /**
     * Close all player inventory.
     */
    private void closeAllPlayerInventory() {
        if (getListenerHandler() != null) {
            getListenerHandler().getPlayerOriginChecker().closeAllOriginPickerGui();

            for (Player player : getListenerHandler().getOriginListenerHandler().getShulk().getShulkInventoryViewers()) {
                UUID playerUUID = player.getUniqueId();

                if (player.getOpenInventory().getTitle().equals(player.getName() + "'s Vault")) {
                    Map<UUID, ItemStack[]> shulkPlayerStorageData = new HashMap<>();

                    shulkPlayerStorageData.put(playerUUID, player.getOpenInventory().getTopInventory().getContents());
                    String s = File.separator;
                    File shulkPlayerStorageDataFile = new File(getDataFolder(), s + "cache" + s + "shulkdata" + s + "inventoriesdata" + s + playerUUID + ".yml");

                    if (!shulkPlayerStorageDataFile.getParentFile().exists()) {
                        shulkPlayerStorageDataFile.getParentFile().mkdirs();
                    }
                    if (!shulkPlayerStorageDataFile.exists()) {
                        try {
                            shulkPlayerStorageDataFile.createNewFile();
                        } catch (IOException event) {
                            event.printStackTrace();
                        }
                    }
                    FileConfiguration shulkPlayerStorageDataConf = YamlConfiguration.loadConfiguration(shulkPlayerStorageDataFile);

                    for (Map.Entry<UUID, ItemStack[]> entry : shulkPlayerStorageData.entrySet()) {
                        if (entry.getKey().equals(playerUUID)) {
                            shulkPlayerStorageDataConf.set("data." + entry.getKey(), entry.getValue());
                        }
                    }
                    try {
                        shulkPlayerStorageDataConf.save(shulkPlayerStorageDataFile);
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                }
                player.closeInventory();
            }
        }
    }

    /**
     * Disable plugin.
     */
    public void disablePlugin() {
        setEnabled(false);
    }
}

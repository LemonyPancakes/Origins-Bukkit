package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.util.Loader;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.factory.action.BlockActions;
import me.lemonypancakes.originsbukkit.factory.action.ItemStackActions;
import me.lemonypancakes.originsbukkit.factory.action.PlayerActions;
import me.lemonypancakes.originsbukkit.factory.condition.BlockConditions;
import me.lemonypancakes.originsbukkit.factory.condition.ItemStackConditions;
import me.lemonypancakes.originsbukkit.factory.condition.PlayerConditions;
import me.lemonypancakes.originsbukkit.factory.event.EntityDamageByEntityEventActions;
import me.lemonypancakes.originsbukkit.factory.event.EntityDamageByEntityEventConditions;
import me.lemonypancakes.originsbukkit.factory.listener.action.*;
import me.lemonypancakes.originsbukkit.factory.listener.special.PlayerSoulBoundArmor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class StartupUtils {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();

    public static void registerFactories() {
        PlayerActions.register();
        PlayerConditions.register();
        ItemStackActions.register();
        ItemStackConditions.register();
        BlockActions.register();
        BlockConditions.register();
        EntityDamageByEntityEventActions.register();
        EntityDamageByEntityEventConditions.register();
        Registry.register(PlayerSoulBoundArmor.getFactory());
        Registry.register(ActionPlayerItemConsume.getFactory());
        Registry.register(ActionPlayerAttackEntity.getFactory());
        Registry.register(ActionEntityAttackPlayer.getFactory());
        Registry.register(ActionPlayerBlockBreak.getFactory());
        Registry.register(ActionPlayerBlockPlace.getFactory());
    }

    public static void registerOriginPacks() {
        String s = File.separator;
        File file = new File(
                OriginsBukkit.getPlugin()
                        .getDataFolder()
                        .getAbsolutePath()
                        + s + "origins"
        );
        File[] packs = file.listFiles();

        if (packs != null) {
            for (File pack : packs) {
                if (pack.isDirectory()) {
                    File packJson = new File(
                            pack.getAbsolutePath()
                                    + s + "origins.json"
                    );
                    File[] powerFiles = new File(
                            pack.getAbsolutePath()
                                    + s + "powers")
                            .listFiles();
                    File[] originFiles = new File(
                            pack.getAbsolutePath()
                                    + s + "origins")
                            .listFiles();

                    if (powerFiles != null) {
                        for (File powerFile : powerFiles) {
                            Registry.register(
                                    Loader.loadPowerFromFile(
                                            powerFile,
                                            pack.getName()
                                    )
                            );
                        }
                    }
                    if (originFiles != null) {
                        for (File originFile : originFiles) {
                            Origin origin = Loader.loadOriginFromFile(
                                    originFile,
                                    pack.getName()
                            );

                            if (origin != null) {
                                if (origin.getJsonObject() != null) {
                                    if (origin.getJsonObject().has("disable_origin")) {
                                        boolean disableOrigin = origin.getJsonObject().get("disable_origin").getAsBoolean();

                                        if (!disableOrigin) {
                                            Registry.register(origin);
                                        }
                                    } else {
                                        Registry.register(origin);
                                    }
                                } else {
                                    Registry.register(origin);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void checkAllOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(
                player -> PLUGIN.getStorageHandler()
                        .getOriginPlayers()
                        .add(
                        new OriginPlayerContainer(
                                player.getUniqueId()
                        )
                )
        );
    }

    public static void checkServerCompatibility() {
        ServerUtils.checkServerSoftwareCompatibility();
        ServerUtils.checkServerVersionCompatibility();
    }

    public static void checkServerDependencies() {
        if (PLUGIN.isEnabled()) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Checking dependencies...");
        }
        if (PLUGIN.isEnabled()) {
            Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");

            if (protocolLib != null) {
                if (protocolLib.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] ProtocolLib found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] ProtocolLib seems to be disabled. Safely disabling plugin...");
                    PLUGIN.disablePlugin();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (ProtocolLib). Safely disabling plugin...");
                PLUGIN.disablePlugin();
            }
        }
        if (PLUGIN.isEnabled()) {
            Plugin pancakeLibCore = Bukkit.getServer().getPluginManager().getPlugin("PancakeLibCore");

            if (pancakeLibCore != null) {
                if (pancakeLibCore.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] PancakeLibCore found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] PancakeLibCore seems to be disabled. Safely disabling plugin...");
                    PLUGIN.disablePlugin();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (PancakeLibCore). Safely disabling plugin...");
                PLUGIN.disablePlugin();
            }
        }
    }
}

package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.OriginContainer;
import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.api.util.Loader;
import me.lemonypancakes.originsbukkit.api.util.Registry;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.factory.action.BlockActions;
import me.lemonypancakes.originsbukkit.factory.action.EntityActions;
import me.lemonypancakes.originsbukkit.factory.action.ItemStackActions;
import me.lemonypancakes.originsbukkit.factory.action.PlayerActions;
import me.lemonypancakes.originsbukkit.factory.condition.BlockConditions;
import me.lemonypancakes.originsbukkit.factory.condition.EntityConditions;
import me.lemonypancakes.originsbukkit.factory.condition.ItemStackConditions;
import me.lemonypancakes.originsbukkit.factory.condition.PlayerConditions;
import me.lemonypancakes.originsbukkit.factory.event.*;
import me.lemonypancakes.originsbukkit.factory.listener.action.*;
import me.lemonypancakes.originsbukkit.factory.listener.custom.*;
import me.lemonypancakes.originsbukkit.factory.listener.special.EntityGroup;
import me.lemonypancakes.originsbukkit.factory.listener.special.PlayerSoulBoundArmor;
import me.lemonypancakes.originsbukkit.factory.listener.special.WorldDayAndNightCycle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartupUtils {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();

    public static void registerFactories() {
        EntityActions.register();
        EntityConditions.register();
        PlayerActions.register();
        PlayerConditions.register();
        ItemStackActions.register();
        ItemStackConditions.register();
        BlockActions.register();
        BlockConditions.register();
        EntityDamageByEntityEventActions.register();
        EntityDamageByEntityEventConditions.register();
        EntityDamageEventActions.register();
        EntityDamageEventConditions.register();
        PlayerOriginChooseEventActions.register();
        PlayerOriginChooseEventConditions.register();
        WorldDayAndNightCycleConditions.register();
        Registry.register(PlayerSoulBoundArmor.getFactory());
        Registry.register(ActionPlayerItemConsume.getFactory());
        Registry.register(ActionPlayerAttackEntity.getFactory());
        Registry.register(ActionEntityAttackPlayer.getFactory());
        Registry.register(ActionPlayerBlockBreak.getFactory());
        Registry.register(ActionPlayerBlockPlace.getFactory());
        Registry.register(ActionPlayerDamage.getFactory());
        Registry.register(ActionPlayerOriginChoose.getFactory());
        Registry.register(ActionPlayerInteractEntity.getFactory());
        Registry.register(WorldDayAndNightCycle.getFactory());
        Registry.register(EntityGroup.getFactory());

        //CUSTOM
        Registry.register(SereneGrace.getFactory());
        Registry.register(Reanimation.getFactory());
        Registry.register(Otherworldly.getFactory());
        Registry.register(MindCorruption.getFactory());
        Registry.register(Gluttony.getFactory());
        Registry.register(Photosynthesis.getFactory());
    }

    public static void registerOriginPacks() {
        String s = File.separator;
        File file = new File(
                OriginsBukkit.getPlugin()
                        .getDataFolder()
                        .getAbsolutePath()
                        + s + "origins"
        );
        if (!file.exists()) {
            file.mkdirs();
        }
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
        if (PLUGIN.getStorageHandler().getOrigins().isEmpty()) {
            Origin origin = new OriginContainer();
            List<Power> emptyPower = new ArrayList<>();

            origin.setIcon(new ItemStack(Material.STONE));
            origin.setImpact(Impact.NONE);
            origin.setAuthors(new String[]{"LemonyPancakes"});
            origin.setDisplayName(ChatUtils.format("&fDummy Origin"));
            origin.setDescription(ChatUtils.formatList(new String[]{"", "&7This appears when there", "&7is no origin available."}));
            origin.setIdentifier(IdentifierUtils.fromString("origins-bukkit:dummy_origin"));
            origin.setPowers(emptyPower);

            Inventory inventory = Bukkit.createInventory(null, 54, ChatUtils.format("&0No origins here. :("));
            ItemStack itemStack = new ItemStack(origin.getIcon());
            itemStack.setAmount(1);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setDisplayName(origin.getDisplayName());
                itemMeta.setLore(Arrays.asList(origin.getDescription()));
                itemMeta.setLocalizedName(origin.getIdentifier().getIdentifier());
                itemStack.setItemMeta(itemMeta);
            }
            inventory.setItem(22, itemStack);
            switch (origin.getImpact()) {
                case NONE:
                    ItemStack none = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta noneMeta = none.getItemMeta();
                    if (noneMeta != null) {
                        noneMeta.setDisplayName(ChatUtils.format("&fImpact: &7None"));
                        none.setItemMeta(noneMeta);
                    }
                    inventory.setItem(6, none);
                    inventory.setItem(7, none);
                    inventory.setItem(8, none);
                    break;
                case LOW:
                    ItemStack low = new ItemStack(Material.LIME_CONCRETE, 1);
                    ItemMeta lowMeta = low.getItemMeta();
                    if (lowMeta != null) {
                        lowMeta.setDisplayName(ChatUtils.format("&fImpact: &aLow"));
                        low.setItemMeta(lowMeta);
                    }
                    ItemStack low1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta lowMeta1 = low1.getItemMeta();
                    if (lowMeta1 != null) {
                        lowMeta1.setDisplayName(ChatUtils.format("&fImpact: &aLow"));
                        low1.setItemMeta(lowMeta);
                    }
                    inventory.setItem(6, low);
                    inventory.setItem(7, low1);
                    inventory.setItem(8, low1);
                    break;
                case MEDIUM:
                    ItemStack medium = new ItemStack(Material.YELLOW_CONCRETE, 1);
                    ItemMeta mediumMeta = medium.getItemMeta();
                    if (mediumMeta != null) {
                        mediumMeta.setDisplayName(ChatUtils.format("&fImpact: &eMedium"));
                        medium.setItemMeta(mediumMeta);
                    }
                    ItemStack medium1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta mediumMeta1 = medium1.getItemMeta();
                    if (mediumMeta1 != null) {
                        mediumMeta1.setDisplayName(ChatUtils.format("&fImpact: &eMedium"));
                        medium1.setItemMeta(mediumMeta);
                    }
                    inventory.setItem(6, medium);
                    inventory.setItem(7, medium);
                    inventory.setItem(8, medium1);
                    break;
                case HIGH:
                    ItemStack high = new ItemStack(Material.RED_CONCRETE, 1);
                    ItemMeta highMeta = high.getItemMeta();
                    if (highMeta != null) {
                        highMeta.setDisplayName(ChatUtils.format("&fImpact: &cHigh"));
                        high.setItemMeta(highMeta);
                    }
                    inventory.setItem(6, high);
                    inventory.setItem(7, high);
                    inventory.setItem(8, high);
                    break;
            }
            ItemStack previous = new ItemStack(Material.ARROW, 1);
            ItemMeta previousMeta = previous.getItemMeta();
            if (previousMeta != null) {
                previousMeta.setDisplayName(ChatUtils.format("&6Previous Page"));
                previous.setItemMeta(previousMeta);
            }

            ItemStack close = new ItemStack(Material.BARRIER, 1);
            ItemMeta closeMeta = close.getItemMeta();
            if (closeMeta != null) {
                closeMeta.setDisplayName(ChatUtils.format("&cQuit Game"));
                close.setItemMeta(closeMeta);
            }

            ItemStack next = new ItemStack(Material.ARROW, 1);
            ItemMeta nextMeta = next.getItemMeta();
            if (nextMeta != null) {
                nextMeta.setDisplayName(ChatUtils.format("&6Next Page"));
                next.setItemMeta(nextMeta);
            }

            inventory.setItem(48, previous);
            inventory.setItem(49, close);
            inventory.setItem(50, next);
            origin.setInventoryGUI(inventory);

            Registry.register(origin);
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

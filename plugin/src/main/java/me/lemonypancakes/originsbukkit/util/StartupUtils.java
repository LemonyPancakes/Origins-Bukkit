package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.Origin;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftOrigin;
import me.lemonypancakes.originsbukkit.factory.power.action.*;
import me.lemonypancakes.originsbukkit.factory.power.modify.CraftModifyBreakSpeedPower;
import me.lemonypancakes.originsbukkit.factory.power.modify.CraftModifyPlayerSpawnPower;
import me.lemonypancakes.originsbukkit.factory.power.prevent.*;
import me.lemonypancakes.originsbukkit.factory.power.regular.*;
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

public final class StartupUtils {

    public static void registerFactories(OriginsBukkitPlugin plugin) {

        //REGULAR
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:burn"), new CraftBurnPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:climbing"), new CraftClimbingPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:conditioned_restrict_armor"), new CraftConditionedRestrictArmorPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:creative_flight"), new CraftCreativeFlightPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:damage_over_time"), new CraftDamageOverTimePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:disable_regen"), new CraftDisableRegenPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:effect_immunity"), new CraftEffectImmunityPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:elytra_flight"), new CraftElytraFlightPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:entity_glow"), new CraftEntityGlowPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:entity_group"), new CraftEntityGroupPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:exhaust"), new CraftExhaustPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:fire_immunity"), new CraftFireImmunityPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:fire_projectile"), new CraftFireProjectilePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:freeze"), new CraftFreezePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:invisibility"), new CraftInvisibilityPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:invulnerability"), new CraftInvulnerabilityPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:keep_inventory"), new CraftKeepInventoryPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:launch"), new CraftLaunchPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:multiple"), new CraftMultiplePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:particle"), new CraftParticlePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_glow"), new CraftSelfGlowPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:swimming"), new CraftSwimmingPower(plugin)));

        //MODIFY
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_break_speed"), new CraftModifyBreakSpeedPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_player_spawn"), new CraftModifyPlayerSpawnPower(plugin)));

        //ACTION
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_being_used"), new CraftActionOnBeingUsedPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_block_break"), new CraftActionOnBlockBreakPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_block_use"), new CraftActionOnBlockUsePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_entity_use"), new CraftActionOnEntityUsePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_hit"), new CraftActionOnHitPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_item_use"), new CraftActionOnItemUsePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_wake_up"), new CraftActionOnWakeUpPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_when_damage_taken"), new CraftActionWhenDamageTakenPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_when_hit"), new CraftActionWhenHitPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:attacker_action_when_hit"), new CraftAttackerActionWhenHitPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_on_hit"), new CraftSelfActionOnHitPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_on_kill"), new CraftSelfActionOnKill(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_when_hit"), new CraftSelfActionWhenHit(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:target_action_on_hit"), new CraftTargetActionOnHit(plugin)));

        //PREVENT
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_being_used"), new CraftPreventBeingUsedPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_block_use"), new CraftPreventBlockUsePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_death"), new CraftPreventDeathPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_elytra_flight"), new CraftPreventElytraFlightPower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_item_use"), new CraftPreventItemUsePower(plugin)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_sleep"), new CraftPreventSleepPower(plugin)));
    }

    public static void loadExpansions(OriginsBukkitPlugin plugin) {
        String s = File.separator;
        File file = new File(plugin.getJavaPlugin().getDataFolder().getAbsolutePath() + s + "expansions");
        if (!file.exists()) {
            file.mkdirs();
        }
        Plugin[] plugins = Bukkit.getPluginManager().loadPlugins(file);

        for (Plugin javaPlugin : plugins) {
            plugin.getExpansions().add(javaPlugin);
            Plugin aPlugin = Bukkit.getPluginManager().getPlugin(javaPlugin.getName());

            if (aPlugin == null) {
                Bukkit.getPluginManager().enablePlugin(javaPlugin);
            } else {
                if (!aPlugin.isEnabled()) {
                    Bukkit.getPluginManager().enablePlugin(aPlugin);
                } else {
                    ChatUtil.sendConsoleMessage("&c[Origins-Bukkit] Duplicate expansion " + javaPlugin.getName() + ".");
                    ChatUtil.sendConsoleMessage("&c[Origins-Bukkit] Please try restarting the server.");
                    plugin.disable();
                }
            }
        }
    }

    public static void registerOriginPacks(OriginsBukkitPlugin plugin) {
        String s = File.separator;
        File file = new File(plugin.getJavaPlugin().getDataFolder().getAbsolutePath() + s + "origins");
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] packs = file.listFiles();

        if (packs != null) {
            for (File pack : packs) {
                if (pack.isDirectory()) {
                    File packJson = new File(pack.getAbsolutePath() + s + "origins.json");
                    File[] powerFiles = new File(pack.getAbsolutePath() + s + "powers").listFiles();
                    File[] originFiles = new File(pack.getAbsolutePath() + s + "origins").listFiles();

                    if (powerFiles != null) {
                        for (File powerFile : powerFiles) {
                            plugin.getRegistry().register(
                                    plugin.getLoader().loadPowerFromFile(powerFile, pack.getName()));
                        }
                    }
                    if (originFiles != null) {
                        for (File originFile : originFiles) {
                            Origin origin = plugin.getLoader().loadOriginFromFile(originFile, pack.getName());

                            if (origin != null) {
                                if (origin.getJsonObject() != null) {
                                    if (origin.getJsonObject().has("disable_origin")) {
                                        boolean disableOrigin = origin.getJsonObject().get("disable_origin").getAsBoolean();

                                        if (!disableOrigin) {
                                            plugin.getRegistry().register(origin);
                                        }
                                    } else {
                                        plugin.getRegistry().register(origin);
                                    }
                                } else {
                                    plugin.getRegistry().register(origin);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (plugin.getRegistry().isOriginsEmpty()) {
            Origin origin = new CraftOrigin();
            List<Power> emptyPower = new ArrayList<>();

            origin.setIcon(new ItemStack(Material.STONE));
            origin.setImpact(Impact.NONE);
            origin.setAuthors(new String[]{"LemonyPancakes"});
            origin.setDisplayName(ChatUtil.format("&fDummy Origin"));
            origin.setDescription(ChatUtil.formatList(new String[]{"", "&7This appears when there", "&7is no origin available."}));
            origin.setIdentifier(Identifier.fromString("origins-bukkit:dummy_origin"));
            origin.setPowers(emptyPower);

            Inventory inventory = Bukkit.createInventory(null, 54, ChatUtil.format("&0No origins here. :("));
            ItemStack itemStack = new ItemStack(origin.getIcon());
            itemStack.setAmount(1);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setDisplayName(origin.getDisplayName());
                itemMeta.setLore(Arrays.asList(origin.getDescription()));
                itemMeta.setLocalizedName(origin.getIdentifier().toString());
                itemStack.setItemMeta(itemMeta);
            }
            inventory.setItem(22, itemStack);
            switch (origin.getImpact()) {
                case NONE:
                    ItemStack none = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta noneMeta = none.getItemMeta();
                    if (noneMeta != null) {
                        noneMeta.setDisplayName(ChatUtil.format("&fImpact: &7None"));
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
                        lowMeta.setDisplayName(ChatUtil.format("&fImpact: &aLow"));
                        low.setItemMeta(lowMeta);
                    }
                    ItemStack low1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta lowMeta1 = low1.getItemMeta();
                    if (lowMeta1 != null) {
                        lowMeta1.setDisplayName(ChatUtil.format("&fImpact: &aLow"));
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
                        mediumMeta.setDisplayName(ChatUtil.format("&fImpact: &eMedium"));
                        medium.setItemMeta(mediumMeta);
                    }
                    ItemStack medium1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta mediumMeta1 = medium1.getItemMeta();
                    if (mediumMeta1 != null) {
                        mediumMeta1.setDisplayName(ChatUtil.format("&fImpact: &eMedium"));
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
                        highMeta.setDisplayName(ChatUtil.format("&fImpact: &cHigh"));
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
                previousMeta.setDisplayName(ChatUtil.format("&6Previous Page"));
                previous.setItemMeta(previousMeta);
            }

            ItemStack close = new ItemStack(Material.BARRIER, 1);
            ItemMeta closeMeta = close.getItemMeta();
            if (closeMeta != null) {
                closeMeta.setDisplayName(ChatUtil.format("&cQuit Game"));
                close.setItemMeta(closeMeta);
            }

            ItemStack next = new ItemStack(Material.ARROW, 1);
            ItemMeta nextMeta = next.getItemMeta();
            if (nextMeta != null) {
                nextMeta.setDisplayName(ChatUtil.format("&6Next Page"));
                next.setItemMeta(nextMeta);
            }

            inventory.setItem(48, previous);
            inventory.setItem(49, close);
            inventory.setItem(50, next);
            origin.setInventoryGUI(inventory);

            plugin.getRegistry().register(origin);
        }
    }

    public static void checkServerCompatibility(OriginsBukkitPlugin plugin) {
        ServerUtils.checkServerSoftwareCompatibility(plugin);
        ServerUtils.checkServerVersionCompatibility(plugin);
    }

    public static void checkServerDependencies(OriginsBukkitPlugin plugin) {
        if (plugin.getJavaPlugin().isEnabled()) {
            ChatUtil.sendConsoleMessage("&3[Origins-Bukkit] Checking dependencies...");
        }
        if (plugin.getJavaPlugin().isEnabled()) {
            Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");

            if (protocolLib != null) {
                if (protocolLib.isEnabled()) {
                    ChatUtil.sendConsoleMessage("&a[Origins-Bukkit] ProtocolLib found! Hooking...");
                } else {
                    ChatUtil.sendConsoleMessage("&c[Origins-Bukkit] ProtocolLib seems to be disabled. Safely disabling plugin...");
                    plugin.disable();
                }
            } else {
                ChatUtil.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (ProtocolLib). Safely disabling plugin...");
                plugin.disable();
            }
        }
        if (plugin.getJavaPlugin().isEnabled()) {
            Plugin pancakeLibCore = Bukkit.getServer().getPluginManager().getPlugin("PancakeLibCore");

            if (pancakeLibCore != null) {
                if (pancakeLibCore.isEnabled()) {
                    ChatUtil.sendConsoleMessage("&a[Origins-Bukkit] PancakeLibCore found! Hooking...");
                } else {
                    ChatUtil.sendConsoleMessage("&c[Origins-Bukkit] PancakeLibCore seems to be disabled. Safely disabling plugin...");
                    plugin.disable();
                }
            } else {
                ChatUtil.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (PancakeLibCore). Safely disabling plugin...");
                plugin.disable();
            }
        }
    }
}
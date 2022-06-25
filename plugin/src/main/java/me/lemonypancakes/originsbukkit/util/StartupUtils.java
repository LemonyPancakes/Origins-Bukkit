package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.Origin;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftOrigin;
import me.lemonypancakes.originsbukkit.factory.power.action.*;
import me.lemonypancakes.originsbukkit.factory.power.modify.*;
import me.lemonypancakes.originsbukkit.factory.power.prevent.*;
import me.lemonypancakes.originsbukkit.factory.power.regular.*;
import me.lemonypancakes.originsbukkit.factory.power.temporary.*;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class StartupUtils {

    public static void registerFactories(OriginsBukkitPlugin plugin) {

        //REGULAR
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:attribute"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftAttributePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:burn"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftBurnPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:climbing"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftClimbingPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:conditioned_restrict_armor"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftConditionedRestrictArmorPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:creative_flight"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftCreativeFlightPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:damage_over_time"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftDamageOverTimePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:disable_regen"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftDisableRegenPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:effect_immunity"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftEffectImmunityPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:elytra_flight"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftElytraFlightPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:entity_glow"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftEntityGlowPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:entity_group"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftEntityGroupPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:exhaust"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftExhaustPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:fire_immunity"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftFireImmunityPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:fire_projectile"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftFireProjectilePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:freeze"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftFreezePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:inventory"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftInventoryPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:invisibility"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftInvisibilityPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:invulnerability"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftInvulnerabilityPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:keep_inventory"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftKeepInventoryPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:launch"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftLaunchPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:multiple"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftMultiplePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:particle"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftParticlePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_glow"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftSelfGlowPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:swimming"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftSwimmingPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:water_breathing"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftWaterBreathingPower(plugin1, identifier, jsonObject)));

        //MODIFY
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_break_speed"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftModifyBreakSpeedPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_damage_dealt"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftModifyDamageDealtPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_damage_taken"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftModifyDamageTakenPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_falling"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftModifyFallingPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_harvest"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftModifyHarvestPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:modify_player_spawn"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftModifyPlayerSpawnPower(plugin1, identifier, jsonObject)));

        //ACTION
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_being_used"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnBeingUsedPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_block_break"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnBlockBreakPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_block_use"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnBlockUsePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_entity_use"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnEntityUsePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_hit"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnHitPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_item_use"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnItemUsePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_wake_up"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOnWakeUpPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_over_time"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionOverTimePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_when_damage_taken"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionWhenDamageTakenPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:action_when_hit"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftActionWhenHitPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:attacker_action_when_hit"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftAttackerActionWhenHitPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_on_hit"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftSelfActionOnHitPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_on_kill"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftSelfActionOnKill(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_when_hit"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftSelfActionWhenHit(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:target_action_on_hit"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftTargetActionOnHit(plugin1, identifier, jsonObject)));

        //PREVENT
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_being_used"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventBeingUsedPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_block_break"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventBlockBreakPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_block_use"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventBlockUsePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_death"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventDeathPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_elytra_flight"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventElytraFlightPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_item_use"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventItemUsePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_sleep"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftPreventSleepPower(plugin1, identifier, jsonObject)));

        //TEMPORARY
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:aerial_combatant"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftAerialCombatantPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:aqua_affinity"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftAquaAffinityPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:burning_wrath"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftBurningWrathPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:claustrophobia"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftClaustrophobiaPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:damage_from_potions"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftDamageFromPotionsPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:damage_from_snowballs"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftDamageFromSnowballsPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:light_armor"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftLightArmorPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:more_kinetic_damage"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftMoreKineticDamagePower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:no_shield"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftNoShieldPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:swim_speed"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftSwimSpeedPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:throw_ender_pearl"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftThrowEnderPearlPower(plugin1, identifier, jsonObject)));
        plugin.getRegistry().register(new Power.Factory(Identifier.fromString("origins-bukkit:water_vision"), (plugin1, identifier) -> (jsonObject) -> () -> new CraftWaterVisionPower(plugin1, identifier, jsonObject)));
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
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Duplicate expansion " + javaPlugin.getName() + ".");
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Please try restarting the server.");
                    plugin.disable();
                }
            }
        }
    }

    public static void registerOriginPacks(OriginsBukkitPlugin plugin) {
        String s = File.separator;
        File serverContainer = new File(plugin.getJavaPlugin().getServer().getWorldContainer().getAbsolutePath());
        File serverProperties = new File(serverContainer.getAbsolutePath() + s + "server.properties");

        try (InputStream stream = Files.newInputStream(serverProperties.toPath())) {
            Properties properties = new Properties();

            properties.load(stream);
            File defaultWorld = new File(serverContainer.getAbsolutePath() + s + properties.getProperty("level-name"));
            File datapacksFolder = new File(defaultWorld.getAbsolutePath() + s + "datapacks");
            InputStream originsBukkitZip = plugin.getJavaPlugin().getResource("datapacks" + s + "origins-bukkit.zip");

            if (originsBukkitZip != null) {
                Files.copy(originsBukkitZip, new File(datapacksFolder.getAbsolutePath() + s + "origins-bukkit.zip").toPath(), StandardCopyOption.REPLACE_EXISTING);
                originsBukkitZip.close();
            }
            File[] packs = datapacksFolder.listFiles();

            if (packs != null) {
                for (File pack : packs) {
                    if (pack.isDirectory()) {
                        File[] dataFolder = new File(pack.getAbsolutePath() + s + "data").listFiles();

                        if (dataFolder != null) {
                            for (File file : dataFolder) {
                                if (file.isDirectory()) {
                                    File[] originOriginFiles = file.listFiles();

                                    if (originOriginFiles != null) {
                                        for (File originOriginFile : originOriginFiles) {
                                            if (originOriginFile.isDirectory()) {
                                                if (originOriginFile.getName().equals("powers")) {
                                                    File[] powerFiles = originOriginFile.listFiles();

                                                    if (powerFiles != null) {
                                                        for (File powerFile : powerFiles) {
                                                            Reader reader = new FileReader(powerFile);

                                                            plugin.getRegistry().register(plugin.getLoader().loadPowerFromFile(reader, file.getName(), FilenameUtils.getBaseName(powerFile.getName())));
                                                            reader.close();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (pack.getName().endsWith(".zip")) {
                        boolean resume = true;

                        for (File file : packs) {
                            if (file.getName().equals(FilenameUtils.getBaseName(pack.getName()))) {
                                resume = false;
                                break;
                            }
                        }
                        if (resume) {
                            try {
                                ZipFile zipFile = new ZipFile(pack);
                                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                                while (entries.hasMoreElements()) {
                                    ZipEntry zipEntry = entries.nextElement();
                                    String zipEntryName = zipEntry.getName();
                                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                                    Reader reader = new InputStreamReader(inputStream);
                                    String[] split = zipEntryName.split(s);

                                    if (split.length >= 3) {
                                        if (split[0].equals("data") && split[2].equals("powers") && zipEntryName.endsWith(".json")) {
                                            plugin.getRegistry().register(plugin.getLoader().loadPowerFromFile(reader, split[1], FilenameUtils.getBaseName(zipEntryName.replaceFirst("powers" + s, ""))));
                                        }
                                    }
                                    reader.close();
                                    inputStream.close();
                                }
                                zipFile.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                for (File pack : packs) {
                    if (pack.isDirectory()) {
                        File[] dataFolder = new File(pack.getAbsolutePath() + s + "data").listFiles();

                        if (dataFolder != null) {
                            for (File file : dataFolder) {
                                if (file.isDirectory()) {
                                    File[] originOriginFiles = file.listFiles();

                                    if (originOriginFiles != null) {
                                        for (File originOriginFile : originOriginFiles) {
                                            if (originOriginFile.isDirectory()) {
                                                if (originOriginFile.getName().equals("origins")) {
                                                    File[] originFiles = originOriginFile.listFiles();

                                                    if (originFiles != null) {
                                                        for (File originFile : originFiles) {
                                                            Reader reader = new FileReader(originFile);

                                                            plugin.getRegistry().register(plugin.getLoader().loadOriginFromFile(reader, file.getName(), FilenameUtils.getBaseName(originFile.getName())));
                                                            reader.close();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (pack.getName().endsWith(".zip")) {
                        boolean resume = true;

                        for (File file : packs) {
                            if (file.getName().equals(FilenameUtils.getBaseName(pack.getName()))) {
                                resume = false;
                                break;
                            }
                        }
                        if (resume) {
                            try {
                                ZipFile zipFile = new ZipFile(pack);
                                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                                while (entries.hasMoreElements()) {
                                    ZipEntry zipEntry = entries.nextElement();
                                    String zipEntryName = zipEntry.getName();
                                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                                    Reader reader = new InputStreamReader(inputStream);
                                    String[] split = zipEntryName.split(s);

                                    if (split.length >= 3) {
                                        if (split[0].equals("data") && split[2].equals("origins") && zipEntryName.endsWith(".json")) {
                                            plugin.getRegistry().register(plugin.getLoader().loadOriginFromFile(reader, split[1], FilenameUtils.getBaseName(zipEntryName.replaceFirst("origins" + s, ""))));
                                        }
                                    }
                                    reader.close();
                                    inputStream.close();
                                }
                                zipFile.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (plugin.getRegistry().isOriginsEmpty()) {
            Origin origin = new CraftOrigin();
            List<Power> emptyPower = new ArrayList<>();

            origin.setIcon(new ItemStack(Material.STONE));
            origin.setImpact(Impact.NONE);
            origin.setAuthors(new String[]{"LemonyPancakes"});
            origin.setDisplayName(Lang.GUI_DUMMY_ORIGIN_TITLE.toString());
            origin.setDescription(Lang.GUI_DUMMY_ORIGIN_DESCRIPTION.toStringArray());
            origin.setIdentifier(Identifier.fromString("origins-bukkit:dummy_origin"));
            origin.setPowers(emptyPower);

            Inventory inventory = Bukkit.createInventory(null, 54, Lang.GUI_HEADER_TEXT_NO_ORIGIN.toString());
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

            plugin.getRegistry().register(origin);
        }
    }

    public static void checkServerCompatibility(OriginsBukkitPlugin plugin) {
        ServerUtils.checkServerSoftwareCompatibility(plugin);
        ServerUtils.checkServerVersionCompatibility(plugin);
    }

    public static void checkServerDependencies(OriginsBukkitPlugin plugin) {
        if (plugin.getJavaPlugin().isEnabled()) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Checking dependencies...");
        }
        if (plugin.getJavaPlugin().isEnabled()) {
            Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");

            if (protocolLib != null) {
                if (protocolLib.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] ProtocolLib found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] ProtocolLib seems to be disabled. Safely disabling plugin...");
                    plugin.disable();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (ProtocolLib). Safely disabling plugin...");
                plugin.disable();
            }
        }
        if (plugin.getJavaPlugin().isEnabled()) {
            Plugin lemonLib = Bukkit.getServer().getPluginManager().getPlugin("LemonLib");

            if (lemonLib != null) {
                if (lemonLib.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] LemonLib found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] LemonLib seems to be disabled. Safely disabling plugin...");
                    plugin.disable();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (LemonLib). Safely disabling plugin...");
                plugin.disable();
            }
        }
    }
}

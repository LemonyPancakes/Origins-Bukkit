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
package me.lemonypancakes.bukkit.origins.util;

import me.lemonypancakes.bukkit.common.org.apache.commons.io.FilenameUtils;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.factory.power.action.*;
import me.lemonypancakes.bukkit.origins.factory.power.modify.*;
import me.lemonypancakes.bukkit.origins.factory.power.prevent.*;
import me.lemonypancakes.bukkit.origins.factory.power.regular.*;
import me.lemonypancakes.bukkit.origins.factory.power.temporary.*;
import org.bukkit.Bukkit;
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
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:attribute"), (p) -> (i) -> (j) -> () -> new CraftAttributePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:burn"), (p) -> (i) -> (j) -> () -> new CraftBurnPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:climbing"), (p) -> (i) -> (j) -> () -> new CraftClimbingPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:conditioned_restrict_armor"), (p) -> (i) -> (j) -> () -> new CraftConditionedRestrictArmorPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:creative_flight"), (p) -> (i) -> (j) -> () -> new CraftCreativeFlightPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:damage_over_time"), (p) -> (i) -> (j) -> () -> new CraftDamageOverTimePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:disable_regen"), (p) -> (i) -> (j) -> () -> new CraftDisableRegenPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:effect_immunity"), (p) -> (i) -> (j) -> () -> new CraftEffectImmunityPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:elytra_flight"), (p) -> (i) -> (j) -> () -> new CraftElytraFlightPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:entity_glow"), (p) -> (i) -> (j) -> () -> new CraftEntityGlowPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:entity_group"), (p) -> (i) -> (j) -> () -> new CraftEntityGroupPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:exhaust"), (p) -> (i) -> (j) -> () -> new CraftExhaustPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:fire_immunity"), (p) -> (i) -> (j) -> () -> new CraftFireImmunityPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:fire_projectile"), (p) -> (i) -> (j) -> () -> new CraftFireProjectilePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:freeze"), (p) -> (i) -> (j) -> () -> new CraftFreezePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:inventory"), (p) -> (i) -> (j) -> () -> new CraftInventoryPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:invisibility"), (p) -> (i) -> (j) -> () -> new CraftInvisibilityPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:invulnerability"), (p) -> (i) -> (j) -> () -> new CraftInvulnerabilityPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:keep_inventory"), (p) -> (i) -> (j) -> () -> new CraftKeepInventoryPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:launch"), (p) -> (i) -> (j) -> () -> new CraftLaunchPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:multiple"), (p) -> (i) -> (j) -> () -> new CraftMultiplePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:particle"), (p) -> (i) -> (j) -> () -> new CraftParticlePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:self_glow"), (p) -> (i) -> (j) -> () -> new CraftSelfGlowPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:swimming"), (p) -> (i) -> (j) -> () -> new CraftSwimmingPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:water_breathing"), (p) -> (i) -> (j) -> () -> new CraftWaterBreathingPower(p, i, j)));

        //MODIFY
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:modify_break_speed"), (p) -> (i) -> (j) -> () -> new CraftModifyBreakSpeedPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:modify_damage_dealt"), (p) -> (i) -> (j) -> () -> new CraftModifyDamageDealtPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:modify_damage_taken"), (p) -> (i) -> (j) -> () -> new CraftModifyDamageTakenPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:modify_falling"), (p) -> (i) -> (j) -> () -> new CraftModifyFallingPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:modify_harvest"), (p) -> (i) -> (j) -> () -> new CraftModifyHarvestPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:modify_player_spawn"), (p) -> (i) -> (j) -> () -> new CraftModifyPlayerSpawnPower(p, i, j)));

        //ACTION
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_being_used"), (p) -> (i) -> (j) -> () -> new CraftActionOnBeingUsedPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_block_break"), (p) -> (i) -> (j) -> () -> new CraftActionOnBlockBreakPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_block_use"), (p) -> (i) -> (j) -> () -> new CraftActionOnBlockUsePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_entity_use"), (p) -> (i) -> (j) -> () -> new CraftActionOnEntityUsePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_hit"), (p) -> (i) -> (j) -> () -> new CraftActionOnHitPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_item_use"), (p) -> (i) -> (j) -> () -> new CraftActionOnItemUsePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_on_wake_up"), (p) -> (i) -> (j) -> () -> new CraftActionOnWakeUpPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_over_time"), (p) -> (i) -> (j) -> () -> new CraftActionOverTimePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_when_damage_taken"), (p) -> (i) -> (j) -> () -> new CraftActionWhenDamageTakenPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:action_when_hit"), (p) -> (i) -> (j) -> () -> new CraftActionWhenHitPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:attacker_action_when_hit"), (p) -> (i) -> (j) -> () -> new CraftAttackerActionWhenHitPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_on_hit"), (p) -> (i) -> (j) -> () -> new CraftSelfActionOnHitPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_on_kill"), (p) -> (i) -> (j) -> () -> new CraftSelfActionOnKill(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:self_action_when_hit"), (p) -> (i) -> (j) -> () -> new CraftSelfActionWhenHit(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:target_action_on_hit"), (p) -> (i) -> (j) -> () -> new CraftTargetActionOnHit(p, i, j)));

        //PREVENT
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_being_used"), (p) -> (i) -> (j) -> () -> new CraftPreventBeingUsedPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_block_break"), (p) -> (i) -> (j) -> () -> new CraftPreventBlockBreakPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_block_use"), (p) -> (i) -> (j) -> () -> new CraftPreventBlockUsePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_death"), (p) -> (i) -> (j) -> () -> new CraftPreventDeathPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_elytra_flight"), (p) -> (i) -> (j) -> () -> new CraftPreventElytraFlightPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_item_use"), (p) -> (i) -> (j) -> () -> new CraftPreventItemUsePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:prevent_sleep"), (p) -> (i) -> (j) -> () -> new CraftPreventSleepPower(p, i, j)));

        //TEMPORARY
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:aerial_combatant"), (p) -> (i) -> (j) -> () -> new CraftAerialCombatantPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:aqua_affinity"), (p) -> (i) -> (j) -> () -> new CraftAquaAffinityPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:burning_wrath"), (p) -> (i) -> (j) -> () -> new CraftBurningWrathPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:claustrophobia"), (p) -> (i) -> (j) -> () -> new CraftClaustrophobiaPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:damage_from_potions"), (p) -> (i) -> (j) -> () -> new CraftDamageFromPotionsPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:damage_from_snowballs"), (p) -> (i) -> (j) -> () -> new CraftDamageFromSnowballsPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:light_armor"), (p) -> (i) -> (j) -> () -> new CraftLightArmorPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:master_of_webs"), (p) -> (i) -> (j) -> () -> new CraftMasterOfWebsPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:more_kinetic_damage"), (p) -> (i) -> (j) -> () -> new CraftMoreKineticDamagePower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:no_shield"), (p) -> (i) -> (j) -> () -> new CraftNoShieldPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:swim_speed"), (p) -> (i) -> (j) -> () -> new CraftSwimSpeedPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:throw_ender_pearl"), (p) -> (i) -> (j) -> () -> new CraftThrowEnderPearlPower(p, i, j)));
        plugin.getRegistry().registerPowerFactory(new Power.Factory(Identifier.fromString("origins-bukkit:water_vision"), (p) -> (i) -> (j) -> () -> new CraftWaterVisionPower(p, i, j)));
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
            InputStream originsBukkitZip = plugin.getJavaPlugin().getResource("datapacks/origins-bukkit.zip");

            if (originsBukkitZip != null) {
                try {
                    Files.copy(originsBukkitZip, new File(datapacksFolder.getAbsolutePath() + s + "origins-bukkit.zip").toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ignored) {}
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

                                                            plugin.getRegistry().registerPower(plugin.getLoader().loadPowerFromFile(reader, file.getName(), FilenameUtils.getBaseName(powerFile.getName())));
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
                                    String[] split = zipEntryName.split("/");

                                    if (split.length >= 3) {
                                        if (split[0].equals("data") && split[2].equals("powers") && zipEntryName.endsWith(".json")) {
                                            plugin.getRegistry().registerPower(plugin.getLoader().loadPowerFromFile(reader, split[1], FilenameUtils.getBaseName(zipEntryName.replaceFirst("powers/", ""))));
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

                                                            plugin.getRegistry().registerOrigin(plugin.getLoader().loadOriginFromFile(reader, file.getName(), FilenameUtils.getBaseName(originFile.getName())));
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
                                    String[] split = zipEntryName.split("/");

                                    if (split.length >= 3) {
                                        if (split[0].equals("data") && split[2].equals("origins") && zipEntryName.endsWith(".json")) {
                                            plugin.getRegistry().registerOrigin(plugin.getLoader().loadOriginFromFile(reader, split[1], FilenameUtils.getBaseName(zipEntryName.replaceFirst("origins/", ""))));
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
                List<OriginLayer> originLayers = new ArrayList<>();

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
                                                if (originOriginFile.getName().equals("origin_layers")) {
                                                    File[] originFiles = originOriginFile.listFiles();

                                                    if (originFiles != null) {
                                                        for (File originFile : originFiles) {
                                                            Reader reader = new FileReader(originFile);
                                                            OriginLayer originLayer = plugin.getLoader().loadOriginLayerFromFile(reader, file.getName(), FilenameUtils.getBaseName(originFile.getName()));

                                                            if (originLayer != null) {
                                                                originLayers.add(originLayer);
                                                            }
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
                                    String[] split = zipEntryName.split("/");

                                    if (split.length >= 3) {
                                        if (split[0].equals("data") && split[2].equals("origin_layers") && zipEntryName.endsWith(".json")) {
                                            OriginLayer originLayer = plugin.getLoader().loadOriginLayerFromFile(reader, split[1], FilenameUtils.getBaseName(zipEntryName.replaceFirst("origin_layers/", "")));

                                            if (originLayer != null) {
                                                originLayers.add(originLayer);
                                            }
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
                originLayers.sort(Comparator.comparingInt(OriginLayer::getLoadingPriority));
                originLayers.forEach(originLayer -> plugin.getRegistry().registerOriginLayer(originLayer));
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            Plugin lemonCommonLib = Bukkit.getServer().getPluginManager().getPlugin("LemonCommonLib");

            if (lemonCommonLib != null) {
                if (lemonCommonLib.isEnabled()) {
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] LemonCommonLib found! Hooking...");
                } else {
                    ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] LemonCommonLib seems to be disabled. Safely disabling plugin...");
                    plugin.disable();
                }
            } else {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Dependency not found (LemonCommonLib). Safely disabling plugin...");
                plugin.disable();
            }
        }
    }
}

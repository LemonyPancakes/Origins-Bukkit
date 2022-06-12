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
package me.lemonypancakes.originsbukkit.config;

import com.tchristofferson.configupdater.ConfigUpdater;
import me.lemonypancakes.originsbukkit.ConfigHandler;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class CraftConfigHandler implements ConfigHandler {

    private final OriginsBukkitPlugin plugin;
    private YamlConfiguration CONFIG;
    private File CONFIG_FILE;
    private YamlConfiguration LANG;
    private File LANG_FILE;

    public YamlConfiguration getConfig() {
        return CONFIG;
    }

    public File getConfigFile() {
        return CONFIG_FILE;
    }

    public YamlConfiguration getLang() {
        return LANG;
    }

    public File getLangFile() {
        return LANG_FILE;
    }

    public CraftConfigHandler(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        loadConfigurationFiles();
    }

    public void loadConfigurationFiles() {
        if (!plugin.getJavaPlugin().getDataFolder().exists()) {
            plugin.getJavaPlugin().getDataFolder().mkdir();
        }
        ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] Loading files...");
        try {
            loadConfig();
            loadLang();
        } catch (Exception event) {
            event.printStackTrace();
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] There was an error loading the files.");
        }
        ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Successfully loaded the files.");
    }

    public void loadConfig() {
        File configFile = new File(plugin.getJavaPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] The config.yml file was not found. Creating one...");
            try {
                configFile.createNewFile();
                InputStream defaultConfigStream = plugin.getJavaPlugin().getResource("config.yml");
                if (defaultConfigStream != null) {
                    YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
                    defaultConfig.save(configFile);
                    try {
                        ConfigUpdater.update(plugin.getJavaPlugin(), "config.yml", configFile, (List<String>) null);
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                    Config.setFile(defaultConfig);
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Successfully created the config.yml file");
                }
            } catch (IOException event) {
                event.printStackTrace();
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Couldn't create config file.");
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] This is a fatal error. Now disabling.");
                plugin.disable();
            }
        }
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        for (Config item : Config.values()) {
            if (yamlConfiguration.getString(item.getPath()) == null) {
                yamlConfiguration.set(item.getPath(), item.getDefaultValue());
            }
        }
        Config.setFile(yamlConfiguration);
        CONFIG = yamlConfiguration;
        CONFIG_FILE = configFile;
        try {
            yamlConfiguration.save(getConfigFile());
        } catch (IOException event) {
            event.printStackTrace();
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Failed to save lang.yml.");
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] this stack trace to <your name>.");
        }
        try {
            ConfigUpdater.update(plugin.getJavaPlugin(), "config.yml", configFile, Collections.singletonList("custom_item.orb_of_origin.recipe.ingredients"));
        } catch (IOException event) {
            event.printStackTrace();
        }
        YamlConfiguration.loadConfiguration(configFile);
    }

    public void loadLang() {
        File langFile = new File(plugin.getJavaPlugin().getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] The lang.yml file was not found. Creating one...");
            try {
                langFile.createNewFile();
                InputStream defaultConfigStream = plugin.getJavaPlugin().getResource("lang.yml");
                if (defaultConfigStream != null) {
                    YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
                    defaultConfig.save(langFile);
                    try {
                        ConfigUpdater.update(plugin.getJavaPlugin(), "lang.yml", langFile, (List<String>) null);
                    } catch (IOException event) {
                        event.printStackTrace();
                    }
                    Lang.setFile(defaultConfig);
                    ChatUtils.sendConsoleMessage("&a[Origins-Bukkit] Successfully created the lang.yml file");
                }
            } catch (IOException event) {
                event.printStackTrace();
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Couldn't create language file.");
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] This is a fatal error. Now disabling.");
                plugin.disable();
            }
        }
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(langFile);
        for (Lang item : Lang.values()) {
            if (yamlConfiguration.getString(item.getPath()) == null) {
                yamlConfiguration.set(item.getPath(), item.getDefaultValue());
            }
        }
        Lang.setFile(yamlConfiguration);
        LANG = yamlConfiguration;
        LANG_FILE = langFile;
        try {
            yamlConfiguration.save(getLangFile());
        } catch (IOException event) {
            event.printStackTrace();
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Failed to save lang.yml.");
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] this stack trace to <your name>.");
        }
        try {
            ConfigUpdater.update(plugin.getJavaPlugin(), "lang.yml", langFile, (List<String>) null);
        } catch (IOException event) {
            event.printStackTrace();
        }
        YamlConfiguration.loadConfiguration(langFile);
    }

    public void reloadFiles() {
        if (!plugin.getJavaPlugin().getDataFolder().exists()) {
            plugin.getJavaPlugin().getDataFolder().mkdir();
        }
        try {
            loadConfig();
            loadLang();
        } catch (Exception event) {
            event.printStackTrace();
        }
    }
}

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
package me.lemonypancakes.bukkit.origins.util;

import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerUtils {

    private static final List<String> SUPPORTED_VERSIONS = new ArrayList<>(
            Arrays.asList("1.17", "1.17.1", "1.18", "1.18.1", "1.18.2", "1.19"));

    public static String getServerSoftware() {
        return Bukkit.getVersion().split("-")[1];
    }

    public static boolean isServerSoftwareSafe() {
        String serverSoftware = getServerSoftware().toUpperCase();

        switch (serverSoftware) {
            case "BUKKIT":
            case "SPIGOT":
            case "PAPER":
            case "TUINITY":
            case "PURPUR":
            case "AIRPLANE":
                return true;
            case "CUSTOM":
            default:
                return false;
        }
    }

    public static void checkServerSoftwareCompatibility(OriginsBukkitPlugin plugin) {
        if (isServerSoftwareSafe()) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &3Server running &6" + getServerSoftware() + " &3version &6" + Bukkit.getVersion());
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &3(Implementing API version &6" + Bukkit.getVersion() + "&3)");
        } else {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &3Server running &6" + getServerSoftware() + " &3version &6" + Bukkit.getVersion());
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &3(Implementing API version &6" + Bukkit.getVersion() + "&3)");
            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] Custom Software Detected! You are using a custom server software.");
            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] There is no assurance of this plugin fully supporting the custom software!");
            ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] Use at your own risk.");
        }
    }

    public static String getServerVersion() {
        return Bukkit.getServer().getBukkitVersion().split("-")[0];
    }

    public static void checkServerVersionCompatibility(OriginsBukkitPlugin plugin) {
        if (SUPPORTED_VERSIONS.contains(getServerVersion())) {
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit]");
            ChatUtils.sendConsoleMessage("&3[Origins-Bukkit] &dSupported Server Version Detected. Initializing!");
        } else {
            ChatUtils.sendConsoleMessage("&4[Origins-Bukkit] Unsupported Server Version Detected! Safely disabling plugin...");
            plugin.disable();
        }
    }
}

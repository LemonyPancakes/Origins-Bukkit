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
package me.lemonypancakes.originsbukkit.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerVersionChecker {

    private final OriginsBukkit plugin;
    private final List<String> SUPPORTED_VERSIONS = new ArrayList<>(
            Arrays.asList("1.17", "1.17.1", "1.18", "1.18.1"));

    public ServerVersionChecker(OriginsBukkit plugin) {
        this.plugin = plugin;
    }

    public String getServerSoftware() {
        return Bukkit.getVersion().split("-")[1];
    }

    public boolean isServerSoftwareSafe() {
        String serverSoftware = getServerSoftware().toUpperCase();

        switch (serverSoftware) {
            case "BUKKIT":
            case "SPIGOT":
            case "PAPER":
            case "TUINITY":
            case "PURPUR":
            case "AIRPLANE":
                return true;
            case "YATOPIA":
            case "CUSTOM":
            default:
                return false;
        }
    }

    public void checkServerSoftwareCompatibility() {
        if (isServerSoftwareSafe()) {
            Message.sendConsoleMessage("&3[Origins-Bukkit] &3Server running &6" + getServerSoftware() + " &3version &6" + Bukkit.getVersion());
            Message.sendConsoleMessage("&3[Origins-Bukkit] &3(Implementing API version &6" + Bukkit.getVersion() + "&3)");
        } else {
            if (getServerSoftware().equalsIgnoreCase("YATOPIA")) {
                Message.sendConsoleMessage("&3[Origins-Bukkit] &3Server running &6" + getServerSoftware() + " &3version &6" + Bukkit.getVersion());
                Message.sendConsoleMessage("&3[Origins-Bukkit] &3(Implementing API version &6" + Bukkit.getVersion() + "&3)");
                Message.sendConsoleMessage("&6[Origins-Bukkit] Warning! You are using an unstable minecraft server software");
                Message.sendConsoleMessage("&6[Origins-Bukkit] that might break some plugin features! Use at your own risk.");
            } else {
                Message.sendConsoleMessage("&3[Origins-Bukkit] &3Server running &6" + getServerSoftware() + " &3version &6" + Bukkit.getVersion());
                Message.sendConsoleMessage("&3[Origins-Bukkit] &3(Implementing API version &6" + Bukkit.getVersion() + "&3)");
                Message.sendConsoleMessage("&6[Origins-Bukkit] Custom Software Detected! You are using a custom server software.");
                Message.sendConsoleMessage("&6[Origins-Bukkit] There is no assurance of this plugin fully supporting the custom software!");
                Message.sendConsoleMessage("&6[Origins-Bukkit] Use at your own risk.");
            }
        }
    }

    public String getServerVersion() {
        return Bukkit.getServer().getBukkitVersion().split("-")[0];
    }

    public void checkServerVersionCompatibility() {
        if (SUPPORTED_VERSIONS.contains(getServerVersion())) {
            Message.sendConsoleMessage("&3[Origins-Bukkit]");
            Message.sendConsoleMessage("&3[Origins-Bukkit] &dSupported Server Version Detected. Initializing!");
        } else {
            Message.sendConsoleMessage("&4[Origins-Bukkit] Unsupported Server Version Detected! Safely disabling plugin...");
            plugin.disablePlugin();
        }
    }
}

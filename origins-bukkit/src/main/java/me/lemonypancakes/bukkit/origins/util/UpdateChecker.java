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

import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private final OriginsBukkitPlugin plugin;
    private final int projectID;
    private URL url;
    private String newVersion;

    public UpdateChecker(OriginsBukkitPlugin plugin, int projectID){
        this.plugin = plugin;
        this.newVersion = plugin.getJavaPlugin().getDescription().getVersion();
        this.projectID = projectID;

        try {
            this.url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException ignored) {}
    }

    public int getProjectID() {
        return projectID;
    }

    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    public String getLatestVersion() {
        return newVersion;
    }

    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + projectID;
    }

    public boolean checkForUpdates() throws Exception {
        URLConnection connection = url.openConnection();
        this.newVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
        String currentVersion = plugin.getJavaPlugin().getDescription().getVersion();

        if (newVersion != null) {
            if (newVersion.contains(".") && currentVersion.contains(".")) {
                String[] newVersionStringArray = newVersion.split("\\.");
                String[] currentVersionStringArray = currentVersion.split("\\.");

                if (newVersionStringArray[0] != null && currentVersionStringArray[0] != null) {
                    if (Integer.parseInt(newVersionStringArray[0]) > Integer.parseInt(currentVersionStringArray[0])) {
                        return true;
                    }
                }
                if (newVersionStringArray[1] != null && currentVersionStringArray[1] != null) {
                    if (Integer.parseInt(newVersionStringArray[1]) > Integer.parseInt(currentVersionStringArray[1])) {
                        return true;
                    }
                }
                if (newVersionStringArray[2] != null && currentVersionStringArray[2] != null) {
                    return Integer.parseInt(newVersionStringArray[2]) > Integer.parseInt(currentVersionStringArray[2]);
                }
            }
        }
        return false;
    }
}

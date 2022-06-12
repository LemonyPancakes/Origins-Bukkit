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

import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class UpdateChecker {

    private final OriginsBukkitPlugin plugin;
    private final int projectID;
    private URL checkURL;
    private String newVersion = "";

    public UpdateChecker(OriginsBukkitPlugin plugin, int projectID){
        this.plugin = plugin;
        this.newVersion = plugin.getJavaPlugin().getDescription().getVersion();
        this.projectID = projectID;

        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException ignored) {
        }
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
        URLConnection con = checkURL.openConnection();
        this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        int newVerson = Integer.parseInt(this.newVersion.split(" ")[1].split("-")[0]);
        int currentVersion = Integer.parseInt(plugin.getJavaPlugin().getDescription().getVersion().split(" ")[1].split("-")[0]);
        String A = this.newVersion.split(" ")[1].split("-")[1];
        String B = plugin.getJavaPlugin().getDescription().getVersion().split(" ")[1].split("-")[1];

        if (newVerson > currentVersion || newVerson >= currentVersion && !Objects.equals(A, B)) {
            return true;
        }
        return false;
    }
}

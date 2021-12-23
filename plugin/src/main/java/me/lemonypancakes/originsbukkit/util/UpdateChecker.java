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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * The type Update checker.
 *
 * @author LemonyPancakes
 */
public class UpdateChecker {

    private final OriginsBukkit plugin;
    private final int projectID;
    private URL checkURL;
    private String newVersion = "";

    /**
     * Instantiates a new Update checker.
     *
     * @param plugin    the plugin
     * @param projectID the project id
     */
    public UpdateChecker(OriginsBukkit plugin, int projectID){
        this.plugin = plugin;
        this.newVersion = plugin.getDescription().getVersion();
        this.projectID = projectID;

        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException ignored) {
        }
    }

    /**
     * Gets project id.
     *
     * @return the project id
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public OriginsBukkit getPlugin() {
        return plugin;
    }

    /**
     * Gets latest version.
     *
     * @return the latest version
     */
    public String getLatestVersion() {
        return newVersion;
    }

    /**
     * Gets resource url.
     *
     * @return the resource url
     */
    public String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + projectID;
    }

    /**
     * Check for updates boolean.
     *
     * @return the boolean
     *
     * @throws Exception the exception
     */
    public boolean checkForUpdates() throws Exception {
        URLConnection con = checkURL.openConnection();
        this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        int newVerson = Integer.parseInt(this.newVersion.split(" ")[1].split("-")[0]);
        int currentVersion = Integer.parseInt(plugin.getDescription().getVersion().split(" ")[1].split("-")[0]);
        String A = this.newVersion.split(" ")[1].split("-")[1];
        String B = plugin.getDescription().getVersion().split(" ")[1].split("-")[1];

        if (newVerson > currentVersion || newVerson >= currentVersion && !Objects.equals(A, B)) {
            return true;
        }
        return false;
    }
}

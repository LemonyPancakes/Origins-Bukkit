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
package me.lemonypancakes.originsbukkit.nms;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.nms.mobs.NMSMobHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * The type Nms handler.
 *
 * @author LemonyPancakes
 */
public class NMSHandler {

    private final OriginsBukkit plugin;
    private NMSMobHandler nmsMobHandler;

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Gets nms mob handler.
     *
     * @return the nms mob handler
     */
    public NMSMobHandler getNMSMobHandler() {
        return nmsMobHandler;
    }

    /**
     * Instantiates a new Nms handler.
     *
     * @param plugin the plugin
     */
    public NMSHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    /**
     * Gets nms server version.
     *
     * @return the nms server version
     */
    public String getNMSServerVersion() {
        return Bukkit.getServer()
                .getClass()
                .getPackage()
                .getName()
                .replace(".", ",")
                .split(",")[3];
    }

    /**
     * Gets nms package name.
     *
     * @return the nms package name
     */
    public String getNMSPackageName() {
        return Bukkit.getServer()
                .getClass()
                .getPackage()
                .getName();
    }

    /**
     * Init.
     */
    private void init() {
        nmsMobHandler = new NMSMobHandler(this);
    }
}

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

public class NMSHandler {

    private final OriginsBukkit plugin;
    private NMSMobHandler nmsMobHandler;

    public Plugin getPlugin() {
        return plugin;
    }

    public NMSMobHandler getNMSMobHandler() {
        return nmsMobHandler;
    }

    public NMSHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    public String getNMSServerVersion() {
        return Bukkit.getServer()
                .getClass()
                .getPackage()
                .getName()
                .replace(".", ",")
                .split(",")[3];
    }

    public String getNMSPackageName() {
        return Bukkit.getServer()
                .getClass()
                .getPackage()
                .getName();
    }

    private void init() {
        nmsMobHandler = new NMSMobHandler(this);
    }
}

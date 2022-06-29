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
package me.lemonypancakes.bukkit.origins;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class OriginsBukkit {

    private static OriginsBukkitPlugin plugin;
    public static final String KEY = "origins-bukkit";

    private OriginsBukkit() {}

    public static OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(OriginsBukkitPlugin plugin) {
        if (OriginsBukkit.plugin == null) {
            OriginsBukkit.plugin = plugin;
        }
    }

    public static JavaPlugin getJavaPlugin() {
        return plugin.getJavaPlugin();
    }

    public static Registry getRegistry() {
        return plugin.getRegistry();
    }

    public static Loader getLoader() {
        return plugin.getLoader();
    }

    public static OriginPlayer getOriginPlayer(Player player) {
        return plugin.getOriginPlayer(player);
    }

    public static OriginPlayer getOriginPlayer(UUID uuid) {
        return plugin.getOriginPlayer(uuid);
    }

    public static OriginPlayer getOriginPlayer(String name) {
        return plugin.getOriginPlayer(name);
    }

    public static Storage getStorage() {
        return plugin.getStorage();
    }

    public static List<Plugin> getExpansions() {
        return plugin.getExpansions();
    }

    public static void disable() {
        plugin.disable();
    }
}

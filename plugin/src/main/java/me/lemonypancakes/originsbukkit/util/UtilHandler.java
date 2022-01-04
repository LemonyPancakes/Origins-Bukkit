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

public class UtilHandler {

    private final OriginsBukkit plugin;
    private GhostFactory ghostFactory;
    private GhostMaker ghostMaker;

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public GhostFactory getGhostFactory() {
        return ghostFactory;
    }

    public GhostMaker getGhostMaker() {
        return ghostMaker;
    }

    public UtilHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        ghostFactory = new GhostFactory(this);
        ghostMaker = new GhostMaker(this);
    }
}

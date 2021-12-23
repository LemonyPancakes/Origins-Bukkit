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

/**
 * The type Util handler.
 *
 * @author LemonyPancakes
 */
public class UtilHandler {

    private final OriginsBukkit plugin;
    private GhostFactory ghostFactory;
    private GhostMaker ghostMaker;

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public OriginsBukkit getPlugin() {
        return plugin;
    }

    /**
     * Gets ghost factory.
     *
     * @return the ghost factory
     */
    public GhostFactory getGhostFactory() {
        return ghostFactory;
    }

    /**
     * Gets ghost maker.
     *
     * @return the ghost maker
     */
    public GhostMaker getGhostMaker() {
        return ghostMaker;
    }

    /**
     * Instantiates a new Util handler.
     *
     * @param plugin the plugin
     */
    public UtilHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        ghostFactory = new GhostFactory(this);
        ghostMaker = new GhostMaker(this);
    }
}

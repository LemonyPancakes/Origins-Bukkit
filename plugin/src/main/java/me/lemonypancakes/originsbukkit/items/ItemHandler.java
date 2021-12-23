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
package me.lemonypancakes.originsbukkit.items;

import me.lemonypancakes.originsbukkit.OriginsBukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Item handler.
 *
 * @author LemonyPancakes
 */
public class ItemHandler {

    private final OriginsBukkit plugin;
    private final List<String> items = new ArrayList<>();
    private OrbOfOrigin orbOfOrigin;
    private ArachnidCobweb arachnidCobweb;

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public OriginsBukkit getPlugin() {
        return this.plugin;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<String> getItems() {
        return items;
    }

    /**
     * Gets orb of origin.
     *
     * @return the orb of origin
     */
    public OrbOfOrigin getOrbOfOrigin() {
        return this.orbOfOrigin;
    }

    /**
     * Gets arachnid cobweb.
     *
     * @return the arachnid cobweb
     */
    public ArachnidCobweb getArachnidCobweb() {
        return arachnidCobweb;
    }

    /**
     * Instantiates a new Item handler.
     *
     * @param plugin the plugin
     */
    public ItemHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        orbOfOrigin = new OrbOfOrigin(this);
        arachnidCobweb = new ArachnidCobweb(this);
    }
}

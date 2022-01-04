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

public class ItemHandler {

    private final OriginsBukkit plugin;
    private final List<String> items = new ArrayList<>();
    private OrbOfOrigin orbOfOrigin;
    private ArachnidCobweb arachnidCobweb;

    public OriginsBukkit getPlugin() {
        return this.plugin;
    }

    public List<String> getItems() {
        return items;
    }

    public OrbOfOrigin getOrbOfOrigin() {
        return this.orbOfOrigin;
    }

    public ArachnidCobweb getArachnidCobweb() {
        return arachnidCobweb;
    }

    public ItemHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        orbOfOrigin = new OrbOfOrigin(this);
        arachnidCobweb = new ArachnidCobweb(this);
    }
}

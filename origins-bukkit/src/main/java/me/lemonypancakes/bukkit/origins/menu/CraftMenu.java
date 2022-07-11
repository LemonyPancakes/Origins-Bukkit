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
package me.lemonypancakes.bukkit.origins.menu;

import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import java.util.Objects;

public class CraftMenu implements Menu {

    private OriginsBukkitPlugin plugin;
    protected Inventory inventory;

    public CraftMenu(OriginsBukkitPlugin plugin) {
        setPlugin(plugin);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {
        if (this.plugin == null) {
            this.plugin = plugin;
        }
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {}

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {}

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftMenu)) return false;
        CraftMenu craftMenu = (CraftMenu) itemStack;
        return Objects.equals(plugin, craftMenu.plugin) && Objects.equals(inventory, craftMenu.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin);
    }

    @Override
    public String toString() {
        return "CraftMenu{" +
                "plugin=" + plugin +
                ", inventory=" + inventory +
                '}';
    }
}

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
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CraftPaginatedMenu extends CraftMenu {

    protected final List<Inventory> pages = new LinkedList<>();
    protected final Set<Player> exemptions = new HashSet<>();

    public CraftPaginatedMenu(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player) {
        Inventory inventory = pages.get(0);

        if (inventory != null) {
            player.openInventory(inventory);
        }
    }

    public void addExemption(Player player) {
        exemptions.add(player);
        new BukkitRunnable() {

            @Override
            public void run() {
                exemptions.remove(player);
            }
        }.runTaskLater(getPlugin().getJavaPlugin(), 20L);
    }

    public void removeExemption(Player player) {
        exemptions.remove(player);
    }

    public boolean isExempted(Player player) {
        return exemptions.contains(player);
    }

    protected void setAsAPreviousPageButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            BukkitPersistentDataUtils.setPersistentData(itemMeta, "origins-bukkit:paginated_menu", PersistentDataType.STRING, "origins-bukkit:previous_page_button");
            itemStack.setItemMeta(itemMeta);
        }
    }

    protected boolean isAPreviousPageButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String string = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:paginated_menu", PersistentDataType.STRING);

            if (string != null) {
                return string.equals("origins-bukkit:previous_page_button");
            }
        }
        return false;
    }

    protected void setAsANextPageButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            BukkitPersistentDataUtils.setPersistentData(itemMeta, "origins-bukkit:paginated_menu", PersistentDataType.STRING, "origins-bukkit:next_page_button");
            itemStack.setItemMeta(itemMeta);
        }
    }

    protected boolean isANextPageButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String string = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:paginated_menu", PersistentDataType.STRING);

            if (string != null) {
                return string.equals("origins-bukkit:next_page_button");
            }
        }
        return false;
    }
}

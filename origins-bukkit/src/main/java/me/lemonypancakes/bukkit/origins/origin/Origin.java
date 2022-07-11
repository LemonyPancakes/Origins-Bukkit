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
package me.lemonypancakes.bukkit.origins.origin;

import me.lemonypancakes.bukkit.origins.Identifiable;
import me.lemonypancakes.bukkit.origins.JsonObjectHolder;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPluginHolder;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.util.Impact;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Origin extends OriginsBukkitPluginHolder, JsonObjectHolder, Identifiable {

    String getName();

    void setName(String name);

    String[] getDescription();

    void setDescription(String[] description);

    boolean isUnchoosable();

    void setUnchoosable(boolean unchoosable);

    int getOrder();

    void setOrder(int order);

    Impact getImpact();

    void setImpact(Impact impact);

    List<Power> getPowers();

    void addPower(Power power);

    void removePower(Power power);

    boolean hasPower(Power power);

    ItemStack getIcon();

    void setIcon(ItemStack icon);

    String[] getAuthors();

    void setAuthors(String[] authors);
}

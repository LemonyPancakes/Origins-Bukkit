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
package me.lemonypancakes.originsbukkit.api.util;

import me.lemonypancakes.originsbukkit.enums.Impact;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public interface OriginInterface extends Listener {

    String getOriginIdentifier();

    Impact getImpact();

    String getAuthor();

    Material getOriginIcon();

    boolean isOriginIconGlowing();

    String getOriginTitle();

    String[] getOriginDescription();
}

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

/**
 * The interface Origin interface.
 */
public interface OriginInterface extends Listener {

    /**
     * Gets origin identifier.
     *
     * @return the origin identifier
     */
    String getOriginIdentifier();

    /**
     * Gets impact.
     *
     * @return the impact
     */
    Impact getImpact();

    /**
     * Gets author.
     *
     * @return the author
     */
    String getAuthor();

    /**
     * Gets origin icon.
     *
     * @return the origin icon
     */
    Material getOriginIcon();

    /**
     * Is origin icon glowing boolean.
     *
     * @return the boolean
     */
    boolean isOriginIconGlowing();

    /**
     * Gets origin title.
     *
     * @return the origin title
     */
    String getOriginTitle();

    /**
     * Get origin description string [ ].
     *
     * @return the string [ ]
     */
    String[] getOriginDescription();
}

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
package me.lemonypancakes.originsbukkit.nms.mobs;

import me.lemonypancakes.originsbukkit.api.internal.ModifiedCreeper;
import me.lemonypancakes.originsbukkit.nms.NMSHandler;

/**
 * The type Nms mob handler.
 *
 * @author LemonyPancakes
 */
public class NMSMobHandler {

    private final NMSHandler nmsHandler;
    private ModifiedCreeper modifiedCreeper;

    /**
     * Gets nms handler.
     *
     * @return the nms handler
     */
    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }

    /**
     * Gets modified creeper.
     *
     * @return the modified creeper
     */
    public ModifiedCreeper getModifiedCreeper() {
        return modifiedCreeper;
    }

    /**
     * Instantiates a new Nms mob handler.
     *
     * @param nmsHandler the nms handler
     */
    public NMSMobHandler(NMSHandler nmsHandler) {
        this.nmsHandler = nmsHandler;
        init();
    }

    /**
     * Init.
     */
    private void init() {
        String packageName = getNMSHandler().getNMSPackageName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        try {
            final Class<?> clazz = Class.forName("me.lemonypancakes.originsbukkit.nms." + version + ".mobs.modifiedcreeper.EntityModifiedCreeperImpl");

            if (ModifiedCreeper.class.isAssignableFrom(clazz)) {
                this.modifiedCreeper = (ModifiedCreeper) clazz.getConstructor().newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

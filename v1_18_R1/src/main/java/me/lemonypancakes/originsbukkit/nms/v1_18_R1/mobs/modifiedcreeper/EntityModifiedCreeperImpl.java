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
package me.lemonypancakes.originsbukkit.nms.v1_18_R1.mobs.modifiedcreeper;

import me.lemonypancakes.originsbukkit.api.internal.ModifiedCreeper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Creeper;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftCreeper;
import org.bukkit.entity.Entity;

/**
 * The type Entity modified creeper.
 *
 * @author LemonyPancakes
 */
public class EntityModifiedCreeperImpl implements ModifiedCreeper {

    /**
     * Summon modified creeper.
     *
     * @param location the location
     */
    @Override
    public void summonModifiedCreeper(Location location) {
        if (location.getWorld() != null) {
            ServerLevel worldServer = ((CraftWorld) location.getWorld()).getHandle();
            me.lemonypancakes.originsbukkit.nms.v1_18_R1.mobs.modifiedcreeper.EntityModifiedCreeper customCreeper = new me.lemonypancakes.originsbukkit.nms.v1_18_R1.mobs.modifiedcreeper.EntityModifiedCreeper(location);
            customCreeper.setPos(location.getX(), location.getY(), location.getZ());
            worldServer.addFreshEntity(customCreeper);
        }
    }

    /**
     * Is modified creeper boolean.
     *
     * @param entity the entity
     *
     * @return the boolean
     */
    @Override
    public boolean isModifiedCreeper(Entity entity) {
        Creeper entityCreeper = ((CraftCreeper) entity).getHandle();

        return entityCreeper instanceof EntityModifiedCreeper;
    }
}

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
package me.lemonypancakes.originsbukkit.nms.v1_17_R1.mobs.modifiedcreeper;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * The type Entity modified creeper.
 *
 * @author LemonyPancakes
 */
public class EntityModifiedCreeper extends Creeper {

    /**
     * Instantiates a new Entity modified creeper.
     *
     * @param location the location
     */
    public EntityModifiedCreeper(Location location) {
        super(EntityType.CREEPER, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, net.minecraft.world.entity.player.Player.class, 16f,  1d, 1.2d, entityLiving -> isFeline((net.minecraft.world.entity.player.Player) entityLiving)));
    }

    /**
     * Is feline boolean.
     *
     * @param entityPlayer the entity player
     *
     * @return the boolean
     */
    private boolean isFeline(net.minecraft.world.entity.player.Player entityPlayer) {
        Player player = new CraftPlayer((CraftServer) Bukkit.getServer(), (ServerPlayer) entityPlayer);
        UUID playerUUID = player.getUniqueId();

        return Objects.equals(OriginsBukkit.getPlugin().getStorageHandler().getOriginsPlayerData().getPlayerOrigin(playerUUID), OriginsBukkit.getPlugin().getListenerHandler().getOriginListenerHandler().getFeline().getOriginIdentifier());
    }
}
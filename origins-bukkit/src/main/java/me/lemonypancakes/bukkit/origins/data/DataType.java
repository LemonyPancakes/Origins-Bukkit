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
package me.lemonypancakes.bukkit.origins.data;

import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.wrapper.BiEntity;
import me.lemonypancakes.bukkit.origins.wrapper.Biome;
import me.lemonypancakes.bukkit.origins.wrapper.Damage;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface DataType<T> {

    DataType<Entity> ENTITY = new CraftDataType<>(Entity.class);
    DataType<OriginPlayer> ORIGIN_PLAYER = new CraftDataType<>(OriginPlayer.class);
    DataType<BiEntity> BI_ENTITY = new CraftDataType<>(BiEntity.class);
    DataType<Block> BLOCK = new CraftDataType<>(Block.class);
    DataType<Biome> BIOME = new CraftDataType<>(Biome.class);
    DataType<Damage> DAMAGE = new CraftDataType<>(Damage.class);
    DataType<ItemStack> ITEM_STACK = new CraftDataType<>(ItemStack.class);

    Class<T> getType();
}

package me.lemonypancakes.originsbukkit;

import me.lemonypancakes.originsbukkit.data.CraftDataType;
import me.lemonypancakes.originsbukkit.wrapper.BiEntity;
import me.lemonypancakes.originsbukkit.wrapper.Biome;
import me.lemonypancakes.originsbukkit.wrapper.Damage;
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
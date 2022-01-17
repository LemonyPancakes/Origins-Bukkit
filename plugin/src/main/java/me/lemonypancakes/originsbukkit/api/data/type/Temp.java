package me.lemonypancakes.originsbukkit.api.data.type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Temp {

    Entity getEntity();

    void setEntity(Entity entity);

    Player getPlayer();

    void setPlayer(Player player);

    Block getBlock();

    void setBlock(Block block);

    ItemStack getItemStack();

    void setItemStack(ItemStack itemStack);

    Material getMaterial();

    void setMaterial(Material material);

    Location getLocation();

    void setLocation(Location location);

    World getWorld();

    void setWorld(World world);

    Object getObject();

    void setObject(Object object);
}

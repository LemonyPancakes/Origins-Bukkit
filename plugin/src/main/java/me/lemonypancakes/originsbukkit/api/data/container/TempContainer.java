package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@SuppressWarnings("unused")
public class TempContainer implements Temp {

    private Entity entity;
    private Player player;
    private Block block;
    private ItemStack itemStack;
    private Material material;
    private Location location;
    private World world;
    private Object object;

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempContainer)) return false;
        TempContainer that = (TempContainer) o;
        return Objects.equals(getEntity(), that.getEntity()) && Objects.equals(getPlayer(), that.getPlayer()) && Objects.equals(getBlock(), that.getBlock()) && Objects.equals(getItemStack(), that.getItemStack()) && getMaterial() == that.getMaterial() && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getWorld(), that.getWorld()) && Objects.equals(getObject(), that.getObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntity(), getPlayer(), getBlock(), getItemStack(), getMaterial(), getLocation(), getWorld(), getObject());
    }

    @Override
    public String toString() {
        return "TempContainer{" +
                "entity=" + entity +
                ", player=" + player +
                ", block=" + block +
                ", itemStack=" + itemStack +
                ", material=" + material +
                ", location=" + location +
                ", world=" + world +
                ", object=" + object +
                '}';
    }
}

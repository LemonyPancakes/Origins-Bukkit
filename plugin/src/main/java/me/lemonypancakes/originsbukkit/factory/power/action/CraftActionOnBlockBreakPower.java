package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class CraftActionOnBlockBreakPower extends CraftPower {

    public CraftActionOnBlockBreakPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftActionOnBlockBreakPower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            Block block = event.getBlock();
            Temp temp = new CraftTemp();

            temp.set(DataType.ENTITY, player);
            temp.set(DataType.BLOCK, block);
            testAndAccept(temp);
        }
    }
}

package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftInvisibilityPower extends CraftPower {

    public CraftInvisibilityPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    getMembers().forEach(player -> {
                        Temp temp = new CraftTemp();

                        temp.set(DataType.ENTITY, player);
                        player.setInvisible(testAndAccept(temp));
                    });
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftInvisibilityPower(plugin, identifier, jsonObject, false);
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setInvisible(false);
    }
}

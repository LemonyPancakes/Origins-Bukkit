package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftInvisibilityPower extends CraftPower {

    public CraftInvisibilityPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> player.setInvisible(getCondition().test(player)));
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
    }

    public CraftInvisibilityPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftInvisibilityPower(plugin, identifier, jsonObject);
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setInvisible(false);
    }
}

package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftSwimSpeedPower extends CraftPower {

    public CraftSwimSpeedPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, false, false, false)));
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1200L);
    }

    @Override
    protected void onMemberAdd(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, false, false, false));
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
    }
}

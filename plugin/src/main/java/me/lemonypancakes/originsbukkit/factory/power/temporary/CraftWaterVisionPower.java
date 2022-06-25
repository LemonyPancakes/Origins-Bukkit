package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftWaterVisionPower extends CraftPower {

    public CraftWaterVisionPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    Location eyeLocation = player.getEyeLocation();
                    Block eyeBlock = eyeLocation.getBlock();
                    Material eyeBlockMaterial = eyeBlock.getType();

                    if (eyeBlockMaterial == Material.WATER || eyeBlock.getBlockData() instanceof Waterlogged && ((Waterlogged) eyeBlock.getBlockData()).isWaterlogged()) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false));
                    } else {
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1L);
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}

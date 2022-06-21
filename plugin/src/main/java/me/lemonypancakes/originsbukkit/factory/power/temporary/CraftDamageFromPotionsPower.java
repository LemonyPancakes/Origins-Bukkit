package me.lemonypancakes.originsbukkit.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class CraftDamageFromPotionsPower extends CraftPower {

    public CraftDamageFromPotionsPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    public CraftDamageFromPotionsPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftDamageFromPotionsPower(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            ItemStack itemStack = event.getItem();
            Material material = itemStack.getType();

            if (material == Material.POTION) {
                player.damage(2);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPotionSplash(PotionSplashEvent event) {
        Collection<LivingEntity> livingEntities = event.getAffectedEntities();

        livingEntities.forEach(livingEntity -> {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;

                if (getMembers().contains(player)) {
                    player.damage(2);
                }
            }
        });
    }
}

package me.lemonypancakes.bukkit.origins.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
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

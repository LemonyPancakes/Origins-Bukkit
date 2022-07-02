/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.factory.power.temporary;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginItem;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftCooldownPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftMasterOfWebsPower extends CraftCooldownPower {

    public CraftMasterOfWebsPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity actorEntity = event.getDamager();

        if (actorEntity instanceof Player) {
            Player actorPlayer = (Player) event.getDamager();

            if (getMembers().contains(actorPlayer)) {
                if (canUse(actorPlayer)) {
                    Entity targetEntity = event.getEntity();
                    Location targetEntityLocation = targetEntity.getLocation();
                    Block targetEntityLocationBlock = targetEntityLocation.getBlock();

                    if (!targetEntityLocationBlock.getType().isSolid()) {
                        targetEntityLocationBlock.setType(Material.COBWEB);
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                targetEntityLocationBlock.setType(Material.AIR);
                            }
                        }.runTaskLater(getPlugin().getJavaPlugin(), getCooldown());
                        setCooldown(actorPlayer);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        HumanEntity humanEntity = event.getView().getPlayer();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;

            if (!getMembers().contains(player)) {
                Recipe recipe = event.getRecipe();
                OriginItem arachnidCobwebOriginItem = getPlugin().getRegistry().getOriginItem(new Identifier(Identifier.ORIGINS_BUKKIT, "arachnid_cobweb"));

                if (arachnidCobwebOriginItem != null) {
                    Recipe arachnidCobwebOriginItemRecipe = arachnidCobwebOriginItem.getRecipe();

                    if (arachnidCobwebOriginItemRecipe != null) {
                        if (recipe != null) {
                            if (recipe.getResult().equals(arachnidCobwebOriginItemRecipe.getResult())) {
                                event.getInventory().setResult(null);
                            }
                        }
                    }
                }
            }
        }
    }
}

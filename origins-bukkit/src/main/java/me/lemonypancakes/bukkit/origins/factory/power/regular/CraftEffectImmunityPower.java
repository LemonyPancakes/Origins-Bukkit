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
package me.lemonypancakes.bukkit.origins.factory.power.regular;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftEffectImmunityPower extends CraftPower {

    private PotionEffectType potionEffectType;
    private PotionEffectType[] potionEffectTypes;

    public CraftEffectImmunityPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("effect")) {
            this.potionEffectType = PotionEffectType.getByName(jsonObject.get("effect").getAsString());
        }
        if (jsonObject.has("effects")) {
            List<PotionEffectType> list = new ArrayList<>();
            String[] strings = new Gson().fromJson(jsonObject.get("effects"), String[].class);

            for (String string : strings) {
                PotionEffectType type = PotionEffectType.getByName(string);
                if (type != null) {
                    list.add(type);
                }
            }

            this.potionEffectTypes = list.toArray(new PotionEffectType[0]);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (hasMember(player)) {
                if (getCondition().test(player)) {
                    PotionEffect potionEffect = event.getNewEffect();

                    if (potionEffect != null) {
                        PotionEffectType type = event.getNewEffect().getType();

                        if (this.potionEffectType != null && this.potionEffectType.equals(type) || this.potionEffectTypes != null && Arrays.asList(this.potionEffectTypes).contains(type)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
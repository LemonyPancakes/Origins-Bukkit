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

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftActiveWithCooldownPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Key;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CraftLaunchPower extends CraftActiveWithCooldownPower {

    private double speed;
    private Sound sound;

    public CraftLaunchPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("speed")) {
            this.speed = jsonObject.get("speed").getAsDouble();
        }
        if (jsonObject.has("sound")) {
            this.sound = Sound.valueOf(jsonObject.get("sound").getAsString());
        }
    }

    @Override
    protected void onUse(Player player, Key key) {
        if (key == Key.PRIMARY) {
            player.setVelocity(new Vector(0, speed, 0));
            player.getWorld().playSound(player.getLocation(), sound, 1f, 0f);
        }
    }
}

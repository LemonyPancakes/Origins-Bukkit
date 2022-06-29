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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.AttributedAttributeModifier;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftAttributePower extends CraftPower {

    private AttributedAttributeModifier modifier;
    private AttributedAttributeModifier[] modifiers;
    private boolean updateHealth = true;

    public CraftAttributePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("modifier")) {
            this.modifier = new AttributedAttributeModifier(jsonObject.getAsJsonObject("modifier"));
        }
        if (jsonObject.has("modifiers")) {
            JsonObject[] jsonObjects = new Gson().fromJson(jsonObject.get("modifiers"), JsonObject[].class);
            List<AttributedAttributeModifier> list = new ArrayList<>();
            Arrays.stream(jsonObjects).forEach(jsonObject1 -> list.add(new AttributedAttributeModifier(jsonObject1)));
            this.modifiers = list.toArray(new AttributedAttributeModifier[0]);
        }
        if (jsonObject.has("update_health")) {
            this.updateHealth = jsonObject.get("update_health").getAsBoolean();
        }
    }

    @Override
    protected void onMemberAdd(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(modifier.getAttribute());

        if (attributeInstance != null) {
            attributeInstance.addModifier(modifier.getModifier());
        }
        if (modifiers != null && modifiers.length != 0) {
            Arrays.stream(modifiers).forEach(attributedAttributeModifier -> {
                AttributeInstance instance = player.getAttribute(attributedAttributeModifier.getAttribute());

                if (instance != null) {
                    instance.addModifier(attributedAttributeModifier.getModifier());
                }
            });
        }
        if (updateHealth) {
            AttributeInstance instance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

            if (instance != null) {
                player.setHealthScale(instance.getValue());
            }
        }
    }

    @Override
    protected void onMemberRemove(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(modifier.getAttribute());

        if (attributeInstance != null) {
            attributeInstance.removeModifier(modifier.getModifier());
        }
        if (modifiers != null && modifiers.length != 0) {
            Arrays.stream(modifiers).forEach(attributedAttributeModifier -> {
                AttributeInstance instance = player.getAttribute(attributedAttributeModifier.getAttribute());

                if (instance != null) {
                    instance.removeModifier(attributedAttributeModifier.getModifier());
                }
            });
        }
        if (updateHealth) {
            AttributeInstance instance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

            if (instance != null) {
                player.setHealthScale(instance.getValue());
            }
        }
    }
}

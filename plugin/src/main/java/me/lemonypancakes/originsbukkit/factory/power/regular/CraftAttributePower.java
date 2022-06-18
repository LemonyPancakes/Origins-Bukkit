package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.wrapper.AttributedAttributeModifier;
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

    public CraftAttributePower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftAttributePower(plugin, identifier, jsonObject);
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

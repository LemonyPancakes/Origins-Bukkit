package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
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

    public CraftEffectImmunityPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
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
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftEffectImmunityPower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (getMembers().contains(player)) {
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
package me.lemonypancakes.originsbukkit.factory.condition;

import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.util.BiEntity;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.util.RayTraceResult;

public class BiEntityConditions {

    public BiEntityConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "attack_target"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor instanceof Monster && target instanceof LivingEntity) {
                    Monster monster = (Monster) actor;
                    LivingEntity livingEntity = (LivingEntity) target;

                    return monster.getTarget() != null && monster.getTarget() == livingEntity;
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "attacker"), new CraftCondition(plugin, null, (jsonObject, temp) -> false)));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "can_see"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) actor;
                    RayTraceResult rayTraceResult = livingEntity.getWorld().rayTraceEntities(livingEntity.getEyeLocation(), livingEntity.getEyeLocation().getDirection(), Bukkit.getViewDistance());

                    if (rayTraceResult != null) {
                        Bukkit.broadcastMessage("" + rayTraceResult.getHitEntity());
                        return rayTraceResult.getHitEntity() != null && rayTraceResult.getHitEntity() == target;
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "distance"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "owner"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {
                Entity actor = biEntity.getActor();
                Entity target = biEntity.getTarget();

                if (actor != null && target != null) {
                    if (actor instanceof AnimalTamer) {
                        AnimalTamer animalTamer = (AnimalTamer) actor;

                        if (target instanceof Tameable) {
                            Tameable tameable = (Tameable) target;

                            return tameable.getOwner() == animalTamer;
                        }
                    }
                }
            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "relative_rotation"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "riding_recursive"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "riding_root"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {

            }
            return false;
        })));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "riding"), new CraftCondition(plugin, null, (jsonObject, temp) -> {
            BiEntity biEntity = temp.get(DataType.BI_ENTITY);

            if (biEntity != null) {

            }
            return false;
        })));
    }
}

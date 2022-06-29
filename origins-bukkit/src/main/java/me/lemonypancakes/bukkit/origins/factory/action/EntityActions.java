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
package me.lemonypancakes.bukkit.origins.factory.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Action;
import me.lemonypancakes.bukkit.origins.DataType;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftAction;
import me.lemonypancakes.bukkit.origins.factory.action.meta.*;
import me.lemonypancakes.bukkit.origins.util.EquipmentSlot;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.wrapper.ItemStackWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class EntityActions {

    public EntityActions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAndAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftChanceAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftChoiceAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftDelayAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftIfElseAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftIfElseListAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftNothingAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_velocity"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                Vector vector = entity.getVelocity();
                float x = 0;
                float y = 0;
                float z = 0;
                boolean setVelocity = false;

                if (jsonObject.has("x")) {
                    x = jsonObject.get("x").getAsFloat();
                }
                if (jsonObject.has("y")) {
                    y = jsonObject.get("y").getAsFloat();
                }
                if (jsonObject.has("z")) {
                    z = jsonObject.get("z").getAsFloat();
                }
                if (jsonObject.has("set_velocity")) {
                    setVelocity = jsonObject.get("set_velocity").getAsBoolean();
                }
                if (setVelocity) {
                    entity.setVelocity(new Vector(x, y, z));
                } else {
                    entity.setVelocity(new Vector(vector.getX() + x, vector.getY() + y, vector.getZ() + z));
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_xp"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    int points = 0;
                    int levels = 0;

                    if (jsonObject.has("points")) {
                        points = jsonObject.get("points").getAsInt();
                    }
                    if (jsonObject.has("levels")) {
                        levels = jsonObject.get("levels").getAsInt();
                    }
                    player.setTotalExperience(player.getTotalExperience() + points);
                    player.setLevel(player.getLevel() + levels);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "apply_effect"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    if (jsonObject.has("effect")) {
                        JsonObject effectJsonObject = jsonObject.getAsJsonObject("effect");
                        PotionEffectType potionEffectType = null;
                        int duration = 100;
                        int amplifier = 0;
                        boolean isAmbient = false;
                        boolean showParticles = true;
                        boolean showIcon = true;

                        if (effectJsonObject.has("effect")) {
                            potionEffectType = PotionEffectType.getByName(effectJsonObject.get("effect").getAsString());
                        }
                        if (effectJsonObject.has("duration")) {
                            duration = effectJsonObject.get("duration").getAsInt();
                        }
                        if (effectJsonObject.has("amplifier")) {
                            amplifier = effectJsonObject.get("amplifier").getAsInt();
                        }
                        if (effectJsonObject.has("is_ambient")) {
                            isAmbient = effectJsonObject.get("is_ambient").getAsBoolean();
                        }
                        if (effectJsonObject.has("show_particles")) {
                            showParticles = effectJsonObject.get("show_particles").getAsBoolean();
                        }
                        if (effectJsonObject.has("show_icon")) {
                            showIcon = effectJsonObject.get("show_icon").getAsBoolean();
                        }
                        if (potionEffectType != null) {
                            livingEntity.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier, isAmbient, showParticles, showIcon));
                        }
                    }
                    if (jsonObject.has("effects")) {
                        JsonObject[] jsonObjects = new Gson().fromJson(jsonObject.get("effects"), JsonObject[].class);

                        Arrays.stream(jsonObjects).forEach(effectJsonObject -> {
                            PotionEffectType potionEffectType = null;
                            int duration = 100;
                            int amplifier = 0;
                            boolean isAmbient = false;
                            boolean showParticles = true;
                            boolean showIcon = true;

                            if (effectJsonObject.has("effect")) {
                                potionEffectType = PotionEffectType.getByName(effectJsonObject.get("effect").getAsString());
                            }
                            if (effectJsonObject.has("duration")) {
                                duration = effectJsonObject.get("duration").getAsInt();
                            }
                            if (effectJsonObject.has("amplifier")) {
                                amplifier = effectJsonObject.get("amplifier").getAsInt();
                            }
                            if (effectJsonObject.has("is_ambient")) {
                                isAmbient = effectJsonObject.get("is_ambient").getAsBoolean();
                            }
                            if (effectJsonObject.has("show_particles")) {
                                showParticles = effectJsonObject.get("show_particles").getAsBoolean();
                            }
                            if (effectJsonObject.has("show_icon")) {
                                showIcon = effectJsonObject.get("show_icon").getAsBoolean();
                            }
                            if (potionEffectType != null) {
                                livingEntity.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier, isAmbient, showParticles, showIcon));
                            }
                        });
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "area_of_effect"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block_action_at"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "clear_effect"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PotionEffectType potionEffectType = null;

                    if (jsonObject.has("effect")) {
                        potionEffectType = PotionEffectType.getByName(jsonObject.get("effect").getAsString());
                    }
                    if (potionEffectType != null) {
                        livingEntity.removePotionEffect(potionEffectType);
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "crafting_table"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    humanEntity.openWorkbench(null, true);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "dismount"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                entity.leaveVehicle();
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "ender_chest"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    humanEntity.openInventory(humanEntity.getEnderChest());
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "equipped_item_action"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new EquippedItemAction(p, j, d, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "execute_command"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                String command = null;

                if (jsonObject.has("command")) {
                    command = jsonObject.get("command").getAsString();
                }
                if (command != null) {
                    Bukkit.dispatchCommand(entity, command);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "explode"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {

            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "extinguish"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                entity.setFireTicks(0);
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "give"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    ItemStack stack = null;

                    if (jsonObject.has("stack")) {
                        stack = new ItemStackWrapper(jsonObject.getAsJsonObject("stack")).getItemStack();
                    }
                    if (stack != null) {
                        player.getInventory().addItem(stack);
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "play_sound"), DataType.ENTITY, (p) -> (j) -> (d) -> () -> new CraftAction<>(p, j, d, (jsonObject, entity) -> {
            if (entity != null) {
                Sound sound = null;
                float volume = 1;
                float pitch = 1;

                if (jsonObject.has("sound")) {
                    sound = Sound.valueOf(jsonObject.get("sound").getAsString());
                }
                if (sound != null) {
                    entity.getWorld().playSound(entity.getLocation(), sound, volume, pitch);
                }
            }
        })));
    }

    public static class EquippedItemAction extends CraftAction<Entity> {

        private Action<ItemStack> action;

        public EquippedItemAction(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<Entity> dataType, BiConsumer<JsonObject, Entity> biConsumer) {
            super(plugin, jsonObject, dataType, biConsumer);
            if (jsonObject != null) {
                this.action = plugin.getLoader().loadAction(DataType.ITEM_STACK, jsonObject);
                setBiConsumer((jsonObject1, entity) -> {
                    if (entity != null) {
                        if (entity instanceof HumanEntity) {
                            HumanEntity humanEntity = (HumanEntity) entity;
                            ItemStack itemStack = EquipmentSlot.parseEquipmentSlot(jsonObject1).getItemStack(humanEntity.getInventory());

                            if (itemStack != null) {
                                action.accept(itemStack);
                            }
                        }
                    }
                });
            }
        }
    }
}

package me.lemonypancakes.originsbukkit.factory.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.CraftAction;
import me.lemonypancakes.originsbukkit.factory.action.meta.*;
import me.lemonypancakes.originsbukkit.util.EquipmentSlot;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ENTITY, new CraftAndAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.ENTITY, new CraftChanceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.ENTITY, new CraftChoiceAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.ENTITY, new CraftDelayAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.ENTITY, new CraftIfElseAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.ENTITY, new CraftIfElseListAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.ENTITY, new CraftNothingAction<>(plugin, null, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_velocity"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_xp"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "apply_effect"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "area_of_effect"), DataType.ENTITY, new CraftAction<>(plugin, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block_action_at"), DataType.ENTITY, new CraftAction<>(plugin, null, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "clear_effect"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "crafting_table"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    humanEntity.openWorkbench(null, true);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "dismount"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {
                entity.leaveVehicle();
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "ender_chest"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    humanEntity.openInventory(humanEntity.getEnderChest());
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "equipped_item_action"), DataType.ENTITY, new EquippedItemAction(plugin, null, DataType.ENTITY, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "execute_command"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "explode"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {

            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "extinguish"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {
                entity.setFireTicks(0);
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "b"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {

            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "a"), DataType.ENTITY, new CraftAction<>(plugin, null, (jsonObject, entity) -> {
            if (entity != null) {

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
                            ItemStack itemStack = EquipmentSlot.parseEquipmentSlot(jsonObject).getItemStack(humanEntity.getInventory());

                            if (itemStack != null) {
                                action.accept(itemStack);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public Action<Entity> newInstance(OriginsBukkitPlugin plugin, JsonObject jsonObject, DataType<Entity> dataType) {
            return new EquippedItemAction(plugin, jsonObject, dataType, getBiConsumer());
        }
    }
}

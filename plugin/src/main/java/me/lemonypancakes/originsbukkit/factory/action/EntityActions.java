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
import me.lemonypancakes.originsbukkit.wrapper.ItemStackWrapper;
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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "and"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAndAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "chance"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftChanceAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "choice"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftChoiceAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "delay"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftDelayAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftIfElseAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "if_else_list"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftIfElseListAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "nothing"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftNothingAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_velocity"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                Vector vector = entity.getVelocity();
                float x = 0;
                float y = 0;
                float z = 0;
                boolean setVelocity = false;

                if (jsonObject1.has("x")) {
                    x = jsonObject1.get("x").getAsFloat();
                }
                if (jsonObject1.has("y")) {
                    y = jsonObject1.get("y").getAsFloat();
                }
                if (jsonObject1.has("z")) {
                    z = jsonObject1.get("z").getAsFloat();
                }
                if (jsonObject1.has("set_velocity")) {
                    setVelocity = jsonObject1.get("set_velocity").getAsBoolean();
                }
                if (setVelocity) {
                    entity.setVelocity(new Vector(x, y, z));
                } else {
                    entity.setVelocity(new Vector(vector.getX() + x, vector.getY() + y, vector.getZ() + z));
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "add_xp"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    int points = 0;
                    int levels = 0;

                    if (jsonObject1.has("points")) {
                        points = jsonObject1.get("points").getAsInt();
                    }
                    if (jsonObject1.has("levels")) {
                        levels = jsonObject1.get("levels").getAsInt();
                    }
                    player.setTotalExperience(player.getTotalExperience() + points);
                    player.setLevel(player.getLevel() + levels);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "apply_effect"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    if (jsonObject1.has("effect")) {
                        JsonObject effectJsonObject = jsonObject1.getAsJsonObject("effect");
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
                    if (jsonObject1.has("effects")) {
                        JsonObject[] jsonObjects = new Gson().fromJson(jsonObject1.get("effects"), JsonObject[].class);

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
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "area_of_effect"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "block_action_at"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "clear_effect"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PotionEffectType potionEffectType = null;

                    if (jsonObject1.has("effect")) {
                        potionEffectType = PotionEffectType.getByName(jsonObject1.get("effect").getAsString());
                    }
                    if (potionEffectType != null) {
                        livingEntity.removePotionEffect(potionEffectType);
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "crafting_table"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    humanEntity.openWorkbench(null, true);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "dismount"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                entity.leaveVehicle();
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "ender_chest"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof HumanEntity) {
                    HumanEntity humanEntity = (HumanEntity) entity;

                    humanEntity.openInventory(humanEntity.getEnderChest());
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "equipped_item_action"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new EquippedItemAction(plugin1, jsonObject, dataType, null)));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "execute_command"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                String command = null;

                if (jsonObject1.has("command")) {
                    command = jsonObject1.get("command").getAsString();
                }
                if (command != null) {
                    Bukkit.dispatchCommand(entity, command);
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "explode"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {

            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "extinguish"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                entity.setFireTicks(0);
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "give"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    ItemStack stack = null;

                    if (jsonObject1.has("stack")) {
                        stack = new ItemStackWrapper(jsonObject1.getAsJsonObject("stack")).getItemStack();
                    }
                    if (stack != null) {
                        player.getInventory().addItem(stack);
                    }
                }
            }
        })));
        plugin.getRegistry().register(new Action.Factory<>(new Identifier(Identifier.ORIGINS_BUKKIT, "play_sound"), DataType.ENTITY, (plugin1, jsonObject) -> (dataType) -> () -> new CraftAction<>(plugin1, jsonObject, dataType, (jsonObject1, entity) -> {
            if (entity != null) {
                Sound sound = null;
                float volume = 1;
                float pitch = 1;

                if (jsonObject1.has("sound")) {
                    sound = Sound.valueOf(jsonObject1.get("sound").getAsString());
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
                            ItemStack itemStack = EquipmentSlot.parseEquipmentSlot(jsonObject).getItemStack(humanEntity.getInventory());

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

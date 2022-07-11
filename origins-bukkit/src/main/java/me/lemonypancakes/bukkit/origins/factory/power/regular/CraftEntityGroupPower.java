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

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PlayerUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("unused")
public class CraftEntityGroupPower extends CraftPower {

    public CraftEntityGroupPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("group")) {
            String entityGroup = jsonObject.get("group").getAsString();

            if (entityGroup != null) {
                switch (entityGroup.toUpperCase()) {
                    case "UNDEAD":
                        new Undead();
                        break;
                    case "AQUATIC":
                        new Aquatic();
                        break;
                    case "ARTHROPOD":
                        new Arthropod();
                        break;
                    case "ILLAGER":
                        new Illager();
                        break;
                }
            }
        }
    }

    private class Undead implements Listener {

        public Undead() {
            Bukkit.getPluginManager().registerEvents(this, getPlugin().getJavaPlugin());
            new BukkitRunnable() {

                @Override
                public void run() {
                    getMembers().forEach(player -> {
                        World world = player.getWorld();
                        Location location = player.getLocation();
                        ItemStack itemStack = player.getInventory().getHelmet();

                        if (world.getTime() > 0 && world.getTime() < 13000) {
                            if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                if (itemStack == null) {
                                    player.setFireTicks(200);
                                } else {
                                    ItemMeta itemMeta = itemStack.getItemMeta();

                                    if (itemMeta != null) {
                                        if (itemMeta instanceof Damageable) {
                                            Damageable damageable = (Damageable) itemMeta;

                                            if (damageable.getDamage() < itemStack.getType().getMaxDurability()) {
                                                damageable.setDamage(damageable.getDamage() + 1);
                                                itemStack.setItemMeta(itemMeta);
                                            } else {
                                                itemStack.setAmount(0);
                                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 20L);
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    Entity damager = event.getDamager();

                    if (damager instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) damager;
                        EntityEquipment entityEquipment = livingEntity.getEquipment();

                        if (entityEquipment != null) {
                            ItemStack itemStack = entityEquipment.getItemInMainHand();
                            ItemMeta itemMeta = itemStack.getItemMeta();

                            if (itemMeta != null && itemMeta.hasEnchant(Enchantment.DAMAGE_UNDEAD)) {
                                int enchantLevel = itemMeta.getEnchantLevel(Enchantment.DAMAGE_UNDEAD);
                                double baseDamage = event.getDamage();

                                event.setDamage(baseDamage + (2.5 * enchantLevel));
                            }
                        }
                    }
                }
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamage(EntityDamageEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    EntityDamageEvent.DamageCause magic = EntityDamageEvent.DamageCause.MAGIC;
                    EntityDamageEvent.DamageCause poison = EntityDamageEvent.DamageCause.POISON;

                    if (event.getCause() == magic) {
                        event.setCancelled(true);
                        PlayerUtils.heal(player, event.getDamage());
                    } else if (event.getCause() == poison) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityRegainHealth(EntityRegainHealthEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    EntityRegainHealthEvent.RegainReason magic = EntityRegainHealthEvent.RegainReason.MAGIC;
                    EntityRegainHealthEvent.RegainReason magicRegen = EntityRegainHealthEvent.RegainReason.MAGIC_REGEN;

                    if (event.getRegainReason() == magic) {
                        event.setCancelled(true);
                        player.damage(event.getAmount() + ((event.getAmount() / 4) * 2));
                    } else if (event.getRegainReason() == magicRegen) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityPotionEffect(EntityPotionEffectEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    PotionEffect potionEffect = event.getNewEffect();

                    if (potionEffect != null) {
                        PotionEffectType regeneration = PotionEffectType.REGENERATION;
                        PotionEffectType poison = PotionEffectType.POISON;

                        if (potionEffect.getType().equals(regeneration) || potionEffect.getType().equals(poison)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    private class Aquatic implements Listener {

        public Aquatic() {
            Bukkit.getPluginManager().registerEvents(this, getPlugin().getJavaPlugin());
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    Entity damager = event.getDamager();

                    if (damager instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) damager;
                        EntityEquipment entityEquipment = livingEntity.getEquipment();

                        if (entityEquipment != null) {
                            ItemStack itemStack = entityEquipment.getItemInMainHand();
                            ItemMeta itemMeta = itemStack.getItemMeta();

                            if (itemStack.getType() == Material.TRIDENT) {
                                if (itemMeta != null && itemMeta.hasEnchant(Enchantment.IMPALING)) {
                                    int enchantLevel = itemMeta.getEnchantLevel(Enchantment.IMPALING);
                                    double baseDamage = event.getDamage();

                                    event.setDamage(baseDamage + (2.5 * enchantLevel));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private class Arthropod implements Listener {

        public Arthropod() {
            Bukkit.getPluginManager().registerEvents(this, getPlugin().getJavaPlugin());
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    Entity damager = event.getDamager();

                    if (damager instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) damager;
                        EntityEquipment entityEquipment = livingEntity.getEquipment();

                        if (entityEquipment != null) {
                            ItemStack itemStack = entityEquipment.getItemInMainHand();
                            ItemMeta itemMeta = itemStack.getItemMeta();

                            if (itemMeta != null && itemMeta.hasEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                                int enchantLevel = itemMeta.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS);
                                double baseDamage = event.getDamage();

                                player.addPotionEffect(PotionEffectType.SLOW.createEffect(enchantLevel * 20, 4));
                                event.setDamage(baseDamage + (2.5 * enchantLevel));
                            }
                        }
                    }
                }
            }
        }
    }

    private class Illager implements Listener {

        public Illager() {
            Bukkit.getPluginManager().registerEvents(this, getPlugin().getJavaPlugin());
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (hasMember(player)) {
                    Entity damager = event.getDamager();

                    if (damager.getType() == EntityType.EVOKER_FANGS) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Ravager || entity instanceof Pillager || entity instanceof Illusioner || entity instanceof Evoker || entity instanceof Vindicator) {
                LivingEntity target = event.getTarget();

                if (target instanceof Player) {
                    Player player = (Player) target;

                    if (hasMember(player)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
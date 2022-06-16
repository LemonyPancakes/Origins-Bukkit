package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.PlayerUtils;
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
        if (jsonObject.has("entity_group")) {
            String entityGroup = jsonObject.get("entity_group").getAsString();

            if (entityGroup != null) {
                switch (entityGroup.toUpperCase()) {
                    case "UNDEAD":
                        new Undead();
                        break;
                    case "AQUATIC":
                        new Aquatic();
                        break;
                    case "ARTHROPODS":
                        new Arthropods();
                        break;
                    case "ILLAGERS":
                        new Illagers();
                        break;
                }
            }
        }
    }

    public CraftEntityGroupPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftEntityGroupPower(plugin, identifier, jsonObject);
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

                if (getMembers().contains(player)) {
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

                if (getMembers().contains(player)) {
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

                if (getMembers().contains(player)) {
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

                if (getMembers().contains(player)) {
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

                if (getMembers().contains(player)) {
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

    private class Arthropods implements Listener {

        public Arthropods() {
            Bukkit.getPluginManager().registerEvents(this, getPlugin().getJavaPlugin());
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getMembers().contains(player)) {
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

    private class Illagers implements Listener {

        public Illagers() {
            Bukkit.getPluginManager().registerEvents(this, getPlugin().getJavaPlugin());
        }

        @EventHandler(priority = EventPriority.LOW)
        private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getMembers().contains(player)) {
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

                    if (getMembers().contains(player)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
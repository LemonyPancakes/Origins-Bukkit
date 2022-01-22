package me.lemonypancakes.originsbukkit.factory.listener.special;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Action;
import me.lemonypancakes.originsbukkit.api.data.type.Condition;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.util.PlayerUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
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
public class EntityGroup extends ListenerPowerContainer {

    private String entityGroup;

    public EntityGroup(Identifier identifier,
                       JsonObject jsonObject,
                       Action<?>[] actions,
                       Condition<?> condition,
                       boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                JsonObject fields = jsonObject.getAsJsonObject("fields");

                if (fields != null) {
                    if (fields.has("entity_group")) {
                        String entityGroup = fields.get("entity_group").getAsString();

                        if (entityGroup != null) {
                            switch (entityGroup.toUpperCase()) {
                                case "UNDEAD":
                                    this.entityGroup = "UNDEAD";
                                    new Undead();
                                    break;
                                case "AQUATIC":
                                    this.entityGroup = "AQUATIC";
                                    new Aquatic();
                                    break;
                                case "ARTHROPODS":
                                    this.entityGroup = "ARTHROPODS";
                                    new Arthropods();
                                    break;
                                case "ILLAGERS":
                                    this.entityGroup = "ILLAGERS";
                                    new Illagers();
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    public EntityGroup(Identifier identifier,
                       JsonObject jsonObject,
                       Action<?>[] actions,
                       boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public EntityGroup(Identifier identifier,
                       JsonObject jsonObject,
                       boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public EntityGroup(Identifier identifier,
                       boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public EntityGroup(boolean isFactory) {
        this(null, isFactory);
    }

    public EntityGroup() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new EntityGroup(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new EntityGroup(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new EntityGroup(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new EntityGroup(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new EntityGroup();
    }

    @Override
    protected <T> void onInvoke(T t) {}

    @Override
    protected void onUnlisten(Player player) {
        if (entityGroup != null) {
            if (entityGroup.equalsIgnoreCase("AQUATIC")) {
                player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
            }
        }
    }

    public static Power getFactory() {
        return new EntityGroup(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/special/entity_group"
                ), true
        );
    }

    private class Undead implements Listener {

        public Undead() {
            Bukkit.getPluginManager().registerEvents(this, OriginsBukkit.getPlugin());
            init();
        }

        private void init() {
            new BukkitRunnable() {

                @Override
                public void run() {
                    getPlayersToListen().forEach(player -> {
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
            }.runTaskTimerAsynchronously(OriginsBukkit.getPlugin(), 0L, 20L);
        }

        @EventHandler
        private void onEntityAttackSelf(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
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

        @EventHandler
        private void onDamage(EntityDamageEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
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

        @EventHandler
        private void onHeal(EntityRegainHealthEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
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

        @EventHandler
        private void onPotionEffect(EntityPotionEffectEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
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
            Bukkit.getPluginManager().registerEvents(this, OriginsBukkit.getPlugin());
        }

        @EventHandler
        private void onEntityAttackSelf(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
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
            Bukkit.getPluginManager().registerEvents(this, OriginsBukkit.getPlugin());
        }

        @EventHandler
        private void onEntityAttackSelf(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
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
            Bukkit.getPluginManager().registerEvents(this, OriginsBukkit.getPlugin());
        }

        @EventHandler
        private void onDamage(EntityDamageByEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (getPlayersToListen().contains(player)) {
                    Entity damager = event.getDamager();

                    if (damager.getType() == EntityType.EVOKER_FANGS) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        @EventHandler
        private void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
            Entity entity = event.getEntity();

            if (entity instanceof Ravager
                    || entity instanceof Pillager
                    || entity instanceof Illusioner
                    || entity instanceof Evoker
                    || entity instanceof Vindicator) {
                LivingEntity target = event.getTarget();

                if (target instanceof Player) {
                    Player player = (Player) target;

                    if (getPlayersToListen().contains(player)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

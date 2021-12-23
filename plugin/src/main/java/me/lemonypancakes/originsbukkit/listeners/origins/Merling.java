/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
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
package me.lemonypancakes.originsbukkit.listeners.origins;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginChangeEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.api.wrappers.PlayerAirBubbles;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import me.lemonypancakes.originsbukkit.storage.wrappers.MerlingTimerSessionDataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * The type Merling.
 *
 * @author LemonyPancakes
 */
public class Merling extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final Map<UUID, Integer> merlingAirBreathing = new HashMap<>();
    private final Map<UUID, Integer> merlingAirTicks = new HashMap<>();
    private final Map<UUID, PlayerAirBubbles> merlingAirBubbles = new HashMap<>();
    private final List<UUID> merlingWaterBreathing = new ArrayList<>();
    private final List<UUID> merlingAirDamage = new ArrayList<>();

    /**
     * Gets origin listener handler.
     *
     * @return the origin listener handler
     */
    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    /**
     * Gets merling air breathing.
     *
     * @return the merling air breathing
     */
    public Map<UUID, Integer> getMerlingAirBreathing() {
        return merlingAirBreathing;
    }

    /**
     * Gets merling air ticks.
     *
     * @return the merling air ticks
     */
    public Map<UUID, Integer> getMerlingAirTicks() {
        return merlingAirTicks;
    }

    /**
     * Gets merling air bubbles.
     *
     * @return the merling air bubbles
     */
    public Map<UUID, PlayerAirBubbles> getMerlingAirBubbles() {
        return merlingAirBubbles;
    }

    /**
     * Gets merling water breathing.
     *
     * @return the merling water breathing
     */
    public List<UUID> getMerlingWaterBreathing() {
        return merlingWaterBreathing;
    }

    /**
     * Gets merling air damage.
     *
     * @return the merling air damage
     */
    public List<UUID> getMerlingAirDamage() {
        return merlingAirDamage;
    }

    /**
     * Instantiates a new Merling.
     *
     * @param originListenerHandler the origin listener handler
     */
    public Merling(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_MERLING_MAX_HEALTH.toDouble(),
                Config.ORIGINS_MERLING_WALK_SPEED.toFloat(),
                Config.ORIGINS_MERLING_FLY_SPEED.toFloat());
        this.originListenerHandler = originListenerHandler;
        init();
    }

    /**
     * Gets origin identifier.
     *
     * @return the origin identifier
     */
    @Override
    public String getOriginIdentifier() {
        return "Merling";
    }

    /**
     * Gets impact.
     *
     * @return the impact
     */
    @Override
    public Impact getImpact() {
        return Impact.HIGH;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    @Override
    public String getAuthor() {
        return "LemonyPancakes";
    }

    /**
     * Gets origin icon.
     *
     * @return the origin icon
     */
    @Override
    public Material getOriginIcon() {
        return Material.COD;
    }

    /**
     * Is origin icon glowing boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isOriginIconGlowing() {
        return false;
    }

    /**
     * Gets origin title.
     *
     * @return the origin title
     */
    @Override
    public String getOriginTitle() {
        return Lang.MERLING_TITLE.toString();
    }

    /**
     * Get origin description string [ ].
     *
     * @return the string [ ]
     */
    @Override
    public String[] getOriginDescription() {
        return Lang.MERLING_DESCRIPTION.toStringList();
    }

    /**
     * Init.
     */
    private void init() {
        getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin()
                .getServer()
                .getPluginManager()
                .registerEvents(this, getOriginListenerHandler()
                        .getListenerHandler()
                        .getPlugin());
        registerOrigin(this);
        registerAirBreathingListener();
        registerWaterBreathingListener();
        registerMerlingAirDamageListener();
        registerMerlingBlockDiggingPacketListener();
        registerMerlingMovePacketListener();
    }

    /**
     * Merling join.
     *
     * @param event the event
     */
    @EventHandler
    private void merlingJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        UUID playerUUID = player.getUniqueId();
        String origin = event.getOrigin();
        double maxTime = Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toDouble();

        if (Objects.equals(origin, Origins.MERLING.toString())) {
            if (originPlayer.findMerlingTimerSessionData() != null) {
                getMerlingAirBreathing().put(
                        playerUUID,
                        originPlayer.getMerlingTimerSessionDataTimeLeft());
                getMerlingAirTicks().put(
                        playerUUID,
                        switchAirTicks(
                                calculatePercentage(
                                        getMerlingAirBreathing()
                                                .get(playerUUID), maxTime)));
            } else {
                getMerlingAirTicks().put(playerUUID, -27);
                getMerlingWaterBreathing().add(playerUUID);
            }
            PlayerAirBubbles playerAirBubbles = new PlayerAirBubbles(player);
            getMerlingAirBubbles().put(playerUUID, playerAirBubbles);
        }
    }

    /**
     * On origin change.
     *
     * @param event the event
     */
    @EventHandler
    private void onOriginChange(AsyncPlayerOriginChangeEvent event) {
        Player player = event.getPlayer();
        String oldOrigin = event.getOldOrigin();

        if (Objects.equals(oldOrigin, Origins.MERLING.toString())) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            }.runTask(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin());
        }
    }

    /**
     * Merling underwater breathing.
     *
     * @param event the event
     */
    @EventHandler
    private void merlingUnderwaterBreathing(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (entity instanceof Player) {
            Player player = ((Player) entity).getPlayer();

            if (player != null) {
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();

                if (Objects.equals(playerOrigin, Origins.MERLING.toString())) {
                    if (damageCause == EntityDamageEvent.DamageCause.DROWNING) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Calculate percentage double.
     *
     * @param obtained the obtained
     * @param total    the total
     *
     * @return the double
     */
    private double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

    /**
     * Switch air ticks int.
     *
     * @param percentage the percentage
     *
     * @return the int
     */
    private int switchAirTicks(double percentage) {
        if (percentage <= 100 && percentage > 90) {
            return 273;
        } else if (percentage <= 90 && percentage > 80) {
            return 243;
        } else if (percentage <= 80 && percentage > 70) {
            return 213;
        } else if (percentage <= 70 && percentage > 60) {
            return 183;
        } else if (percentage <= 60 && percentage > 50) {
            return 153;
        } else if (percentage <= 50 && percentage > 40) {
            return 123;
        } else if (percentage <= 40 && percentage > 30) {
            return 93;
        } else if (percentage <= 30 && percentage > 20) {
            return 63;
        } else if (percentage <= 20 && percentage > 10) {
            return 33;
        } else if (percentage <= 10 && percentage > 0) {
            return 3;
        } else if (percentage == 0) {
            return -27;
        }
        return -27;
    }

    /**
     * Register air breathing listener.
     */
    private void registerAirBreathingListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!getMerlingAirBreathing().isEmpty()) {
                    for (Map.Entry<UUID, Integer> entry : getMerlingAirBreathing().entrySet()) {
                        UUID key = entry.getKey();
                        int value = entry.getValue();
                        Player player = Bukkit.getPlayer(key);

                        if (player != null) {
                            OriginPlayer originPlayer = new OriginPlayer(player);
                            String playerOrigin = originPlayer.getOrigin();
                            Location location = player.getLocation();
                            Block block = location.getBlock();
                            Material material = block.getType();
                            double maxTime = Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toDouble();

                            if (Objects.equals(playerOrigin, Origins.MERLING.toString())) {
                                if (player.isOnline()) {
                                    getMerlingAirTicks().put(key, switchAirTicks(calculatePercentage(value, maxTime)));

                                    if (originPlayer.findMerlingTimerSessionData() == null) {
                                        originPlayer.createMerlingTimerSessionData(value);
                                    } else {
                                        originPlayer.updateMerlingTimerSessionData(
                                                new MerlingTimerSessionDataWrapper(key, value));
                                    }
                                    if (value <= 0) {
                                        if (!player.getWorld().hasStorm()) {
                                            if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                                value += 2;
                                                getMerlingAirBreathing().put(key, value);
                                            } else {
                                                getMerlingAirDamage().add(key);
                                                getMerlingAirBreathing().remove(key);
                                            }
                                        } else {
                                            if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                                getMerlingAirBreathing().put(key, value + 2);
                                            } else {
                                                if (!(location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY())) {
                                                    getMerlingAirDamage().add(key);
                                                    getMerlingAirBreathing().remove(key);
                                                } else {
                                                    value += 2;
                                                    getMerlingAirBreathing().put(key, value);
                                                }
                                            }
                                        }
                                    } else {
                                        if (!player.getWorld().hasStorm()) {
                                            if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                                if (value < Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toInt()) {
                                                    value += 2;
                                                    getMerlingAirBreathing().put(key, value);
                                                } else {
                                                    if (originPlayer.findMerlingTimerSessionData() != null) {
                                                        originPlayer.deleteMerlingTimerSessionData();
                                                    }
                                                    getMerlingWaterBreathing().add(key);
                                                    getMerlingAirBreathing().remove(key);
                                                    getMerlingAirTicks().put(key, -27);
                                                }
                                            }
                                        } else {
                                            if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                                if (value < Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toInt()) {
                                                    value += 2;
                                                    getMerlingAirBreathing().put(key, value);
                                                } else {
                                                    if (originPlayer.findMerlingTimerSessionData() != null) {
                                                        originPlayer.deleteMerlingTimerSessionData();
                                                    }
                                                    getMerlingWaterBreathing().add(key);
                                                    getMerlingAirBreathing().remove(key);
                                                    getMerlingAirTicks().put(key, -27);
                                                }
                                            } else {
                                                if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                                    if (value < Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toInt()) {
                                                        value += 2;
                                                        getMerlingAirBreathing().put(key, value);
                                                    } else {
                                                        if (originPlayer.findMerlingTimerSessionData() != null) {
                                                            originPlayer.deleteMerlingTimerSessionData();
                                                        }
                                                        getMerlingWaterBreathing().add(key);
                                                        getMerlingAirBreathing().remove(key);
                                                        getMerlingAirTicks().put(key, -27);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (value > 0 && getMerlingAirBreathing().containsKey(key)) {
                                        value--;
                                        getMerlingAirBreathing().put(key, value);
                                    }
                                } else {
                                    getMerlingAirBreathing().remove(key);
                                    getMerlingAirBubbles().get(key).cancel();
                                    getMerlingAirBubbles().remove(key);
                                }
                            } else {
                                if (originPlayer.findMerlingTimerSessionData() != null) {
                                    originPlayer.deleteMerlingTimerSessionData();
                                }
                                getMerlingAirBreathing().remove(key);
                                getMerlingAirBubbles().get(key).cancel();
                                getMerlingAirBubbles().remove(key);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 20L);
    }

    /**
     * Register water breathing listener.
     */
    private void registerWaterBreathingListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!getMerlingWaterBreathing().isEmpty()) {
                    for (int i = 0; i < getMerlingWaterBreathing().size(); i++) {
                        Player player = Bukkit.getPlayer(getMerlingWaterBreathing().get(i));

                        if (player != null) {
                            UUID playerUUID = player.getUniqueId();
                            OriginPlayer originPlayer = new OriginPlayer(player);
                            String playerOrigin = originPlayer.getOrigin();
                            Location location = player.getLocation();
                            Block block = location.getBlock();
                            Material material = block.getType();
                            int timeLeft = originPlayer.getMerlingTimerSessionDataTimeLeft();

                            if (Objects.equals(playerOrigin, Origins.MERLING.toString())) {
                                if (player.isOnline()) {
                                    if (!player.getWorld().hasStorm()) {
                                        if (!(player.isInWater() || material == Material.WATER_CAULDRON)) {
                                            if (originPlayer.findMerlingTimerSessionData() != null) {
                                                if (timeLeft != 0) {
                                                    getMerlingAirBreathing().put(playerUUID, timeLeft);
                                                    getMerlingWaterBreathing().remove(playerUUID);
                                                } else {
                                                    getMerlingAirDamage().add(playerUUID);
                                                }
                                            } else {
                                                getMerlingAirBreathing().put(playerUUID, Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toInt());
                                            }
                                            getMerlingWaterBreathing().remove(playerUUID);
                                        }
                                    } else {
                                        if (!(player.isInWater() || material == Material.WATER_CAULDRON)) {
                                            if (!(location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY())) {
                                                if (originPlayer.findMerlingTimerSessionData() != null) {
                                                    if (timeLeft != 0) {
                                                        getMerlingAirBreathing().put(playerUUID, timeLeft);
                                                    } else {
                                                        getMerlingAirDamage().add(playerUUID);
                                                    }
                                                } else {
                                                    getMerlingAirBreathing().put(playerUUID, Config.ORIGINS_MERLING_AIR_BREATHING_MAX_TIME.toInt());
                                                }
                                                getMerlingWaterBreathing().remove(playerUUID);
                                            } else {
                                                if (originPlayer.findMerlingTimerSessionData() != null) {
                                                    getMerlingAirBreathing().put(playerUUID, timeLeft);
                                                    getMerlingWaterBreathing().remove(playerUUID);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    getMerlingWaterBreathing().remove(playerUUID);
                                    getMerlingAirBubbles().get(playerUUID).cancel();
                                    getMerlingAirBubbles().remove(playerUUID);
                                }
                            } else {
                                getMerlingWaterBreathing().remove(playerUUID);
                                getMerlingAirBubbles().get(playerUUID).cancel();
                                getMerlingAirBubbles().remove(playerUUID);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 5L);
    }

    /**
     * Register merling air damage listener.
     */
    private void registerMerlingAirDamageListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!getMerlingAirDamage().isEmpty()) {
                    for (int i = 0; i < getMerlingAirDamage().size(); i++) {
                        Player player = Bukkit.getPlayer(getMerlingAirDamage().get(i));

                        if (player != null) {
                            UUID playerUUID = player.getUniqueId();
                            OriginPlayer originPlayer = new OriginPlayer(player);
                            String playerOrigin = originPlayer.getOrigin();
                            Location location = player.getLocation();
                            Block block = location.getBlock();
                            Material material = block.getType();
                            int timeLeft = originPlayer.getMerlingTimerSessionDataTimeLeft();

                            if (Objects.equals(playerOrigin, Origins.MERLING.toString())) {
                                if (player.isOnline()) {
                                    if (!player.getWorld().hasStorm()) {
                                        if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                            if (originPlayer.findMerlingTimerSessionData() != null) {
                                                getMerlingAirBreathing().put(playerUUID, timeLeft);
                                            }
                                            getMerlingAirDamage().remove(playerUUID);
                                        } else {
                                            damageMerling(player, Config.ORIGINS_MERLING_AIR_BREATHING_DAMAGE_AMOUNT.toDouble());
                                        }
                                    } else {
                                        if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                            if (originPlayer.findMerlingTimerSessionData() != null) {
                                                getMerlingAirBreathing().put(playerUUID, timeLeft);
                                            }
                                            getMerlingAirDamage().remove(playerUUID);
                                        } else {
                                            if (!(location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY())) {
                                                damageMerling(player, Config.ORIGINS_MERLING_AIR_BREATHING_DAMAGE_AMOUNT.toDouble());
                                            } else {
                                                if (originPlayer.findMerlingTimerSessionData() != null) {
                                                    getMerlingAirBreathing().put(playerUUID, timeLeft);
                                                }
                                                getMerlingAirDamage().remove(playerUUID);
                                            }
                                        }
                                    }
                                } else {
                                    getMerlingAirDamage().remove(playerUUID);
                                    getMerlingAirBubbles().get(playerUUID).cancel();
                                    getMerlingAirBubbles().remove(playerUUID);
                                }
                            } else {
                                getMerlingAirDamage().remove(playerUUID);
                                getMerlingAirBubbles().get(playerUUID).cancel();
                                getMerlingAirBubbles().remove(playerUUID);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), Config.ORIGINS_MERLING_AIR_BREATHING_DAMAGE_DELAY.toLong(), Config.ORIGINS_MERLING_AIR_BREATHING_DAMAGE_PERIOD_DELAY.toLong());
    }

    /**
     * Damage merling.
     *
     * @param player the player
     * @param amount the amount
     */
    private void damageMerling(Player player, double amount) {

        new BukkitRunnable() {

            @Override
            public void run() {
                player.damage(amount);
            }
        }.runTask(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin());
    }

    /**
     * On merling impaling damage.
     *
     * @param event the event
     */
    @EventHandler
    private void onMerlingImpalingDamage(EntityDamageByEntityEvent event) {
        Entity target = event.getEntity();
        Entity damager = event.getDamager();
        double baseDamage = event.getDamage();

        if (target instanceof Player && damager instanceof LivingEntity) {
            Player targetPlayer = (Player) target;
            LivingEntity livingDamager = (LivingEntity) damager;
            OriginPlayer originPlayer = new OriginPlayer(targetPlayer);
            String targetPlayerOrigin = originPlayer.getOrigin();
            EntityEquipment entityEquipment = livingDamager.getEquipment();

            if (entityEquipment != null) {
                ItemStack itemStack = livingDamager.getEquipment().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (Objects.equals(targetPlayerOrigin, Origins.MERLING.toString())) {
                    if (itemMeta != null && itemMeta.hasEnchant(Enchantment.IMPALING)) {
                        int enchantLevel = itemMeta.getEnchantLevel(Enchantment.IMPALING);

                        event.setDamage(baseDamage + (2.5 * enchantLevel));
                    }
                }
            }
        }
    }

    /**
     * Register merling block digging packet listener.
     */
    private void registerMerlingBlockDiggingPacketListener() {
        getOriginListenerHandler().getListenerHandler().getPlugin().getProtocolManager().addPacketListener(
                new PacketAdapter(getOriginListenerHandler().getListenerHandler().getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
                Player player = event.getPlayer();
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                Location playerLocation = player.getLocation();

                if (Objects.equals(playerOrigin, Origins.MERLING.toString())) {
                    if (playerLocation.getBlock().isLiquid() && playerLocation.add(0, 1, 0).getBlock().isLiquid()) {
                        if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 20, false, false));
                                }
                            }.runTask(getPlugin());
                        } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                }
                            }.runTask(getPlugin());
                        } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                }
                            }.runTask(getPlugin());
                        }
                    }
                }
            }
        });
    }

    /**
     * Register merling move packet listener.
     */
    private void registerMerlingMovePacketListener() {
        getOriginListenerHandler().getListenerHandler().getPlugin().getProtocolManager().addPacketListener(
                new PacketAdapter(getOriginListenerHandler().getListenerHandler().getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();

                if (Objects.equals(playerOrigin, Origins.MERLING.toString())) {
                    if (player.isInWater()) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                player.addPotionEffect(
                                        new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0 , false, false));
                            }
                        }.runTask(getPlugin());
                    } else {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            }
                        }.runTask(getPlugin());
                    }
                }
            }
        });
    }
}

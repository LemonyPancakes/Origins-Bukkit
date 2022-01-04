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

import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginAbilityUseEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import me.lemonypancakes.originsbukkit.storage.wrappers.ElytrianClaustrophobiaTimerDataWrapper;
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Elytrian extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final Map<UUID, Long> COOLDOWN = new HashMap<>();
    private final int COOLDOWNTIME = Config.ORIGINS_ELYTRIAN_ABILITY_COOLDOWN.toInt();
    private final Map<UUID, Integer> claustrophobia = new HashMap<>();
    private final Map<UUID, Integer> claustrophibiaEffects = new HashMap<>();
    private static ItemStack elytra;

    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    public Map<UUID, Long> getCOOLDOWN() {
        return COOLDOWN;
    }

    public int getCOOLDOWNTIME() {
        return COOLDOWNTIME;
    }

    public Map<UUID, Integer> getClaustrophobia() {
        return claustrophobia;
    }

    public Map<UUID, Integer> getClaustrophibiaEffects() {
        return claustrophibiaEffects;
    }

    public static ItemStack getElytra() {
        return elytra;
    }

    public static void setElytra(ItemStack elytra) {
        Elytrian.elytra = elytra;
    }

    public Elytrian(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_ELYTRIAN_MAX_HEALTH.toDouble(),
                Config.ORIGINS_ELYTRIAN_WALK_SPEED.toFloat(),
                Config.ORIGINS_ELYTRIAN_FLY_SPEED.toFloat());
        this.originListenerHandler = originListenerHandler;
        init();
    }

    @Override
    public String getOriginIdentifier() {
        return "Elytrian";
    }

    @Override
    public Impact getImpact() {
        return Impact.LOW;
    }

    @Override
    public String getAuthor() {
        return "LemonyPancakes";
    }

    @Override
    public Material getOriginIcon() {
        return Material.ELYTRA;
    }

    @Override
    public boolean isOriginIconGlowing() {
        return false;
    }

    @Override
    public String getOriginTitle() {
        return Lang.ELYTRIAN_TITLE.toString();
    }

    @Override
    public String[] getOriginDescription() {
        return Lang.ELYTRIAN_DESCRIPTION.toStringList();
    }

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
        registerClaustrophobiaListener();
        createElytra();
    }

    @EventHandler
    private void elytrianJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        UUID playerUUID = player.getUniqueId();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.ELYTRIAN.toString())) {
            elytrianElytra(player);

            if (originPlayer.findElytrianClaustrophobiaTimerData() != null) {
                getClaustrophobia().put(playerUUID, originPlayer
                                .findElytrianClaustrophobiaTimerData()
                                .getTimerTimeLeft());
                getClaustrophibiaEffects().put(playerUUID, originPlayer
                        .findElytrianClaustrophobiaTimerData()
                        .getClaustrophobiaTimeLeft());
            } else {
                getClaustrophobia().put(playerUUID, 6);
                getClaustrophibiaEffects().put(playerUUID, 0);
            }
        } else {
            ItemStack prevChestplate = player.getInventory().getChestplate();

            if (prevChestplate != null && prevChestplate.equals(getElytra())) {
                player.getInventory().setChestplate(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    private void elytrianAbilityUse(AsyncPlayerOriginAbilityUseEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.ELYTRIAN.toString())) {
            elytrianLaunchIntoAir(player);
        }
    }

    private void createElytra() {
        ItemStack itemStack = new ItemStack(Material.ELYTRA, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(Message.format("&dElytra"));
            itemMeta.setUnbreakable(true);
            itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemStack.setItemMeta(itemMeta);
        }

        setElytra(itemStack);
    }

    private void elytrianElytra(Player player) {
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Location location = player.getLocation();
        World world = player.getWorld();
        PlayerInventory playerInventory = player.getInventory();
        ItemStack prevChestplate = playerInventory.getChestplate();

        if (Objects.equals(playerOrigin, Origins.ELYTRIAN.toString())) {
            if (prevChestplate != null && !prevChestplate.equals(getElytra())) {
                if (playerInventory.firstEmpty() == -1) {
                    world.dropItem(location, prevChestplate);
                } else {
                    playerInventory.addItem(prevChestplate);
                }
            }
            playerInventory.setChestplate(getElytra());
        }
    }

    @EventHandler
    private void elytrianArmorEquip(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        Player player = (Player) humanEntity;
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        int clickedSlot = event.getRawSlot();
        ItemStack cursorItemStack = event.getCursor();
        ItemStack clickedItemStack = event.getCurrentItem();

        if (Objects.equals(playerOrigin, Origins.ELYTRIAN.toString())) {
            if (clickedSlot == 6) {
                if (clickedItemStack != null && clickedItemStack.isSimilar(getElytra())) {
                    if (cursorItemStack != null && cursorItemStack.getItemMeta() != null) {
                        Message.sendPlayerMessage(player, "&dpeek-a-boo!");
                    }
                }
            }
        }
    }

    private void elytrianLaunchIntoAir(Player player) {
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (getCOOLDOWN().containsKey(playerUUID)) {
            long secondsLeft = ((getCOOLDOWN().get(playerUUID) / 1000) + getCOOLDOWNTIME() - (System.currentTimeMillis() / 1000));

            if (secondsLeft > 0) {
                Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_COOLDOWN
                        .toString()
                        .replace("%seconds_left%", String.valueOf(secondsLeft)));
            } else {
                player.setVelocity(new Vector(0, Config.ORIGINS_ELYTRIAN_ABILITY_Y_VELOCITY.toDouble(), 0));
                getCOOLDOWN().put(playerUUID, System.currentTimeMillis());
                Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                        .toString()
                        .replace("%player_current_origin%", playerOrigin));
            }
        } else {
            player.setVelocity(new Vector(0, Config.ORIGINS_ELYTRIAN_ABILITY_Y_VELOCITY.toDouble(), 0));
            getCOOLDOWN().put(playerUUID, System.currentTimeMillis());
            Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                    .toString()
                    .replace("%player_current_origin%", playerOrigin));
        }
    }

    @EventHandler
    private void elytrianAerialCombatant(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        double baseDamage = event.getDamage();

        if (damager instanceof Player) {
            Player player = (Player) damager;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();
            double additionalDamage = baseDamage * 0.5;

            if (Objects.equals(playerOrigin, Origins.ELYTRIAN.toString())) {
                if (player.isGliding()) {
                    event.setDamage(baseDamage + additionalDamage);
                }
            }
        }
    }

    @EventHandler
    private void elytrianBrittleBones(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();

            if (Objects.equals(playerOrigin, Origins.ELYTRIAN.toString())) {
                EntityDamageEvent.DamageCause damageCause = event.getCause();

                if (damageCause == EntityDamageEvent.DamageCause.FALL || damageCause == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                    double baseDamage = event.getDamage();
                    double halfBaseDamage = baseDamage * 0.5;

                    event.setDamage(baseDamage + halfBaseDamage);
                }
            }
        }
    }

    private void registerClaustrophobiaListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                getClaustrophobia().forEach((key, value) -> {
                    Player player = Bukkit.getPlayer(key);

                    if (player != null) {
                        UUID playerUUID = player.getUniqueId();
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        Location location = player.getLocation();
                        Location locationA = new Location(
                                location.getWorld(),
                                location.getX(),
                                location.getY() + 1,
                                location.getZ());
                        Location locationB = new Location(
                                location.getWorld(),
                                location.getX(),
                                location.getY() + 2,
                                location.getZ());
                        Location locationC = new Location(
                                location.getWorld(),
                                location.getX(),
                                location.getY() + 3,
                                location.getZ());
                        int maxDuration = Config.ORIGINS_ELYTRIAN_CLAUSTROPHOBIA_MAX_DURATION.toInt();

                        if (Objects.equals(playerOrigin, Origins.ELYTRIAN.toString())) {
                            if (player.isOnline()) {
                                if (
                                        location
                                                .getBlock()
                                                .getType()
                                                .isSolid() ||
                                                locationA
                                                        .getBlock()
                                                        .getType()
                                                        .isSolid() ||
                                                locationB
                                                        .getBlock()
                                                        .getType()
                                                        .isSolid() ||
                                                locationC
                                                        .getBlock()
                                                        .getType()
                                                        .isSolid()) {
                                    if (value == 0) {
                                        getClaustrophibiaEffects().putIfAbsent(key, 0);

                                        if (getClaustrophibiaEffects().get(key) < maxDuration) {
                                            getClaustrophibiaEffects().put(key, getClaustrophibiaEffects().get(key) + 20);
                                            syncAddPotionEffect(player, PotionEffectType.WEAKNESS, getClaustrophibiaEffects().get(key));
                                            syncAddPotionEffect(player, PotionEffectType.SLOW, getClaustrophibiaEffects().get(key));
                                        } else {
                                            syncAddPotionEffect(player, PotionEffectType.WEAKNESS, maxDuration);
                                            syncAddPotionEffect(player, PotionEffectType.SLOW, maxDuration);
                                        }
                                    } else {
                                        getClaustrophobia().put(key, value - 1);

                                        if (getClaustrophibiaEffects().get(key) != null && getClaustrophibiaEffects().get(key) != 0) {
                                            getClaustrophibiaEffects().put(key, getClaustrophibiaEffects().get(key) - 20);
                                        }
                                    }
                                    if (originPlayer.findElytrianClaustrophobiaTimerData() == null) {
                                        originPlayer.createElytrianClaustrophobiaTimerData(value, getClaustrophibiaEffects().get(key));
                                    } else {
                                        originPlayer.updateElytrianClaustrophobiaTimerData(new ElytrianClaustrophobiaTimerDataWrapper(
                                                playerUUID,
                                                value,
                                                getClaustrophibiaEffects().get(key)));
                                    }
                                } else {
                                    if (getClaustrophibiaEffects().get(key) != null && getClaustrophibiaEffects().get(key) != 0) {
                                        getClaustrophibiaEffects().put(key, getClaustrophibiaEffects().get(key) - 20);
                                        if (originPlayer.findElytrianClaustrophobiaTimerData() == null) {
                                            originPlayer.createElytrianClaustrophobiaTimerData(value, getClaustrophibiaEffects().get(key));
                                        } else {
                                            originPlayer.updateElytrianClaustrophobiaTimerData(new ElytrianClaustrophobiaTimerDataWrapper(
                                                    playerUUID,
                                                    value,
                                                    getClaustrophibiaEffects().get(key)));
                                        }
                                    }
                                    if (value != 6) {
                                        getClaustrophobia().put(key, value + 1);
                                        if (originPlayer.findElytrianClaustrophobiaTimerData() == null) {
                                            originPlayer.createElytrianClaustrophobiaTimerData(value, getClaustrophibiaEffects().get(key));
                                        } else {
                                            originPlayer.updateElytrianClaustrophobiaTimerData(new ElytrianClaustrophobiaTimerDataWrapper(
                                                    playerUUID,
                                                    value,
                                                    getClaustrophibiaEffects().get(key)));
                                        }
                                    }
                                }
                            } else {
                                getClaustrophobia().remove(key);
                                getClaustrophibiaEffects().remove(key);
                            }
                        } else {
                            if (originPlayer.findElytrianClaustrophobiaTimerData() != null) {
                                originPlayer.deleteElytrianClaustrophobiaTimerData();
                            }
                        }
                    }
                });
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 20L);
    }

    private void syncAddPotionEffect(Player player, PotionEffectType potionEffectType, int time) {

        new BukkitRunnable() {

            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(potionEffectType, time, 0));
            }
        }.runTask(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin());
    }
}
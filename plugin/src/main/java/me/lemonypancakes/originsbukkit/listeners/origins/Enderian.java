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
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Enderian extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final List<Player> enderianPlayersInWater = new ArrayList<>();
    private final List<Player> enderianPlayersInAir = new ArrayList<>();
    private final List<Integer> enderPearls = new ArrayList<>();
    private final Map<UUID, Long> COOLDOWN = new HashMap<>();
    private final int COOLDOWNTIME = Config.ORIGINS_ENDERIAN_ABILITY_COOLDOWN.toInt();

    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    public Enderian(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_ENDERIAN_MAX_HEALTH.toDouble(),
                Config.ORIGINS_ENDERIAN_WALK_SPEED.toFloat(),
                Config.ORIGINS_ENDERIAN_FLY_SPEED.toFloat());
        this.originListenerHandler = originListenerHandler;
        init();
    }

    @Override
    public String getOriginIdentifier() {
        return "Enderian";
    }

    @Override
    public Impact getImpact() {
        return Impact.MEDIUM;
    }

    @Override
    public String getAuthor() {
        return "LemonyPancakes";
    }

    @Override
    public Material getOriginIcon() {
        return Material.ENDER_PEARL;
    }

    @Override
    public boolean isOriginIconGlowing() {
        return false;
    }

    @Override
    public String getOriginTitle() {
        return Lang.ENDERIAN_TITLE.toString();
    }

    @Override
    public String[] getOriginDescription() {
        return Lang.ENDERIAN_DESCRIPTION.toStringList();
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
        registerEnderianWaterDamageListener();
        registerEnderianAirEnterListener();
    }

    @EventHandler
    private void enderianJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.ENDERIAN.toString())) {
            enderianPlayersInWater.add(player);
            enderianEnderParticles(player);
        }
    }

    @EventHandler
    private void enderianAbilityUse(AsyncPlayerOriginAbilityUseEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.ENDERIAN.toString())) {
            enderianEnderPearlThrow(player);
        }
    }

    private void registerEnderianWaterDamageListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!enderianPlayersInWater.isEmpty()) {
                    for (int i = 0; i < enderianPlayersInWater.size(); i++) {
                        Player player = enderianPlayersInWater.get(i);
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        Location location = player.getLocation();
                        Block block = location.getBlock();
                        Material material = block.getType();

                        if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
                            if (player.isOnline()) {
                                if (player.getWorld().hasStorm()) {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        damageEnderian(player, Config.ORIGINS_ENDERIAN_WATER_DAMAGE_AMOUNT.toDouble());
                                    } else if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                        damageEnderian(player, Config.ORIGINS_ENDERIAN_WATER_DAMAGE_AMOUNT.toDouble());
                                    } else {
                                        enderianPlayersInWater.remove(player);
                                        enderianPlayersInAir.add(player);
                                    }
                                } else {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        damageEnderian(player, Config.ORIGINS_ENDERIAN_WATER_DAMAGE_AMOUNT.toDouble());
                                    } else {
                                        enderianPlayersInWater.remove(player);
                                        enderianPlayersInAir.add(player);
                                    }
                                }
                            } else {
                                enderianPlayersInWater.remove(player);
                            }
                        } else {
                            enderianPlayersInWater.remove(player);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), Config.ORIGINS_ENDERIAN_WATER_DAMAGE_DELAY.toLong(), Config.ORIGINS_ENDERIAN_WATER_DAMAGE_PERIOD_DELAY.toLong());
    }

    private void damageEnderian(Player player, double amount) {

        new BukkitRunnable() {

            @Override
            public void run() {
                player.damage(amount);
            }
        }.runTask(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin());
    }

    private void registerEnderianAirEnterListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!enderianPlayersInAir.isEmpty()) {
                    for (int i = 0; i < enderianPlayersInAir.size(); i++) {
                        Player player = enderianPlayersInAir.get(i);
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        Location location = player.getLocation();
                        Block block = location.getBlock();
                        Material material = block.getType();

                        if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
                            if (player.isOnline()) {
                                if (player.getWorld().hasStorm()) {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        enderianPlayersInAir.remove(player);
                                        enderianPlayersInWater.add(player);
                                    } else if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                        enderianPlayersInAir.remove(player);
                                        enderianPlayersInWater.add(player);
                                    }
                                } else {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        enderianPlayersInAir.remove(player);
                                        enderianPlayersInWater.add(player);
                                    }
                                }
                            } else {
                                enderianPlayersInAir.remove(player);
                            }
                        } else {
                            enderianPlayersInAir.remove(player);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 5L);
    }

    private void enderianEnderParticles(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                World world = player.getWorld();
                Location location = player.getLocation();

                if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
                    if (player.isOnline()) {
                        world.spawnParticle(Particle.PORTAL, location.add(0, 1, 0), 10);
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 20L);
    }

    private void enderianEnderPearlThrow(Player player) {
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (COOLDOWN.containsKey(playerUUID)) {
            long secondsLeft = ((COOLDOWN.get(playerUUID) / 1000) + COOLDOWNTIME - (System.currentTimeMillis() / 1000));

            if (secondsLeft > 0) {
                Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_COOLDOWN
                        .toString()
                        .replace("%seconds_left%", String.valueOf(secondsLeft)));
            } else {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
                        enderPearls.add(enderPearl.getEntityId());
                    }
                }.runTask(getOriginListenerHandler()
                        .getListenerHandler()
                        .getPlugin());
                COOLDOWN.put(playerUUID, System.currentTimeMillis());
                Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                        .toString()
                        .replace("%player_current_origin%", playerOrigin));
            }
        } else {
            new BukkitRunnable() {

                @Override
                public void run() {
                    EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
                    enderPearls.add(enderPearl.getEntityId());
                }
            }.runTask(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin());
            COOLDOWN.put(playerUUID, System.currentTimeMillis());
            Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                    .toString()
                    .replace("%player_current_origin%", playerOrigin));
        }
    }

    @EventHandler
    private void enderianEnderPearlDamageImmunity(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
            PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();

            if (teleportCause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                if (!event.isCancelled()) {
                    Location getTo = event.getTo();

                    event.setCancelled(true);

                    if (getTo != null) {
                        player.teleport(getTo);
                    }
                }
            }
        }
    }

    @EventHandler
    private void enderianPumpkinPieEatingDisability(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Material material = event.getItem().getType();

        if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
            if (material == Material.PUMPKIN_PIE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void enderianPotionDrinkingDamage(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        ItemStack itemStack = event.getItem();
        Material material = itemStack.getType();

        if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
            if (material == Material.POTION) {
                player.damage(2);
            }
        }
    }

    @EventHandler
    private void enderianSplashPotionDamage(PotionSplashEvent event) {
        Collection<LivingEntity> livingEntities = event.getAffectedEntities();

        for (LivingEntity livingEntity : livingEntities) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();

                if (Objects.equals(playerOrigin, Origins.ENDERIAN.toString())) {
                    player.damage(2);
                }
            }
        }
    }
}

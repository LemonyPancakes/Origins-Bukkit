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

import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Blazeborn extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final List<Player> blazebornPlayersInWater = new ArrayList<>();
    private final List<Player> blazebornPlayersInAir = new ArrayList<>();

    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    public Blazeborn(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_BLAZEBORN_MAX_HEALTH.toDouble(),
                Config.ORIGINS_BLAZEBORN_WALK_SPEED.toFloat(),
                Config.ORIGINS_BLAZEBORN_FLY_SPEED.toFloat());
        this.originListenerHandler = originListenerHandler;
        init();
    }

    @Override
    public String getOriginIdentifier() {
        return "Blazeborn";
    }

    @Override
    public Impact getImpact() {
        return Impact.HIGH;
    }

    @Override
    public String getAuthor() {
        return "LemonyPancakes";
    }

    @Override
    public Material getOriginIcon() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public boolean isOriginIconGlowing() {
        return false;
    }

    @Override
    public String getOriginTitle() {
        return Lang.BLAZEBORN_TITLE.toString();
    }

    @Override
    public String[] getOriginDescription() {
        return Lang.BLAZEBORN_DESCRIPTION.toStringList();
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
        registerBlazebornWaterDamageListener();
        registerBlazebornAirEnterListener();
    }

    @EventHandler
    private void blazebornJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();
        OriginPlayer originPlayer = new OriginPlayer(player);

        if (Objects.equals(origin, Origins.BLAZEBORN.toString())) {
            blazebornPlayersInWater.add(player);
            blazebornFlameParticles(player);

            if (originPlayer.findBlazebornNetherSpawnData() == null) {
                originPlayer.createBlazebornNetherSpawnData(true);
                blazebornNetherSpawn(player);
            }
        }
    }

    private void registerBlazebornWaterDamageListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!blazebornPlayersInWater.isEmpty()) {
                    for (int i = 0; i < blazebornPlayersInWater.size(); i++) {
                        Player player = blazebornPlayersInWater.get(i);
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        Location location = player.getLocation();
                        Block block = location.getBlock();
                        Material material = block.getType();

                        if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
                            if (player.isOnline()) {
                                if (player.getWorld().hasStorm()) {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        damageBlazeborn(player, Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_AMOUNT.toDouble());
                                    } else if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                        damageBlazeborn(player, Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_AMOUNT.toDouble());
                                    } else {
                                        blazebornPlayersInWater.remove(player);
                                        blazebornPlayersInAir.add(player);
                                    }
                                } else {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        damageBlazeborn(player, Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_AMOUNT.toDouble());
                                    } else {
                                        blazebornPlayersInWater.remove(player);
                                        blazebornPlayersInAir.add(player);
                                    }
                                }
                            } else {
                                blazebornPlayersInWater.remove(player);
                            }
                        } else {
                            blazebornPlayersInWater.remove(player);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_DELAY.toLong(), Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_PERIOD_DELAY.toLong());
    }

    private void damageBlazeborn(Player player, double amount) {

        new BukkitRunnable() {

            @Override
            public void run() {
                player.damage(amount);
            }
        }.runTask(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin());
    }

    private void registerBlazebornAirEnterListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!blazebornPlayersInAir.isEmpty()) {
                    for (int i = 0; i < blazebornPlayersInAir.size(); i++) {
                        Player player = blazebornPlayersInAir.get(i);
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        Location location = player.getLocation();
                        Block block = location.getBlock();
                        Material material = block.getType();

                        if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
                            if (player.isOnline()) {
                                if (player.getWorld().hasStorm()) {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        blazebornPlayersInAir.remove(player);
                                        blazebornPlayersInWater.add(player);
                                    } else if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                        blazebornPlayersInAir.remove(player);
                                        blazebornPlayersInWater.add(player);
                                    }
                                } else {
                                    if (player.isInWater() || material == Material.WATER_CAULDRON) {
                                        blazebornPlayersInAir.remove(player);
                                        blazebornPlayersInWater.add(player);
                                    }
                                }
                            } else {
                                blazebornPlayersInAir.remove(player);
                            }
                        } else {
                            blazebornPlayersInAir.remove(player);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 5L);
    }

    private void blazebornFlameParticles(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                World world = player.getWorld();
                Location location = player.getLocation();

                if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
                    if (player.isOnline()) {
                        world.spawnParticle(Particle.SMALL_FLAME, location.add(0, 1, 0), 5);
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

    @EventHandler
    private void blazebornBurningWrath(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        double baseDamage = event.getDamage();

        if (damager instanceof Player) {
            Player player = (Player) damager;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();

            if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
                if (player.getFireTicks() > 0) {
                    event.setDamage(baseDamage + 1.5);
                }
            }
        }
    }

    @EventHandler
    private void blazebornSnowBallDamage(EntityDamageByEntityEvent event) {
        Entity target = event.getEntity();
        Entity damager = event.getDamager();
        double baseDamage = event.getDamage();

        if (target instanceof Player && damager instanceof Snowball) {
            Player targetPlayer = (Player) target;
            OriginPlayer originPlayer = new OriginPlayer(targetPlayer);
            String targetPlayerOrigin = originPlayer.getOrigin();

            if (Objects.equals(targetPlayerOrigin, Origins.BLAZEBORN.toString())) {
                event.setDamage(baseDamage + 1.5);
            }
        }
    }

    @EventHandler
    private void blazebornDamageImmunities(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (entity instanceof Player) {
            Player player = ((Player) entity).getPlayer();

            if (player != null) {
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();

                if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
                    if (damageCause == EntityDamageEvent.DamageCause.LAVA || damageCause == EntityDamageEvent.DamageCause.FIRE || damageCause == EntityDamageEvent.DamageCause.FIRE_TICK || damageCause == EntityDamageEvent.DamageCause.HOT_FLOOR || damageCause == EntityDamageEvent.DamageCause.POISON || damageCause == EntityDamageEvent.DamageCause.STARVATION) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    private void blazebornPotionDrinkingDamage(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        ItemStack itemStack = event.getItem();
        Material material = itemStack.getType();

        if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
            if (material == Material.POTION) {
                player.damage(2);
            }
        }
    }

    @EventHandler
    private void blazebornSplashPotionDamage(PotionSplashEvent event) {
        Collection<LivingEntity> livingEntities = event.getAffectedEntities();

        for (LivingEntity livingEntity : livingEntities) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();

                if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
                    player.damage(2);
                }
            }
        }
    }

    @EventHandler
    private void blazebornRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (Objects.equals(playerOrigin, Origins.BLAZEBORN.toString())) {
            if (player.getBedSpawnLocation() == null) {
                Location playerLocation = player.getLocation();
                Location randomLocation = randomNetherCoordinatesGenerator(playerLocation);

                findSafeLocation(randomLocation, event);
            }
        }
    }

    private void blazebornNetherSpawn(Player player) {
        Location playerLocation = player.getLocation();
        Location location = randomNetherCoordinatesGenerator(playerLocation);

        teleportPlayer(location, player);
    }

    private void teleportPlayer(Location location, Player player) {
        new BukkitRunnable() {
            Location loc = location;

            @Override
            public void run() {
                if (isSafeLocation(loc)) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            player.teleport(loc);
                        }
                    }.runTask(getOriginListenerHandler()
                            .getListenerHandler()
                            .getPlugin());
                    cancel();
                } else {
                    Location newLocation = loc.subtract(0, 1, 0);

                    if (newLocation.getY() == 0) {
                        loc = randomNetherCoordinatesGenerator(loc);
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 1L);
    }

    private void findSafeLocation(Location location, PlayerRespawnEvent event) {
        if (isSafeLocation(location)) {
            event.setRespawnLocation(location);
        } else {
            Location newLocation = location.subtract(0, 1, 0);
            if (newLocation.getY() != 0) {
                findSafeLocation(newLocation, event);
            } else {
                Location generateNewLocation = randomNetherCoordinatesGenerator(location);
                findSafeLocation(generateNewLocation, event);
            }
        }
    }

    private Location randomNetherCoordinatesGenerator(Location location) {
        Location randomLocation = new Location(Bukkit.getWorld(Config.WORLDS_NETHER.toString()),
                location.getX(), location.getY(), location.getZ());
        Random random = new Random();
        int x = random.nextInt(250);
        int z = random.nextInt(250);

        randomLocation.setX(x);
        randomLocation.setY(90);
        randomLocation.setZ(z);

        return randomLocation;
    }

    private boolean isSafeLocation(Location location) {
        Block ground = location.getBlock().getRelative(BlockFace.DOWN);
        Block head = location.getBlock().getRelative(BlockFace.UP);

        if (head.getType().isSolid()) {
            return false;
        }
        if (ground.getType().isAir()) {
            return false;
        }
        if (ground.isLiquid() || head.isLiquid()) {
            return false;
        }

        return ground.getType().isSolid();
    }
}
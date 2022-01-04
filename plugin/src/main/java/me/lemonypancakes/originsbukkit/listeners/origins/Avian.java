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

import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginChangeEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import me.lemonypancakes.originsbukkit.util.Message;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Avian extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;

    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    public Avian(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_AVIAN_MAX_HEALTH.toDouble(),
                Config.ORIGINS_AVIAN_WALK_SPEED.toFloat(),
                Config.ORIGINS_AVIAN_FLY_SPEED.toFloat());
        this.originListenerHandler = originListenerHandler;
        init();
    }

    @Override
    public String getOriginIdentifier() {
        return "Avian";
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
        return Material.FEATHER;
    }

    @Override
    public boolean isOriginIconGlowing() {
        return false;
    }

    @Override
    public String getOriginTitle() {
        return Lang.AVIAN_TITLE.toString();
    }

    @Override
    public String[] getOriginDescription() {
        return Lang.AVIAN_DESCRIPTION.toStringList();
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
    }

    @EventHandler
    private void avianJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.AVIAN.toString())) {
            avianSlowFalling(player);
        }
    }

    @EventHandler
    private void onOriginChange(AsyncPlayerOriginChangeEvent event) {
        Player player = event.getPlayer();
        String oldOrigin = event.getOldOrigin();

        if (Objects.equals(oldOrigin, Origins.AVIAN.toString())) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                }
            }.runTask(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin());
        }
    }

    private void avianSlowFalling(Player player) {
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.addPotionEffect(new PotionEffect(
                            PotionEffectType.SLOW_FALLING,
                            Integer.MAX_VALUE,
                            0,
                            false,
                            false));
                }
            }.runTask(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin());
        }
    }

    @EventHandler
    private void onAvianSlowFallingToggle(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
            if (player.isGliding()) {
                if (!player.isSneaking()) {
                    player.setVelocity(player.getVelocity().setY(0));
                    player.addPotionEffect(new PotionEffect(
                            PotionEffectType.SLOW_FALLING,
                            Integer.MAX_VALUE,
                            0,
                            false,
                            false));
                } else {
                    player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                }
            } else {
                if (player.isSneaking()) {
                    player.setVelocity(player.getVelocity().setY(0));
                    player.addPotionEffect(new PotionEffect(
                            PotionEffectType.SLOW_FALLING,
                            Integer.MAX_VALUE,
                            0,
                            false,
                            false));
                } else {
                    player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                }
            }
        }
    }

    @EventHandler
    private void onAvianSlowFallingElytraToggle(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();

            if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
                if (!player.isGliding()) {
                    if (player.isSneaking()) {
                        player.setVelocity(player.getVelocity().setY(0));
                        player.addPotionEffect(new PotionEffect(
                                PotionEffectType.SLOW_FALLING,
                                Integer.MAX_VALUE,
                                0,
                                false,
                                false));
                    } else {
                        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                    }
                } else {
                    if (!player.isSneaking()) {
                        player.setVelocity(player.getVelocity().setY(0));
                        player.addPotionEffect(new PotionEffect(
                                PotionEffectType.SLOW_FALLING,
                                Integer.MAX_VALUE,
                                0,
                                false,
                                false));
                    } else {
                        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                    }
                }
            }
        }
    }

    @EventHandler
    private void onAvianSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Block bed = event.getBed();
        Location bedLocation = bed.getLocation();
        double bedLocationY = bedLocation.getY();

        if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
            if (bedLocationY < 86) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onAvianBedRespawnPointSet(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();

        if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (clickedBlock != null && clickedBlock.getLocation().getY() < 86 && Tag.BEDS.isTagged(clickedBlock.getType())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    private void onAvianEggLay(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        PlayerInventory playerInventory = player.getInventory();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        World world = player.getWorld();
        long getWorldTime = world.getTime();

        if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
            if (getWorldTime == 0) {
                if (playerInventory.firstEmpty() == -1) {
                    world.dropItem(location, new ItemStack(Material.EGG, 1));
                    Message.sendPlayerMessage(player, "&cYour inventory was full, so we dropped your egg on the ground.");
                } else {
                    playerInventory.addItem(new ItemStack(Material.EGG, 1));
                }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You laid an egg!"));
            }
        }
    }

    @EventHandler
    private void avianAntiSlowFallingEffectRemove(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();
            PotionEffect oldEffect = event.getOldEffect();
            EntityPotionEffectEvent.Cause cause = event.getCause();

            if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
                if (oldEffect != null) {
                    if (oldEffect.getType().equals(PotionEffectType.SLOW_FALLING) && cause != EntityPotionEffectEvent.Cause.PLUGIN) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    private void avianEatingDisabilities(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Material material = event.getItem().getType();
        List<Material> materials = Arrays.asList(
                Material.COOKED_BEEF,
                Material.COOKED_COD,
                Material.COOKED_CHICKEN,
                Material.COOKED_MUTTON,
                Material.COOKED_RABBIT,
                Material.COOKED_PORKCHOP,
                Material.COOKED_SALMON,
                Material.BEEF,
                Material.COD,
                Material.CHICKEN,
                Material.MUTTON,
                Material.RABBIT,
                Material.PORKCHOP,
                Material.SALMON,
                Material.TROPICAL_FISH,
                Material.PUFFERFISH,
                Material.ROTTEN_FLESH);

        if (Objects.equals(playerOrigin, Origins.AVIAN.toString())) {
            if (materials.contains(material)) {
                event.setCancelled(true);
            }
        }
    }
}

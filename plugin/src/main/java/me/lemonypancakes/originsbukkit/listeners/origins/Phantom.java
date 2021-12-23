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
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginAbilityUseEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import me.lemonypancakes.originsbukkit.storage.wrappers.PhantomAbilityToggleDataWrapper;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Phantom.
 *
 * @author LemonyPancakes
 */
public class Phantom extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final List<Player> phantomPlayers = new ArrayList<>();

    /**
     * Gets origin listener handler.
     *
     * @return the origin listener handler
     */
    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    /**
     * Instantiates a new Phantom.
     *
     * @param originListenerHandler the origin listener handler
     */
    public Phantom(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_PHANTOM_MAX_HEALTH.toDouble(),
                Config.ORIGINS_PHANTOM_WALK_SPEED.toFloat(),
                Config.ORIGINS_PHANTOM_FLY_SPEED.toFloat());
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
        return "Phantom";
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
        return Material.PHANTOM_MEMBRANE;
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
        return Lang.PHANTOM_TITLE.toString();
    }

    /**
     * Get origin description string [ ].
     *
     * @return the string [ ]
     */
    @Override
    public String[] getOriginDescription() {
        return Lang.PHANTOM_DESCRIPTION.toStringList();
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
        registerPhantomSunlightDamageListener();
        registerPhantomInvisibilityPotionPacketListener();
    }

    /**
     * Phantom join.
     *
     * @param event the event
     */
    @EventHandler
    private void phantomJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String origin = event.getOrigin();
        boolean isToggled = originPlayer.getPhantomAbilityToggleData();

        if (Objects.equals(origin, Origins.PHANTOM.toString())) {
            if (!isToggled) {
                getOriginListenerHandler()
                        .getListenerHandler()
                        .getPlugin()
                        .getUtilHandler()
                        .getGhostFactory()
                        .setGhost(player, true);
                player.setFlySpeed(0.1f);
                player.setAllowFlight(false);
                player.setFlying(false);
            } else {
                player.setFlySpeed(0.05f);
                player.setAllowFlight(true);
                player.setFlying(true);
            }
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.addPotionEffect(
                            new PotionEffect(
                                    PotionEffectType.INVISIBILITY,
                                    Integer.MAX_VALUE, 255,
                                    false,
                                    false
                            ));
                }
            }.runTask(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin());
            phantomPlayers.add(player);
        } else {
            getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin()
                    .getUtilHandler()
                    .getGhostFactory()
                    .addPlayer(player);
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                }
            }.runTask(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin());
            player.setInvisible(false);
        }
    }

    /**
     * Phantom ability use.
     *
     * @param event the event
     */
    @EventHandler
    private void phantomAbilityUse(AsyncPlayerOriginAbilityUseEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.PHANTOM.toString())) {
            phantomSwitchToggleAbility(player);
        }
    }

    /**
     * Register phantom sunlight damage listener.
     */
    private void registerPhantomSunlightDamageListener() {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!phantomPlayers.isEmpty()) {
                    for (int i = 0; i < phantomPlayers.size(); i++) {
                        Player player = phantomPlayers.get(i);
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        boolean isToggled = originPlayer.getPhantomAbilityToggleData();
                        World world = player.getWorld();
                        Location location = player.getLocation();

                        if (Objects.equals(playerOrigin, Origins.PHANTOM.toString())) {
                            if (!isToggled) {
                                if (world.getTime() > 0 && world.getTime() < 13000) {
                                    if (location.getBlockY() > player.getWorld().getHighestBlockAt(location).getLocation().getBlockY()) {
                                        player.setFireTicks(20);
                                    }
                                }
                            }
                        } else {
                            phantomPlayers.remove(player);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_DELAY.toLong(), Config.ORIGINS_BLAZEBORN_WATER_DAMAGE_PERIOD_DELAY.toLong());
    }

    /**
     * Phantom switch toggle ability.
     *
     * @param player the player
     */
    private void phantomSwitchToggleAbility(Player player) {
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        boolean isToggled = originPlayer.getPhantomAbilityToggleData();

        if (originPlayer.findPhantomAbilityToggleData() == null) {
            if (!(player.getFoodLevel() < 4)) {
                originPlayer.createPhantomAbilityToggleData(true);
                ChatUtils.sendPlayerMessage(player, "&7Ability Toggled &aON");
                getOriginListenerHandler()
                        .getListenerHandler()
                        .getPlugin()
                        .getUtilHandler()
                        .getGhostFactory()
                        .removePlayer(player);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setFlySpeed(0.05f);
            }
        } else {
            if (isToggled) {
                originPlayer.updatePhantomAbilityToggleData(
                        new PhantomAbilityToggleDataWrapper(playerUUID, false));
                ChatUtils.sendPlayerMessage(player, "&7Ability Toggled &cOFF");
                getOriginListenerHandler()
                        .getListenerHandler()
                        .getPlugin()
                        .getUtilHandler()
                        .getGhostFactory()
                        .addPlayer(player);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setFlySpeed(0.1f);
            } else {
                if (!(player.getFoodLevel() < 4)) {
                    originPlayer.updatePhantomAbilityToggleData(
                            new PhantomAbilityToggleDataWrapper(playerUUID, true));
                    getOriginListenerHandler()
                            .getListenerHandler()
                            .getPlugin()
                            .getUtilHandler()
                            .getGhostFactory()
                            .removePlayer(player);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.setFlySpeed(0.05f);
                    ChatUtils.sendPlayerMessage(player, "&7Ability Toggled &aON");
                } else {
                    ChatUtils.sendPlayerMessage(player, "&cCannot use ability because your hunger level is less than 4 levels.");
                }
            }
        }
    }

    /**
     * Phantom fast exhaustion.
     *
     * @param event the event
     */
    @EventHandler
    private void phantomFastExhaustion(FoodLevelChangeEvent event) {
        HumanEntity humanEntity = event.getEntity();
        Player player = (Player) humanEntity;
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        boolean isToggled = originPlayer.getPhantomAbilityToggleData();

        if (Objects.equals(playerOrigin, Origins.PHANTOM.toString())) {
            if (isToggled) {
                if (player.getFoodLevel() < 4) {
                    phantomSwitchToggleAbility(player);
                    ChatUtils.sendPlayerMessage(player, "&cToggled your ability because your hunger level is less than 4 levels");
                }
                event.setFoodLevel(event.getFoodLevel() - 2);
            }
        }
    }

    /**
     * Phantom anti invisibility effect remove.
     *
     * @param event the event
     */
    @EventHandler
    private void phantomAntiInvisibilityEffectRemove(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();
            PotionEffect oldEffect = event.getOldEffect();
            EntityPotionEffectEvent.Cause cause = event.getCause();

            if (Objects.equals(playerOrigin, Origins.PHANTOM.toString())) {
                if (oldEffect != null) {
                    if (oldEffect.getType().equals(PotionEffectType.INVISIBILITY) && cause != EntityPotionEffectEvent.Cause.PLUGIN) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Anti non phantom invisibility potion.
     *
     * @param event the event
     */
    @EventHandler
    private void antiNonPhantomInvisibilityPotion(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();
            PotionEffect newEffect = event.getNewEffect();

            if (!Objects.equals(playerOrigin, Origins.PHANTOM.toString())) {
                if (newEffect != null) {
                    if (newEffect.getType().equals(PotionEffectType.INVISIBILITY)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Register phantom invisibility potion packet listener.
     */
    private void registerPhantomInvisibilityPotionPacketListener() {
        getOriginListenerHandler().getListenerHandler().getPlugin().getProtocolManager().addPacketListener(
                new PacketAdapter(getOriginListenerHandler().getListenerHandler().getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EFFECT) {

                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        Player player = event.getPlayer();
                        OriginPlayer originPlayer = new OriginPlayer(player);
                        String playerOrigin = originPlayer.getOrigin();
                        byte effectType = packet.getBytes().read(0);

                        if (Objects.equals(playerOrigin, Origins.PHANTOM.toString())) {
                            if (effectType == 14) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
        );
    }
}

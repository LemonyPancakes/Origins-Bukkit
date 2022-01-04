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
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginChangeEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import me.lemonypancakes.originsbukkit.storage.wrappers.ArachnidAbilityToggleDataWrapper;
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Arachnid extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final Map<UUID, Long> COOLDOWN = new HashMap<>();
    private final int COOLDOWNTIME = Config.ORIGINS_ARACHNID_ABILITY_SPIDER_WEB_COOLDOWN.toInt();

    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    public Arachnid(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_ARACHNID_MAX_HEALTH.toDouble(),
                Config.ORIGINS_ARACHNID_WALK_SPEED.toFloat(),
                Config.ORIGINS_ARACHNID_FLY_SPEED.toFloat());
        this.originListenerHandler = originListenerHandler;
        init();
    }

    @Override
    public String getOriginIdentifier() {
        return "Arachnid";
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
        return Material.COBWEB;
    }

    @Override
    public boolean isOriginIconGlowing() {
        return false;
    }

    @Override
    public String getOriginTitle() {
        return Lang.ARACHNID_TITLE.toString();
    }

    @Override
    public String[] getOriginDescription() {
        return Lang.ARACHNID_DESCRIPTION.toStringList();
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
        registerArachnidCobwebMovePacketListener();
    }

    @EventHandler
    private void onOriginChange(AsyncPlayerOriginChangeEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Block block = location.getBlock();
        Block block1 = location.add(0, 1, 0).getBlock();
        Material material = block.getType();
        Material material1 = block1.getType();
        String oldOrigin = event.getOldOrigin();
        String newOrigin = event.getNewOrigin();

        if (Objects.equals(oldOrigin, Origins.ARACHNID.toString())) {
            if (player.getAllowFlight()) {
                if (material == Material.COBWEB || material1 == Material.COBWEB) {
                    if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                        player.setFlying(false);
                        player.setAllowFlight(false);
                    }
                    if (newOrigin == null) {
                        player.setFlySpeed(0.1f);
                    }
                }
            }
        }
    }

    @EventHandler
    private void arachnidAbilityUse(AsyncPlayerOriginAbilityUseEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.ARACHNID.toString())) {
            arachnidClimbToggleAbility(player);
        }
    }

    @EventHandler
    private void onArachnidClimbToggle(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (Objects.equals(playerOrigin, Origins.ARACHNID.toString())) {
            if (originPlayer.findArachnidAbilityToggleData() == null) {
                originPlayer.createArachnidAbilityToggleData(false);
            }
            if (originPlayer.getArachnidAbilityToggleData()) {
                if (!player.isSneaking()) {
                    arachnidClimb(player);
                }
            }
        }
    }

    @EventHandler
    private void onArachnidAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        Location location = entity.getLocation();

        if (damager instanceof Player) {
            Player player = (Player) event.getDamager();
            UUID playerUUID = player.getUniqueId();
            OriginPlayer originPlayer = new OriginPlayer(player);
            String playerOrigin = originPlayer.getOrigin();

            if (Objects.equals(playerOrigin, Origins.ARACHNID.toString())) {
                Location location1 = location.add(0, 1, 0);
                Block block1 = location1.getBlock();
                Material material1 = block1.getType();

                if (material1.isAir()) {
                    if (COOLDOWN.containsKey(playerUUID)) {
                        long secondsLeft = ((COOLDOWN.get(playerUUID) / 1000) + COOLDOWNTIME - (System.currentTimeMillis() / 1000));

                        if (secondsLeft > 0) {
                            Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_COOLDOWN
                                    .toString()
                                    .replace("%seconds_left%", String.valueOf(secondsLeft)));
                        } else {
                            removeArachnidCobwebs(material1, block1);
                            block1.setType(Material.COBWEB);
                            COOLDOWN.put(playerUUID, System.currentTimeMillis());
                            Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                                    .toString()
                                    .replace("%player_current_origin%", playerOrigin));
                        }
                    } else {
                        removeArachnidCobwebs(material1, block1);
                        block1.setType(Material.COBWEB);
                        COOLDOWN.put(playerUUID, System.currentTimeMillis());
                        Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                                .toString()
                                .replace("%player_current_origin%", playerOrigin));
                    }
                }
            }
        }
    }

    @EventHandler
    private void onArachnidBaneOfArthropodsDamage(EntityDamageByEntityEvent event) {
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

                if (Objects.equals(targetPlayerOrigin, Origins.ARACHNID.toString())) {
                    if (itemMeta != null && itemMeta.hasEnchant(Enchantment.DAMAGE_ARTHROPODS)) {
                        int enchantLevel = itemMeta.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS);

                        event.setDamage(baseDamage + (2.5 * enchantLevel));
                    }
                }
            }
        }
    }

    private void arachnidClimb(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                Location location = player.getLocation();
                Block block = location.getBlock();

                if (Objects.equals(playerOrigin, Origins.ARACHNID.toString())) {
                    if (player.isOnline()) {
                        if (originPlayer.getArachnidAbilityToggleData()) {
                            if (player.isSneaking() && !player.isGliding()) {
                                if (nextToWall(player) && !block.isLiquid()) {
                                    player.setVelocity(player.getVelocity()
                                            .setY(Config.ORIGINS_ARACHNID_ABILITY_CLIMBING_Y_VELOCITY.toDouble()));
                                }
                            } else {
                                cancel();
                            }
                        } else {
                            cancel();
                        }
                    } else {
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 0L, 1L);
    }

    private void arachnidClimbToggleAbility(Player player) {
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        Location location = player.getLocation();
        Block block = location.getBlock();

        if (originPlayer.findArachnidAbilityToggleData() == null) {
            originPlayer.createArachnidAbilityToggleData(false);
            Message.sendPlayerMessage(player, "&7Ability Toggled &cOFF");
        } else {
            if (originPlayer.getArachnidAbilityToggleData()) {
                originPlayer.updateArachnidAbilityToggleData(
                        new ArachnidAbilityToggleDataWrapper(playerUUID, false));
                Message.sendPlayerMessage(player, "&7Ability Toggled &cOFF");
            } else {
                originPlayer.updateArachnidAbilityToggleData(
                        new ArachnidAbilityToggleDataWrapper(playerUUID, true));
                if (nextToWall(player) && !block.isLiquid()) {
                    arachnidClimb(player);
                }
                Message.sendPlayerMessage(player, "&7Ability Toggled &aON");
            }
        }
    }

    private void registerArachnidCobwebMovePacketListener() {
        getOriginListenerHandler().getListenerHandler().getPlugin().getProtocolManager().addPacketListener(
                new PacketAdapter(getOriginListenerHandler().getListenerHandler().getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                Location playerLocation = player.getLocation();
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                double x = packet.getDoubles().read(0);
                double y = packet.getDoubles().read(1);
                double z = packet.getDoubles().read(2);
                World world = player.getWorld();
                Location location = new Location(world, x, y, z);
                Block block = location.getBlock();
                Block block1 = location.add(0, 1, 0).getBlock();
                Material material = block.getType();
                Material material1 = block1.getType();

                if (Objects.equals(playerOrigin, Origins.ARACHNID.toString())) {
                    if (material == Material.COBWEB || material1 == Material.COBWEB || nextToCobweb(player)) {
                        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            if (!player.isFlying()) {
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        player.teleport(playerLocation.add(0, 0.00001, 0));
                                    }
                                }.runTask(plugin);
                            }
                            player.setFlySpeed(0.04F);
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        } else {
                            player.setFlySpeed(0.1F);
                        }
                    } else {
                        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            player.setFlySpeed(0.1F);
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        }
                    }
                }
            }
        });
    }

    private boolean nextToWall(Player player) {
        World world = player.getWorld();
        double locX = player.getLocation().getX();
        double locY = player.getLocation().getY();
        double locZ = player.getLocation().getZ();
        Location xp = new Location(world, locX + 0.30175, locY, locZ);
        Location xn = new Location(world, locX - 0.30175, locY, locZ);
        Location zp = new Location(world, locX, locY, locZ + 0.30175);
        Location zn = new Location(world, locX, locY, locZ - 0.30175);

        if (xp.getBlock().getType().isSolid()) {
            return true;
        }
        if (xn.getBlock().getType().isSolid()) {
            return true;
        }
        if (zp.getBlock().getType().isSolid()) {
            return true;
        }
        return zn.getBlock().getType().isSolid();
    }

    private boolean nextToCobweb(Player player) {
        World world = player.getWorld();
        double locX = player.getLocation().getX();
        double locY = player.getLocation().getY();
        double locZ = player.getLocation().getZ();
        Location xp = new Location(world, locX + 0.3, locY, locZ);
        Location xn = new Location(world, locX - 0.3, locY, locZ);
        Location zp = new Location(world, locX, locY, locZ + 0.3);
        Location zn = new Location(world, locX, locY, locZ - 0.3);

        if (xp.getBlock().getType() == Material.COBWEB) {
            return true;
        }
        if (xn.getBlock().getType() == Material.COBWEB) {
            return true;
        }
        if (zp.getBlock().getType() == Material.COBWEB) {
            return true;
        }
        return zn.getBlock().getType() == Material.COBWEB;
    }

    private void removeArachnidCobwebs(Material material, Block block) {

        new BukkitRunnable() {

            @Override
            public void run() {
                block.setType(material);
            }
        }.runTaskLater(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin(), 20L * 10);
    }

    @EventHandler
    private void arachnidCrafting(PrepareItemCraftEvent event) {
        HumanEntity humanEntity = event.getView().getPlayer();
        Player player = (Player) humanEntity;
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Recipe recipe = event.getRecipe();
        ItemStack cobweb = getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin()
                .getItemHandler()
                .getArachnidCobweb()
                .getItemStack();

        if (!Objects.equals(playerOrigin, Origins.ARACHNID.toString())) {
            if (recipe != null) {
                if (recipe.getResult().equals(cobweb)) {
                    event.getInventory().setResult(null);
                }
            }
        }
    }

    @EventHandler
    private void arachnidEatingDisabilities(PlayerItemConsumeEvent event) {
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

        if (Objects.equals(playerOrigin, Origins.ARACHNID.toString())) {
            if (!materials.contains(material)) {
                event.setCancelled(true);
            }
        }
    }
}

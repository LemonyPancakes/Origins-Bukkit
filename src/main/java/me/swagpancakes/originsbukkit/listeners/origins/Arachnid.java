package me.swagpancakes.originsbukkit.listeners.origins;

import me.swagpancakes.originsbukkit.Main;
import me.swagpancakes.originsbukkit.enums.Lang;
import me.swagpancakes.originsbukkit.enums.Origins;
import me.swagpancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * The type Arachnid.
 */
public class Arachnid implements Listener {

    private final Main plugin;

    /**
     * Instantiates a new Arachnid.
     *
     * @param plugin the plugin
     */
    public Arachnid(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    private final HashMap<UUID, Long> COOLDOWN = new HashMap<>();
    private final int COOLDOWNTIME = 5;

    /**
     * Arachnid join.
     *
     * @param player the player
     */
    public void arachnidJoin(Player player) {
        UUID playerUUID = player.getUniqueId();

        if (Objects.equals(plugin.storageUtils.getPlayerOrigin(playerUUID), Origins.ARACHNID)) {
            player.setHealthScale((10 - 3)*2);
        }
    }

    /**
     * On arachnid climb toggle.
     *
     * @param event the event
     */
    @EventHandler
    public void onArachnidClimbToggle(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (Objects.equals(plugin.storageUtils.getPlayerOrigin(playerUUID), Origins.ARACHNID)) {
            if (!player.isSneaking()) {
                arachnidClimb(player);
            }
        }
    }

    /**
     * On entity damage by entity.
     *
     * @param event the event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        Location location = entity.getLocation();

        if (damager instanceof Player) {
            Player player = (Player) event.getDamager();
            UUID playerUUID = player.getUniqueId();

            if (Objects.equals(plugin.storageUtils.getPlayerOrigin(playerUUID), Origins.ARACHNID)) {
                Location location1 = location.add(0, 1 , 0);
                Block block1 = location1.getBlock();
                Material material1 = block1.getType();

                if (material1.isAir()) {
                    if (COOLDOWN.containsKey(playerUUID)) {
                        long secondsLeft = ((COOLDOWN.get(playerUUID) / 1000) + COOLDOWNTIME - (System.currentTimeMillis() / 1000));

                        if (secondsLeft > 0) {
                            ChatUtils.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_COOLDOWN
                                    .toString()
                                    .replace("%seconds_left%", String.valueOf(secondsLeft)));
                        } else {
                            removeArachnidCobwebs(material1, block1);
                            block1.setType(Material.COBWEB);
                            COOLDOWN.put(playerUUID, System.currentTimeMillis());
                            ChatUtils.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                                    .toString()
                                    .replace("%player_current_origin%", String.valueOf(plugin.storageUtils.getPlayerOrigin(playerUUID))));
                        }
                    } else {
                        removeArachnidCobwebs(material1, block1);
                        block1.setType(Material.COBWEB);
                        COOLDOWN.put(playerUUID, System.currentTimeMillis());
                        ChatUtils.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ABILITY_USE
                                .toString()
                                .replace("%player_current_origin%", String.valueOf(plugin.storageUtils.getPlayerOrigin(playerUUID))));
                    }
                }
            }
        }
    }

    /**
     * Arachnid climb.
     *
     * @param player the player
     */
    public void arachnidClimb(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                UUID playerUUID = player.getUniqueId();

                if (Objects.equals(plugin.storageUtils.getPlayerOrigin(playerUUID), Origins.ARACHNID)) {
                    if (player.isOnline()) {
                        if (player.isSneaking()) {
                            if (nextToWall(player)) {
                                player.setVelocity(player.getVelocity().setY(0.175));
                            }
                        } else {
                            this.cancel();
                        }
                    } else {
                        this.cancel();
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    /**
     * Next to wall boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public boolean nextToWall(Player player) {
        Location location = player.getLocation();
        Block block = location.getBlock();

        if (block.getRelative(BlockFace.NORTH).getType().isSolid()) {
            return true;
        }
        if (block.getRelative(BlockFace.SOUTH).getType().isSolid()) {
            return true;
        }
        if (block.getRelative(BlockFace.EAST).getType().isSolid()) {
            return true;
        }
        if (block.getRelative(BlockFace.WEST).getType().isSolid()) {
            return true;
        }
        return false;
    }

    /**
     * Remove arachnid cobwebs.
     *
     * @param material the material
     * @param block    the block
     */
    public void removeArachnidCobwebs(Material material, Block block) {

        new BukkitRunnable() {

            @Override
            public void run() {
                block.setType(material);
            }
        }.runTaskLater(plugin, 20L * 10);
    }

    /**
     * Arachnid eating disabilities.
     *
     * @param event the event
     */
    @EventHandler
    public void arachnidEatingDisabilities(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
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

        if (Objects.equals(plugin.storageUtils.getPlayerOrigin(playerUUID), Origins.ARACHNID)) {
            if (!materials.contains(material)) {
                event.setCancelled(true);
            }
        }
    }
}

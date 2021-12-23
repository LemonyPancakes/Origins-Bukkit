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
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginAbilityUseEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginChangeEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.util.Origin;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Config;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Origins;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.*;

/**
 * The type Shulk.
 *
 * @author LemonyPancakes
 */
public class Shulk extends Origin implements Listener {

    private final OriginListenerHandler originListenerHandler;
    private final List<Player> shulkInventoryViewers = new ArrayList<>();
    private final List<Material> materialList = new ArrayList<>();

    /**
     * Gets origin listener handler.
     *
     * @return the origin listener handler
     */
    public OriginListenerHandler getOriginListenerHandler() {
        return originListenerHandler;
    }

    /**
     * Gets material list.
     *
     * @return the material list
     */
    public List<Material> getMaterialList() {
        return materialList;
    }

    /**
     * Gets shulk inventory viewers.
     *
     * @return the shulk inventory viewers
     */
    public List<Player> getShulkInventoryViewers() {
        return shulkInventoryViewers;
    }

    /**
     * Instantiates a new Shulk.
     *
     * @param originListenerHandler the origin listener handler
     */
    public Shulk(OriginListenerHandler originListenerHandler) {
        super(Config.ORIGINS_SHULK_MAX_HEALTH.toDouble(),
                Config.ORIGINS_SHULK_WALK_SPEED.toFloat(),
                Config.ORIGINS_SHULK_FLY_SPEED.toFloat());
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
        return "Shulk";
    }

    /**
     * Gets impact.
     *
     * @return the impact
     */
    @Override
    public Impact getImpact() {
        return Impact.LOW;
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
        return Material.SHULKER_SHELL;
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
        return Lang.SHULK_TITLE.toString();
    }

    /**
     * Get origin description string [ ].
     *
     * @return the string [ ]
     */
    @Override
    public String[] getOriginDescription() {
        return Lang.SHULK_DESCRIPTION.toStringList();
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
        registerShulkBlockDiggingPacketListener();
        String[] materialList = Config.ORIGINS_SHULK_ABILITY_BLOCK_LIST.toStringList();
        for (String material : materialList) {
            getMaterialList().add(Material.valueOf(material));
        }
    }

    /**
     * Shulk join.
     *
     * @param event the event
     */
    @EventHandler
    private void shulkJoin(AsyncPlayerOriginInitiateEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String origin = event.getOrigin();
        AttributeInstance genericArmorAttribute = player.getAttribute(Attribute.GENERIC_ARMOR);

        if (Objects.equals(origin, Origins.SHULK.toString())) {
            if (genericArmorAttribute != null) {
                double defaultBaseValue = genericArmorAttribute.getBaseValue();

                if (!(defaultBaseValue >= 8))
                genericArmorAttribute.setBaseValue(defaultBaseValue + 8);
            }
            if (!getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin()
                    .getStorageHandler()
                    .getShulkPlayerStorageData()
                    .getShulkPlayerStorageData()
                    .containsKey(playerUUID)) {
                try {
                    getOriginListenerHandler()
                            .getListenerHandler()
                            .getPlugin()
                            .getStorageHandler()
                            .getShulkPlayerStorageData()
                            .loadShulkPlayerStorageData(playerUUID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        AttributeInstance genericArmorAttribute = player.getAttribute(Attribute.GENERIC_ARMOR);
        String oldOrigin = event.getOldOrigin();

        if (Objects.equals(oldOrigin, Origins.SHULK.toString())) {
            if (genericArmorAttribute != null) {
                double modifiedBaseValue = genericArmorAttribute.getBaseValue();

                if (modifiedBaseValue == 8) {
                    genericArmorAttribute.setBaseValue(modifiedBaseValue - 8);
                }
            }
        }
    }

    /**
     * Shulk ability use.
     *
     * @param event the event
     */
    @EventHandler
    private void shulkAbilityUse(AsyncPlayerOriginAbilityUseEvent event) {
        Player player = event.getPlayer();
        String origin = event.getOrigin();

        if (Objects.equals(origin, Origins.SHULK.toString())) {
            shulkInventoryAbility(player);
        }
    }

    /**
     * Shulk shield disability.
     *
     * @param event the event
     */
    @EventHandler
    private void shulkShieldDisability(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();
        World world = player.getWorld();
        Location location = player.getLocation();
        EquipmentSlot equipmentSlot = event.getHand();
        PlayerInventory playerInventory = player.getInventory();

        if (Objects.equals(playerOrigin, Origins.SHULK.toString())) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (itemStack != null) {
                    Material material = itemStack.getType();

                    if (material == Material.SHIELD) {
                        if (equipmentSlot == EquipmentSlot.HAND) {
                            playerInventory.remove(itemStack);
                            world.dropItemNaturally(location, itemStack);
                        } else if (equipmentSlot == EquipmentSlot.OFF_HAND) {
                            playerInventory.setItemInOffHand(null);
                            world.dropItemNaturally(location, itemStack);
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Shulk fast exhaustion.
     *
     * @param event the event
     */
    @EventHandler
    private void shulkFastExhaustion(FoodLevelChangeEvent event) {
        HumanEntity humanEntity = event.getEntity();
        Player player = (Player) humanEntity;
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();

        if (Objects.equals(playerOrigin, Origins.SHULK.toString())) {
            event.setFoodLevel(event.getFoodLevel() - 1);
        }
    }

    /**
     * Shulk inventory ability.
     *
     * @param player the player
     */
    private void shulkInventoryAbility(Player player) {
        UUID playerUUID = player.getUniqueId();
        Inventory inv = Bukkit.createInventory(player, InventoryType.DROPPER, player.getName() + "'s Vault");

        if (getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin()
                .getStorageHandler()
                .getShulkPlayerStorageData()
                .getShulkPlayerStorageData()
                .containsKey(playerUUID)) {
            inv.setContents(getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin()
                    .getStorageHandler()
                    .getShulkPlayerStorageData()
                    .getShulkPlayerStorageData()
                    .get(playerUUID));
        }
        if (!shulkInventoryViewers.contains(player)) {
            shulkInventoryViewers.add(player);
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTask(getOriginListenerHandler()
                .getListenerHandler()
                .getPlugin());
    }

    /**
     * On shulk custom inventory close.
     *
     * @param event the event
     */
    @EventHandler
    private void onShulkCustomInventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        Player player = (Player) humanEntity;
        UUID playerUUID = player.getUniqueId();

        if (event.getView().getTitle().contains(player.getName() + "'s Vault")) {
            getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin()
                    .getStorageHandler()
                    .getShulkPlayerStorageData()
                    .getShulkPlayerStorageData()
                    .remove(playerUUID);
            getOriginListenerHandler()
                    .getListenerHandler()
                    .getPlugin()
                    .getStorageHandler()
                    .getShulkPlayerStorageData()
                    .getShulkPlayerStorageData()
                    .put(playerUUID, event.getInventory().getContents());
            try {
                getOriginListenerHandler()
                        .getListenerHandler()
                        .getPlugin()
                        .getStorageHandler()
                        .getShulkPlayerStorageData()
                        .saveShulkPlayerStorageData(playerUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            shulkInventoryViewers.remove(player);
        }
    }

    /**
     * Shulk block break.
     *
     * @param event the event
     */
    @EventHandler
    private void shulkBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        Block block = event.getBlock();
        Material material = block.getType();
        Material mainHandItemType = player.getInventory().getItemInMainHand().getType();
        List<Material> tools = Arrays.asList(
                Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.IRON_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.DIAMOND_PICKAXE,
                Material.NETHERITE_PICKAXE);

        if (Objects.equals(playerOrigin, Origins.SHULK.toString())) {
            if (!tools.contains(mainHandItemType)) {
                if (getMaterialList().contains(material)) {
                    block.breakNaturally();
                }
            }
        }
    }

    /**
     * Register shulk block digging packet listener.
     */
    private void registerShulkBlockDiggingPacketListener() {
        getOriginListenerHandler().getListenerHandler().getPlugin().getProtocolManager().addPacketListener(
                new PacketAdapter(getOriginListenerHandler().getListenerHandler().getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
                BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
                int x = blockPosition.getX();
                int y = blockPosition.getY();
                int z = blockPosition.getZ();
                Player player = event.getPlayer();
                World world = player.getWorld();
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                Location location = new Location(world, x, y, z);
                Block block = location.getBlock();
                Material material = block.getType();
                PlayerInventory playerInventory = player.getInventory();
                ItemStack itemStackMainHand = playerInventory.getItemInMainHand();
                Material mainHandItemType = itemStackMainHand.getType();
                List<Material> tools = Arrays.asList(
                        Material.WOODEN_PICKAXE,
                        Material.STONE_PICKAXE,
                        Material.IRON_PICKAXE,
                        Material.GOLDEN_PICKAXE,
                        Material.DIAMOND_PICKAXE,
                        Material.NETHERITE_PICKAXE);

                if (Objects.equals(playerOrigin, Origins.SHULK.toString())) {
                    if (getMaterialList().contains(material)) {
                        if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                            if (!tools.contains(mainHandItemType)) {
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, Config.ORIGINS_SHULK_ABILITY_DIGGING_SPEED.toInt(), false, false));
                                    }
                                }.runTask(getPlugin());
                            }
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
}

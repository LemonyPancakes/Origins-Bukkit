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
package me.lemonypancakes.originsbukkit.listeners.playerchecks;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginChangeEvent;
import me.lemonypancakes.originsbukkit.api.events.player.AsyncPlayerOriginInitiateEvent;
import me.lemonypancakes.originsbukkit.api.wrappers.OriginPlayer;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.storage.wrappers.OriginsPlayerDataWrapper;
import me.lemonypancakes.originsbukkit.util.Message;
import me.lemonypancakes.originsbukkit.util.Storage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PlayerOriginChecker implements Listener {

    private final ListenerHandler listenerHandler;
    private final Map<UUID, Integer> originPickerGUIViewers = new HashMap<>();

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    public PlayerOriginChecker(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        init();
    }

    private void init() {
        getListenerHandler()
                .getPlugin()
                .getServer()
                .getPluginManager()
                .registerEvents(this, getListenerHandler().getPlugin());
        originPickerGui();

        new BukkitRunnable() {

            @Override
            public void run() {
                if (getListenerHandler().getPlugin().getStorageHandler().getOriginsPlayerData().isOriginsPlayerDataLoaded()) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        checkPlayerOriginData(player);
                    }
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(getListenerHandler().getPlugin(), 0L, 1L);
    }

    @EventHandler
    private void onPlayerOriginCheck(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        checkPlayerOriginData(player);
        Storage.getPowersData().forEach(
                (key, value) -> value.invoke(player)
        );
        new BukkitRunnable() {

            @Override
            public void run() {
                int start = 0;
                player.sendMessage("test");
                start++;
            }
        }.runTaskTimerAsynchronously(OriginsBukkit.getPlugin(), 0L, 40L);
    }

    public void checkPlayerOriginData(Player player) {
        new BukkitRunnable() {

            @Override
            public void run() {
                OriginPlayer originPlayer = new OriginPlayer(player);
                OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

                resetPlayer(player);
                if (originsPlayerDataWrapper == null) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            openOriginPickerGui(player);
                            getListenerHandler().getNoOriginPlayerRestrict().restrictPlayerMovement(player);
                        }
                    }.runTask(getListenerHandler().getPlugin());
                } else {
                    initiatePlayerOrigin(player);
                }
            }
        }.runTaskLaterAsynchronously(getListenerHandler().getPlugin(), 4L);
    }

    public void initiatePlayerOrigin(Player player) {
        new BukkitRunnable() {

            @Override
            public void run() {
                UUID playerUUID = player.getUniqueId();
                OriginPlayer originPlayer = new OriginPlayer(player);
                String playerOrigin = originPlayer.getOrigin();
                OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

                resetPlayer(player);
                if (originsPlayerDataWrapper != null) {
                    if (getListenerHandler().getPlugin().getOrigins().containsKey(playerOrigin)) {
                        getListenerHandler().getPlugin().getOrigins().forEach((key, value) -> {
                            if (Objects.equals(key, playerOrigin)) {
                                AttributeInstance genericMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

                                if (genericMaxHealth != null) {
                                    genericMaxHealth.setBaseValue(value.getMaxHealth());
                                }
                                player.setHealthScale(value.getMaxHealth());
                                player.setWalkSpeed(value.getWalkSpeed());
                                player.setFlySpeed(value.getFlySpeed());
                                AsyncPlayerOriginInitiateEvent asyncPlayerOriginInitiateEvent = new AsyncPlayerOriginInitiateEvent(player, key);
                                Bukkit.getPluginManager().callEvent(asyncPlayerOriginInitiateEvent);
                            }
                        });
                    } else {
                        Message.sendPlayerMessage(player, "&cYour origin (" + playerOrigin + ") doesn't exist so we pruned your player data.");
                        getListenerHandler().getPlugin().getStorageHandler().getOriginsPlayerData().deleteOriginsPlayerData(playerUUID);
                        checkPlayerOriginData(player);
                    }
                }
            }
        }.runTaskAsynchronously(getListenerHandler().getPlugin());
    }

    public void originPickerGui() {
        if (!getListenerHandler().getPlugin().getOriginsInventoryGUI().isEmpty()) {
            for (Inventory inv : getListenerHandler().getPlugin().getOriginsInventoryGUI()) {
                ItemStack previous = new ItemStack(Material.ARROW, 1);
                ItemMeta previousMeta = previous.getItemMeta();
                if (previousMeta != null) {
                    previousMeta.setDisplayName(Message.format("&6Previous Page"));
                    previous.setItemMeta(previousMeta);
                }

                ItemStack close = new ItemStack(Material.BARRIER, 1);
                ItemMeta closeMeta = close.getItemMeta();
                if (closeMeta != null) {
                    closeMeta.setDisplayName(Message.format("&cQuit Game"));
                    close.setItemMeta(closeMeta);
                }

                ItemStack next = new ItemStack(Material.ARROW, 1);
                ItemMeta nextMeta = next.getItemMeta();
                if (nextMeta != null) {
                    nextMeta.setDisplayName(Message.format("&6Next Page"));
                    next.setItemMeta(nextMeta);
                }

                inv.setItem(48, previous);
                inv.setItem(49, close);
                inv.setItem(50, next);
            }
        }
    }

    public void openOriginPickerGui(HumanEntity humanEntity) {
        UUID playerUUID = humanEntity.getUniqueId();

        humanEntity.openInventory(getListenerHandler().getPlugin().getOriginsInventoryGUI().get(0));
        originPickerGUIViewers.put(playerUUID, 0);
    }

    @EventHandler
    private void onOriginPickerGuiClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (getListenerHandler().getPlugin().getOriginsInventoryGUI().contains(event.getInventory())) {
            if (clickedItem != null) {
                if (!clickedItem.getType().isAir()) {
                    if (event.getRawSlot() == 48) {
                        if (getListenerHandler().getPlugin().getOriginsInventoryGUI().indexOf(event.getClickedInventory()) != 0) {
                            player.openInventory(getListenerHandler().getPlugin().getOriginsInventoryGUI().get(getListenerHandler().getPlugin().getOriginsInventoryGUI().indexOf(event.getClickedInventory()) - 1));
                            if (originPickerGUIViewers.containsKey(player.getUniqueId())) {
                                originPickerGUIViewers.put(player.getUniqueId(), originPickerGUIViewers.get(player.getUniqueId()) - 1);
                                player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                    if (event.getRawSlot() == 49) {
                        player.kickPlayer(null);
                    }
                    if (event.getRawSlot() == 50) {
                        if (!getListenerHandler().getPlugin().getOriginsInventoryGUI().get(getListenerHandler().getPlugin().getOriginsInventoryGUI().indexOf(event.getClickedInventory())).equals(getListenerHandler().getPlugin().getOriginsInventoryGUI().get(getListenerHandler().getPlugin().getOriginsInventoryGUI().size() - 1))) {
                            player.openInventory(getListenerHandler().getPlugin().getOriginsInventoryGUI().get(getListenerHandler().getPlugin().getOriginsInventoryGUI().indexOf(event.getClickedInventory()) + 1));
                            if (originPickerGUIViewers.containsKey(player.getUniqueId())) {
                                originPickerGUIViewers.put(player.getUniqueId(), originPickerGUIViewers.get(player.getUniqueId()) + 1);
                                player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1f, 1f);
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                    if (event.getRawSlot() == 22){
                        if (clickedItem.getItemMeta() != null) {
                            getListenerHandler().getPlugin().getOrigins().forEach((key, value) -> {
                                if (key.equals(clickedItem.getItemMeta().getLocalizedName())) {
                                    executeOriginPickerGuiOriginOption(player, key);
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
                                }
                            });
                        }
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    private void executeOriginPickerGuiOriginOption(Player player, String origin) {
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        String playerOrigin = originPlayer.getOrigin();
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();

        if (originsPlayerDataWrapper == null) {
            getListenerHandler().getPlugin().getStorageHandler().getOriginsPlayerData().createOriginsPlayerData(playerUUID, player, origin);
            initiatePlayerOrigin(player);
            originPickerGUIViewers.remove(playerUUID);
            player.closeInventory();
            getListenerHandler().getNoOriginPlayerRestrict().unrestrictPlayerMovement(player);
        } else if (Objects.equals(origin, playerOrigin)) {
            Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_ALREADY_SELECTED
                    .toString()
                    .replace("%player_current_origin%", playerOrigin));
        } else {
            getListenerHandler().getPlugin().getStorageHandler().getOriginsPlayerData().updateOriginsPlayerData(playerUUID, new OriginsPlayerDataWrapper(playerUUID, player.getName(), origin));
            initiatePlayerOrigin(player);
            player.closeInventory();
            Message.sendPlayerMessage(player, Lang.PLAYER_ORIGIN_UPDATE
                    .toString()
                    .replace("%player_selected_origin%", playerOrigin));
        }
    }

    public void closeAllOriginPickerGui() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();

            if (originPickerGUIViewers.containsKey(playerUUID)) {
                player.closeInventory();
            }
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryDragEvent event) {
        if (getListenerHandler().getPlugin().getOriginsInventoryGUI().contains(event.getInventory())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        Player player = (Player) humanEntity;
        UUID playerUUID = player.getUniqueId();
        OriginPlayer originPlayer = new OriginPlayer(player);
        OriginsPlayerDataWrapper originsPlayerDataWrapper = originPlayer.findOriginsPlayerData();
        Inventory inventory = event.getInventory();

        if (originsPlayerDataWrapper == null) {
            if (getListenerHandler().getPlugin().getOriginsInventoryGUI().contains(inventory)) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!player.getOpenInventory().getTopInventory().equals(getListenerHandler().getPlugin().getOriginsInventoryGUI().get(originPickerGUIViewers.get(playerUUID)))) {
                            player.openInventory(getListenerHandler().getPlugin().getOriginsInventoryGUI().get(originPickerGUIViewers.get(playerUUID)));
                            player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 0);
                        }
                    }
                }.runTaskLater(getListenerHandler().getPlugin(), 1L);
            }
        }
    }

    @EventHandler
    private void nullOrigin(AsyncPlayerOriginChangeEvent event) {
        Player player = event.getPlayer();
        String newOrigin = event.getNewOrigin();

        if (newOrigin == null) {
            checkPlayerOriginData(player);
        }
    }

    private void resetPlayer(Player player) {
        GameMode gameMode = player.getGameMode();
        AttributeInstance genericArmor = player.getAttribute(Attribute.GENERIC_ARMOR);
        AttributeInstance genericArmorToughness = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        AttributeInstance genericLuck = player.getAttribute(Attribute.GENERIC_LUCK);
        AttributeInstance genericAttackDamage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        AttributeInstance genericAttackKnockBack = player.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK);
        AttributeInstance genericAttackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        AttributeInstance genericFlyingSpeed = player.getAttribute(Attribute.GENERIC_FLYING_SPEED);
        AttributeInstance genericFollowRange = player.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
        AttributeInstance genericKnockBackResistance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        AttributeInstance genericMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        AttributeInstance genericMovementSpeed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        AttributeInstance horseJumpStrength = player.getAttribute(Attribute.HORSE_JUMP_STRENGTH);
        AttributeInstance zombieSpawnReinforcements = player.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS);

        if (genericArmor != null) {
            genericArmor.setBaseValue(genericArmor.getDefaultValue());
        }
        if (genericArmorToughness != null) {
            genericArmorToughness.setBaseValue(genericArmorToughness.getDefaultValue());
        }
        if (genericLuck != null) {
            genericLuck.setBaseValue(genericLuck.getDefaultValue());
        }
        if (genericAttackDamage != null) {
            genericAttackDamage.setBaseValue(genericAttackDamage.getDefaultValue());
        }
        if (genericAttackKnockBack != null) {
            genericAttackKnockBack.setBaseValue(genericAttackKnockBack.getDefaultValue());
        }
        if (genericAttackSpeed != null) {
            genericAttackSpeed.setBaseValue(genericAttackSpeed.getDefaultValue());
        }
        if (genericFlyingSpeed != null) {
            genericFlyingSpeed.setBaseValue(genericFlyingSpeed.getDefaultValue());
        }
        if (genericFollowRange != null) {
            genericFollowRange.setBaseValue(genericFollowRange.getDefaultValue());
        }
        if (genericKnockBackResistance != null) {
            genericKnockBackResistance.setBaseValue(genericKnockBackResistance.getDefaultValue());
        }
        if (genericMaxHealth != null) {
            genericMaxHealth.setBaseValue(genericMaxHealth.getDefaultValue());
        }
        if (genericMovementSpeed != null) {
            genericMovementSpeed.setBaseValue(genericMovementSpeed.getDefaultValue());
        }
        if (horseJumpStrength != null) {
            horseJumpStrength.setBaseValue(horseJumpStrength.getDefaultValue());
        }
        if (zombieSpawnReinforcements != null) {
            zombieSpawnReinforcements.setBaseValue(zombieSpawnReinforcements.getDefaultValue());
        }
        player.setHealthScale(20);
        player.setInvisible(false);
        player.setGravity(true);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }
}

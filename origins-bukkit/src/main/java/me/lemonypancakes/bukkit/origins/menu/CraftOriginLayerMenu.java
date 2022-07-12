/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
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
package me.lemonypancakes.bukkit.origins.menu;

import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.registry.Registry;
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Lang;
import me.lemonypancakes.bukkit.origins.wrapper.GUITitle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CraftOriginLayerMenu extends CraftPaginatedMenu {

    private final OriginLayer originLayer;
    private final List<Inventory> originsPages = new LinkedList<>();
    private final Set<Player> exemptions = new HashSet<>();

    public CraftOriginLayerMenu(OriginsBukkitPlugin plugin, OriginLayer originLayer) {
        super(plugin);
        this.originLayer = originLayer;
        List<Origin> origins = originLayer.getOrigins();
        GUITitle guiTitle = originLayer.getGuiTitle();


        if (origins != null) {
            origins.forEach(origin -> {
                if (origin != null) {
                    Inventory originInventory = createOriginInventory(guiTitle, origin);

                    pages.add(originInventory);
                    originsPages.add(originInventory);
                    List<Power> powers = origin.getPowers();

                    if (powers != null) {
                        powers.forEach(power -> {
                            Inventory inventory = createPowerInventory(guiTitle, origin, power);

                            if (inventory != null) {
                                pages.add(inventory);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            OriginPlayer originPlayer = getPlugin().getOriginPlayer(player);

            if (originPlayer != null) {
                if (originLayer != null) {
                    if (originLayer.isEnabled()) {
                        Inventory inventory = event.getInventory();
                        ItemStack itemStack = event.getCurrentItem();

                        if (itemStack != null) {
                            if (isAOriginButton(itemStack)) {
                                Origin originFromOriginButton = getOriginFromOriginButton(itemStack);

                                if (originFromOriginButton != null) {
                                    /*new BukkitRunnable() {
                                        final int random = new Random().nextInt(128);
                                        int counter;

                                        @Override
                                        public void run() {
                                            if (!player.isOnline()) {
                                                cancel();
                                            }
                                            InventoryView inventoryView = player.getOpenInventory();
                                            Inventory topInventory = inventoryView.getTopInventory();

                                            if (pages.contains(topInventory)) {
                                                if (counter >= random) {
                                                    ItemStack itemStack = topInventory.getItem(22);

                                                    if (itemStack != null) {
                                                        Origin origin = getOriginFromOriginButton(itemStack);

                                                        if (origin != null) {
                                                            originPlayer.setOrigin(originLayer, origin);
                                                            player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2f, 1f);
                                                            exemptions.add(player);
                                                            player.closeInventory();
                                                            exemptions.remove(player);
                                                            cancel();
                                                        }
                                                    }
                                                } else {
                                                    boolean isSuccess = nextOrigin(player, topInventory);

                                                    if (!isSuccess) {
                                                        nextOrigin(player, pages.get(0));
                                                    }
                                                    player.playSound(player.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 2f, 1f);
                                                }
                                            }
                                            counter++;
                                        }
                                    }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 2L);*/
                                    originPlayer.setOrigin(originLayer, originFromOriginButton);
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
                                    exemptions.add(player);
                                    player.closeInventory();
                                    exemptions.remove(player);
                                }
                            } else if (isAQuitGameButton(itemStack)) {
                                player.kickPlayer(null);
                            } else {
                                boolean success = turnPage(player, inventory, itemStack);
                                Location location = player.getLocation();

                                if (success) {
                                    player.playSound(location, Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 2f, 1f);
                                } else {
                                    player.playSound(location, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 2f, 0f);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            OriginPlayer originPlayer = getPlugin().getOriginPlayer(player);

            if (originPlayer != null) {
                Origin origin = originPlayer.getOrigin(originLayer);

                if (origin == null) {
                    Inventory inventory = event.getInventory();

                    if (!exemptions.contains(player)) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                player.openInventory(inventory);
                            }
                        }.runTask(getPlugin().getJavaPlugin());
                    }
                }
            }
        }
    }

    private boolean turnPage(Player player, Inventory inventory, ItemStack itemStack) {
        if (pages.contains(inventory)) {
            if (isAPreviousPageButton(itemStack)) {
                return previousPage(player, inventory);
            } else if (isAPreviousOriginButton(itemStack)) {
                return previousOrigin(player, inventory);
            } else if (isANextPageButton(itemStack)) {
                return nextPage(player, inventory);
            } else if (isANextOriginButton(itemStack)) {
                return nextOrigin(player, inventory);
            }
        }
        return false;
    }

    private boolean previousPage(Player player, Inventory inventory) {
        int indexOfInventory = pages.indexOf(inventory);

        indexOfInventory--;
        if (indexOfInventory >= 0) {
            Inventory page = pages.get(indexOfInventory);

            if (page != null) {
                exemptions.add(player);
                player.openInventory(page);
                exemptions.remove(player);

                return true;
            }
        }
        return false;
    }

    private boolean previousOrigin(Player player, Inventory inventory) {
        int indexOfInventory = pages.indexOf(inventory);

        indexOfInventory--;
        if (indexOfInventory >= 0) {
            if (!originsPages.contains(pages.get(indexOfInventory))) {
                for (int i = indexOfInventory - 1; i >= 0; i--) {
                    if (originsPages.contains(pages.get(i))) {
                        indexOfInventory = i;
                        break;
                    }
                }
            }
            Inventory page = pages.get(indexOfInventory);

            if (page != null) {
                exemptions.add(player);
                player.openInventory(page);
                exemptions.remove(player);

                return true;
            }
        }
        return false;
    }

    private boolean nextPage(Player player, Inventory inventory) {
        int indexOfInventory = pages.indexOf(inventory);

        indexOfInventory++;
        if (indexOfInventory < pages.size()) {
            Inventory page = pages.get(indexOfInventory);

            if (page != null) {
                exemptions.add(player);
                player.openInventory(page);
                exemptions.remove(player);

                return true;
            }
        }
        return false;
    }

    private boolean nextOrigin(Player player, Inventory inventory) {
        int indexOfInventory = pages.indexOf(inventory);

        indexOfInventory++;
        if (indexOfInventory < pages.size()) {
            if (!originsPages.contains(pages.get(indexOfInventory))) {
                for (int i = indexOfInventory; i <= pages.size(); i++) {
                    if (originsPages.contains(pages.get(i))) {
                        indexOfInventory = i;
                        break;
                    }
                }
            }
            Inventory page = pages.get(indexOfInventory);

            if (page != null) {
                exemptions.add(player);
                player.openInventory(page);
                exemptions.remove(player);

                return true;
            }
        }
        return false;
    }

    private void setAsAPreviousOriginButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            BukkitPersistentDataUtils.setPersistentData(itemMeta, "origins-bukkit:origin_layer_menu", PersistentDataType.STRING, "origins-bukkit:previous_origin");
            itemStack.setItemMeta(itemMeta);
        }
    }

    private boolean isAPreviousOriginButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String string = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:origin_layer_menu", PersistentDataType.STRING);

            if (string != null) {
                return string.equals("origins-bukkit:previous_origin");
            }
        }
        return false;
    }

    private void setAsANextOriginButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            BukkitPersistentDataUtils.setPersistentData(itemMeta, "origins-bukkit:origin_layer_menu", PersistentDataType.STRING, "origins-bukkit:next_origin");
            itemStack.setItemMeta(itemMeta);
        }
    }

    private boolean isANextOriginButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String string = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:origin_layer_menu", PersistentDataType.STRING);

            if (string != null) {
                return string.equals("origins-bukkit:next_origin");
            }
        }
        return false;
    }

    private void setAsAOriginButton(Origin origin, ItemStack itemStack) {
        if (origin != null && itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                Identifier identifier = origin.getIdentifier();

                if (identifier != null) {
                    BukkitPersistentDataUtils.setPersistentData(itemMeta, "origins-bukkit:origin_layer_menu_origin_button", PersistentDataType.STRING, identifier.toString());
                    itemStack.setItemMeta(itemMeta);
                }
            }
        }
    }

    private boolean isAOriginButton(ItemStack itemStack) {
        return getOriginFromOriginButton(itemStack) != null;
    }

    private void setAsAQuitGameButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            BukkitPersistentDataUtils.setPersistentData(itemMeta, "origins-bukkit:origin_layer_menu", PersistentDataType.STRING, "origins-bukkit:quit_game");
            itemStack.setItemMeta(itemMeta);
        }
    }

    private boolean isAQuitGameButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String string = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:origin_layer_menu", PersistentDataType.STRING);

            if (string != null) {
                return string.equals("origins-bukkit:quit_game");
            }
        }
        return false;
    }

    private Origin getOriginFromOriginButton(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            String string = BukkitPersistentDataUtils.getPersistentData(itemMeta, "origins-bukkit:origin_layer_menu_origin_button", PersistentDataType.STRING);

            if (string != null) {
                if (string.contains(":")) {
                    Identifier identifier = Identifier.fromString(string);

                    if (identifier != null) {
                        Registry registry = getPlugin().getRegistry();

                        if (registry != null) {
                            return registry.getRegisteredOrigin(identifier);
                        }
                    }
                }
            }
        }
        return null;
    }

    private Inventory createOriginLayerInventory(GUITitle guiTitle) {
        Inventory inventory = Bukkit.createInventory(this, 54, guiTitle != null && guiTitle.getChooseOrigin() != null ? guiTitle.getChooseOrigin() : "");
        ItemStack previous = new ItemStack(Material.ARROW, 1);
        ItemMeta previousMeta = previous.getItemMeta();

        if (previousMeta != null) {
            previousMeta.setDisplayName(Lang.GUI_ICON_TEXT_PREVIOUS_PAGE.toString());
            previous.setItemMeta(previousMeta);
        }
        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();

        if (closeMeta != null) {
            closeMeta.setDisplayName(Lang.GUI_ICON_TEXT_QUIT_GAME.toString());
            close.setItemMeta(closeMeta);
        }
        ItemStack next = new ItemStack(Material.ARROW, 1);
        ItemMeta nextMeta = next.getItemMeta();

        if (nextMeta != null) {
            nextMeta.setDisplayName(Lang.GUI_ICON_TEXT_NEXT_PAGE.toString());
            next.setItemMeta(nextMeta);
        }

        setAsAPreviousPageButton(previous);
        setAsAQuitGameButton(close);
        setAsANextPageButton(next);
        inventory.setItem(46, previous);
        inventory.setItem(49, close);
        inventory.setItem(52, next);
        return inventory;
    }

    private Inventory createOriginInventory(GUITitle guiTitle, Origin origin) {
        Inventory inventory = createOriginLayerInventory(guiTitle);
        ItemStack itemStack = new ItemStack(origin.getIcon() != null ? origin.getIcon() : new ItemStack(Material.BEDROCK));
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setDisplayName("§f" + origin.getName());
            itemMeta.setLore(Arrays.asList(origin.getDescription()));
            itemStack.setItemMeta(itemMeta);
        }
        ItemStack previous = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta previousMeta = previous.getItemMeta();

        if (previousMeta != null) {
            previousMeta.setDisplayName("§fPrevious Origin");
            previous.setItemMeta(previousMeta);
        }
        ItemStack next = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta nextMeta = next.getItemMeta();

        if (nextMeta != null) {
            nextMeta.setDisplayName("§fNext Origin");
            next.setItemMeta(nextMeta);
        }
        setAsAPreviousOriginButton(previous);
        setAsANextOriginButton(next);
        setAsAOriginButton(origin, itemStack);
        inventory.setItem(22, itemStack);
        inventory.setItem(48, previous);
        inventory.setItem(50, next);
        return inventory;
    }

    private Inventory createPowerInventory(GUITitle guiTitle, Origin origin, Power power) {
        String powerName = power.getName();
        String[] powerDescription = power.getDescription();

        if (powerName != null && !powerName.isEmpty() && powerDescription != null && powerDescription.length != 0) {
            Inventory inventory = createOriginInventory(guiTitle, origin);
            ItemStack itemStack = inventory.getItem(22);

            if (itemStack != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta != null) {
                    List<String> lore = new ArrayList<>(Arrays.asList(origin.getDescription()));

                    lore.add("§f§n" + powerName);
                    lore.addAll(Arrays.asList(powerDescription));
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    return inventory;
                }
            }
        }
        return null;
    }
}

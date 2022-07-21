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
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.wrapper.GUITitle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CraftOriginPlayerInfoMenu extends CraftPaginatedMenu {

    protected final OriginPlayer originPlayer;
    protected final Map<OriginLayer, List<Inventory>> origins = new LinkedHashMap<>();
    protected final List<Inventory> originsPages = new LinkedList<>();

    public CraftOriginPlayerInfoMenu(OriginsBukkitPlugin plugin, OriginPlayer originPlayer) {
        super(plugin);
        this.originPlayer = originPlayer;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            Inventory inventory = event.getInventory();
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack != null) {
                Location location = player.getLocation();
                boolean success = turnPage(player, inventory, itemStack);

                if (success) {
                    player.playSound(location, Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 2f, 1f);
                } else {
                    player.playSound(location, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 2f, 0f);
                }
            }
        }
    }

    @Override
    public void open(Player player) {
        if (!pages.isEmpty()) {
            Inventory inventory = pages.get(0);

            if (inventory != null) {
                player.openInventory(inventory);
            }
        }
    }

    public void addOrigin(OriginLayer originLayer, Origin origin) {
        if (originLayer != null) {
            if (origin != null) {
                if (origins.get(originLayer) != null) {
                    removeOrigin(originLayer);
                }
                GUITitle guiTitle = originLayer.getGuiTitle();
                Inventory originInventory = createOriginInventory(guiTitle, origin);
                List<Inventory> inventories = new LinkedList<>();

                pages.add(originInventory);
                originsPages.add(originInventory);
                inventories.add(originInventory);
                List<Power> powers = origin.getPowers();

                if (powers != null) {
                    powers.forEach(power -> {
                        Inventory inventory = createPowerInventory(guiTitle, originInventory, origin, power);

                        if (inventory != null) {
                            if (inventory != originInventory) {
                                pages.add(inventory);
                                inventories.add(inventory);
                            }
                        }
                    });
                }
                origins.put(originLayer, inventories);
            } else {
                removeOrigin(originLayer);
            }
        }
    }

    public void removeOrigin(OriginLayer originLayer) {
        List<Inventory> inventories = origins.get(originLayer);

        if (inventories != null) {
            inventories.forEach(inventory -> {
                pages.remove(inventory);
                originsPages.remove(inventory);
                Bukkit.getOnlinePlayers().forEach(player -> {
                    InventoryView inventoryView = player.getOpenInventory();
                    Inventory topInventory = inventoryView.getTopInventory();
                    InventoryHolder inventoryHolder = topInventory.getHolder();

                    if (inventoryHolder != null) {
                        if (inventoryHolder instanceof CraftOriginLayerMenu) {
                            return;
                        }
                    }
                    if (topInventory == inventory) {
                        player.closeInventory();
                    }
                });
            });
        }
        origins.remove(originLayer);
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

    private Inventory createOriginLayerInventory(GUITitle guiTitle) {
        Inventory inventory = Bukkit.createInventory(this, 54, guiTitle != null && guiTitle.getViewOrigin() != null ? guiTitle.getViewOrigin() : "");
        ItemStack previous = new ItemStack(Material.ARROW, 1);
        ItemMeta previousMeta = previous.getItemMeta();

        if (previousMeta != null) {
            previousMeta.setDisplayName(ChatUtils.format("&fPrevious Page"));
            previous.setItemMeta(previousMeta);
        }
        ItemStack next = new ItemStack(Material.ARROW, 1);
        ItemMeta nextMeta = next.getItemMeta();

        if (nextMeta != null) {
            nextMeta.setDisplayName(ChatUtils.format("&fNext Page"));
            next.setItemMeta(nextMeta);
        }

        setAsAPreviousPageButton(previous);
        setAsANextPageButton(next);
        inventory.setItem(46, previous);
        inventory.setItem(52, next);
        return inventory;
    }

    private Inventory createOriginInventory(GUITitle guiTitle, Origin origin) {
        Inventory inventory = createOriginLayerInventory(guiTitle);
        ItemStack itemStack = new ItemStack(origin.getIcon() != null ? origin.getIcon() : new ItemStack(Material.BEDROCK));
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setDisplayName(ChatUtils.format("&f" + origin.getName()));
            itemMeta.setLore(Arrays.asList(ChatUtils.format(origin.getDescription())));
            itemStack.setItemMeta(itemMeta);
        }
        ItemStack previous = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta previousMeta = previous.getItemMeta();

        if (previousMeta != null) {
            previousMeta.setDisplayName(ChatUtils.format("&fPrevious Origin"));
            previous.setItemMeta(previousMeta);
        }
        ItemStack next = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta nextMeta = next.getItemMeta();

        if (nextMeta != null) {
            nextMeta.setDisplayName(ChatUtils.format("&fNext Origin"));
            next.setItemMeta(nextMeta);
        }
        setAsAPreviousOriginButton(previous);
        setAsANextOriginButton(next);
        ItemStack map = new ItemStack(Material.MAP);
        ItemMeta mapMeta = map.getItemMeta();

        if (mapMeta != null) {
            mapMeta.setDisplayName(" ");
            map.setItemMeta(mapMeta);
        }
        for (int i = 20; i < 25; i++) {
            inventory.setItem(i, map);
        }
        for (int i = 29; i < 34; i++) {
            inventory.setItem(i, map);
        }
        inventory.setItem(13, itemStack);
        inventory.setItem(48, previous);
        inventory.setItem(50, next);
        generateImpact(inventory, origin);
        return inventory;
    }

    private Inventory createPowerInventory(GUITitle guiTitle, Inventory inventory, Origin origin, Power power) {
        String powerName = power.getName();
        String[] powerDescription = power.getDescription();

        if (powerName != null && !powerName.isEmpty() && powerDescription != null && powerDescription.length != 0) {
            ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                List<String> lore = new ArrayList<>(Arrays.asList(ChatUtils.format(powerDescription)));

                itemMeta.setDisplayName(ChatUtils.format(powerName));
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
            }
            boolean isFull = false;

            for (int i = 29; i < 34; i++) {
                boolean a = false;

                for (int h = 20; h < 25; h++) {
                    ItemStack item = inventory.getItem(h);

                    if (item != null) {
                        if (item.getType() == Material.MAP) {
                            inventory.setItem(h, itemStack);
                            a = true;
                            break;
                        }
                    }
                }
                if (a) {
                    break;
                } else {
                    ItemStack item = inventory.getItem(i);

                    if (item != null) {
                        if (item.getType() == Material.MAP) {
                            inventory.setItem(i, itemStack);
                            break;
                        }
                    }
                }
                if (i == 33) {
                    ItemStack item = inventory.getItem(i);

                    if (item != null) {
                        if (item.getType() == Material.FILLED_MAP) {
                            isFull = true;
                            break;
                        }
                    }
                }
            }
            if (isFull) {
                inventory = createPowerInventory(guiTitle, createOriginInventory(guiTitle, origin), origin, power);
            }
            return inventory;
        }
        return null;
    }

    public void generateImpact(Inventory inventory, Origin origin) {
        switch (origin.getImpact()) {
            case NONE:
                ItemStack none = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                ItemMeta noneMeta = none.getItemMeta();
                if (noneMeta != null) {
                    noneMeta.setDisplayName(ChatUtils.format("&fImpact: &7None"));
                    none.setItemMeta(noneMeta);
                }
                inventory.setItem(6, none);
                inventory.setItem(7, none);
                inventory.setItem(8, none);
                break;
            case LOW:
                ItemStack low = new ItemStack(Material.LIME_CONCRETE, 1);
                ItemMeta lowMeta = low.getItemMeta();
                if (lowMeta != null) {
                    lowMeta.setDisplayName(ChatUtils.format("&fImpact: &aLow"));
                    low.setItemMeta(lowMeta);
                }
                ItemStack low1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                ItemMeta lowMeta1 = low1.getItemMeta();
                if (lowMeta1 != null) {
                    lowMeta1.setDisplayName(ChatUtils.format("&fImpact: &aLow"));
                    low1.setItemMeta(lowMeta);
                }
                inventory.setItem(6, low);
                inventory.setItem(7, low1);
                inventory.setItem(8, low1);
                break;
            case MEDIUM:
                ItemStack medium = new ItemStack(Material.YELLOW_CONCRETE, 1);
                ItemMeta mediumMeta = medium.getItemMeta();
                if (mediumMeta != null) {
                    mediumMeta.setDisplayName(ChatUtils.format("&fImpact: &eMedium"));
                    medium.setItemMeta(mediumMeta);
                }
                ItemStack medium1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                ItemMeta mediumMeta1 = medium1.getItemMeta();
                if (mediumMeta1 != null) {
                    mediumMeta1.setDisplayName(ChatUtils.format("&fImpact: &eMedium"));
                    medium1.setItemMeta(mediumMeta);
                }
                inventory.setItem(6, medium);
                inventory.setItem(7, medium);
                inventory.setItem(8, medium1);
                break;
            case HIGH:
                ItemStack high = new ItemStack(Material.RED_CONCRETE, 1);
                ItemMeta highMeta = high.getItemMeta();
                if (highMeta != null) {
                    highMeta.setDisplayName(ChatUtils.format("&fImpact: &cHigh"));
                    high.setItemMeta(highMeta);
                }
                inventory.setItem(6, high);
                inventory.setItem(7, high);
                inventory.setItem(8, high);
                break;
        }
    }
}

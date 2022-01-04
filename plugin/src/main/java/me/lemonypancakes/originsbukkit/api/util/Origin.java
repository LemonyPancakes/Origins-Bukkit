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
package me.lemonypancakes.originsbukkit.api.util;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Origin implements OriginInterface {

    private final double maxHealth;
    private final float walkSpeed;
    private final float flySpeed;

    public Origin(double maxHealth, float walkSpeed, float flySpeed) {
        this.maxHealth = maxHealth;
        this.walkSpeed = walkSpeed;
        this.flySpeed = flySpeed;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public void registerOrigin(Origin origin) {
        String originIdentifier = getOriginIdentifier();
        Impact originImpact = getImpact();
        String originAuthor = getAuthor();
        String originTitle = getOriginTitle();
        String[] originDescription = getOriginDescription();
        Material originIcon = getOriginIcon();

        if (originIdentifier != null && !originIdentifier.isEmpty()) {
            if (!containsSpecialChars(originIdentifier)) {
                if (originAuthor != null && !originAuthor.isEmpty()) {
                    if (!containsSpecialChars(originAuthor)) {
                        if (originIcon != null) {
                            if (originIcon != Material.AIR) {
                                if (originImpact != null) {
                                    if (!OriginsBukkit.getPlugin().getOrigins().containsKey(originIdentifier)) {
                                        OriginsBukkit.getPlugin().getOrigins().put(originIdentifier, origin);
                                        OriginsBukkit.getPlugin().getOriginsList().add(originIdentifier);
                                        Inventory inventory = Bukkit.createInventory(null, 54, Message.format("&0Choose your Origin."));
                                        inventory.setItem(22, createGuiItem(originIcon, 1,
                                                originTitle,
                                                originDescription));
                                        switch (originImpact) {
                                            case NONE:
                                                ItemStack none = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                                                ItemMeta noneMeta = none.getItemMeta();
                                                if (noneMeta != null) {
                                                    noneMeta.setDisplayName(Message.format("&fImpact: &7None"));
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
                                                    lowMeta.setDisplayName(Message.format("&fImpact: &aLow"));
                                                    low.setItemMeta(lowMeta);
                                                }
                                                ItemStack low1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                                                ItemMeta lowMeta1 = low1.getItemMeta();
                                                if (lowMeta1 != null) {
                                                    lowMeta1.setDisplayName(Message.format("&fImpact: &aLow"));
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
                                                    mediumMeta.setDisplayName(Message.format("&fImpact: &eMedium"));
                                                    medium.setItemMeta(mediumMeta);
                                                }
                                                ItemStack medium1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                                                ItemMeta mediumMeta1 = medium1.getItemMeta();
                                                if (mediumMeta1 != null) {
                                                    mediumMeta1.setDisplayName(Message.format("&fImpact: &eMedium"));
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
                                                    highMeta.setDisplayName(Message.format("&fImpact: &cHigh"));
                                                    high.setItemMeta(highMeta);
                                                }
                                                inventory.setItem(6, high);
                                                inventory.setItem(7, high);
                                                inventory.setItem(8, high);
                                                break;
                                        }
                                        OriginsBukkit.getPlugin().getOriginsInventoryGUI().add(inventory);
                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {
                                                if (OriginsBukkit.getPlugin().getListenerHandler().getPlayerOriginChecker() != null) {
                                                    OriginsBukkit.getPlugin().getListenerHandler().getPlayerOriginChecker().originPickerGui();
                                                }
                                            }
                                        }.runTaskAsynchronously(OriginsBukkit.getPlugin());
                                        Message.sendConsoleMessage("&a[Origins-Bukkit] Successfully registered &6" + originIdentifier + "&a origin by &e" + originAuthor);
                                    } else {
                                        Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The origin &e\"" + originIdentifier + "\"&c has already been registered. Ignoring it... Please restart the server if you've updated or made changes to the extension for changes to take full effect.");
                                    }
                                } else {
                                    Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The impact of the origin &e\"" + originIdentifier + "\"&c cannot be null. Please contact the author (" + originAuthor + ") of this origin.");
                                }
                            } else {
                                Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The icon of the origin &e\"" + originIdentifier + "\"&c cannot be set to air. Please contact the author (" + originAuthor + ") of this origin.");
                            }
                        } else {
                            Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The icon of the origin &e\"" + originIdentifier + "\"&c cannot be null. Please contact the author (" + originAuthor + ") of this origin.");
                        }
                    } else {
                        Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The author of the origin &e\"" + originIdentifier + "\"&c contains character(s) that are not allowed.");
                    }
                } else {
                    Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The author of the origin &e\"" + originIdentifier + "\"&c cannot be null.");
                }
            } else {
                Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The unique identifier of the origin &e\"" + originIdentifier + "\"&c contains character(s) that are not allowed. Please contact the author (" + originAuthor + ") of this origin.");
            }
        } else {
            Message.sendConsoleMessage("&c[Origins-Bukkit] Error registering origin. The unique identifier of the origin &e\"" + originIdentifier + "\"&c cannot be null. Please contact the author (" + originAuthor + ") of this origin.");
        }
    }

    ItemStack createGuiItem(Material material, Integer amount, String itemName, String... itemLore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(itemName);
            itemMeta.setLocalizedName(getOriginIdentifier());
            itemMeta.setLore(Arrays.asList(itemLore));
            if (isOriginIconGlowing()) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }

    private boolean containsSpecialChars(String toExamine) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }
}

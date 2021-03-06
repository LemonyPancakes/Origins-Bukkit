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
package me.lemonypancakes.bukkit.origins.factory.power.regular;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.factory.power.CraftActivePower;
import me.lemonypancakes.bukkit.origins.util.BukkitPersistentDataUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class CraftInventoryPower extends CraftActivePower {

    private String title;
    private InventoryType containerType = InventoryType.DROPPER;
    private String metadataIdentifierValue = "shulker_inventory";
    private final Set<Player> players = new HashSet<>();

    public CraftInventoryPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("title")) {
            this.title = jsonObject.get("title").getAsString();
        }
        if (jsonObject.has("container_type")) {
            this.containerType = InventoryType.valueOf(jsonObject.get("container_type").getAsString());
        }
        if (jsonObject.has("metadata_identifier_value")) {
            this.metadataIdentifierValue = jsonObject.get("metadata_identifier_value").getAsString();
        }
    }

    @Override
    protected void onMemberRemove(Player player) {
        if (players.contains(player)) {
            storeItems(Arrays.asList(player.getOpenInventory().getTopInventory().getContents()), player);
            player.closeInventory();
        }
    }

    @Override
    protected void onUse(Player player, Key key) {
        Inventory inventory = Bukkit.createInventory(player, containerType, title);

        inventory.setContents(getItems(player).toArray(new ItemStack[0]));
        player.openInventory(inventory);
        players.add(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();

        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;

            if (players.contains(player)) {
                storeItems(Arrays.asList(event.getInventory().getContents()), player);
                players.remove(player);
            }
        }
    }

    public void storeItems(List<ItemStack> items, Player player) {
        if (items.size() == 0) {
            BukkitPersistentDataUtils.setPersistentData(player, new Identifier(Identifier.ORIGINS_BUKKIT, metadataIdentifierValue), PersistentDataType.STRING, "");
        } else {
            try{
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

                os.writeInt(items.size());

                for (ItemStack itemStack : items) {
                    os.writeObject(itemStack);
                }

                os.flush();
                byte[] rawData = io.toByteArray();
                String encodedData = Base64.getEncoder().encodeToString(rawData);

                BukkitPersistentDataUtils.setPersistentData(player, new Identifier(Identifier.ORIGINS_BUKKIT, metadataIdentifierValue), PersistentDataType.STRING, encodedData);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<ItemStack> getItems(Player player) {
        List<ItemStack> items = new ArrayList<>();
        String encodedItems = BukkitPersistentDataUtils.getPersistentData(player, new Identifier(Identifier.ORIGINS_BUKKIT, metadataIdentifierValue), PersistentDataType.STRING);

        if (encodedItems != null) {
            if (!encodedItems.isEmpty()) {
                byte[] rawData = Base64.getDecoder().decode(encodedItems);

                try{
                    ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                    BukkitObjectInputStream in = new BukkitObjectInputStream(io);

                    int itemsCount = in.readInt();

                    for (int i = 0; i < itemsCount; i++){
                        items.add((ItemStack) in.readObject());
                    }
                    in.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        return items;
    }
}

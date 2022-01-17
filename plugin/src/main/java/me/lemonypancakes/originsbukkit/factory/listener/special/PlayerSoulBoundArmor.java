package me.lemonypancakes.originsbukkit.factory.listener.special;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.ListenerPowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@SuppressWarnings("unused")
public class PlayerSoulBoundArmor extends ListenerPowerContainer {

    private ArmorSlot armorSlot;
    private ItemStack itemStack;
    private Integer amount;

    public PlayerSoulBoundArmor(Identifier identifier,
                                JsonObject jsonObject,
                                Action<?>[] actions,
                                Condition<?> condition,
                                boolean isFactory) {
        super(identifier, jsonObject, actions, condition, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                JsonObject fields = jsonObject.getAsJsonObject("fields");

                if (fields != null) {
                    if (fields.has("armor_slot")) {
                        this.armorSlot = ArmorSlot.valueOf(
                                fields.get(
                                        "armor_slot"
                                ).getAsString()
                        );
                    }
                    if (fields.has("item_stack")) {
                        JsonObject item
                                = fields.getAsJsonObject("item_stack");

                        if (item != null) {
                            ItemStack itemStack = new ItemStack(Material.STONE);

                            if (item.has("material")) {
                                Material material = new Gson().fromJson(
                                        item.get("material"),
                                        Material.class
                                );

                                if (material != null) {
                                    itemStack.setType(material);
                                }
                            }
                            if (item.has("amount")) {
                                int amount = item.get("amount").getAsInt();

                                itemStack.setAmount(amount);
                                this.amount = amount;
                            }
                            ItemMeta itemMeta = itemStack.getItemMeta();

                            if (itemMeta != null) {
                                if (item.has("meta")) {
                                    JsonObject itemStackMeta
                                            = item.getAsJsonObject("meta");

                                    if (itemStackMeta != null) {
                                        if (itemStackMeta.has("item_flags")) {
                                            ItemFlag[] itemFlags = new Gson().fromJson(
                                                    itemStackMeta.get("item_flags"),
                                                    ItemFlag[].class
                                            );

                                            if (itemFlags != null) {
                                                itemMeta.addItemFlags(itemFlags);
                                            }
                                        }
                                        if (itemStackMeta.has("display_name")) {
                                            String originIconDisplayName
                                                    = itemStackMeta.get("display_name").getAsString();

                                            if (originIconDisplayName != null) {
                                                itemMeta.setDisplayName(
                                                        Message.format(
                                                                originIconDisplayName
                                                        )
                                                );
                                            }
                                        }
                                        if (itemStackMeta.has("description")) {
                                            String[] originIconDescription = new Gson().fromJson(
                                                    itemStackMeta.get("description"),
                                                    String[].class
                                            );

                                            if (originIconDescription != null) {
                                                itemMeta.setLore(
                                                        Arrays.asList(
                                                                originIconDescription
                                                        )
                                                );
                                            }
                                        }
                                        if (itemStackMeta.has("custom_model_data")) {
                                            int customModelData
                                                    = itemStackMeta.get("custom_model_data").getAsInt();

                                            itemMeta.setCustomModelData(customModelData);
                                        }
                                    }
                                }
                                itemMeta.setLocalizedName("soulbound");
                                itemStack.setItemMeta(itemMeta);
                            }
                            this.itemStack = itemStack;
                        }
                    }
                }
            }
        }
    }

    public PlayerSoulBoundArmor(Identifier identifier,
                                JsonObject jsonObject,
                                Action<?>[] actions,
                                boolean isFactory) {
        this(identifier, jsonObject, actions, null, isFactory);
    }

    public PlayerSoulBoundArmor(Identifier identifier,
                                JsonObject jsonObject,
                                boolean isFactory) {
        this(identifier, jsonObject, null, isFactory);
    }

    public PlayerSoulBoundArmor(Identifier identifier,
                                boolean isFactory) {
        this(identifier, null, isFactory);
    }

    public PlayerSoulBoundArmor(boolean isFactory) {
        this(null, isFactory);
    }

    public PlayerSoulBoundArmor() {
        this(false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions,
                             Condition<?> condition) {
        return new PlayerSoulBoundArmor(identifier, jsonObject, actions, condition, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject,
                             Action<?>[] actions) {
        return new PlayerSoulBoundArmor(identifier, jsonObject, actions, false);
    }

    @Override
    public Power newInstance(Identifier identifier,
                             JsonObject jsonObject) {
        return new PlayerSoulBoundArmor(identifier, jsonObject, false);
    }

    @Override
    public Power newInstance(Identifier identifier) {
        return new PlayerSoulBoundArmor(identifier, false);
    }

    @Override
    public Power newInstance() {
        return new PlayerSoulBoundArmor();
    }

    @Override
    public <T> void onInvoke(T t) {
        if (t instanceof Temp) {
            Temp temp = (Temp) t;
            Player player = temp.getPlayer();
            World world = player.getWorld();
            PlayerInventory playerInventory = player.getInventory();
            ItemStack prevArmor = playerInventory.getItem(armorSlot.getSlot());

            if (prevArmor != null && !prevArmor.isSimilar(itemStack)) {
                if (playerInventory.firstEmpty() == -1) {
                    world.dropItem(player.getLocation(), prevArmor);
                } else {
                    playerInventory.addItem(prevArmor);
                }
            }
            playerInventory.setItem(armorSlot.getSlot(), itemStack);
        }
    }

    public static Power getFactory() {
        return new PlayerSoulBoundArmor(
                new IdentifierContainer(
                        OriginsBukkit.KEY, "listener/special/soulbound_armor"
                ), true
        );
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (getPlayersToListen().contains(player)) {
            Inventory inventory = event.getClickedInventory();

            if (player.getInventory().equals(inventory)) {
                if (event.getSlot() == armorSlot.getSlot()) {
                    ItemStack clickedItem = event.getCurrentItem();

                    if (clickedItem != null) {
                        if (clickedItem.isSimilar(itemStack)) {
                            if (amount != null) {
                                if (amount == clickedItem.getAmount()) {
                                    event.setCancelled(true);
                                }
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private enum ArmorSlot {
        HELMET(39),
        CHESTPLATE(38),
        LEGGINGS(37),
        BOOTS(36);

        private final int slot;

        ArmorSlot(int slot) {
            this.slot = slot;
        }

        public int getSlot() {
            return slot;
        }
    }
}

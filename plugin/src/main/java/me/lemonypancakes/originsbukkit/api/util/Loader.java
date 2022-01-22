package me.lemonypancakes.originsbukkit.api.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.OriginContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.storage.Powers;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Loader {

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Powers POWERS = PLUGIN.getStorageHandler().getPowers();

    public static Origin loadOriginFromFile(File originFile, String identifierKey) {
        return loadOriginFromFile(
                originFile,
                new IdentifierContainer(
                        identifierKey,
                        FilenameUtils.getBaseName(
                                originFile.getName()
                        )
                )
        );
    }

    public static Origin loadOriginFromFile(File originFile) {
        return loadOriginFromFile(
                originFile,
                new IdentifierContainer(
                        "undefined",
                        FilenameUtils.getBaseName(
                                originFile.getName()
                        )
                )
        );
    }

    private static Origin loadOriginFromFile(File originFile, Identifier identifier) {
        Origin origin = new OriginContainer(identifier);

        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(originFile);
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            if (jsonObject.has("origin")) {
                JsonObject originSection = jsonObject.getAsJsonObject("origin");

                if (originSection != null) {

                    origin.setJsonObject(originSection);
                    if (originSection.has("display_name")) {
                        String displayName = originSection.get("display_name").getAsString();

                        if (displayName != null) {
                            origin.setDisplayName(ChatUtils.format(displayName));
                        }
                    }
                    if (originSection.has("description")) {
                        String[] description = gson.fromJson(
                                originSection.get("description"),
                                String[].class
                        );

                        if (description != null) {
                            origin.setDescription(ChatUtils.formatList(description));
                        }
                    }
                    if (originSection.has("impact")) {
                        Impact impact = gson.fromJson(
                                originSection.get("impact"),
                                Impact.class
                        );

                        if (impact != null) {
                            origin.setImpact(impact);
                        }
                    }
                    if (originSection.has("icon")) {
                        JsonObject originIconSection
                                = originSection.getAsJsonObject("icon");

                        if (originIconSection != null) {
                            ItemStack originIcon = new ItemStack(Material.STONE);

                            if (originIconSection.has("material")) {
                                Material material = gson.fromJson(
                                        originIconSection.get("material"),
                                        Material.class
                                );

                                if (material != null) {
                                    originIcon.setType(material);
                                }
                            }
                            if (originIconSection.has("amount")) {
                                int amount = originIconSection.get("amount").getAsInt();

                                originIcon.setAmount(amount);
                            } else {
                                originIcon.setAmount(1);
                            }
                            ItemMeta itemMeta = originIcon.getItemMeta();

                            if (itemMeta != null) {
                                if (originIconSection.has("meta")) {
                                    JsonObject originIconMeta
                                            = originIconSection.getAsJsonObject("meta");

                                    if (originIconMeta != null) {
                                        if (originIconMeta.has("item_flags")) {
                                            ItemFlag[] itemFlags = gson.fromJson(
                                                    originIconMeta.get("item_flags"),
                                                    ItemFlag[].class
                                            );

                                            if (itemFlags != null) {
                                                itemMeta.addItemFlags(itemFlags);
                                            }
                                        }
                                        if (originIconMeta.has("display_name")) {
                                            String originIconDisplayName
                                                    = originIconMeta.get("display_name").getAsString();

                                            if (originIconDisplayName != null) {
                                                itemMeta.setDisplayName(
                                                        ChatUtils.format(
                                                                originIconDisplayName
                                                        )
                                                );
                                            } else {
                                                itemMeta.setDisplayName(
                                                        ChatUtils.format(
                                                                origin.getDisplayName()
                                                        )
                                                );
                                            }
                                        } else {
                                            itemMeta.setDisplayName(
                                                    ChatUtils.format(
                                                            origin.getDisplayName()
                                                    )
                                            );
                                        }
                                        if (originIconMeta.has("description")) {
                                            String[] originIconDescription = gson.fromJson(
                                                    originIconMeta.get("description"),
                                                    String[].class
                                            );

                                            if (originIconDescription != null) {
                                                itemMeta.setLore(
                                                        Arrays.asList(
                                                                originIconDescription
                                                        )
                                                );
                                            } else {
                                                itemMeta.setLore(
                                                        Arrays.asList(
                                                                origin.getDescription()
                                                        )
                                                );
                                            }
                                        } else {
                                            itemMeta.setLore(
                                                    Arrays.asList(
                                                            origin.getDescription()
                                                    )
                                            );
                                        }
                                        if (originIconMeta.has("custom_model_data")) {
                                            int customModelData
                                                    = originIconMeta.get("custom_model_data").getAsInt();

                                            itemMeta.setCustomModelData(customModelData);
                                        }
                                    }
                                } else {
                                    itemMeta.setDisplayName(
                                            ChatUtils.format(
                                                    origin.getDisplayName()
                                            )
                                    );
                                    itemMeta.setLore(
                                            Arrays.asList(
                                                    origin.getDescription()
                                            )
                                    );
                                }
                            }

                            origin.setIcon(originIcon);
                        }
                    }
                    if (originSection.has("authors")) {
                        String[] authors = gson.fromJson(
                                originSection.get("authors"),
                                String[].class
                        );

                        if (authors != null) {
                            origin.setAuthors(authors);
                        }
                    }
                    if (originSection.has("powers")) {
                        String[] powers = gson.fromJson(
                                originSection.get("powers"),
                                String[].class
                        );

                        if (powers != null) {
                            List<Power> originPowers = new ArrayList<>();

                            for (String power : powers) {
                                String key = power.split(":")[0];
                                String value = power.split(":")[1];
                                Identifier identifier1 = new IdentifierContainer(key, value);

                                if (POWERS.hasIdentifier(identifier1)) {
                                    originPowers.add(POWERS.getByIdentifier(identifier1));
                                }
                            }
                            origin.setPowers(originPowers);
                        }
                    }
                }
                Inventory inventory = Bukkit.createInventory(null, 54, ChatUtils.format("&0Choose your origin."));
                ItemStack itemStack = new ItemStack(origin.getIcon());
                itemStack.setAmount(1);
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (itemMeta != null) {
                    itemMeta.setDisplayName(origin.getDisplayName());
                    itemMeta.setLore(Arrays.asList(origin.getDescription()));
                    itemMeta.setLocalizedName(origin.getIdentifier().getIdentifier());
                    itemStack.setItemMeta(itemMeta);
                }
                inventory.setItem(22, itemStack);
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
                ItemStack previous = new ItemStack(Material.ARROW, 1);
                ItemMeta previousMeta = previous.getItemMeta();
                if (previousMeta != null) {
                    previousMeta.setDisplayName(ChatUtils.format("&6Previous Page"));
                    previous.setItemMeta(previousMeta);
                }

                ItemStack close = new ItemStack(Material.BARRIER, 1);
                ItemMeta closeMeta = close.getItemMeta();
                if (closeMeta != null) {
                    closeMeta.setDisplayName(ChatUtils.format("&cQuit Game"));
                    close.setItemMeta(closeMeta);
                }

                ItemStack next = new ItemStack(Material.ARROW, 1);
                ItemMeta nextMeta = next.getItemMeta();
                if (nextMeta != null) {
                    nextMeta.setDisplayName(ChatUtils.format("&6Next Page"));
                    next.setItemMeta(nextMeta);
                }

                inventory.setItem(48, previous);
                inventory.setItem(49, close);
                inventory.setItem(50, next);
                origin.setInventoryGUI(inventory);
            }
            jsonReader.close();
            reader.close();
            return origin;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Power loadPowerFromFile(File powerFile, String identifierKey) {
        return loadPowerFromFile(
                powerFile,
                new IdentifierContainer(
                        identifierKey,
                        FilenameUtils.getBaseName(
                                powerFile.getName()
                        )
                )
        );
    }

    public static Power loadPowerFromFile(File powerFile) {
        return loadPowerFromFile(
                powerFile,
                new IdentifierContainer(
                        "undefined",
                        FilenameUtils.getBaseName(
                                powerFile.getName()
                        )
                )
        );
    }

    private static Power loadPowerFromFile(File powerFile, Identifier identifier) {
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(powerFile);
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            if (jsonObject.has("power")) {
                JsonObject powerSection = jsonObject.getAsJsonObject("power");

                if (powerSection.has("power_type")) {
                    PowerType powerType =
                            new Gson().fromJson(
                                    powerSection.get(
                                            "power_type"
                                    ),
                                    PowerType.class
                            );

                    if (powerType != null) {
                        PowerType parse = PowerType.parsePower(powerType);

                        if (parse != null) {
                            return parse.newInstance(identifier, powerSection);
                        }
                    } else {
                        ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] Power Type cannot be null.");
                    }
                } else {
                    ChatUtils.sendConsoleMessage("&6[Origins-Bukkit] Please specify a power type.");
                }
            }
            jsonReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}

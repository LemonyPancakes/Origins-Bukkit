package me.lemonypancakes.originsbukkit.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import me.lemonypancakes.originsbukkit.util.Identifier;
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
import java.util.*;

public class CraftLoader implements Loader {

    private final OriginsBukkitPlugin plugin;

    public CraftLoader(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public Origin loadOriginFromFile(File originFile, String identifierKey) {
        return loadOriginFromFile(originFile, new Identifier(identifierKey, FilenameUtils.getBaseName(originFile.getName())));
    }

    public Origin loadOriginFromFile(File originFile) {
        return loadOriginFromFile(originFile, new Identifier("undefined", FilenameUtils.getBaseName(originFile.getName())));
    }

    private Origin loadOriginFromFile(File originFile, Identifier identifier) {
        Origin origin = new CraftOrigin(identifier);

        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(originFile);
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            origin.setJsonObject(jsonObject);
            if (jsonObject.has("display_name")) {
                String displayName = jsonObject.get("display_name").getAsString();

                if (displayName != null) {
                    origin.setDisplayName(ChatUtils.format(displayName));
                }
            }
            if (jsonObject.has("description")) {
                String[] description = gson.fromJson(jsonObject.get("description"), String[].class);

                if (description != null) {
                    origin.setDescription(ChatUtils.formatList(description));
                }
            }
            if (jsonObject.has("impact")) {
                Impact impact = gson.fromJson(jsonObject.get("impact"), Impact.class);

                if (impact != null) {
                    origin.setImpact(impact);
                }
            }
            if (jsonObject.has("icon")) {
                JsonObject originIconSection = jsonObject.getAsJsonObject("icon");

                if (originIconSection != null) {
                    ItemStack originIcon = new ItemStack(Material.STONE);

                    if (originIconSection.has("material")) {
                        Material material = gson.fromJson(originIconSection.get("material"), Material.class);

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
                            JsonObject originIconMeta = originIconSection.getAsJsonObject("meta");

                            if (originIconMeta != null) {
                                if (originIconMeta.has("item_flags")) {
                                    ItemFlag[] itemFlags = gson.fromJson(originIconMeta.get("item_flags"), ItemFlag[].class);

                                    if (itemFlags != null) {
                                        itemMeta.addItemFlags(itemFlags);
                                    }
                                }
                                if (originIconMeta.has("display_name")) {
                                    String originIconDisplayName = originIconMeta.get("display_name").getAsString();

                                    if (originIconDisplayName != null) {
                                        itemMeta.setDisplayName(ChatUtils.format(originIconDisplayName));
                                    } else {
                                        if (origin.getDisplayName() != null) {
                                            itemMeta.setDisplayName(ChatUtils.format(origin.getDisplayName()));
                                        }
                                    }
                                } else {
                                    if (origin.getDisplayName() != null) {
                                        itemMeta.setDisplayName(ChatUtils.format(origin.getDisplayName()));
                                    }
                                }
                                if (originIconMeta.has("description")) {
                                    String[] originIconDescription = gson.fromJson(originIconMeta.get("description"), String[].class);

                                    if (originIconDescription != null) {
                                        itemMeta.setLore(Arrays.asList(originIconDescription));
                                    } else {
                                        if (origin.getDescription() != null) {
                                            itemMeta.setLore(Arrays.asList(origin.getDescription()));
                                        }
                                    }
                                } else {
                                    if (origin.getDescription() != null) {
                                        itemMeta.setLore(Arrays.asList(origin.getDescription()));
                                    }
                                }
                                if (originIconMeta.has("custom_model_data")) {
                                    int customModelData = originIconMeta.get("custom_model_data").getAsInt();

                                    itemMeta.setCustomModelData(customModelData);
                                }
                            }
                        } else {
                            if (origin.getDisplayName() != null) {
                                itemMeta.setDisplayName(ChatUtils.format(origin.getDisplayName()));
                            }
                            if (origin.getDescription() != null) {
                                itemMeta.setLore(Arrays.asList(origin.getDescription()));
                            }
                        }
                    }
                    origin.setIcon(originIcon);
                }
            }
            if (jsonObject.has("authors")) {
                String[] authors = gson.fromJson(jsonObject.get("authors"), String[].class);

                if (authors != null) {
                    origin.setAuthors(authors);
                }
            }
            if (jsonObject.has("powers")) {
                String[] powers = gson.fromJson(jsonObject.get("powers"), String[].class);

                if (powers != null) {
                    List<Power> originPowers = new ArrayList<>();

                    for (String power : powers) {
                        String key = power.split(":")[0];
                        String value = power.split(":")[1];
                        Identifier powerIdentifier = new Identifier(key, value);

                        if (plugin.getRegistry().hasPower(powerIdentifier)) {
                            originPowers.add(plugin.getRegistry().getPower(powerIdentifier));
                        }
                    }
                    origin.setPowers(originPowers);
                }
            }
            Inventory inventory = Bukkit.createInventory(null, 54, ChatUtils.format("&0Choose your origin."));
            Inventory originInfo = Bukkit.createInventory(null, 54, ChatUtils.format("&0Origin info."));
            ItemStack itemStack = new ItemStack(origin.getIcon());
            itemStack.setAmount(1);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                itemMeta.setDisplayName(origin.getDisplayName());
                itemMeta.setLore(Arrays.asList(origin.getDescription()));
                itemMeta.setLocalizedName(origin.getIdentifier().toString());
                itemStack.setItemMeta(itemMeta);
            }
            inventory.setItem(22, itemStack);
            originInfo.setItem(22, itemStack);
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
                    originInfo.setItem(6, none);
                    originInfo.setItem(7, none);
                    originInfo.setItem(8, none);
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
                    originInfo.setItem(6, low);
                    originInfo.setItem(7, low1);
                    originInfo.setItem(8, low1);
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
                    originInfo.setItem(6, medium);
                    originInfo.setItem(7, medium);
                    originInfo.setItem(8, medium1);
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
                    originInfo.setItem(6, high);
                    originInfo.setItem(7, high);
                    originInfo.setItem(8, high);
                    break;
            }
            Misc.ORIGINS_INFO_GUI.put(origin.getIdentifier().toString(), originInfo);
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
            jsonReader.close();
            reader.close();
            return origin;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Power loadPowerFromFile(File powerFile, String identifierKey) {
        return loadPowerFromFile(powerFile, new Identifier(identifierKey, FilenameUtils.getBaseName(powerFile.getName())));
    }

    public Power loadPowerFromFile(File powerFile) {
        return loadPowerFromFile(powerFile, new Identifier("undefined", FilenameUtils.getBaseName(powerFile.getName())));
    }

    private Power loadPowerFromFile(File powerFile, Identifier identifier) {
        Power power = null;

        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(powerFile);
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            if (jsonObject != null) {
                if (jsonObject.has("type")) {
                    String type = jsonObject.get("type").getAsString();

                    if (type != null) {
                        Identifier powerFactoryIdentifier = new Identifier(type.split(":")[0], type.split(":")[1]);

                        if (plugin.getRegistry().hasPowerFactory(powerFactoryIdentifier)) {
                            Power.Factory powerFactory = plugin.getRegistry().getPowerFactory(powerFactoryIdentifier);

                            if (powerFactory != null) {
                                power = powerFactory.newInstance(plugin, identifier, jsonObject);
                            }
                        }
                    }
                }
                if (power != null) {

                    power.setJsonObject(jsonObject);
                    power.setActions(loadActions(jsonObject));
                    power.setConditions(loadConditions(jsonObject));
                }
            }
            jsonReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return power;
    }

    public Tag<?> loadTagFromFile(File tagFile, String identifierKey) {
        return loadTagFromFile(
                tagFile,
                new Identifier(
                        identifierKey,
                        FilenameUtils.getBaseName(
                                tagFile.getName()
                        )
                )
        );
    }

    public Tag<?> loadTagFromFile(File tagFile) {
        return loadTagFromFile(
                tagFile,
                new Identifier(
                        "undefined",
                        FilenameUtils.getBaseName(
                                tagFile.getName()
                        )
                )
        );
    }

    private Tag<?> loadTagFromFile(File tagFile, Identifier identifier) {
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(tagFile);
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            if (jsonObject != null) {
                if (jsonObject.has("materials")) {
                    Material[] materials = gson.fromJson(jsonObject.get("materials"), Material[].class);

                    if (materials != null) {
                        return new CraftTag<Material>(identifier);
                    }
                }
            }
            jsonReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Action[] loadActions(JsonObject jsonObject) {
        List<Action> actions = new ArrayList<>();

        if (jsonObject != null) {
            if (jsonObject.has("action")) {
                JsonObject[] actionList = new Gson().fromJson(jsonObject.get("action"), JsonObject[].class);

                if (actionList != null) {
                    for (JsonObject action : actionList) {
                        if (action.has("type")) {
                            String actionType = action.get("type").getAsString();
                            Identifier actionTypeIdentifier = new Identifier(actionType.split(":")[0], actionType.split(":")[1]);

                            if (plugin.getRegistry().hasActionFactory(actionTypeIdentifier)) {
                                Action.Factory actionFactory = plugin.getRegistry().getActionFactory(actionTypeIdentifier);
                                Action actionBuff = actionFactory.newInstance(plugin, action);

                                actions.add(actionBuff);
                            }
                        }
                    }
                }
            }
        }
        return actions.toArray(new Action[0]);
    }

    @Override
    public Action[] loadActions(JsonArray jsonArray) {
        List<Action> actions = new ArrayList<>();

        if (jsonArray != null) {
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("type")) {
                    String actionType = jsonObject.get("type").getAsString();
                    Identifier actionTypeIdentifier = new Identifier(actionType.split(":")[0], actionType.split(":")[1]);

                    if (plugin.getRegistry().hasActionFactory(actionTypeIdentifier)) {
                        Action.Factory actionFactory = plugin.getRegistry().getActionFactory(actionTypeIdentifier);
                        Action actionBuff = actionFactory.newInstance(plugin, jsonObject);

                        actions.add(actionBuff);
                    }
                }
            }
        }
        return actions.toArray(new Action[0]);
    }

    @Override
    public Condition[] loadConditions(JsonObject jsonObject) {
        List<Condition> conditions = new ArrayList<>();

        if (jsonObject != null) {
            if (jsonObject.has("condition")) {
                JsonObject[] conditionsList = new Gson().fromJson(jsonObject.get("condition"), JsonObject[].class);

                if (conditionsList != null) {
                    for (JsonObject condition : conditionsList) {
                        if (condition.has("type")) {
                            String conditionType = condition.get("type").getAsString();
                            Identifier conditionTypeIdentifier = new Identifier(conditionType.split(":")[0], conditionType.split(":")[1]);

                            if (plugin.getRegistry().hasConditionFactory(conditionTypeIdentifier)) {
                                Condition.Factory conditionsFactory = plugin.getRegistry().getConditionFactory(conditionTypeIdentifier);
                                Condition conditionsBuff = conditionsFactory.newInstance(plugin, condition);

                                conditions.add(conditionsBuff);
                            }
                        }
                    }
                }
            }
        }
        return conditions.toArray(new Condition[0]);
    }

    @Override
    public Condition[] loadConditions(JsonArray jsonArray) {
        List<Condition> conditions = new ArrayList<>();

        if (jsonArray != null) {
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("type")) {
                    String conditionType = jsonObject.get("type").getAsString();
                    Identifier conditionTypeIdentifier = new Identifier(conditionType.split(":")[0], conditionType.split(":")[1]);

                    if (plugin.getRegistry().hasConditionFactory(conditionTypeIdentifier)) {
                        Condition.Factory conditionsFactory = plugin.getRegistry().getConditionFactory(conditionTypeIdentifier);
                        Condition conditionsBuff = conditionsFactory.newInstance(plugin, jsonObject);

                        conditions.add(conditionsBuff);
                    }
                }
            }
        }
        return conditions.toArray(new Condition[0]);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}
}

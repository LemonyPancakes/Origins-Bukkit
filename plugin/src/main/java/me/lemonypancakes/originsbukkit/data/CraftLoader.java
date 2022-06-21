package me.lemonypancakes.originsbukkit.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.storage.other.Misc;
import me.lemonypancakes.originsbukkit.util.ChatUtil;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.Impact;
import me.lemonypancakes.originsbukkit.wrapper.ConditionAction;
import me.lemonypancakes.originsbukkit.wrapper.Element;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class CraftLoader implements Loader {

    private final OriginsBukkitPlugin plugin;

    public CraftLoader(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Origin loadOriginFromFile(Reader reader, String identifierKey, String identifierValue) {
        return loadOriginFromFile(reader, new Identifier(identifierKey, identifierValue));
    }

    private Origin loadOriginFromFile(Reader reader, Identifier identifier) {
        Origin origin = new CraftOrigin(identifier);

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            origin.setJsonObject(jsonObject);
            if (jsonObject.has("display_name")) {
                String displayName = jsonObject.get("display_name").getAsString();

                if (displayName != null) {
                    origin.setDisplayName(ChatUtil.format(displayName));
                }
            }
            if (jsonObject.has("description")) {
                String[] description = gson.fromJson(jsonObject.get("description"), String[].class);

                if (description != null) {
                    origin.setDescription(ChatUtil.formatList(description));
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
                                        itemMeta.setDisplayName(ChatUtil.format(originIconDisplayName));
                                    } else {
                                        if (origin.getDisplayName() != null) {
                                            itemMeta.setDisplayName(ChatUtil.format(origin.getDisplayName()));
                                        }
                                    }
                                } else {
                                    if (origin.getDisplayName() != null) {
                                        itemMeta.setDisplayName(ChatUtil.format(origin.getDisplayName()));
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
                                itemMeta.setDisplayName(ChatUtil.format(origin.getDisplayName()));
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
            Inventory inventory = Bukkit.createInventory(null, 54, ChatUtil.format("&0Choose your origin."));
            Inventory originInfo = Bukkit.createInventory(null, 54, ChatUtil.format("&0Origin info."));
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
                        noneMeta.setDisplayName(ChatUtil.format("&fImpact: &7None"));
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
                        lowMeta.setDisplayName(ChatUtil.format("&fImpact: &aLow"));
                        low.setItemMeta(lowMeta);
                    }
                    ItemStack low1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta lowMeta1 = low1.getItemMeta();
                    if (lowMeta1 != null) {
                        lowMeta1.setDisplayName(ChatUtil.format("&fImpact: &aLow"));
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
                        mediumMeta.setDisplayName(ChatUtil.format("&fImpact: &eMedium"));
                        medium.setItemMeta(mediumMeta);
                    }
                    ItemStack medium1 = new ItemStack(Material.LIGHT_GRAY_CONCRETE, 1);
                    ItemMeta mediumMeta1 = medium1.getItemMeta();
                    if (mediumMeta1 != null) {
                        mediumMeta1.setDisplayName(ChatUtil.format("&fImpact: &eMedium"));
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
                        highMeta.setDisplayName(ChatUtil.format("&fImpact: &cHigh"));
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
                previousMeta.setDisplayName(ChatUtil.format("&6Previous Page"));
                previous.setItemMeta(previousMeta);
            }

            ItemStack close = new ItemStack(Material.BARRIER, 1);
            ItemMeta closeMeta = close.getItemMeta();
            if (closeMeta != null) {
                closeMeta.setDisplayName(ChatUtil.format("&cQuit Game"));
                close.setItemMeta(closeMeta);
            }

            ItemStack next = new ItemStack(Material.ARROW, 1);
            ItemMeta nextMeta = next.getItemMeta();
            if (nextMeta != null) {
                nextMeta.setDisplayName(ChatUtil.format("&6Next Page"));
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

    @Override
    public Power loadPowerFromFile(Reader reader, String identifierKey, String identifierValue) {
        return loadPowerFromFile(reader, new Identifier(identifierKey, identifierValue));
    }

    private Power loadPowerFromFile(Reader reader, Identifier identifier) {
        Power power = null;

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            if (jsonObject != null) {
                if (jsonObject.has("type")) {
                    String type = jsonObject.get("type").getAsString();

                    if (type != null) {
                        Identifier powerFactoryIdentifier = new Identifier(type.split(":")[0], type.split(":")[1]);

                        if (plugin.getRegistry().hasPowerFactory(powerFactoryIdentifier)) {
                            Power.Factory factory = plugin.getRegistry().getPowerFactory(powerFactoryIdentifier);

                            if (factory != null) {
                                power = factory.newInstance(plugin, identifier, jsonObject);
                            }
                        }
                    }
                }
                if (power != null) {

                    power.setJsonObject(jsonObject);
                }
            }
            jsonReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return power;
    }

    @Override
    public <T> Action<T> loadAction(DataType<T> dataType, JsonObject jsonObject) {
        return loadAction(dataType, jsonObject, "action");
    }

    @Override
    public <T> Action<T> loadAction(DataType<T> dataType, JsonObject jsonObject, String memberName) {
        if (jsonObject.has(memberName)) {
            JsonObject actionJsonObject = jsonObject.getAsJsonObject(memberName);

            if (actionJsonObject != null) {
                if (actionJsonObject.has("type")) {
                    String actionTypeString = actionJsonObject.get("type").getAsString();
                    Identifier actionTypeIdentifier = new Identifier(actionTypeString.split(":")[0], actionTypeString.split(":")[1]);

                    if (plugin.getRegistry().hasActionFactory(dataType, actionTypeIdentifier)) {
                        Action.Factory<T> factory = plugin.getRegistry().getActionFactory(dataType, actionTypeIdentifier);

                        return factory.newInstance(plugin, actionJsonObject, dataType);
                    }
                }
            }
        }
        return new CraftAction<>(null, null, null, null);
    }

    @Override
    public <T> Action<T>[] loadActions(DataType<T> dataType, JsonObject jsonObject) {
        return loadActions(dataType, jsonObject, "actions");
    }

    @Override
    public <T> Action<T>[] loadActions(DataType<T> dataType, JsonObject jsonObject, String memberName) {
        List<Action<T>> actions = new ArrayList<>();
        JsonObject[] actionJsonObjects = new Gson().fromJson(jsonObject.get(memberName), JsonObject[].class);

        if (actionJsonObjects != null) {
            for (JsonObject actionJsonObject : actionJsonObjects) {
                if (actionJsonObject.has("type")) {
                    String actionTypeString = actionJsonObject.get("type").getAsString();
                    Identifier actionTypeIdentifier = new Identifier(actionTypeString.split(":")[0], actionTypeString.split(":")[1]);

                    if (plugin.getRegistry().hasActionFactory(dataType, actionTypeIdentifier)) {
                        Action.Factory<T> factory = plugin.getRegistry().getActionFactory(dataType, actionTypeIdentifier);
                        Action<T> action = factory.newInstance(plugin, actionJsonObject, dataType);

                        actions.add(action);
                    }
                }
            }
        }
        return (Action<T>[]) actions.toArray(new Action<?>[0]);
    }

    @Override
    public <T> Action<T>[] loadActions(DataType<T> dataType, JsonArray jsonArray) {
        List<Action<T>> actions = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject actionJsonObject = jsonElement.getAsJsonObject();

            if (actionJsonObject.has("type")) {
                String actionTypeString = actionJsonObject.get("type").getAsString();
                Identifier actionTypeIdentifier = new Identifier(actionTypeString.split(":")[0], actionTypeString.split(":")[1]);

                if (plugin.getRegistry().hasActionFactory(dataType, actionTypeIdentifier)) {
                    Action.Factory<T> factory = plugin.getRegistry().getActionFactory(dataType, actionTypeIdentifier);
                    Action<T> action = factory.newInstance(plugin, actionJsonObject, dataType);

                    actions.add(action);
                }
            }
        }
        return (Action<T>[]) actions.toArray(new Action<?>[0]);
    }

    @Override
    public <T> Condition<T> loadCondition(DataType<T> dataType, JsonObject jsonObject) {
        return loadCondition(dataType, jsonObject, "condition");
    }

    @Override
    public <T> Condition<T> loadCondition(DataType<T> dataType, JsonObject jsonObject, String memberName) {
        if (jsonObject.has(memberName)) {
            JsonObject conditionJsonObject = jsonObject.getAsJsonObject(memberName);

            if (conditionJsonObject != null) {
                if (conditionJsonObject.has("type")) {
                    String conditionTypeString = conditionJsonObject.get("type").getAsString();
                    Identifier conditionTypeIdentifier = new Identifier(conditionTypeString.split(":")[0], conditionTypeString.split(":")[1]);

                    if (plugin.getRegistry().hasConditionFactory(dataType, conditionTypeIdentifier)) {
                        Condition.Factory<T> factory = plugin.getRegistry().getConditionFactory(dataType, conditionTypeIdentifier);

                        return factory.newInstance(plugin, conditionJsonObject, dataType);
                    }
                }
            }
        }
        return new CraftCondition<>(null, null, null, null);
    }

    @Override
    public <T> Condition<T>[] loadConditions(DataType<T> dataType, JsonObject jsonObject) {
        return loadConditions(dataType, jsonObject, "conditions");
    }

    @Override
    public <T> Condition<T>[] loadConditions(DataType<T> dataType, JsonObject jsonObject, String memberName) {
        List<Condition<T>> conditions = new ArrayList<>();
        JsonObject[] conditionJsonObjects = new Gson().fromJson(jsonObject.get(memberName), JsonObject[].class);

        if (conditionJsonObjects != null) {
            for (JsonObject conditionJsonObject : conditionJsonObjects) {
                if (conditionJsonObject.has("type")) {
                    String conditionTypeString = conditionJsonObject.get("type").getAsString();
                    Identifier conditionTypeIdentifier = new Identifier(conditionTypeString.split(":")[0], conditionTypeString.split(":")[1]);

                    if (plugin.getRegistry().hasActionFactory(dataType, conditionTypeIdentifier)) {
                        Condition.Factory<T> factory = plugin.getRegistry().getConditionFactory(dataType, conditionTypeIdentifier);
                        Condition<T> condition = factory.newInstance(plugin, conditionJsonObject, dataType);

                        conditions.add(condition);
                    }
                }
            }
        }
        return (Condition<T>[]) conditions.toArray(new Condition<?>[0]);
    }

    @Override
    public <T> Condition<T>[] loadConditions(DataType<T> dataType, JsonArray jsonArray) {
        List<Condition<T>> conditions = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject.has("type")) {
                String conditionTypeString = jsonObject.get("type").getAsString();
                Identifier conditionTypeIdentifier = new Identifier(conditionTypeString.split(":")[0], conditionTypeString.split(":")[1]);

                if (plugin.getRegistry().hasConditionFactory(dataType, conditionTypeIdentifier)) {
                    Condition.Factory<T> factory = plugin.getRegistry().getConditionFactory(dataType, conditionTypeIdentifier);
                    Condition<T> condition = factory.newInstance(plugin, jsonObject, dataType);

                    conditions.add(condition);
                }
            }
        }
        return (Condition<T>[]) conditions.toArray(new Condition<?>[0]);
    }

    @Override
    public <T> ConditionAction<T> loadConditionAction(DataType<T> dataType, JsonObject jsonObject) {
        return new ConditionAction<>(plugin, loadCondition(dataType, jsonObject), loadAction(dataType, jsonObject));
    }

    @Override
    public <T> ConditionAction<T> loadConditionAction(DataType<T> dataType, JsonObject jsonObject, String conditionMemberName, String actionMemberName) {
        return new ConditionAction<>(plugin, loadCondition(dataType, jsonObject, conditionMemberName), loadAction(dataType, jsonObject, actionMemberName));
    }

    @Override
    public <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonObject jsonObject) {
        return loadConditionActions(dataType, jsonObject, "condition", "action");
    }

    @Override
    public <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonObject jsonObject, String conditionMemberName, String actionMemberName) {
        List<ConditionAction<T>> conditionActions = new ArrayList<>();

        if (jsonObject.has("actions")) {
            JsonObject[] actionsJsonObject = new Gson().fromJson(jsonObject.get("actions"), JsonObject[].class);

            for (JsonObject actionJsonObject : actionsJsonObject) {
                conditionActions.add(new ConditionAction<>(plugin, loadCondition(dataType, actionJsonObject, conditionMemberName), loadAction(dataType, actionJsonObject, actionMemberName)));
            }
        }
        return (ConditionAction<T>[]) conditionActions.toArray(new ConditionAction<?>[0]);
    }

    @Override
    public <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonArray jsonArray) {
        List<ConditionAction<T>> conditionActions = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            conditionActions.add(new ConditionAction<>(plugin, loadCondition(dataType, jsonObject), loadAction(dataType, jsonObject)));
        }
        return (ConditionAction<T>[]) conditionActions.toArray(new ConditionAction<?>[0]);
    }

    @Override
    public <T> ConditionAction<T>[] loadConditionActions(DataType<T> dataType, JsonArray jsonArray, String conditionMemberName, String actionMemberName) {
        List<ConditionAction<T>> conditionActions = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            conditionActions.add(new ConditionAction<>(plugin, loadCondition(dataType, jsonObject, conditionMemberName), loadAction(dataType, jsonObject, actionMemberName)));
        }
        return (ConditionAction<T>[]) conditionActions.toArray(new ConditionAction<?>[0]);
    }

    @Override
    public <T> Element<T>[] loadElements(DataType<T> dataType, JsonObject jsonObject) {
        List<Element<T>> elements = new ArrayList<>();

        if (jsonObject.has("actions")) {
            JsonObject[] actionsJsonObject = new Gson().fromJson(jsonObject.get("actions"), JsonObject[].class);

            for (JsonObject actionJsonObject : actionsJsonObject) {
                if (actionJsonObject != null) {
                    if (actionJsonObject.has("type")) {
                        String actionTypeString = actionJsonObject.get("type").getAsString();
                        Identifier actionTypeIdentifier = new Identifier(actionTypeString.split(":")[0], actionTypeString.split(":")[1]);

                        if (plugin.getRegistry().hasConditionFactory(dataType, actionTypeIdentifier)) {
                            Action.Factory<T> factory = plugin.getRegistry().getActionFactory(dataType, actionTypeIdentifier);
                            Action<T> action = factory.newInstance(plugin, jsonObject, dataType);

                            elements.add(new Element<>(action, actionJsonObject.get("weight").getAsInt()));
                        }
                    }
                }
            }
        }
        return (Element<T>[]) elements.toArray(new Element<?>[0]);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftLoader)) return false;
        CraftLoader that = (CraftLoader) itemStack;
        return Objects.equals(getPlugin(), that.getPlugin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin());
    }

    @Override
    public String toString() {
        return "CraftLoader{" +
                "plugin=" + plugin +
                '}';
    }
}

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
package me.lemonypancakes.bukkit.origins.loader;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonArray;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonElement;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.common.com.google.gson.stream.JsonReader;
import me.lemonypancakes.bukkit.origins.data.DataType;
import me.lemonypancakes.bukkit.origins.origin.CraftOrigin;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.CraftOriginLayer;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.Action;
import me.lemonypancakes.bukkit.origins.entity.player.power.action.CraftAction;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.Condition;
import me.lemonypancakes.bukkit.origins.entity.player.power.condition.CraftCondition;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Impact;
import me.lemonypancakes.bukkit.origins.wrapper.ConditionAction;
import me.lemonypancakes.bukkit.origins.wrapper.Element;
import org.bukkit.Material;
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

    private OriginsBukkitPlugin plugin;

    public CraftLoader(OriginsBukkitPlugin plugin) {
        setPlugin(plugin);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {
        if (this.plugin == null) {
            this.plugin = plugin;
        }
    }

    @Override
    public Origin loadOriginFromFile(Reader reader, String identifierKey, String identifierValue) {
        return loadOriginFromFile(reader, new Identifier(identifierKey, identifierValue));
    }

    private Origin loadOriginFromFile(Reader reader, Identifier identifier) {
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            Origin origin = new CraftOrigin(plugin, identifier, jsonObject);

            if (jsonObject.has("display_name")) {
                String displayName = jsonObject.get("display_name").getAsString();

                if (displayName != null) {
                    origin.setName(ChatUtils.format(displayName));
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
                                        if (origin.getName() != null) {
                                            itemMeta.setDisplayName(ChatUtils.format(origin.getName()));
                                        }
                                    }
                                } else {
                                    if (origin.getName() != null) {
                                        itemMeta.setDisplayName(ChatUtils.format(origin.getName()));
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
                            if (origin.getName() != null) {
                                itemMeta.setDisplayName(ChatUtils.format(origin.getName()));
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
                    for (String power : powers) {
                        String key = power.split(":")[0];
                        String value = power.split(":")[1];
                        Identifier powerIdentifier = new Identifier(key, value);
                        Power power0 = plugin.getRegistry().getRegisteredPower(powerIdentifier);

                        if (power0 != null) {
                            origin.addPower(power0);
                        }
                    }
                }
            }
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
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            Power power = null;

            if (jsonObject != null) {
                if (jsonObject.has("type")) {
                    String type = jsonObject.get("type").getAsString();

                    if (type != null) {
                        Identifier powerFactoryIdentifier = new Identifier(type.split(":")[0], type.split(":")[1]);
                        Power.Factory factory = plugin.getRegistry().getRegisteredPowerFactory(powerFactoryIdentifier);

                        if (factory != null) {
                            power = factory.create(plugin, identifier, jsonObject);
                        }
                    }
                }
                if (power != null) {
                    power.setJsonObject(jsonObject);
                }
            }
            jsonReader.close();
            reader.close();
            return power;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OriginLayer loadOriginLayerFromFile(Reader reader, String identifierKey, String identifierValue) {
        return loadOriginLayerFromFile(reader, new Identifier(identifierKey, identifierValue));
    }

    private OriginLayer loadOriginLayerFromFile(Reader reader, Identifier identifier) {
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            OriginLayer originLayer = new CraftOriginLayer(plugin, identifier, jsonObject);

            jsonReader.close();
            reader.close();
            return originLayer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                    Action.Factory<T> factory = plugin.getRegistry().getRegisteredActionFactory(dataType, actionTypeIdentifier);

                    if (factory != null) {
                        return factory.create(plugin, actionJsonObject, dataType);
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
                    Action.Factory<T> factory = plugin.getRegistry().getRegisteredActionFactory(dataType, actionTypeIdentifier);

                    if (factory != null) {
                        Action<T> action = factory.create(plugin, actionJsonObject, dataType);

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

                Action.Factory<T> factory = plugin.getRegistry().getRegisteredActionFactory(dataType, actionTypeIdentifier);

                if (factory != null) {
                    Action<T> action = factory.create(plugin, actionJsonObject, dataType);

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

                    Condition.Factory<T> factory = plugin.getRegistry().getRegisteredConditionFactory(dataType, conditionTypeIdentifier);

                    if (factory != null) {
                        return factory.create(plugin, conditionJsonObject, dataType);
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
                    Condition.Factory<T> factory = plugin.getRegistry().getRegisteredConditionFactory(dataType, conditionTypeIdentifier);

                    if (factory != null) {
                        Condition<T> condition = factory.create(plugin, conditionJsonObject, dataType);

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
                Condition.Factory<T> factory = plugin.getRegistry().getRegisteredConditionFactory(dataType, conditionTypeIdentifier);

                if (factory != null) {
                    Condition<T> condition = factory.create(plugin, jsonObject, dataType);

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
                        Action.Factory<T> factory = plugin.getRegistry().getRegisteredActionFactory(dataType, actionTypeIdentifier);

                        if (factory != null) {
                            Action<T> action = factory.create(plugin, jsonObject, dataType);

                            elements.add(new Element<>(action, actionJsonObject.get("weight").getAsInt()));
                        }
                    }
                }
            }
        }
        return (Element<T>[]) elements.toArray(new Element<?>[0]);
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftLoader)) return false;
        CraftLoader that = (CraftLoader) itemStack;
        return Objects.equals(plugin, that.plugin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin);
    }

    @Override
    public String toString() {
        return "CraftLoader{" +
                "plugin=" + plugin +
                '}';
    }
}

package me.lemonypancakes.originsbukkit.api.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.OriginContainer;
import me.lemonypancakes.originsbukkit.api.data.container.PowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.enums.Impact;
import me.lemonypancakes.originsbukkit.util.Catcher;
import me.lemonypancakes.originsbukkit.util.Message;
import me.lemonypancakes.originsbukkit.util.Storage;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;
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

public final class Loader {

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
                    if (originSection.has("display_name")) {
                        String displayName = originSection.get("display_name").getAsString();

                        if (displayName != null) {
                            origin.setDisplayName(displayName);
                        }
                    }
                    if (originSection.has("description")) {
                        String[] description = gson.fromJson(
                                originSection.get("description"),
                                String[].class
                        );

                        if (description != null) {
                            origin.setDescription(description);
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
                                                        Message.format(
                                                                originIconDisplayName
                                                        )
                                                );
                                            } else {
                                                itemMeta.setDisplayName(
                                                        Message.format(
                                                                origin.getDisplayName()
                                                        )
                                                );
                                            }
                                        } else {
                                            itemMeta.setDisplayName(
                                                    Message.format(
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
                                            Message.format(
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

                                Storage.getPowersData().forEach((key1, value1) -> {
                                    if (Catcher.catchDuplicate(key1, identifier1)) {
                                        originPowers.add(value1);
                                    }
                                });
                            }
                            origin.setPowers(originPowers);
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
        Power power = new PowerContainer(identifier);

        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(powerFile);
            JsonReader jsonReader = new JsonReader(reader);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            if (jsonObject.has("power")) {
                JsonObject powerSection = jsonObject.getAsJsonObject("power");

                if (powerSection != null) {
                    if (powerSection.has("action")) {
                        JsonObject actionSection = powerSection.getAsJsonObject("action");

                        if (actionSection != null) {
                            if (actionSection.has("type")) {
                                String actionTypeString = actionSection.get("type").getAsString();

                                if (actionTypeString != null) {
                                    Identifier actionTypeIdentifier = new IdentifierContainer(
                                            actionTypeString.split(":")[0],
                                            actionTypeString.split(":")[1]
                                    );

                                    Storage.getActionsData().forEach((key, value) -> {
                                        if (Catcher.catchDuplicate(key, actionTypeIdentifier)) {
                                            power.setAction(value);
                                            power.getAction().setJsonObject(actionSection);
                                        }
                                    });
                                }
                            }
                        }
                    }
                    if (powerSection.has("condition")) {
                        JsonObject conditionSection = powerSection.getAsJsonObject("condition");

                        if (conditionSection != null) {
                            if (conditionSection.has("type")) {
                                String conditionTypeString = conditionSection.get("type").getAsString();

                                if (conditionTypeString != null) {
                                    Identifier conditionTypeIdentifier = new IdentifierContainer(
                                            conditionTypeString.split(":")[0],
                                            conditionTypeString.split(":")[1]
                                    );

                                    Storage.getConditionsData().forEach((key, value) -> {
                                        if (Catcher.catchDuplicate(key, conditionTypeIdentifier)) {
                                            power.setCondition(value);
                                            power.getCondition().setJsonObject(conditionSection);
                                        }
                                    });
                                }
                            }
                        }
                    }
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
}

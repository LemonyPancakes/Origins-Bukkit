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
package me.lemonypancakes.bukkit.origins.origin;

import me.lemonypancakes.bukkit.common.com.google.gson.Gson;
import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Impact;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SuppressWarnings("unused")
public class CraftOrigin implements Origin {

    private OriginsBukkitPlugin plugin;
    private Identifier identifier;
    private JsonObject jsonObject;
    private final List<Power> powers = new LinkedList<>();
    private ItemStack icon;
    private boolean unchoosable;
    private int order;
    private Impact impact;
    private String name;
    private String[] description;
    private String[] authors;
    private int loadingPriority;

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        setPlugin(plugin);
        setIdentifier(identifier);
        setJsonObject(jsonObject);
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
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.identifier == null) {
            this.identifier = identifier;
        }
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        if (this.jsonObject == null) {
            this.jsonObject = jsonObject;
            if (jsonObject != null) {
                Gson gson = new Gson();

                if (jsonObject.has("name")) {
                    String name = jsonObject.get("name").getAsString();

                    if (name != null) {
                        setName(name);
                    }
                }
                if (jsonObject.has("description")) {
                    String[] description = gson.fromJson(jsonObject.get("description"), String[].class);

                    if (description != null) {
                        setDescription(description);
                    }
                }
                if (jsonObject.has("impact")) {
                    Impact impact = gson.fromJson(jsonObject.get("impact"), Impact.class);

                    if (impact != null) {
                        setImpact(impact);
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
                                            itemMeta.setDisplayName(originIconDisplayName);
                                        } else {
                                            if (getName() != null) {
                                                itemMeta.setDisplayName(getName());
                                            }
                                        }
                                    } else {
                                        if (getName() != null) {
                                            itemMeta.setDisplayName(getName());
                                        }
                                    }
                                    if (originIconMeta.has("description")) {
                                        String[] originIconDescription = gson.fromJson(originIconMeta.get("description"), String[].class);

                                        if (originIconDescription != null) {
                                            itemMeta.setLore(Arrays.asList(originIconDescription));
                                        } else {
                                            if (getDescription() != null) {
                                                itemMeta.setLore(Arrays.asList(getDescription()));
                                            }
                                        }
                                    } else {
                                        if (getDescription() != null) {
                                            itemMeta.setLore(Arrays.asList(getDescription()));
                                        }
                                    }
                                    if (originIconMeta.has("custom_model_data")) {
                                        int customModelData = originIconMeta.get("custom_model_data").getAsInt();

                                        itemMeta.setCustomModelData(customModelData);
                                    }
                                }
                            } else {
                                if (getName() != null) {
                                    itemMeta.setDisplayName(getName());
                                }
                                if (getDescription() != null) {
                                    itemMeta.setLore(Arrays.asList(getDescription()));
                                }
                            }
                        }
                        setIcon(originIcon);
                    }
                }
                if (jsonObject.has("authors")) {
                    String[] authors = gson.fromJson(jsonObject.get("authors"), String[].class);

                    if (authors != null) {
                        setAuthors(authors);
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
                                addPower(power0);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Power> getPowers() {
        return Collections.unmodifiableList(new LinkedList<>(powers));
    }

    @Override
    public void addPower(Power power) {
        if (!powers.contains(power)) {
            powers.add(power);
        }
    }

    @Override
    public void removePower(Power power) {
        powers.remove(power);
    }

    @Override
    public boolean hasPower(Power power) {
        return powers.contains(power);
    }

    @Override
    public ItemStack getIcon() {
        return icon;
    }

    @Override
    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    @Override
    public boolean isUnchoosable() {
        return unchoosable;
    }

    @Override
    public void setUnchoosable(boolean unchoosable) {
        this.unchoosable = unchoosable;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public Impact getImpact() {
        return impact;
    }

    @Override
    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String[] getDescription() {
        return description;
    }

    @Override
    public void setDescription(String[] description) {
        this.description = description;
    }

    @Override
    public String[] getAuthors() {
        return authors;
    }

    @Override
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getLoadingPriority() {
        return loadingPriority;
    }

    public void setLoadingPriority(int loadingPriority) {
        this.loadingPriority = loadingPriority;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOrigin)) return false;
        CraftOrigin that = (CraftOrigin) itemStack;
        return unchoosable == that.unchoosable && order == that.order && loadingPriority == that.loadingPriority && Objects.equals(plugin, that.plugin) && Objects.equals(identifier, that.identifier) && Objects.equals(jsonObject, that.jsonObject) && Objects.equals(powers, that.powers) && Objects.equals(icon, that.icon) && impact == that.impact && Objects.equals(name, that.name) && Arrays.equals(description, that.description) && Arrays.equals(authors, that.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin, identifier, jsonObject);
    }

    @Override
    public String toString() {
        return "CraftOrigin{" +
                "plugin=" + plugin +
                ", identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", powers=" + powers +
                ", icon=" + icon +
                ", unchoosable=" + unchoosable +
                ", order=" + order +
                ", impact=" + impact +
                ", name='" + name + '\'' +
                ", description=" + Arrays.toString(description) +
                ", authors=" + Arrays.toString(authors) +
                ", loadingPriority=" + loadingPriority +
                '}';
    }
}

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
package me.lemonypancakes.bukkit.origins.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Origin;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.Power;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Impact;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class CraftOrigin implements Origin {

    private final OriginsBukkitPlugin plugin;
    private Identifier identifier;
    private JsonObject jsonObject;
    private String displayName;
    private String[] description;
    private Impact impact;
    private ItemStack icon;
    private String[] authors;
    private List<Power> powers;

    // The plugin will automagically provide.
    private Inventory inventoryGUI;

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, String displayName, String[] description, Impact impact, ItemStack icon, String[] authors, List<Power> powers) {
        this.plugin = plugin;
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.displayName = displayName;
        this.description = description;
        this.impact = impact;
        this.icon = icon;
        this.authors = authors;
        this.powers = powers;
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, String displayName, String[] description, Impact impact, ItemStack icon, String[] authors) {
        this(plugin, identifier, jsonObject, displayName, description, impact, icon, authors, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, String displayName, String[] description, Impact impact, ItemStack icon) {
        this(plugin, identifier, jsonObject, displayName, description, impact, icon, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, String displayName, String[] description, Impact impact) {
        this(plugin, identifier, jsonObject, displayName, description, impact, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, String displayName, String[] description) {
        this(plugin, identifier, jsonObject, displayName, description, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, String displayName) {
        this(plugin, identifier, jsonObject, displayName, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        this(plugin, identifier, jsonObject, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin, Identifier identifier) {
        this(plugin, identifier, null);
    }

    public CraftOrigin(OriginsBukkitPlugin plugin) {
        this(plugin, null);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
    public Impact getImpact() {
        return impact;
    }

    @Override
    public void setImpact(Impact impact) {
        this.impact = impact;
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
    public String[] getAuthors() {
        return authors;
    }

    @Override
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    @Override
    public List<Power> getPowers() {
        return powers;
    }

    @Override
    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }

    @Override
    public Inventory getInventoryGUI() {
        return inventoryGUI;
    }

    @Override
    public void setInventoryGUI(Inventory inventoryGUI) {
        this.inventoryGUI = inventoryGUI;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOrigin)) return false;
        CraftOrigin that = (CraftOrigin) itemStack;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getJsonObject(), that.getJsonObject()) && Objects.equals(getDisplayName(), that.getDisplayName()) && Arrays.equals(getDescription(), that.getDescription()) && getImpact() == that.getImpact() && Objects.equals(getIcon(), that.getIcon()) && Arrays.equals(getAuthors(), that.getAuthors()) && Objects.equals(getPowers(), that.getPowers()) && Objects.equals(getInventoryGUI(), that.getInventoryGUI());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getIdentifier(), getJsonObject(), getDisplayName(), getImpact(), getIcon(), getPowers(), getInventoryGUI());
        result = 31 * result + Arrays.hashCode(getDescription());
        result = 31 * result + Arrays.hashCode(getAuthors());
        return result;
    }

    @Override
    public String toString() {
        return "CraftOrigin{" +
                "identifier=" + identifier +
                ", jsonObject=" + jsonObject +
                ", displayName='" + displayName + '\'' +
                ", description=" + Arrays.toString(description) +
                ", impact=" + impact +
                ", icon=" + icon +
                ", authors=" + Arrays.toString(authors) +
                ", powers=" + powers +
                ", inventoryGUI=" + inventoryGUI +
                '}';
    }
}

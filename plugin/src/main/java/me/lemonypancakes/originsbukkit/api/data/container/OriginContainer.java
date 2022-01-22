package me.lemonypancakes.originsbukkit.api.data.container;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.enums.Impact;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class OriginContainer implements Origin {

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

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject,
                           String displayName,
                           String[] description,
                           Impact impact,
                           ItemStack icon,
                           String[] authors,
                           List<Power> powers) {
        this.identifier = identifier;
        this.jsonObject = jsonObject;
        this.displayName = displayName;
        this.description = description;
        this.impact = impact;
        this.icon = icon;
        this.authors = authors;
        this.powers = powers;
    }

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject,
                           String displayName,
                           String[] description,
                           Impact impact,
                           ItemStack icon,
                           String[] authors) {
        this(identifier, jsonObject, displayName, description, impact, icon, authors, null);
    }

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject,
                           String displayName,
                           String[] description,
                           Impact impact,
                           ItemStack icon) {
        this(identifier, jsonObject, displayName, description, impact, icon, null);
    }

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject,
                           String displayName,
                           String[] description,
                           Impact impact) {
        this(identifier, jsonObject, displayName, description, impact, null);
    }

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject,
                           String displayName,
                           String[] description) {
        this(identifier, jsonObject, displayName, description, null);
    }

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject,
                           String displayName) {
        this(identifier, jsonObject, displayName, null);
    }

    public OriginContainer(Identifier identifier,
                           JsonObject jsonObject) {
        this(identifier, jsonObject, null);
    }

    public OriginContainer(Identifier identifier) {
        this(identifier, null);
    }

    public OriginContainer() {
        this(null);
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OriginContainer)) return false;
        OriginContainer that = (OriginContainer) o;
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
        return "OriginContainer{" +
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

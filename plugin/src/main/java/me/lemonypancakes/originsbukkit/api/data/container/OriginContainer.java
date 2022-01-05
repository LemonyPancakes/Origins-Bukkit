package me.lemonypancakes.originsbukkit.api.data.container;

import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.api.data.type.Power;
import me.lemonypancakes.originsbukkit.enums.Impact;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class OriginContainer implements Origin {

    private Identifier identifier;
    private String displayName;
    private String[] description;
    private Impact impact;
    private ItemStack icon;
    private String[] authors;
    private List<Power> powers;

    public OriginContainer(Identifier identifier,
                           String displayName,
                           String[] description,
                           Impact impact,
                           ItemStack icon,
                           String[] authors,
                           List<Power> powers) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.description = description;
        this.impact = impact;
        this.icon = icon;
        this.authors = authors;
        this.powers = powers;
    }

    public OriginContainer(Identifier identifier,
                           String displayName,
                           String[] description,
                           Impact impact,
                           ItemStack icon,
                           String[] authors) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.description = description;
        this.impact = impact;
        this.icon = icon;
        this.authors = authors;
    }

    public OriginContainer(Identifier identifier,
                           String displayName,
                           String[] description,
                           Impact impact,
                           ItemStack icon) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.description = description;
        this.impact = impact;
        this.icon = icon;
    }

    public OriginContainer(Identifier identifier,
                           String displayName,
                           String[] description,
                           Impact impact) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.description = description;
        this.impact = impact;
    }

    public OriginContainer(Identifier identifier,
                           String displayName,
                           String[] description) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.description = description;
    }

    public OriginContainer(Identifier identifier,
                           String displayName) {
        this.identifier = identifier;
        this.displayName = displayName;
    }

    public OriginContainer(Identifier identifier) {
        this.identifier = identifier;
    }

    public OriginContainer() {}

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
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
    public String toString() {
        return "OriginContainer{" +
                "identifier=" + identifier +
                ", displayName='" + displayName + '\'' +
                ", description=" + Arrays.toString(description) +
                ", impact=" + impact +
                ", icon=" + icon +
                ", authors=" + Arrays.toString(authors) +
                ", powers=" + powers +
                '}';
    }
}

/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2021 LemonyPancakes
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
package me.lemonypancakes.originsbukkit.enums;

import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Config {
    CUSTOM_ITEM_ORB_OF_ORIGIN_MATERIAL("custom_item.orb_of_origin.material", Material.MAGMA_CREAM),
    CUSTOM_ITEM_ORB_OF_ORIGIN_CUSTOM_MODEL_DATA("custom_item.orb_of_origin.custom_model_data"),
    CUSTOM_ITEM_ORB_OF_ORIGIN_DISPLAY_NAME("custom_item.orb_of_origin.display_name", "&bOrb of Origin"),
    CUSTOM_ITEM_ORB_OF_ORIGIN_LORE("custom_item.orb_of_origin.lore"),
    CUSTOM_ITEM_ORB_OF_ORIGIN_RECIPE_SHAPE("custom_item.orb_of_origin.recipe.shape", "ABA", "BCB", "ABA"),
    CUSTOM_ITEM_ORB_OF_ORIGIN_RECIPE_INGREDIENTS("custom_item.orb_of_origin.recipe.ingredients");

    private static YamlConfiguration CONFIG;
    private final String Path;
    private final Object defaultValue;

    Config(final String Path, final Object defaultValue) {
        this.Path = Path;
        this.defaultValue = defaultValue;
    }

    Config(final String Path, final String... defaultValue) {
        this.Path = Path;
        this.defaultValue = defaultValue;
    }

    public static void setFile(YamlConfiguration config) {
        CONFIG = config;
    }

    @Override
    public String toString() {
        return ChatUtils.format(CONFIG.getString(this.Path, (String) this.defaultValue));
    }

    public String[] toStringArray() {
        return ChatUtils.formatList(CONFIG.getStringList(this.Path).toArray(new String[0]));
    }

    public boolean toBoolean() {
        return CONFIG.getBoolean(this.Path);
    }

    public long toLong() {
        return CONFIG.getLong(this.Path);
    }

    public double toDouble() {
        return CONFIG.getDouble(this.Path);
    }

    public int toInt() {
        return CONFIG.getInt(this.Path);
    }

    public float toFloat() {
        return (float) CONFIG.getDouble(this.Path);
    }

    public BarColor toBarColor() {
        return BarColor.valueOf(CONFIG.getString(this.Path));
    }

    public BarStyle toBarStyle() {
        return BarStyle.valueOf(CONFIG.getString(this.Path));
    }

    public Material toMaterial() {
        return Material.valueOf(CONFIG.getString(this.Path));
    }

    public ConfigurationSection getConfigurationSection() {
        return CONFIG.getConfigurationSection(this.Path);
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public String[] getDefaultStringArrayValue() {
        return (String[]) this.defaultValue;
    }

    public String getPath() {
        return this.Path;
    }
}

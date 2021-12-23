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

import java.util.Arrays;
import java.util.Collections;

/**
 * The enum Config.
 */
public enum Config {
    NOTIFICATIONS_UPDATES("Config.Notifications.Updates", true),
    WORLDS_OVERWORLD("Config.Worlds.Overworld", "world"),
    WORLDS_NETHER("Config.Worlds.Nether", "world_nether"),
    WORLDS_THE_END("Config.Worlds.The-End", "world_the_end"),
    RECIPES_ORB_OF_ORIGIN_RECIPE("Config.Recipes.Orb-Of-Origin.Shape", "A", "A", "A"),
    RECIPES_ORB_OF_ORIGIN_INGREDIENTS("Config.Recipes.Orb-Of-Origin.Ingredients"),
    ORIGINS_ENDERIAN_MAX_HEALTH("Config.Origins.Enderian.Max-Health", 20),
    ORIGINS_ENDERIAN_WALK_SPEED("Config.Origins.Enderian.Walk-Speed", 0.2f),
    ORIGINS_ENDERIAN_FLY_SPEED("Config.Origins.Enderian.Fly-Speed", 0.1f),
    ORIGINS_ENDERIAN_WATER_DAMAGE_AMOUNT("Config.Origins.Enderian.Water-Damage.Amount", 1),
    ORIGINS_ENDERIAN_WATER_DAMAGE_DELAY("Config.Origins.Enderian.Water-Damage.Delay", 0L),
    ORIGINS_ENDERIAN_WATER_DAMAGE_PERIOD_DELAY("Config.Origins.Enderian.Water-Damage.Period-Delay", 20L),
    ORIGINS_ENDERIAN_ABILITY_COOLDOWN("Config.Origins.Enderian.Ability.Cooldown", 1),
    ORIGINS_MERLING_MAX_HEALTH("Config.Origins.Merling.Max-Health", 20),
    ORIGINS_MERLING_WALK_SPEED("Config.Origins.Merling.Walk-Speed", 0.2f),
    ORIGINS_MERLING_FLY_SPEED("Config.Origins.Merling.Fly-Speed", 0.1f),
    ORIGINS_MERLING_AIR_BREATHING_MAX_TIME("Config.Origins.Merling.Air-Breathing.Max-Time", 120),
    ORIGINS_MERLING_AIR_BREATHING_DAMAGE_AMOUNT("Config.Origins.Merling.Air-Breathing.Damage.Amount", 1),
    ORIGINS_MERLING_AIR_BREATHING_DAMAGE_DELAY("Config.Origins.Merling.Air-Breathing.Damage.Delay", 0),
    ORIGINS_MERLING_AIR_BREATHING_DAMAGE_PERIOD_DELAY("Config.Origins.Merling.Air-Breathing.Damage.Period-Delay", 20),
    ORIGINS_MERLING_BOSSBAR_AIR_BREATHING_BARCOLOR_ON_DECREASE("Config.Origins.Merling.Bossbar.Air-Breathing-Timer.BarColor.On-Decrease", BarColor.BLUE),
    ORIGINS_MERLING_BOSSBAR_AIR_BREATHING_BARCOLOR_ON_INCREASE("Config.Origins.Merling.Bossbar.Air-Breathing-Timer.BarColor.On-Increase", BarColor.GREEN),
    ORIGINS_MERLING_BOSSBAR_AIR_BREATHING_BARCOLOR_ON_DROWNING("Config.Origins.Merling.Bossbar.Air-Breathing-Timer.BarColor.On-Drowning", BarColor.RED),
    ORIGINS_MERLING_BOSSBAR_AIR_BREATHING_BARSTYLE_ON_DECREASE("Config.Origins.Merling.Bossbar.Air-Breathing-Timer.BarStyle.On-Decrease", BarStyle.SOLID),
    ORIGINS_MERLING_BOSSBAR_AIR_BREATHING_BARSTYLE_ON_INCREASE("Config.Origins.Merling.Bossbar.Air-Breathing-Timer.BarStyle.On-Increase", BarStyle.SOLID),
    ORIGINS_MERLING_BOSSBAR_AIR_BREATHING_BARSTYLE_ON_DROWNING("Config.Origins.Merling.Bossbar.Air-Breathing-Timer.BarStyle.On-Drowning", BarStyle.SOLID),
    ORIGINS_PHANTOM_MAX_HEALTH("Config.Origins.Phantom.Max-Health", 14),
    ORIGINS_PHANTOM_WALK_SPEED("Config.Origins.Phantom.Walk-Speed", 0.2f),
    ORIGINS_PHANTOM_FLY_SPEED("Config.Origins.Phantom.Fly-Speed", 0.1f),
    ORIGINS_ELYTRIAN_MAX_HEALTH("Config.Origins.Elytrian.Max-Health", 20),
    ORIGINS_ELYTRIAN_WALK_SPEED("Config.Origins.Elytrian.Walk-Speed", 0.2f),
    ORIGINS_ELYTRIAN_FLY_SPEED("Config.Origins.Elytrian.Fly-Speed", 0.1f),
    ORIGINS_ELYTRIAN_CLAUSTROPHOBIA_MAX_DURATION("Config.Origins.Elytrian.Claustrophobia.Max-Duration", 3600),
    ORIGINS_ELYTRIAN_ABILITY_COOLDOWN("Config.Origins.Elytrian.Ability.Cooldown", 30),
    ORIGINS_ELYTRIAN_ABILITY_Y_VELOCITY("Config.Origins.Elytrian.Ability.Y-Velocity", 2.1),
    ORIGINS_BLAZEBORN_MAX_HEALTH("Config.Origins.Blazeborn.Max-Health", 20),
    ORIGINS_BLAZEBORN_WALK_SPEED("Config.Origins.Blazeborn.Walk-Speed", 0.2f),
    ORIGINS_BLAZEBORN_FLY_SPEED("Config.Origins.Blazeborn.Fly-Speed", 0.1f),
    ORIGINS_BLAZEBORN_WATER_DAMAGE_AMOUNT("Config.Origins.Blazeborn.Water-Damage.Amount", 1),
    ORIGINS_BLAZEBORN_WATER_DAMAGE_DELAY("Config.Origins.Blazeborn.Water-Damage.Delay", 0L),
    ORIGINS_BLAZEBORN_WATER_DAMAGE_PERIOD_DELAY("Config.Origins.Blazeborn.Water-Damage.Period-Delay", 20L),
    ORIGINS_AVIAN_MAX_HEALTH("Config.Origins.Avian.Max-Health", 20),
    ORIGINS_AVIAN_WALK_SPEED("Config.Origins.Avian.Walk-Speed", 0.2f),
    ORIGINS_AVIAN_FLY_SPEED("Config.Origins.Avian.Fly-Speed", 0.1f),
    ORIGINS_ARACHNID_MAX_HEALTH("Config.Origins.Arachnid.Max-Health", 14),
    ORIGINS_ARACHNID_WALK_SPEED("Config.Origins.Arachnid.Walk-Speed", 0.2f),
    ORIGINS_ARACHNID_FLY_SPEED("Config.Origins.Arachnid.Fly-Speed", 0.1f),
    ORIGINS_ARACHNID_ABILITY_SPIDER_WEB_COOLDOWN("Config.Origins.Arachnid.Ability.Spider-Web.Cooldown", 5),
    ORIGINS_ARACHNID_ABILITY_CLIMBING_Y_VELOCITY("Config.Origins.Arachnid.Ability.Climbing.Y-Velocity", 0.175),
    ORIGINS_SHULK_MAX_HEALTH("Config.Origins.Shulk.Max-Health", 20),
    ORIGINS_SHULK_WALK_SPEED("Config.Origins.Shulk.Walk-Speed", 0.2f),
    ORIGINS_SHULK_FLY_SPEED("Config.Origins.Shulk.Fly-Speed", 0.1f),
    ORIGINS_SHULK_ABILITY_DIGGING_SPEED("Config.Origins.Shulk.Ability.Digging-Speed", 27),
    ORIGINS_SHULK_ABILITY_BLOCK_LIST("Config.Origins.Shulk.Ability.Block-List", Arrays.asList("STONE", "NETHERRACK")),
    ORIGINS_FELINE_MAX_HEALTH("Config.Origins.Feline.Max-Health", 18),
    ORIGINS_FELINE_WALK_SPEED("Config.Origins.Feline.Walk-Speed", 0.2f),
    ORIGINS_FELINE_FLY_SPEED("Config.Origins.Feline.Fly-Speed", 0.1f),
    ORIGINS_FELINE_WEAK_ARMS_MIN_ADJACENT_BLOCKS("Config.Origins.Feline.Weak-Arms.Min-Adjacent-Blocks", 2),
    ORIGINS_FELINE_WEAK_ARMS_BLOCK_LIST("Config.Origins.Feline.Weak-Arms.Block-List", Collections.singletonList("STONE"));

    private static YamlConfiguration CONFIG;
    private final String Path;
    private final Object defaultValue;

    /**
     * Instantiates a new Config.
     *
     * @param Path         the path
     * @param defaultValue the default value
     */
    Config(final String Path, final Object defaultValue) {
        this.Path = Path;
        this.defaultValue = defaultValue;
    }

    /**
     * Instantiates a new Config.
     *
     * @param Path         the path
     * @param defaultValue the default value
     */
    Config(final String Path, final String... defaultValue) {
        this.Path = Path;
        this.defaultValue = defaultValue;
    }

    /**
     * Sets file.
     *
     * @param config the config
     */
    public static void setFile(YamlConfiguration config) {
        CONFIG = config;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return ChatUtils.format(CONFIG.getString(this.Path, (String) this.defaultValue));
    }

    /**
     * To string list string [ ].
     *
     * @return the string [ ]
     */
    public String[] toStringList() {
        return ChatUtils.formatList(CONFIG.getStringList(this.Path).toArray(new String[0]));
    }

    /**
     * To boolean boolean.
     *
     * @return the boolean
     */
    public boolean toBoolean() {
        return CONFIG.getBoolean(this.Path);
    }

    /**
     * To long long.
     *
     * @return the long
     */
    public long toLong() {
        return CONFIG.getLong(this.Path);
    }

    /**
     * To double double.
     *
     * @return the double
     */
    public double toDouble() {
        return CONFIG.getDouble(this.Path);
    }

    /**
     * To int int.
     *
     * @return the int
     */
    public int toInt() {
        return CONFIG.getInt(this.Path);
    }

    /**
     * To float float.
     *
     * @return the float
     */
    public float toFloat() {
        return (float) CONFIG.getDouble(this.Path);
    }

    /**
     * To bar color bar color.
     *
     * @return the bar color
     */
    public BarColor toBarColor() {
        return BarColor.valueOf(CONFIG.getString(this.Path));
    }

    /**
     * To bar style bar style.
     *
     * @return the bar style
     */
    public BarStyle toBarStyle() {
        return BarStyle.valueOf(CONFIG.getString(this.Path));
    }

    /**
     * To material material.
     *
     * @return the material
     */
    public Material toMaterial() {
        return Material.valueOf(CONFIG.getString(this.Path));
    }

    /**
     * Gets configuration section.
     *
     * @return the configuration section
     */
    public ConfigurationSection getConfigurationSection() {
        return CONFIG.getConfigurationSection(this.Path);
    }

    /**
     * Gets default value.
     *
     * @return the default value
     */
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * Get default string list value string [ ].
     *
     * @return the string [ ]
     */
    public String[] getDefaultStringListValue() {
        return (String[]) this.defaultValue;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return this.Path;
    }
}

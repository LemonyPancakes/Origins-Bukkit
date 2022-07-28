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
package me.lemonypancakes.bukkit.origins.util;

import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    COMMAND_INVALID_IDENTIFIER("command.invalid_identifier", "Invalid identifier: %identifier%"),
    COMMAND_ORIGIN_UNKNOWN_ORIGIN_LAYER("command.origin.invalid_identifier", "Unknown origin layer: %origin_layer%"),
    COMMAND_ORIGIN_UNKNOWN_ORIGIN("command.origin.unknown_origin", "Unknown origin: %origin%"),
    COMMAND_ORIGIN_PRUNE_SUCCESS("command.origin.prune.success", "Successfully pruned %player%'s origin in origin layer %origin_layer%."),
    COMMAND_ORIGIN_PRUNE_NO_ORIGIN("command.origin.prune.no_origin", "&cPlayer %player% has no origin from in origin layer %origin_layer%."),
    COMMAND_ORIGIN_SET_SUCCESS("command.origin.set.success", "Successfully set %player%'s origin in origin layer %origin_layer% to %new_origin%."),
    COMMAND_ORIGIN_SET_ALREADY_HAD("command.origin.set.already_had", "&cPlayer %player% already had the origin %new_origin% in origin layer %origin_layer%."),
    COMMAND_ORIGIN_ITEM_UNKNOWN_ORIGIN_ITEM("command.origin.item.unknown_origin_item", "Unknown origin item: %origin_item%"),
    COMMAND_ORIGIN_ITEM_GIVE("command.origin.item.give", "Gave %amount% [%origin_item%&r] to %player%."),
    COMMAND_POWER_UNKNOWN_POWER("command.power.unknown_power", "Unknown power: %power%"),
    COMMAND_POWER_ADD_SUCCESS("command.power.add.success", "Successfully added the power %power% to %player% from power source %power_source%."),
    COMMAND_POWER_ADD_HAD_ALREADY("command.power.add.had_already", "&cPlayer %player% already had the power %power% from power source %power_source%."),
    COMMAND_POWER_REMOVE_SUCCESS("command.power.remove.success", "Successfully removed the power %power% from %player% from power source %power_source%."),
    COMMAND_POWER_REMOVE_NO_POWER("command.power.remove.no_power", "&cPlayer %player% did not have the power %power% from power source %power_source%."),
    COMMAND_POWER_REMOVE_ALL_SUCCESS("command.power.remove-all.success", "Successfully removed all of %player%'s powers from power source %power_source%."),
    COMMAND_POWER_REMOVE_ALL_NO_POWER("command.power.remove-all.no_power", "&cPlayer %player% does not have powers from power source %power_source%."),
    COMMAND_POWER_CLEAR_SUCCESS("command.power.clear.success", "Successfully removed all of %player%'s powers."),
    COMMAND_POWER_CLEAR_NO_POWER("command.power.clear.no_power", "&cPlayer %player% does not have powers."),
    COMMAND_POWER_LIST_HAS_POWER("command.power.list.has_power", "Player %player% has %powers_count% powers: %powers%."),
    COMMAND_POWER_LIST_NO_POWER("command.power.list.no_power", "&cPlayer %player% does not have powers.");

    private static YamlConfiguration LANG;
    private final String Path;
    private final Object defaultValue;

    Lang(final String Path, final Object defaultValue) {
        this.Path = Path;
        this.defaultValue = defaultValue;
    }

    Lang(final String Path, final String... defaultValue) {
        this.Path = Path;
        this.defaultValue = defaultValue;
    }

    public static void setFile(YamlConfiguration lang) {
        LANG = lang;
    }

    @Override
    public String toString() {
        return ChatUtils.format(LANG.getString(this.Path, (String) this.defaultValue));
    }

    public String[] toStringArray() {
        return ChatUtils.format(LANG.getStringList(this.Path).toArray(new String[0]));
    }

    public boolean toBoolean() {
        return LANG.getBoolean(this.Path);
    }

    public long toLong() {
        return LANG.getLong(this.Path);
    }

    public double toDouble() {
        return LANG.getDouble(this.Path);
    }

    public int toInt() {
        return LANG.getInt(this.Path);
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public String[] getDefaultStringListValue() {
        return (String[]) this.defaultValue;
    }

    public String getPath() {
        return this.Path;
    }
}

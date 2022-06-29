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
    GUI_HEADER_TEXT("gui.header_text", "&0Choose your origin."),
    GUI_HEADER_TEXT_NO_ORIGIN("gui.header_no_origin_text", "&0No origins here. :("),
    GUI_DUMMY_ORIGIN_TITLE("gui.dummy_origin.title", "&fDummy Origin"),
    GUI_DUMMY_ORIGIN_DESCRIPTION("gui.dummy_origin.description", "&7This appears when there", "&7is no origin available."),
    GUI_IMPACT_TEXT_NONE("gui.impact_text.none", "&fImpact: &7None"),
    GUI_IMPACT_TEXT_LOW("gui.impact_text.low", "&fImpact: &aLow"),
    GUI_IMPACT_TEXT_MEDIUM("gui.impact_text.medium", "&fImpact: &eMedium"),
    GUI_IMPACT_TEXT_HIGH("gui.impact_text.high", "&fImpact: &cHigh"),
    GUI_ICON_TEXT_PREVIOUS_PAGE("gui.icon_text.previous_page", "&6Previous Page"),
    GUI_ICON_TEXT_QUIT_GAME("gui.icon_text.quit_game", "&cQuit Game"),
    GUI_ICON_TEXT_NEXT_PAGE("gui.icon_text.next_page", "&6Next Page"),
    COMMAND_NO_PERMISSION("command.no_permission", "&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."),
    COMMAND_NOT_ENOUGH_ARGUMENTS("command.not_enough_arguments", "&cNot Enough Arguments. Usage: &e{command_usage}"),
    COMMAND_TOO_MANY_ARGUMENTS("command.too_many_arguments", "&cToo many arguments. Usage: &e{command_usage}"),
    COMMAND_PLAYER_NOT_FOUND("command.player_not_found", "&cPlayer {player} not found. Player must be online to do this."),
    COMMAND_MAIN_COMMAND_USAGE("command.main_command_usage", "/origins <subcommand>"),
    SUBCOMMAND_HELP_USAGE("subcommand.help.usage", "/origins help"),
    SUBCOMMAND_HELP_DESCRIPTION("subcommand.help.description", "Displays a help message."),
    SUBCOMMAND_HELP_HEADER("subcommand.help.header", "&aAvailable Commands:\n&r"),
    SUBCOMMAND_HELP_SYNTAX("subcommand.help.syntax", "&e{subcommand_usage} &8- &7{subcommand_description}"),
    SUBCOMMAND_HELP_FOOTER("subcommand.help.footer"),
    SUBCOMMAND_UPDATE_USAGE("subcommand.update.usage", "/origins update <player> <new_origin>"),
    SUBCOMMAND_UPDATE_DESCRIPTION("subcommand.update.description", "Changes the player's origin."),
    SUBCOMMAND_UPDATE_ORIGIN_CHANGE("subcommand.update.origin_change", "&aSuccessfully changed {player}'s origin to (&e\"{new_origin}\"&a)."),
    SUBCOMMAND_UPDATE_CANNOT_FIND_ORIGIN("subcommand.update.cannot_find_origin", "&cCannot find the origin (&e\"{new_origin}\"&c)."),
    SUBCOMMAND_UPDATE_NO_CHANGES("subcommand.update.no_changes", "&cNothing changed. {player}'s origin is already (&e\"{new_origin}\"&c)."),
    SUBCOMMAND_PRUNE_USAGE("subcommand.prune.usage", "/origins prune <player>"),
    SUBCOMMAND_PRUNE_DESCRIPTION("subcommand.prune.description", "Deletes the player's origin data."),
    SUBCOMMAND_PRUNE_SUCCESS("subcommand.prune.prune_success", "&aSuccessfully pruned {player}'s data."),
    SUBCOMMAND_PRUNE_CANNOT_FIND_DATA("subcommand.prune.cannot_find_data", "&cCannot find {player}'s data."),
    SUBCOMMAND_GIVE_USAGE("subcommand.give.usage", "/origins give <player> <item> (amount)"),
    SUBCOMMAND_GIVE_DESCRIPTION("subcommand.give.description", "Gives the player origins items."),
    SUBCOMMAND_GIVE_SUCCESS("subcommand.give.give_success", "&aGave {amount} [{item}&a] to {player}."),
    SUBCOMMAND_GIVE_CANNOT_FIND_ITEM("subcommand.give.cannot_find_item", "&cUnknown item '{item}'"),
    SUBCOMMAND_GIVE_INVALID_AMOUNT("subcommand.give.invalid_amount", "&c{amount} is not an integer."),
    SUBCOMMAND_INFO_USAGE("subcommand.info.usage", "/origins info (player)"),
    SUBCOMMAND_INFO_DESCRIPTION("subcommand.info.description", "Displays info about the player's origin."),
    SUBCOMMAND_INFO_CANNOT_FIND_DATA("subcommand.info.cannot_find_data", "&cCannot find {player}'s data."),
    SUBCOMMAND_INFO_PLAYERS_ONLY("subcommand.info.players_only", "&cOnly players can execute this command.");


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
        return ChatUtils.formatList(LANG.getStringList(this.Path).toArray(new String[0]));
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

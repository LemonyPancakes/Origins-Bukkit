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
package me.lemonypancakes.originsbukkit.commands.maincommand.subcommands;

import me.lemonypancakes.originsbukkit.commands.maincommand.MainCommand;
import me.lemonypancakes.originsbukkit.enums.Permissions;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Help.
 *
 * @author LemonyPancakes
 */
public class Help {

    private final MainCommand mainCommand;

    /**
     * Gets main command.
     *
     * @return the main command
     */
    public MainCommand getMainCommand() {
        return mainCommand;
    }

    /**
     * Instantiates a new Help.
     *
     * @param mainCommand the main command
     */
    public Help(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    /**
     * Help sub command.
     *
     * @param sender  the sender
     * @param command the command
     * @param label   the label
     * @param args    the args
     */
    public void HelpSubCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                helpMessagePlayer(player);
            } else if (args.length == 1) {
                helpMessagePlayer(player);
            } else {
                ChatUtils.sendCommandSenderMessage(sender, "&cToo many arguments. Usage: /origins help");
            }
        } else {
            if (args.length == 0) {
                helpMessageConsole(sender);
            } else if (args.length == 1) {
                helpMessageConsole(sender);
            } else {
                ChatUtils.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Too many arguments. Usage: /origins help");
            }
        }
    }

    /**
     * Help sub command tab complete list.
     *
     * @param sender  the sender
     * @param command the command
     * @param alias   the alias
     * @param args    the args
     *
     * @return the list
     */
    public List<String> HelpSubCommandTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> empty = new ArrayList<>();

        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> subCommand = new ArrayList<>();
                subCommand.add("help");

                return subCommand;
            }
        } else {
            if (args.length == 1) {
                List<String> subCommand = new ArrayList<>();
                subCommand.add("help");

                return subCommand;
            }
        }
        return empty;
    }

    /**
     * Help message player.
     *
     * @param player the player
     */
    public void helpMessagePlayer(Player player) {
        ChatUtils.sendCommandSenderMessage(player, "&aAvailable Commands:");
        ChatUtils.sendCommandSenderMessage(player, "");
        ChatUtils.sendCommandSenderMessage(player, "&e/origins &chelp &b- Prints this help message");
        if (player.hasPermission(Permissions.UPDATE.toString())) {
            ChatUtils.sendCommandSenderMessage(player, "&e/origins &cupdate &6<player> <new origin>&r &b- Updates player origin");
        }
        if (player.hasPermission(Permissions.PRUNE.toString())) {
            ChatUtils.sendCommandSenderMessage(player, "&e/origins &cprune &6<player>&r &b- Deletes the player's origin data");
        }
        if (player.hasPermission(Permissions.RELOAD.toString())) {
            ChatUtils.sendCommandSenderMessage(player, "&e/origins &creload &b- Reloads the files");
        }
        if (player.hasPermission(Permissions.GIVE.toString())) {
            ChatUtils.sendCommandSenderMessage(player, "&e/origins &cgive &6<player> <item> &a[amount] &b- Gives the player origins items");
        }
    }

    /**
     * Help message console.
     *
     * @param commandSender the command sender
     */
    public void helpMessageConsole(CommandSender commandSender) {
        ChatUtils.sendCommandSenderMessage(commandSender, "&aAvailable Console Commands:");
        ChatUtils.sendCommandSenderMessage(commandSender, "");
        ChatUtils.sendCommandSenderMessage(commandSender, "&e/origins &chelp &b- Prints this help message");
        ChatUtils.sendCommandSenderMessage(commandSender, "&e/origins &cupdate &6<player> <new origin>&r &b- Updates player origin");
        ChatUtils.sendCommandSenderMessage(commandSender, "&e/origins &cprune &6<player>&r &b- Deletes the player's origin data");
        ChatUtils.sendCommandSenderMessage(commandSender, "&e/origins &creload &b- Reloads the files");
        ChatUtils.sendCommandSenderMessage(commandSender, "&e/origins &cgive &6<player> <item> &a[amount] &b- Gives the player origins items");
    }
}

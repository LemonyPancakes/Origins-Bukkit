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
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Help {

    private final MainCommand mainCommand;

    public MainCommand getMainCommand() {
        return mainCommand;
    }

    public Help(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void HelpSubCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                helpMessagePlayer(player);
            } else if (args.length == 1) {
                helpMessagePlayer(player);
            } else {
                Message.sendCommandSenderMessage(sender, "&cToo many arguments. Usage: /origins help");
            }
        } else {
            if (args.length == 0) {
                helpMessageConsole(sender);
            } else if (args.length == 1) {
                helpMessageConsole(sender);
            } else {
                Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Too many arguments. Usage: /origins help");
            }
        }
    }

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

    public void helpMessagePlayer(Player player) {
        Message.sendCommandSenderMessage(player, "&aAvailable Commands:");
        Message.sendCommandSenderMessage(player, "");
        Message.sendCommandSenderMessage(player, "&e/origins &chelp &b- Prints this help message");
        if (player.hasPermission(Permissions.UPDATE.toString())) {
            Message.sendCommandSenderMessage(player, "&e/origins &cupdate &6<player> <new origin>&r &b- Updates player origin");
        }
        if (player.hasPermission(Permissions.PRUNE.toString())) {
            Message.sendCommandSenderMessage(player, "&e/origins &cprune &6<player>&r &b- Deletes the player's origin data");
        }
        if (player.hasPermission(Permissions.RELOAD.toString())) {
            Message.sendCommandSenderMessage(player, "&e/origins &creload &b- Reloads the files");
        }
        if (player.hasPermission(Permissions.GIVE.toString())) {
            Message.sendCommandSenderMessage(player, "&e/origins &cgive &6<player> <item> &a[amount] &b- Gives the player origins items");
        }
    }

    public void helpMessageConsole(CommandSender commandSender) {
        Message.sendCommandSenderMessage(commandSender, "&aAvailable Console Commands:");
        Message.sendCommandSenderMessage(commandSender, "");
        Message.sendCommandSenderMessage(commandSender, "&e/origins &chelp &b- Prints this help message");
        Message.sendCommandSenderMessage(commandSender, "&e/origins &cupdate &6<player> <new origin>&r &b- Updates player origin");
        Message.sendCommandSenderMessage(commandSender, "&e/origins &cprune &6<player>&r &b- Deletes the player's origin data");
        Message.sendCommandSenderMessage(commandSender, "&e/origins &creload &b- Reloads the files");
        Message.sendCommandSenderMessage(commandSender, "&e/origins &cgive &6<player> <item> &a[amount] &b- Gives the player origins items");
    }
}

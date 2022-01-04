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
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Permissions;
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Reload {

    private final MainCommand mainCommand;

    public MainCommand getMainCommand() {
        return mainCommand;
    }

    public Reload(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void ReloadSubCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission(Permissions.RELOAD.toString())) {
                if (args.length == 1) {
                    Message.sendCommandSenderMessage(sender, "&3Reloading the plugin files...");
                    try {
                        getMainCommand().getCommandHandler().getPlugin().getConfigHandler().reloadFiles();
                    } catch (Exception event) {
                        event.printStackTrace();
                    }
                    Message.sendCommandSenderMessage(sender, "&aSuccessfully reloaded the plugin files.");
                } else {
                    Message.sendCommandSenderMessage(sender, "&cToo many arguments. Usage: /origins reload");
                }
            } else {
                Message.sendCommandSenderMessage(sender, Lang.NO_PERMISSION_COMMAND.toString());
            }
        } else {
            if (args.length == 1) {
                Message.sendCommandSenderMessage(sender, "&3[Origins-Bukkit] Reloading the plugin files...");
                try {
                    getMainCommand().getCommandHandler().getPlugin().getConfigHandler().reloadFiles();
                } catch (Exception event) {
                    event.printStackTrace();
                }
                Message.sendCommandSenderMessage(sender, "&a[Origins-Bukkit] Successfully reloaded the plugin files.");
            } else {
                Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Too many arguments. Usage: /origins reload");
            }
        }
    }

    public List<String> ReloadSubCommandTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> empty = new ArrayList<>();

        if (sender instanceof Player) {
            if (sender.hasPermission(Permissions.RELOAD.toString())) {
                if (args.length == 1) {
                    List<String> subCommand = new ArrayList<>();
                    subCommand.add("reload");

                    return subCommand;
                }
            }
        } else {
            if (args.length == 1) {
                List<String> subCommand = new ArrayList<>();
                subCommand.add("reload");

                return subCommand;
            }
        }
        return empty;
    }
}

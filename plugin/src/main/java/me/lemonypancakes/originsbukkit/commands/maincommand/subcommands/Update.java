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
import me.lemonypancakes.originsbukkit.storage.wrappers.OriginsPlayerDataWrapper;
import me.lemonypancakes.originsbukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Update {

    private final MainCommand mainCommand;

    public MainCommand getMainCommand() {
        return mainCommand;
    }

    public Update(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void UpdateSubCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission(Permissions.UPDATE.toString())) {
                if (args.length == 1) {
                    Message.sendCommandSenderMessage(sender, "&cNot Enough Arguments. Usage: /origins update <player> <new origin>");
                } else if (args.length == 2) {
                    Message.sendCommandSenderMessage(sender, "&cNot Enough Arguments. Usage: /origins update " + args[1] + " <new origin>");
                } else if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    String origin = args[2];
                    List<String> origins = getMainCommand().getCommandHandler().getPlugin().getOriginsList();

                    if (target != null) {
                        UUID playerUUID = target.getUniqueId();
                        String playerName = target.getName();

                        if (origins.contains(origin)) {
                            if (getMainCommand().getCommandHandler().getPlugin().getStorageHandler().getOriginsPlayerData().findOriginsPlayerData(playerUUID) != null) {
                                if (!getMainCommand().getCommandHandler().getPlugin().getStorageHandler().getOriginsPlayerData().getPlayerOrigin(playerUUID).equals(origin)) {
                                    getMainCommand().getCommandHandler().getPlugin().getStorageHandler().getOriginsPlayerData().updateOriginsPlayerData(playerUUID, new OriginsPlayerDataWrapper(playerUUID, playerName, origin));
                                    getMainCommand().getCommandHandler().getPlugin().getListenerHandler().getPlayerOriginChecker().checkPlayerOriginData(target);
                                    Message.sendCommandSenderMessage(sender, "&aChanged " + playerName + "'s origin to " + origin);
                                } else {
                                    Message.sendCommandSenderMessage(sender, "&cNothing changed. Player's origin is already " + origin);
                                }
                            } else {
                                Message.sendCommandSenderMessage(sender, "&cCannot find " + playerName + "'s data");
                            }
                        } else {
                            Message.sendCommandSenderMessage(sender, "&cCannot find the origins " + args[2]);
                        }
                    } else {
                        Message.sendCommandSenderMessage(sender, "&cPlayer " + args[1] + " not found. Player must be online to do this.");
                    }
                } else {
                    Message.sendCommandSenderMessage(sender, "&cToo many arguments. Usage: /origins update <player> <new origin>");
                }
            } else {
                Message.sendCommandSenderMessage(sender, Lang.NO_PERMISSION_COMMAND.toString());
            }
        } else {
            if (args.length == 1) {
                Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Not Enough Arguments. Usage: /origins update <player> <new origin>");
            } else if (args.length == 2) {
                Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Not Enough Arguments. Usage: /origins update " + args[1] + " <new origin>");
            } else if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                String origin = args[2];
                List<String> origins = getMainCommand().getCommandHandler().getPlugin().getOriginsList();

                if (target != null) {
                    UUID playerUUID = target.getUniqueId();
                    String playerName = target.getName();

                    if (origins.contains(origin)) {
                        if (getMainCommand().getCommandHandler().getPlugin().getStorageHandler().getOriginsPlayerData().findOriginsPlayerData(playerUUID) != null) {
                            if (!getMainCommand().getCommandHandler().getPlugin().getStorageHandler().getOriginsPlayerData().getPlayerOrigin(playerUUID).equals(origin)) {
                                getMainCommand().getCommandHandler().getPlugin().getStorageHandler().getOriginsPlayerData().updateOriginsPlayerData(playerUUID, new OriginsPlayerDataWrapper(playerUUID, playerName, origin));
                                getMainCommand().getCommandHandler().getPlugin().getListenerHandler().getPlayerOriginChecker().checkPlayerOriginData(target);
                                Message.sendCommandSenderMessage(sender, "&a[Origins-Bukkit] Changed " + playerName + "'s origin to " + origin);
                            } else {
                                Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Nothing changed. Player's origin is already " + origin);
                            }
                        } else {
                            Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Cannot find " + playerName + "'s data");
                        }
                    } else {
                        Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Cannot find the origins " + args[2]);
                    }
                } else {
                    Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Player " + args[1] + " not found. Player must be online to do this.");
                }
            } else {
                Message.sendCommandSenderMessage(sender, "&c[Origins-Bukkit] Too many arguments. Usage: /origins update <player> <new origin>");
            }
        }
    }

    public List<String> UpdateSubCommandTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> empty = new ArrayList<>();

        if (sender instanceof Player) {
            if (sender.hasPermission(Permissions.UPDATE.toString())) {
                if (args.length == 1) {
                    List<String> subCommand = new ArrayList<>();
                    subCommand.add("update");

                    return subCommand;
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("update")) {
                        List<String> playerNames = new ArrayList<>();
                        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                        Bukkit.getServer().getOnlinePlayers().toArray(players);
                        for (Player player : players) {
                            playerNames.add(player.getName());
                        }

                        return playerNames;
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("update")) {
                        List<String> originsList = new ArrayList<>();
                        for (String origins : getMainCommand().getCommandHandler().getPlugin().getOriginsList()) {
                            if (origins.startsWith(args[2])) {
                                originsList.add(origins);
                            }
                        }

                        return originsList;
                    }
                }
            }
        } else {
            if (args.length == 1) {
                List<String> subCommand = new ArrayList<>();
                subCommand.add("update");

                return subCommand;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("update")) {
                    List<String> playerNames = new ArrayList<>();
                    Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                    Bukkit.getServer().getOnlinePlayers().toArray(players);
                    for (Player player : players) {
                        playerNames.add(player.getName());
                    }

                    return playerNames;
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("update")) {
                    List<String> originsList = new ArrayList<>();
                    for (String origins : getMainCommand().getCommandHandler().getPlugin().getOriginsList()) {
                        if (origins.startsWith(args[2])) {
                            originsList.add(origins);
                        }
                    }

                    return originsList;
                }
            }
        }
        return empty;
    }
}

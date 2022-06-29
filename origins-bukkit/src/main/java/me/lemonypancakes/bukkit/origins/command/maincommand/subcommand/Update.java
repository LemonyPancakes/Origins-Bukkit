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
package me.lemonypancakes.bukkit.origins.command.maincommand.subcommand;

import me.lemonypancakes.bukkit.origins.Origin;
import me.lemonypancakes.bukkit.origins.OriginPlayer;
import me.lemonypancakes.bukkit.origins.command.maincommand.MainCommand;
import me.lemonypancakes.bukkit.origins.data.storage.other.Misc;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Lang;
import me.lemonypancakes.bukkit.origins.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Update {

    private final MainCommand mainCommand;

    public Update(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void onCommand(@Nonnull CommandSender commandSender,
                          @Nonnull Command command,
                          @Nonnull String label,
                          @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (!player.hasPermission(Permissions.UPDATE.toString())) {
                ChatUtils.sendCommandSenderMessage(
                        commandSender,
                        ChatUtils.Type.ERROR,
                        Lang.COMMAND_NO_PERMISSION.toString()
                );
                return;
            }
        }
        if (args.length < 3) {
            ChatUtils.sendCommandSenderMessage(
                    commandSender,
                    ChatUtils.Type.ERROR,
                    Lang.COMMAND_NOT_ENOUGH_ARGUMENTS.toString()
                            .replace("{command_usage}", Lang.SUBCOMMAND_UPDATE_USAGE.toString()
                            )
            );
        } else if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            String origin = args[2];

            if (target != null) {
                UUID playerUUID = target.getUniqueId();
                String playerName = target.getName();
                String cannotFindOriginMessage = Lang.SUBCOMMAND_UPDATE_CANNOT_FIND_ORIGIN.toString()
                        .replace("{player}", playerName)
                        .replace("{new_origin}", origin);

                if (origin.contains(":")) {
                    Identifier originIdentifier = new Identifier(
                            origin.split(":")[0],
                            origin.split(":")[1]
                    );

                    if (!origin.equals("origins-bukkit:dummy_origin")) {
                        if (mainCommand.getPlugin().getRegistry().hasOrigin(originIdentifier)) {
                            String originChangeMessage = Lang.SUBCOMMAND_UPDATE_ORIGIN_CHANGE.toString()
                                    .replace("{player}", playerName)
                                    .replace("{new_origin}", origin
                                    );
                            OriginPlayer originPlayer = mainCommand.getPlugin().getOriginPlayer(target);

                            if (originPlayer != null) {
                                Origin playerOrigin = originPlayer.getOrigin();

                                if (playerOrigin != null) {
                                    if (!playerOrigin.getIdentifier().equals(originIdentifier)) {
                                        originPlayer.setOrigin(mainCommand.getPlugin().getRegistry().getOrigin(originIdentifier));
                                        ChatUtils.sendCommandSenderMessage(
                                                commandSender,
                                                ChatUtils.Type.SUCCESS,
                                                originChangeMessage
                                        );
                                    } else {
                                        ChatUtils.sendCommandSenderMessage(
                                                commandSender,
                                                ChatUtils.Type.ERROR,
                                                Lang.SUBCOMMAND_UPDATE_NO_CHANGES.toString()
                                                        .replace("{player}", playerName)
                                                        .replace("{new_origin}", origin
                                                        )
                                        );
                                    }
                                } else {
                                    originPlayer.setOrigin(mainCommand.getPlugin().getRegistry().getOrigin(originIdentifier));
                                    ChatUtils.sendCommandSenderMessage(
                                            commandSender,
                                            ChatUtils.Type.SUCCESS,
                                            originChangeMessage
                                    );
                                }
                            }
                        } else {
                            ChatUtils.sendCommandSenderMessage(
                                    commandSender,
                                    ChatUtils.Type.ERROR,
                                    cannotFindOriginMessage
                            );
                        }
                    } else {
                        ChatUtils.sendCommandSenderMessage(
                                commandSender,
                                ChatUtils.Type.ERROR,
                                cannotFindOriginMessage
                        );
                    }
                } else {
                    ChatUtils.sendCommandSenderMessage(
                            commandSender,
                            ChatUtils.Type.ERROR,
                            cannotFindOriginMessage
                    );
                }
            } else {
                ChatUtils.sendCommandSenderMessage(
                        commandSender,
                        ChatUtils.Type.ERROR,
                        Lang.COMMAND_PLAYER_NOT_FOUND.toString()
                                .replace("{player}", args[1]
                                )
                );
            }
        } else {
            ChatUtils.sendCommandSenderMessage(
                    commandSender,
                    ChatUtils.Type.ERROR,
                    Lang.COMMAND_TOO_MANY_ARGUMENTS.toString()
                            .replace("{command_usage}", Lang.SUBCOMMAND_UPDATE_USAGE.toString()
                            )
            );
        }
    }

    public List<String> onTabComplete(@Nonnull CommandSender commandSender,
                                      @Nonnull Command command,
                                      @Nonnull String alias,
                                      @Nonnull String[] args) {
        List<String> empty = new ArrayList<>();

        if (commandSender instanceof Player) {
            if (!commandSender.hasPermission(Permissions.UPDATE.toString())) {
                return empty;
            }
        }
        if (args.length == 1) {
            List<String> subCommand = new ArrayList<>();
            if ("update".startsWith(args[0])) {
                subCommand.add("update");
            }

            return subCommand;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("update")) {
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    if (player.getName().startsWith(args[1])) {
                        playerNames.add(player.getName());
                    }
                }

                return playerNames;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("update")) {
                List<String> originsList = new ArrayList<>();
                for (String origins : Misc.ORIGINS_AS_STRING) {
                    if (origins.startsWith(args[2])) {
                        originsList.add(origins);
                    }
                }

                return originsList;
            }
        }
        return empty;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}

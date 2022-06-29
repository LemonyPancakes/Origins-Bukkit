package me.lemonypancakes.bukkit.origins.command.maincommand.subcommand;

import me.lemonypancakes.bukkit.origins.command.maincommand.MainCommand;
import me.lemonypancakes.bukkit.origins.data.storage.other.Misc;
import me.lemonypancakes.bukkit.origins.util.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Info {

    private final MainCommand mainCommand;

    public Info(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void onCommand(@Nonnull CommandSender commandSender,
                          @Nonnull Command command,
                          @Nonnull String label,
                          @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (!player.hasPermission(Permissions.INFO_SELF.toString()) && !player.hasPermission(Permissions.INFO_OTHERS.toString())) {
                ChatUtils.sendCommandSenderMessage(
                        commandSender,
                        ChatUtils.Type.ERROR,
                        Lang.COMMAND_NO_PERMISSION.toString()
                );
                return;
            }
        } else {
            ChatUtils.sendCommandSenderMessage(
                    commandSender,
                    ChatUtils.Type.ERROR,
                    Lang.SUBCOMMAND_INFO_PLAYERS_ONLY.toString()
            );
            return;
        }
        if (args.length < 2) {
            Player player = (Player) commandSender;
            Identifier identifier = mainCommand.getPlugin().getStorage().getOrigin(player);

            if (identifier != null) {
                Inventory inventory = Misc.ORIGINS_INFO_GUI.get(identifier.toString());

                if (inventory != null) {
                    player.openInventory(inventory);
                } else {
                    ChatUtils.sendCommandSenderMessage(
                            commandSender,
                            ChatUtils.Type.ERROR,
                            Lang.SUBCOMMAND_INFO_CANNOT_FIND_DATA.toString()
                                    .replace("{player}", commandSender.getName()
                                    )
                    );
                }
            } else {
                ChatUtils.sendCommandSenderMessage(
                        commandSender,
                        ChatUtils.Type.ERROR,
                        Lang.SUBCOMMAND_INFO_CANNOT_FIND_DATA.toString()
                                .replace("{player}", commandSender.getName()
                                )
                );
            }
        } else if (args.length == 2) {
            Player player = (Player) commandSender;

            if (!player.hasPermission(Permissions.INFO_OTHERS.toString())) {
                ChatUtils.sendCommandSenderMessage(
                        commandSender,
                        ChatUtils.Type.ERROR,
                        Lang.COMMAND_NO_PERMISSION.toString()
                );
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                String playerName = target.getName();
                String originIdentifier = BukkitPersistentDataUtils.getPersistentData(
                        target,
                        "origins-bukkit:origin",
                        PersistentDataType.STRING);

                if (originIdentifier != null) {
                    Inventory inventory = Misc.ORIGINS_INFO_GUI.get(originIdentifier);

                    if (inventory != null) {
                        player.openInventory(inventory);
                    } else {
                        ChatUtils.sendCommandSenderMessage(
                                commandSender,
                                ChatUtils.Type.ERROR,
                                Lang.SUBCOMMAND_INFO_CANNOT_FIND_DATA.toString()
                                        .replace("{player}", target.getName()
                                        )
                        );
                    }
                } else {
                    ChatUtils.sendCommandSenderMessage(
                            commandSender,
                            ChatUtils.Type.ERROR,
                            Lang.SUBCOMMAND_INFO_CANNOT_FIND_DATA.toString()
                                    .replace("{player}", target.getName()
                                    )
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
                            .replace("{command_usage}", Lang.SUBCOMMAND_INFO_USAGE.toString()
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
            if (!commandSender.hasPermission(Permissions.INFO_SELF.toString()) && !commandSender.hasPermission(Permissions.INFO_OTHERS.toString())) {
                return empty;
            }
        }
        if (args.length == 1) {
            List<String> subCommand = new ArrayList<>();
            if ("info".startsWith(args[0])) {
                subCommand.add("info");
            }

            return subCommand;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                if (commandSender instanceof Player) {
                    if (commandSender.hasPermission(Permissions.INFO_OTHERS.toString())) {
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
                }
            }
        }
        return empty;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}

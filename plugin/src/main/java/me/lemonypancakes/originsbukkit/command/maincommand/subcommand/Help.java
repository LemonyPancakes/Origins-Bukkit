package me.lemonypancakes.originsbukkit.command.maincommand.subcommand;

import me.lemonypancakes.originsbukkit.command.maincommand.MainCommand;
import me.lemonypancakes.originsbukkit.util.ChatUtil;
import me.lemonypancakes.originsbukkit.util.Lang;
import me.lemonypancakes.originsbukkit.util.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Help {

    private final MainCommand mainCommand;

    public Help(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void onCommand(@Nonnull CommandSender commandSender,
                             @Nonnull Command command,
                             @Nonnull String label,
                             @Nonnull String[] args) {
        if (!commandSender.hasPermission(Permissions.HELP.toString())) {
            ChatUtil.sendCommandSenderMessage(
                    commandSender,
                    ChatUtil.Type.ERROR,
                    Lang.COMMAND_NO_PERMISSION.toString()
            );
            return;
        }
        if (args.length < 2) {
            ChatUtil.sendCommandSenderMessage(
                    commandSender,
                    ChatUtil.Type.INFO,
                    Lang.SUBCOMMAND_HELP_HEADER.toString()
            );
            ChatUtil.sendCommandSenderMessage(
                    commandSender,
                    ChatUtil.Type.INFO,
                    Lang.SUBCOMMAND_HELP_SYNTAX.toString()
                            .replace("{subcommand_usage}", Lang.SUBCOMMAND_HELP_USAGE.toString())
                            .replace("{subcommand_description}", Lang.SUBCOMMAND_HELP_DESCRIPTION.toString()
                            )
            );
            if (commandSender.hasPermission(Permissions.PRUNE.toString())) {
                ChatUtil.sendCommandSenderMessage(
                        commandSender,
                        ChatUtil.Type.INFO,
                        Lang.SUBCOMMAND_HELP_SYNTAX.toString()
                                .replace("{subcommand_usage}", Lang.SUBCOMMAND_PRUNE_USAGE.toString())
                                .replace("{subcommand_description}", Lang.SUBCOMMAND_PRUNE_DESCRIPTION.toString()
                                )
                );
            }
            if (commandSender.hasPermission(Permissions.UPDATE.toString())) {
                ChatUtil.sendCommandSenderMessage(
                        commandSender,
                        ChatUtil.Type.INFO,
                        Lang.SUBCOMMAND_HELP_SYNTAX.toString()
                                .replace("{subcommand_usage}", Lang.SUBCOMMAND_UPDATE_USAGE.toString())
                                .replace("{subcommand_description}", Lang.SUBCOMMAND_UPDATE_DESCRIPTION.toString()
                                )
                );
            }
            if (commandSender.hasPermission(Permissions.GIVE.toString())) {
                ChatUtil.sendCommandSenderMessage(
                        commandSender,
                        ChatUtil.Type.INFO,
                        Lang.SUBCOMMAND_HELP_SYNTAX.toString()
                                .replace("{subcommand_usage}", Lang.SUBCOMMAND_GIVE_USAGE.toString())
                                .replace("{subcommand_description}", Lang.SUBCOMMAND_GIVE_DESCRIPTION.toString()
                                )
                );
            }
            if (commandSender.hasPermission(Permissions.INFO_SELF.toString()) || commandSender.hasPermission(Permissions.INFO_OTHERS.toString())) {
                ChatUtil.sendCommandSenderMessage(
                        commandSender,
                        ChatUtil.Type.INFO,
                        Lang.SUBCOMMAND_HELP_SYNTAX.toString()
                                .replace("{subcommand_usage}", Lang.SUBCOMMAND_INFO_USAGE.toString())
                                .replace("{subcommand_description}", Lang.SUBCOMMAND_INFO_DESCRIPTION.toString()
                                )
                );
            }
        } else {
            ChatUtil.sendCommandSenderMessage(
                    commandSender,
                    ChatUtil.Type.ERROR,
                    Lang.COMMAND_TOO_MANY_ARGUMENTS.toString()
                            .replace("{command_usage}", Lang.SUBCOMMAND_HELP_USAGE.toString()
                            )
            );
        }
    }

    public List<String> onTabComplete(@Nonnull CommandSender commandSender,
                                      @Nonnull Command command,
                                      @Nonnull String alias,
                                      @Nonnull String[] args) {
        List<String> empty = new ArrayList<>();

        if (args.length == 1) {
            List<String> subCommand = new ArrayList<>();
            if ("help".startsWith(args[0])) {
                subCommand.add("help");
            }

            return subCommand;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                List<String> subCommands = new ArrayList<>();

                if (commandSender.hasPermission(Permissions.HELP.toString())) {
                    if ("help".startsWith(args[0])) {
                        subCommands.add("help");
                    }
                }
                if (commandSender.hasPermission(Permissions.PRUNE.toString())) {
                    if ("prune".startsWith(args[0])) {
                        subCommands.add("prune");
                    }
                }
                if (commandSender.hasPermission(Permissions.UPDATE.toString())) {
                    if ("update".startsWith(args[0])) {
                        subCommands.add("update");
                    }
                }
                if (commandSender.hasPermission(Permissions.GIVE.toString())) {
                    if ("give".startsWith(args[0])) {
                        subCommands.add("give");
                    }
                }
                if (commandSender.hasPermission(Permissions.INFO_SELF.toString()) || commandSender.hasPermission(Permissions.INFO_OTHERS.toString())) {
                    if ("info".startsWith(args[0])) {
                        subCommands.add("info");
                    }
                }

                return subCommands;
            }
        }
        return empty;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}

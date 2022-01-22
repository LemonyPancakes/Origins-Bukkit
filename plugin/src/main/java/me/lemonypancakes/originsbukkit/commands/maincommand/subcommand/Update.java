package me.lemonypancakes.originsbukkit.commands.maincommand.subcommand;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Identifier;
import me.lemonypancakes.originsbukkit.api.data.type.Origin;
import me.lemonypancakes.originsbukkit.commands.maincommand.MainCommand;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Permissions;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.storage.OriginPlayers;
import me.lemonypancakes.originsbukkit.storage.Origins;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
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

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Origins ORIGINS = PLUGIN.getStorageHandler().getOrigins();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

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
                    Identifier originIdentifier = new IdentifierContainer(
                            origin.split(":")[0],
                            origin.split(":")[1]
                    );

                    if (!origin.equals("origins-bukkit:dummy_origin")) {
                        if (ORIGINS.hasIdentifier(originIdentifier)) {
                            String originChangeMessage = Lang.SUBCOMMAND_UPDATE_ORIGIN_CHANGE.toString()
                                    .replace("{player}", playerName)
                                    .replace("{new_origin}", origin
                                    );

                            if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                                Origin playerOrigin = ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).getOrigin();

                                if (playerOrigin != null) {
                                    if (!playerOrigin.getIdentifier().equals(originIdentifier)) {
                                        ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(
                                                ORIGINS.getByIdentifier(
                                                        originIdentifier
                                                )
                                        );
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
                                    ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(
                                            ORIGINS.getByIdentifier(
                                                    originIdentifier
                                            )
                                    );
                                    ChatUtils.sendCommandSenderMessage(
                                            commandSender,
                                            ChatUtils.Type.SUCCESS,
                                            originChangeMessage
                                    );
                                }
                            } else {
                                ORIGIN_PLAYERS.add(new OriginPlayerContainer(playerUUID));
                                ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(
                                        ORIGINS.getByIdentifier(
                                                originIdentifier
                                        )
                                );
                                ChatUtils.sendCommandSenderMessage(
                                        commandSender,
                                        ChatUtils.Type.SUCCESS,
                                        originChangeMessage
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

package me.lemonypancakes.originsbukkit.commands.maincommand.subcommand;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.commands.maincommand.MainCommand;
import me.lemonypancakes.originsbukkit.enums.Permissions;
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
public class Prune {

    private final MainCommand mainCommand;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();
    private static final Origins ORIGINS = PLUGIN.getStorageHandler().getOrigins();
    private static final OriginPlayers ORIGIN_PLAYERS = PLUGIN.getStorageHandler().getOriginPlayers();

    public Prune(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void onCommand(@Nonnull CommandSender commandSender,
                          @Nonnull Command command,
                          @Nonnull String label,
                          @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            if (!commandSender.hasPermission(Permissions.PRUNE.toString())) {
                return;
            }
        }
        if (args.length == 1) {
            ChatUtils.sendCommandSenderMessage(commandSender, ChatUtils.Type.ERROR, "&cNot enough arguments. Usage: &e/origins prune <player>");
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                String playerName = target.getName();
                UUID playerUUID = target.getUniqueId();

                if (ORIGIN_PLAYERS.hasPlayerUUID(playerUUID)) {
                    if (ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).getOrigin() != null) {
                        ORIGIN_PLAYERS.getByPlayerUUID(playerUUID).setOrigin(null);
                        ChatUtils.sendCommandSenderMessage(commandSender, ChatUtils.Type.SUCCESS, "&aSuccessfully pruned " + playerName + "'s data.");
                    } else {
                        ChatUtils.sendCommandSenderMessage(commandSender, ChatUtils.Type.ERROR, "&cCannot find " + playerName + "'s data.");
                    }
                } else {
                    ChatUtils.sendCommandSenderMessage(commandSender, ChatUtils.Type.ERROR, "&cCannot find " + playerName + "'s data.");
                }
            } else {
                ChatUtils.sendCommandSenderMessage(commandSender, ChatUtils.Type.ERROR, "&cPlayer " + args[1] + " not found. Player must be online to do this.");
            }
        } else {
            ChatUtils.sendCommandSenderMessage(commandSender, ChatUtils.Type.ERROR, "&cToo many arguments. Usage: &e/origins prune <player>");
        }
    }

    public List<String> onTabComplete(@Nonnull CommandSender commandSender,
                                      @Nonnull Command command,
                                      @Nonnull String alias,
                                      @Nonnull String[] args) {
        List<String> empty = new ArrayList<>();

        if (commandSender instanceof Player) {
            if (!commandSender.hasPermission(Permissions.PRUNE.toString())) {
                return empty;
            }
        }
        if (args.length == 1) {
            List<String> subCommand = new ArrayList<>();
            subCommand.add("prune");

            return subCommand;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("prune")) {
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    playerNames.add(player.getName());
                }

                return playerNames;
            }
        }
        return empty;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}

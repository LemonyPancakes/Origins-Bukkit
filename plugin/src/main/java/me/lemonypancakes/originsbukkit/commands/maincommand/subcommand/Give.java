package me.lemonypancakes.originsbukkit.commands.maincommand.subcommand;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.commands.maincommand.MainCommand;
import me.lemonypancakes.originsbukkit.enums.Lang;
import me.lemonypancakes.originsbukkit.enums.Permissions;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Give {

    private final MainCommand mainCommand;

    private static final OriginsBukkit PLUGIN = OriginsBukkit.getPlugin();

    public Give(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public void onCommand(@Nonnull CommandSender commandSender,
                          @Nonnull Command command,
                          @Nonnull String label,
                          @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (!player.hasPermission(Permissions.GIVE.toString())) {
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
                            .replace("{command_usage}", Lang.SUBCOMMAND_GIVE_USAGE.toString()
                            )
            );
        } else if (args.length == 3 || args.length == 4) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                String item = args[2];
                ItemStack itemStack = null;
                Integer amount = null;

                try {
                    if (args.length == 4) {
                        amount = Integer.parseInt(args[3]);
                    } else {
                        amount = 1;
                    }
                } catch (NumberFormatException exception) {
                    ChatUtils.sendCommandSenderMessage(
                            commandSender,
                            ChatUtils.Type.ERROR,
                            Lang.SUBCOMMAND_GIVE_INVALID_AMOUNT.toString()
                                    .replace("{amount}", args[3]
                                    )
                    );
                }

                if (amount != null) {
                    switch (item) {
                        case "origins-bukkit:orb_of_origin":
                        case "orb_of_origin":
                            itemStack = new ItemStack(
                                    PLUGIN.getItemHandler()
                                            .getOrbOfOrigin()
                                            .getItemStack()
                            );
                            itemStack.setAmount(amount);
                            break;
                    }
                }

                if (amount != null) {
                    if (itemStack != null) {
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        if (itemMeta != null) {
                            ChatUtils.sendCommandSenderMessage(
                                    commandSender,
                                    ChatUtils.Type.SUCCESS,
                                    Lang.SUBCOMMAND_GIVE_SUCCESS.toString()
                                            .replace("{player}", target.getName())
                                            .replace("{item}", itemStack.getItemMeta().getDisplayName())
                                            .replace("{amount}", String.valueOf(amount)
                                            )
                            );
                        } else {
                            ChatUtils.sendCommandSenderMessage(
                                    commandSender,
                                    ChatUtils.Type.SUCCESS,
                                    Lang.SUBCOMMAND_GIVE_SUCCESS.toString()
                                            .replace("{player}", target.getName())
                                            .replace("{item}", "null")
                                            .replace("{amount}", String.valueOf(amount)
                                            )
                            );
                        }
                        target.getInventory().addItem(itemStack);
                    } else {
                        ChatUtils.sendCommandSenderMessage(
                                commandSender,
                                ChatUtils.Type.ERROR,
                                Lang.SUBCOMMAND_GIVE_CANNOT_FIND_ITEM.toString()
                                        .replace("{item}", args[2]
                                        )
                        );
                    }
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
                            .replace("{command_usage}", Lang.SUBCOMMAND_GIVE_USAGE.toString()
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
            if (!commandSender.hasPermission(Permissions.GIVE.toString())) {
                return empty;
            }
        }
        if (args.length == 1) {
            List<String> subCommand = new ArrayList<>();
            subCommand.add("give");

            return subCommand;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    playerNames.add(player.getName());
                }

                return playerNames;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                List<String> itemList = new ArrayList<>();
                for (String item : PLUGIN.getItemHandler().getItems()) {
                    if (item.startsWith(args[2])) {
                        itemList.add(item);
                    }
                }

                return itemList;
            }
        }
        return empty;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}

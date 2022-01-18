package me.lemonypancakes.originsbukkit.commands.maincommand;

import me.lemonypancakes.originsbukkit.commands.CommandHandler;
import me.lemonypancakes.originsbukkit.commands.maincommand.subcommand.Give;
import me.lemonypancakes.originsbukkit.commands.maincommand.subcommand.Help;
import me.lemonypancakes.originsbukkit.commands.maincommand.subcommand.Prune;
import me.lemonypancakes.originsbukkit.commands.maincommand.subcommand.Update;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MainCommand implements TabExecutor {

    private final CommandHandler commandHandler;
    private final Help help;
    private final Prune prune;
    private final Update update;
    private final Give give;

    public MainCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.help = new Help(this);
        this.prune = new Prune(this);
        this.update = new Update(this);
        this.give = new Give(this);
        init();
    }

    private void init() {
        PluginCommand pluginMainCommand = getCommandHandler().getPlugin().getCommand("origins");

        if (pluginMainCommand != null) {
            pluginMainCommand.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender,
                             @Nonnull Command command,
                             @Nonnull String label,
                             @Nonnull String[] args) {
        if (args.length == 0) {
            getHelp().onCommand(commandSender, command, label, args);
        } else {
            switch (args[0].toLowerCase()) {
                case "prune":
                    getPrune().onCommand(commandSender, command, label, args);
                    break;
                case "update":
                    getUpdate().onCommand(commandSender, command, label, args);
                    break;
                case "give":
                    getGive().onCommand(commandSender, command, label, args);
                    break;
                case "help":
                default:
                    getHelp().onCommand(commandSender, command, label, args);
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender,
                                      @Nonnull Command command,
                                      @Nonnull String alias,
                                      @Nonnull String[] args) {
        if (args.length >= 1) {
            List<String> subCommands = new ArrayList<>();

            subCommands.addAll(getPrune().onTabComplete(commandSender, command, alias, args));
            subCommands.addAll(getUpdate().onTabComplete(commandSender, command, alias, args));

            return subCommands;
        }
        return null;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public Help getHelp() {
        return help;
    }

    public Prune getPrune() {
        return prune;
    }

    public Update getUpdate() {
        return update;
    }

    public Give getGive() {
        return give;
    }
}

package me.lemonypancakes.bukkit.origins.command.maincommand;

import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.command.maincommand.subcommand.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MainCommand implements TabExecutor {

    private final OriginsBukkitPlugin plugin;
    private final Help help;
    private final Prune prune;
    private final Update update;
    private final Give give;
    private final Info info;

    public MainCommand(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        this.help = new Help(this);
        this.prune = new Prune(this);
        this.update = new Update(this);
        this.give = new Give(this);
        this.info = new Info(this);
        init();
    }

    private void init() {
        PluginCommand pluginMainCommand = plugin.getJavaPlugin().getCommand("origins");

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
                case "info":
                    getInfo().onCommand(commandSender, command, label, args);
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
            subCommands.addAll(getGive().onTabComplete(commandSender, command, alias, args));
            subCommands.addAll(getInfo().onTabComplete(commandSender, command, alias, args));
            subCommands.addAll(getHelp().onTabComplete(commandSender, command, alias, args));

            return subCommands;
        }
        return null;
    }

    public OriginsBukkitPlugin getPlugin() {
        return plugin;
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

    public Info getInfo() {
        return info;
    }
}

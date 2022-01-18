package me.lemonypancakes.originsbukkit.commands.maincommand.subcommand;

import me.lemonypancakes.originsbukkit.commands.maincommand.MainCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("unused")
public class Give {

    private final MainCommand mainCommand;

    public Give(MainCommand mainCommand) {
        this.mainCommand = mainCommand;
    }

    public boolean onCommand(@Nonnull CommandSender commandSender,
                             @Nonnull Command command,
                             @Nonnull String label,
                             @Nonnull String[] args) {
        return true;
    }

    public List<String> onTabComplete(@Nonnull CommandSender commandSender,
                                      @Nonnull Command command,
                                      @Nonnull String alias,
                                      @Nonnull String[] args) {

        return null;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}

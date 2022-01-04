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
package me.lemonypancakes.originsbukkit.commands.maincommand;

import me.lemonypancakes.originsbukkit.commands.CommandHandler;
import me.lemonypancakes.originsbukkit.commands.maincommand.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {

    private final CommandHandler commandHandler;
    private final Help help;
    private final Prune prune;
    private final Reload reload;
    private final Update update;
    private final Give give;

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public Help getHelp() {
        return help;
    }

    public Prune getPrune() {
        return prune;
    }

    public Reload getReload() {
        return reload;
    }

    public Update getUpdate() {
        return update;
    }

    public Give getGive() {
        return give;
    }

    public MainCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.help = new Help(this);
        this.prune = new Prune(this);
        this.reload = new Reload(this);
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            help.HelpSubCommand(sender, command, label, args);
        } else {
            switch (args[0].toLowerCase()) {
                case "reload":
                    reload.ReloadSubCommand(sender, command, label, args);
                    break;
                case "prune":
                    prune.PruneSubCommand(sender, command, label, args);
                    break;
                case "update":
                    update.UpdateSubCommand(sender, command, label, args);
                    break;
                case "give":
                    give.GiveSubCommand(sender, command, label, args);
                    break;
                case "help":
                default:
                    help.HelpSubCommand(sender, command, label, args);
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length >= 1) {
            List<String> subCommands = new ArrayList<>();

            subCommands.addAll(help.HelpSubCommandTabComplete(sender, command, alias, args));
            subCommands.addAll(prune.PruneSubCommandTabComplete(sender, command, alias, args));
            subCommands.addAll(reload.ReloadSubCommandTabComplete(sender, command, alias, args));
            subCommands.addAll(update.UpdateSubCommandTabComplete(sender, command, alias, args));
            subCommands.addAll(give.GiveSubCommandTabComplete(sender, command, alias, args));

            return subCommands;
        }
        return null;
    }
}

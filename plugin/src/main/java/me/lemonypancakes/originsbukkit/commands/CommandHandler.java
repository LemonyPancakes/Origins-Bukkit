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
package me.lemonypancakes.originsbukkit.commands;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.commands.maincommand.MainCommand;

public class CommandHandler {

    private final OriginsBukkit plugin;
    private MainCommand mainCommand;

    public OriginsBukkit getPlugin() {
        return plugin;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }

    public CommandHandler(OriginsBukkit plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        mainCommand = new MainCommand(this);
    }
}

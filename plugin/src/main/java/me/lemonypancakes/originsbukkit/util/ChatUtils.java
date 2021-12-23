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
package me.lemonypancakes.originsbukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The type Chat utils.
 *
 * @author LemonyPancakes
 */
public class ChatUtils {

    /**
     * Format string.
     *
     * @param format the format
     *
     * @return the string
     */
    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    /**
     * Format list string [ ].
     *
     * @param format the format
     *
     * @return the string [ ]
     */
    public static String[] formatList(String[] format) {
        String[] result;
        result = new String[format.length];
        for (int i = 0; i < format.length; i++) {
            result[i] = ChatColor.translateAlternateColorCodes('&', format[i]);
        }
        return result;
    }

    /**
     * Send console message.
     *
     * @param message the message
     */
    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(format(message));
    }

    /**
     * Send player message.
     *
     * @param player  the player
     * @param message the message
     */
    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage(format(message));
    }

    /**
     * Send command sender message.
     *
     * @param sender  the sender
     * @param message the message
     */
    public static void sendCommandSenderMessage(CommandSender sender, String message) {
        sender.sendMessage(format(message));
    }
}

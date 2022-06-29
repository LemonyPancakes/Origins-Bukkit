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
package me.lemonypancakes.bukkit.origins.util;

public enum Permissions {
    ADMIN("origins.admin"),
    USER("origins.user"),
    HELP("origins.commands.help"),
    UPDATE("origins.commands.update"),
    PRUNE("origins.commands.prune"),
    RELOAD("origins.commands.reload"),
    GIVE("origins.commands.give"),
    INFO_SELF("origins.commands.info.self"),
    INFO_OTHERS("origins.commands.info.others");

    private final Object value;

    Permissions(final Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return (String) this.value;
    }

    public Object getValue() {
        return this.value;
    }
}

/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
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
package me.lemonypancakes.bukkit.origins.data;

import me.lemonypancakes.bukkit.origins.Scheduler;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class CraftScheduler implements Scheduler {

    private Identifier identifier;
    private BukkitTask bukkitTask;

    public CraftScheduler(Identifier identifier, BukkitTask bukkitTask) {
        this.identifier = identifier;
        this.bukkitTask = bukkitTask;
    }

    public CraftScheduler(Identifier identifier) {
        this(identifier, null);
    }

    public CraftScheduler() {}

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public BukkitTask getBukkitTask() {
        return bukkitTask;
    }

    public void setBukkitTask(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftScheduler)) return false;
        CraftScheduler that = (CraftScheduler) itemStack;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getBukkitTask(), that.getBukkitTask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getBukkitTask());
    }

    @Override
    public String toString() {
        return "CraftScheduler{" +
                "identifier=" + identifier +
                ", bukkitTask=" + bukkitTask +
                '}';
    }
}

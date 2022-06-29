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
package me.lemonypancakes.bukkit.origins.wrapper;

import org.bukkit.entity.Entity;

import java.util.Objects;

public class BiEntity {

    private Entity actor;
    private Entity target;

    public BiEntity(Entity actor, Entity target) {
        this.actor = actor;
        this.target = target;
    }

    public Entity getActor() {
        return actor;
    }

    public void setActor(Entity actor) {
        this.actor = actor;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiEntity)) return false;
        BiEntity biEntity = (BiEntity) o;
        return Objects.equals(getActor(), biEntity.getActor()) && Objects.equals(getTarget(), biEntity.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActor(), getTarget());
    }

    @Override
    public String toString() {
        return "BiEntity{" +
                "actor=" + actor +
                ", target=" + target +
                '}';
    }
}

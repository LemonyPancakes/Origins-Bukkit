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
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage {

    private final double amount;
    private final Entity entity;
    private final EntityDamageEvent.DamageCause damageCause;

    public Damage(double amount, Entity entity, EntityDamageEvent.DamageCause damageCause) {
        this.amount = amount;
        this.entity = entity;
        this.damageCause = damageCause;
    }

    public double getAmount() {
        return amount;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }
}

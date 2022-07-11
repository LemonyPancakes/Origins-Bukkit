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
package me.lemonypancakes.bukkit.origins.factory.power.regular;

import me.lemonypancakes.bukkit.common.com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.scheduler.Scheduler;
import me.lemonypancakes.bukkit.origins.entity.player.power.CraftPower;
import me.lemonypancakes.bukkit.origins.scheduler.CraftScheduler;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CraftExhaustPower extends CraftPower {

    private long interval = 4;
    private long onsetDelay = interval;
    private int exhaustion;

    public CraftExhaustPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("interval")) {
            this.interval = jsonObject.get("interval").getAsLong();
        }
        if (jsonObject.has("onset_delay")) {
            this.onsetDelay = jsonObject.get("onset_delay").getAsLong();
        }
        if (jsonObject.has("exhaustion")) {
            this.exhaustion = jsonObject.get("exhaustion").getAsInt();
        }
    }

    @Override
    protected void onMemberAdd(Player player) {
        OriginPlayer originPlayer = getPlugin().getOriginPlayer(player);

        if (originPlayer != null) {
            BukkitTask bukkitTask = new BukkitRunnable() {

                @Override
                public void run() {
                    if (getCondition().test(player)) {
                        PlayerUtils.exhaust(player, exhaustion);
                    }
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), onsetDelay, interval);
            Scheduler scheduler = new CraftScheduler(Identifier.fromString("origins-bukkit:exhaust"));
            scheduler.setBukkitTask(bukkitTask);
            originPlayer.getSchedulers().add(scheduler);
        }
    }
}

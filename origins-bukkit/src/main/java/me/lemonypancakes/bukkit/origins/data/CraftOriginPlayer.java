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

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Origin;
import me.lemonypancakes.bukkit.origins.OriginPlayer;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.storage.other.Misc;
import me.lemonypancakes.bukkit.origins.event.entity.player.PlayerOriginSetEvent;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Config;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CraftOriginPlayer implements OriginPlayer {

    private final OriginsBukkitPlugin plugin;
    private final Player player;
    private final Schedulers schedulers;
    private JsonObject metadata;
    private Origin origin;

    public CraftOriginPlayer(OriginsBukkitPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.schedulers = new Schedulers();
        this.metadata = plugin.getStorage().getMetadata(player);
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public PlayerOriginSetEvent setOrigin(Origin origin) {
        PlayerOriginSetEvent playerOriginSetEvent = new PlayerOriginSetEvent(player, this.origin, origin);

        if (!playerOriginSetEvent.isCancelled()) {
            origin = playerOriginSetEvent.getNewOrigin();

            if (origin == null) {
                if (this.origin != null) {
                    unlistenAndDestroy();
                    plugin.getStorage().setOrigin(player, null);
                    this.origin = null;
                    Misc.VIEWERS.put(player.getUniqueId(), 0);
                    player.openInventory(Misc.GUIS.get(0));
                    Bukkit.getPluginManager().callEvent(playerOriginSetEvent);

                    return playerOriginSetEvent;
                }
            } else {
                if (!origin.getIdentifier().toString().equals("origins-bukkit:dummy_origin")) {
                    if (plugin.getRegistry().hasOrigin(origin.getIdentifier())) {
                        if (this.origin != null) {
                            unlistenAndDestroy();
                        }
                        this.origin = origin;
                        if (Misc.VIEWERS.containsKey(player.getUniqueId())) {
                            Misc.VIEWERS.remove(player.getUniqueId());
                            player.closeInventory();
                        }
                        if (origin.getPowers() != null) {
                            origin.getPowers().forEach(power -> power.addMember(player));
                        }
                        plugin.getStorage().setOrigin(player, origin.getIdentifier());
                        Bukkit.getPluginManager().callEvent(playerOriginSetEvent);

                        return playerOriginSetEvent;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Schedulers getSchedulers() {
        return schedulers;
    }

    @Override
    public void unlisten() {
        if (getOrigin() != null) {
            if (getOrigin().getPowers() != null) {
                getOrigin().getPowers().forEach(power -> power.removeMember(player));
            }
        }
    }

    @Override
    public void unlistenAndDestroy() {
        unlisten();
        getSchedulers().destroy();
    }

    @Override
    public void refresh() {
        if (plugin.getStorage().getOrigin(player) != null) {
            Identifier originIdentifier = plugin.getStorage().getOrigin(player);

            if (plugin.getRegistry().hasOrigin(originIdentifier)) {
                Origin origin = plugin.getRegistry().getOrigin(originIdentifier);

                if (origin != null) {
                    setOrigin(origin);
                }
            } else {
                plugin.getStorage().setOrigin(player, null);
                Bukkit.getScheduler().runTaskLater(plugin.getJavaPlugin(), bukkitTask -> {
                    Misc.VIEWERS.put(player.getUniqueId(), 0);
                    player.openInventory(Misc.GUIS.get(0));
                }, 20L);
                ChatUtils.sendPlayerMessage(player, "&cYour origin (&e\"" + originIdentifier + "\"&c) doesn't exist so we pruned your player data.");
            }
        } else {
            if (!plugin.getStorage().hasOriginPlayerData(player)) {
                String starterOriginString = Config.STARTER_ORIGIN.toString();

                if (!starterOriginString.isEmpty()) {
                    Identifier starterOriginIdentifier = Identifier.fromString(starterOriginString);

                    if (starterOriginIdentifier != null) {
                        Origin starterOrigin = plugin.getRegistry().getOrigin(starterOriginIdentifier);

                        if (starterOrigin != null) {
                            setOrigin(starterOrigin);
                            return;
                        }
                    }
                }
            }
            Bukkit.getScheduler().runTaskLater(plugin.getJavaPlugin(), bukkitTask -> {
                Misc.VIEWERS.put(player.getUniqueId(), 0);
                player.openInventory(Misc.GUIS.get(0));
            }, 20L);
        }
    }

    @Override
    public JsonObject getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOriginPlayer)) return false;
        CraftOriginPlayer that = (CraftOriginPlayer) itemStack;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(player, that.player) && Objects.equals(getSchedulers(), that.getSchedulers()) && Objects.equals(getMetadata(), that.getMetadata()) && Objects.equals(getOrigin(), that.getOrigin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), player, getSchedulers(), getMetadata(), getOrigin());
    }

    @Override
    public String toString() {
        return "CraftOriginPlayer{" +
                "plugin=" + plugin +
                ", player=" + player +
                ", schedulers=" + schedulers +
                ", metadata=" + metadata +
                ", origin=" + origin +
                '}';
    }
}

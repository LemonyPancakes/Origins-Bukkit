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
package me.lemonypancakes.bukkit.origins.command;

import com.mojang.brigadier.CommandDispatcher;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.OriginsBukkit;
import me.lemonypancakes.bukkit.origins.util.Permission;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class MainCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("origins").requires(
                                commandSourceStack -> Arrays.stream(Permission.values())
                                        .anyMatch(
                                                permission -> commandSourceStack.getBukkitSender().hasPermission(permission.name()
                                                )
                                        )
                        )
                        .then(literal("set")
                                .then(argument("targets", EntityArgument.players())
                                        .then(argument("layer", OriginLayerArgument.layer())
                                                .then(argument("origin", OriginArgument.origin())
                                                        .executes((context -> {
                                                            AtomicInteger i = new AtomicInteger();
                                                            Collection<ServerPlayer> serverPlayers = EntityArgument.getPlayers(context, "targets");
                                                            List<Player> players = serverPlayers.stream().map(ServerPlayer::getBukkitEntity).collect(Collectors.toList());
                                                            OriginLayer originLayer = OriginLayerArgument.getOriginLayer(context, "layer");
                                                            Origin origin = OriginArgument.getOrigin(context, "origin");

                                                            if (originLayer != null && origin != null) {
                                                                if (originLayer.hasOrigin(origin)) {
                                                                    players.forEach(player -> {
                                                                        OriginPlayer originPlayer = OriginsBukkit.getOriginPlayer(player);

                                                                        if (originPlayer != null) {
                                                                            originPlayer.setOrigin(originLayer, origin);
                                                                        }
                                                                        i.getAndIncrement();
                                                                    });
                                                                }
                                                            }
                                                            return i.get();
                                                        }))
                                                )
                                        )
                                )
                        )
        );
    }
}

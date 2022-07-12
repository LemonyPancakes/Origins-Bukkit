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

import me.lemonypancakes.bukkit.common.dev.jorel.commandapi.CommandAPICommand;
import me.lemonypancakes.bukkit.common.dev.jorel.commandapi.arguments.*;
import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.entity.player.power.Power;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.PowerSource;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.Collection;

public class MainCommand {

    private final OriginsBukkitPlugin plugin;

    public MainCommand(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        new CommandAPICommand("origin")
                .withPermission("bukkit.origins.command.origin")
                .withSubcommand(new CommandAPICommand("prune")
                        .withPermission("bukkit.origins.command.origin.prune")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .executes(((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    originPlayer.getOrigins().forEach(((originLayer, origin) -> originPlayer.setOrigin(originLayer, null)));
                                }
                            });
                        })))
                .withSubcommand(new CommandAPICommand("set")
                        .withPermission("bukkit.origins.command.origin.set")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .withArguments(originLayerArgument("origin_layer"))
                        .withArguments(originArgument("origin"))
                        .executes((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];
                            OriginLayer originLayer = (OriginLayer) objects[1];
                            Origin origin = (Origin) objects[2];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    originPlayer.setOrigin(originLayer, origin);
                                }
                            });
                        }))
                .register();
        new CommandAPICommand("power")
                .withPermission("bukkit.origins.command.power")
                .withSubcommand(new CommandAPICommand("add")
                        .withPermission("bukkit.origins.command.power.add")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .withArguments(powerArgument("power"))
                        .withArguments(new NamespacedKeyArgument("power_source"))
                        .executes((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];
                            Power power = (Power) objects[1];
                            NamespacedKey namespacedKey = (NamespacedKey) objects[2];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    originPlayer.addPower(power, new PowerSource(Identifier.fromString(namespacedKey.toString())));
                                }
                            });
                        }))
                .withSubcommand(new CommandAPICommand("remove")
                        .withPermission("bukkit.origins.command.power.remove")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .withArguments(powerArgument("power"))
                        .withArguments(new NamespacedKeyArgument("power_source"))
                        .executes((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];
                            Power power = (Power) objects[1];
                            NamespacedKey namespacedKey = (NamespacedKey) objects[2];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    originPlayer.removePower(power, new PowerSource(Identifier.fromString(namespacedKey.toString())));
                                }
                            });
                        }))
                .register();
    }

    public Argument<OriginLayer> originLayerArgument(String nodeName) {
        return new CustomArgument<>(new NamespacedKeyArgument(nodeName), customArgumentInfo -> {
            String string = customArgumentInfo.input();

            if (string.contains(":")) {
                Identifier identifier = Identifier.fromString(string);

                if (identifier != null) {
                    OriginLayer originLayer = plugin.getRegistry().getRegisteredOriginLayer(identifier);

                    if (originLayer != null) {
                        return originLayer;
                    } else {
                        throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Unknown origin layer: ").appendArgInput());
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Invalid identifier: ").appendArgInput());
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Invalid identifier: ").appendArgInput());
            }
        }).replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> plugin.getRegistry().getRegisteredOriginLayersKeySet().stream().map(Identifier::toString).toArray(String[]::new)));
    }

    public Argument<Origin> originArgument(String nodeName) {
        return new CustomArgument<>(new NamespacedKeyArgument(nodeName), customArgumentInfo -> {
            String string = customArgumentInfo.input();

            if (string.contains(":")) {
                Identifier identifier = Identifier.fromString(string);

                if (identifier != null) {
                    Origin origin = plugin.getRegistry().getRegisteredOrigin(identifier);

                    if (origin != null) {
                        return origin;
                    } else {
                        throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Unknown origin: ").appendArgInput());
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Invalid identifier: ").appendArgInput());
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Invalid identifier: ").appendArgInput());
            }
        }).replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> ((OriginLayer) suggestionInfo.previousArgs()[1]).getOrigins().stream().map(origin -> origin.getIdentifier().toString()).toArray(String[]::new)));
    }

    public Argument<Power> powerArgument(String nodeName) {
        return new CustomArgument<>(new NamespacedKeyArgument(nodeName), customArgumentInfo -> {
            String string = customArgumentInfo.input();

            if (string.contains(":")) {
                Identifier identifier = Identifier.fromString(string);

                if (identifier != null) {
                    Power power = plugin.getRegistry().getRegisteredPower(identifier);

                    if (power != null) {
                        return power;
                    } else {
                        throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Unknown power: ").appendArgInput());
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Invalid identifier: ").appendArgInput());
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder("Invalid identifier: ").appendArgInput());
            }
        }).replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> plugin.getRegistry().getRegisteredPowersKeySet().stream().map(Identifier::toString).toArray(String[]::new)));
    }
}

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
import me.lemonypancakes.bukkit.origins.item.OriginItem;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.origin.layer.OriginLayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import me.lemonypancakes.bukkit.origins.util.Lang;
import me.lemonypancakes.bukkit.origins.util.PowerSource;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainCommand {

    private final OriginsBukkitPlugin plugin;

    public MainCommand(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        new CommandAPICommand("origin")
                .withPermission("bukkit.origins.command.origin")
                .withSubcommand(new CommandAPICommand("prune")
                        .withPermission("bukkit.origins.command.origin.prune")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .withArguments(originLayerArgument("origin_layer"))
                        .executes(((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];
                            OriginLayer originLayer = (OriginLayer) objects[1];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    Origin origin = originPlayer.getOrigin(originLayer);

                                    if (origin != null) {
                                        originPlayer.setOrigin(originLayer, null);
                                        commandSender.sendMessage(Lang.COMMAND_ORIGIN_PRUNE_SUCCESS.toString().replace("%player%", player.getName()).replace("%origin_layer%", originLayer.getIdentifier().toString()));
                                    } else {
                                        commandSender.sendMessage(Lang.COMMAND_ORIGIN_PRUNE_NO_ORIGIN.toString().replace("%player%", player.getName()).replace("%origin_layer%", originLayer.getIdentifier().toString()));
                                    }
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
                                    if (originPlayer.getOrigin(originLayer) != origin) {
                                        originPlayer.setOrigin(originLayer, origin);
                                        commandSender.sendMessage(Lang.COMMAND_ORIGIN_SET_SUCCESS.toString().replace("%player%", player.getName()).replace("%origin_layer%", originLayer.getIdentifier().toString()).replace("%new_origin%", origin.getIdentifier().toString()));
                                    } else {
                                        commandSender.sendMessage(Lang.COMMAND_ORIGIN_SET_ALREADY_HAD.toString().replace("%player%", player.getName()).replace("%origin_layer%", originLayer.getIdentifier().toString()).replace("%new_origin%", origin.getIdentifier().toString()));
                                    }
                                }
                            });
                        }))
                .withSubcommand(new CommandAPICommand("item")
                        .withPermission("bukkit.origins.command.origin.item")
                        .withSubcommand(new CommandAPICommand("give")
                                .withPermission("bukkit.origins.command.origin.item.give")
                                .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                                .withArguments(originItemArgument("origin_item"))
                                .withArguments(new IntegerArgument("amount"))
                                .executes((commandSender, objects) -> {
                                    @SuppressWarnings("unchecked")
                                    Collection<Player> players = (Collection<Player>) objects[0];
                                    OriginItem originItem = (OriginItem) objects[1];
                                    int amount = (int) objects[2];
                                    ItemStack itemStack = originItem.getItemStack();

                                    if (itemStack != null) {
                                        itemStack = itemStack.clone();

                                        itemStack.setAmount(amount);
                                        ItemStack finalItemStack = itemStack;

                                        players.forEach(player -> {
                                            player.getInventory().addItem(finalItemStack);
                                            commandSender.sendMessage(Lang.COMMAND_ORIGIN_ITEM_GIVE.toString().replace("%player%", player.getName()).replace("%origin_item%", finalItemStack.getItemMeta() != null ? finalItemStack.getItemMeta().getDisplayName() : "").replace("%amount%", String.valueOf(amount)));
                                        });
                                    }
                                })))
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
                                    Set<PowerSource> powerSources = originPlayer.getPowerSources(power);

                                    if (powerSources != null) {
                                        PowerSource powerSource = new PowerSource(Identifier.fromString(namespacedKey.toString()));

                                        if (!powerSources.contains(powerSource)) {
                                            originPlayer.addPower(power, powerSource);
                                            commandSender.sendMessage(Lang.COMMAND_POWER_ADD_SUCCESS.toString().replace("%player%", player.getName()).replace("%power%", power.getIdentifier().toString()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                        } else {
                                            commandSender.sendMessage(Lang.COMMAND_POWER_ADD_HAD_ALREADY.toString().replace("%player%", player.getName()).replace("%power%", power.getIdentifier().toString()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                        }
                                    }
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
                                    Set<PowerSource> powerSources = originPlayer.getPowerSources(power);

                                    if (powerSources != null) {
                                        PowerSource powerSource = new PowerSource(Identifier.fromString(namespacedKey.toString()));

                                        if (powerSources.contains(powerSource)) {
                                            originPlayer.removePower(power, powerSource);
                                            commandSender.sendMessage(Lang.COMMAND_POWER_REMOVE_SUCCESS.toString().replace("%player%", player.getName()).replace("%power%", power.getIdentifier().toString()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                        } else {
                                            commandSender.sendMessage(Lang.COMMAND_POWER_REMOVE_NO_POWER.toString().replace("%player%", player.getName()).replace("%power%", power.getIdentifier().toString()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                        }
                                    }
                                }
                            });
                        }))
                .withSubcommand(new CommandAPICommand("remove-all")
                        .withPermission("bukkit.origins.command.power.remove-all")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .withArguments(new NamespacedKeyArgument("power_source"))
                        .executes((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];
                            NamespacedKey namespacedKey = (NamespacedKey) objects[1];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    PowerSource powerSource = new PowerSource(Identifier.fromString(namespacedKey.toString()));
                                    List<Power> powers = originPlayer.getPowers().keySet().stream().filter(power -> originPlayer.getPowerSources(power).contains(powerSource)).collect(Collectors.toList());

                                    if (!powers.isEmpty()) {
                                        powers.forEach(power -> {
                                            originPlayer.removePower(power, powerSource);
                                            commandSender.sendMessage(Lang.COMMAND_POWER_REMOVE_SUCCESS.toString().replace("%player%", player.getName()).replace("%power%", power.getIdentifier().toString()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                        });
                                        commandSender.sendMessage(Lang.COMMAND_POWER_REMOVE_ALL_SUCCESS.toString().replace("%player%", player.getName()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                    } else {
                                        commandSender.sendMessage(Lang.COMMAND_POWER_REMOVE_ALL_NO_POWER.toString().replace("%player%", player.getName()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                    }
                                }
                            });
                        }))
                .withSubcommand(new CommandAPICommand("clear")
                        .withPermission("bukkit.origins.command.power.clear")
                        .withArguments(new EntitySelectorArgument<Collection<Player>>("players", EntitySelector.MANY_PLAYERS))
                        .executes((commandSender, objects) -> {
                            @SuppressWarnings("unchecked")
                            Collection<Player> players = (Collection<Player>) objects[0];

                            players.forEach(player -> {
                                OriginPlayer originPlayer = plugin.getOriginPlayer(player);

                                if (originPlayer != null) {
                                    Map<Power, Set<PowerSource>> powers = originPlayer.getPowers();

                                    if (powers != null) {
                                        if (!powers.isEmpty()) {
                                            powers.forEach((power, powerSources) -> {
                                                if (powerSources != null) {
                                                    powerSources.forEach(powerSource -> {
                                                        originPlayer.removePower(power, powerSource);
                                                        commandSender.sendMessage(Lang.COMMAND_POWER_REMOVE_SUCCESS.toString().replace("%player%", player.getName()).replace("%power%", power.getIdentifier().toString()).replace("%power_source%", powerSource.getIdentifier().toString()));
                                                    });
                                                }
                                                commandSender.sendMessage(Lang.COMMAND_POWER_CLEAR_SUCCESS.toString().replace("%player%", player.getName()));
                                            });
                                        } else {
                                            commandSender.sendMessage(Lang.COMMAND_POWER_CLEAR_NO_POWER.toString().replace("%player%", player.getName()));
                                        }
                                    } else {
                                        commandSender.sendMessage(Lang.COMMAND_POWER_CLEAR_NO_POWER.toString().replace("%player%", player.getName()));
                                    }
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
                        throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_ORIGIN_UNKNOWN_ORIGIN_LAYER.toString().replace("%origin_layer%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
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
                        throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_ORIGIN_UNKNOWN_ORIGIN.toString().replace("%origin%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
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
                        throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_POWER_UNKNOWN_POWER.toString().replace("%power%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
            }
        }).replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> plugin.getRegistry().getRegisteredPowersKeySet().stream().map(Identifier::toString).toArray(String[]::new)));
    }

    public Argument<OriginItem> originItemArgument(String nodeName) {
        return new CustomArgument<>(new NamespacedKeyArgument(nodeName), customArgumentInfo -> {
            String string = customArgumentInfo.input();

            if (string.contains(":")) {
                Identifier identifier = Identifier.fromString(string);

                if (identifier != null) {
                    OriginItem originItem = plugin.getRegistry().getRegisteredOriginItem(identifier);

                    if (originItem != null) {
                        return originItem;
                    } else {
                        throw new CustomArgument.CustomArgumentException(Lang.COMMAND_ORIGIN_ITEM_UNKNOWN_ORIGIN_ITEM.toString().replace("%origin_item%", new CustomArgument.MessageBuilder().appendArgInput().toString()));
                    }
                } else {
                    throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
                }
            } else {
                throw new CustomArgument.CustomArgumentException(new CustomArgument.MessageBuilder(Lang.COMMAND_INVALID_IDENTIFIER.toString().replace("%identifier%", new CustomArgument.MessageBuilder().appendArgInput().toString())));
            }
        }).replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> plugin.getRegistry().getRegisteredOriginItemsKeySet().stream().map(Identifier::toString).toArray(String[]::new)));
    }
}

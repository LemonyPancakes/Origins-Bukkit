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

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.lemonypancakes.bukkit.origins.origin.Origin;
import me.lemonypancakes.bukkit.origins.OriginsBukkit;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class OriginArgument implements ArgumentType<Identifier> {

    public static OriginArgument origin() {
        return new OriginArgument();
    }

    public static Origin getOrigin(CommandContext<CommandSourceStack> context, String argumentName) {
        Identifier identifier = context.getArgument(argumentName, Identifier.class);

        if (identifier != null) {
            return OriginsBukkit.getRegistry().getRegisteredOrigin(identifier);
        }
        return null;
    }

    @Override
    public Identifier parse(StringReader reader) throws CommandSyntaxException {
        String string = reader.getString();
        return Identifier.fromString(string);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(OriginsBukkit.getRegistry().getRegisteredOriginsKeySet().stream().map(Objects::toString), builder);
    }
}

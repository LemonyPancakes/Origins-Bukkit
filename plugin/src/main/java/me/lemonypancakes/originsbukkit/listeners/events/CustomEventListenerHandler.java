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
package me.lemonypancakes.originsbukkit.listeners.events;

import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;
import me.lemonypancakes.originsbukkit.listeners.events.player.AsyncPlayerFluidInteract;
import me.lemonypancakes.originsbukkit.listeners.events.player.AsyncPlayerJoin;

public class CustomEventListenerHandler {

    private final ListenerHandler listenerHandler;
    private AsyncPlayerFluidInteract asyncPlayerFluidInteract;
    private AsyncPlayerJoin asyncPlayerJoin;

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    public AsyncPlayerFluidInteract getAsyncPlayerFluidInteract() {
        return asyncPlayerFluidInteract;
    }

    public AsyncPlayerJoin getAsyncPlayerJoin() {
        return asyncPlayerJoin;
    }

    public CustomEventListenerHandler(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        init();
    }

    private void init() {
        asyncPlayerFluidInteract
                = new AsyncPlayerFluidInteract(this);
        asyncPlayerJoin
                = new AsyncPlayerJoin(this);
    }
}

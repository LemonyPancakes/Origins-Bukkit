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
package me.lemonypancakes.originsbukkit.listeners.origins;

import me.lemonypancakes.originsbukkit.listeners.ListenerHandler;

public class OriginListenerHandler {
    
    private final ListenerHandler listenerHandler;
    private Human human;
    private Avian avian;
    private Arachnid arachnid;
    private Elytrian elytrian;
    private Shulk shulk;
    private Feline feline;
    private Enderian enderian;
    private Merling merling;
    private Phantom phantom;
    private Blazeborn blazeborn;

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    public Human getHuman() {
        return human;
    }

    public Avian getAvian() {
        return avian;
    }

    public Arachnid getArachnid() {
        return arachnid;
    }

    public Elytrian getElytrian() {
        return elytrian;
    }

    public Shulk getShulk() {
        return shulk;
    }

    public Feline getFeline() {
        return feline;
    }

    public Enderian getEnderian() {
        return enderian;
    }

    public Merling getMerling() {
        return merling;
    }

    public Phantom getPhantom() {
        return phantom;
    }

    public Blazeborn getBlazeborn() {
        return blazeborn;
    }

    public OriginListenerHandler(ListenerHandler listenerHandler) {
        this.listenerHandler = listenerHandler;
        init();
    }

    private void init() {
        human = new Human(this);
        avian = new Avian(this);
        arachnid = new Arachnid(this);
        elytrian = new Elytrian(this);
        shulk = new Shulk(this);
        feline = new Feline(this);
        enderian = new Enderian(this);
        merling = new Merling(this);
        phantom = new Phantom(this);
        blazeborn = new Blazeborn(this);
    }
}

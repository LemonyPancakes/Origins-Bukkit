package me.lemonypancakes.originsbukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class WorldDayAndNightCycleEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final World world;
    private final Time time;

    public WorldDayAndNightCycleEvent(World world, Time time) {
        this.world = world;
        this.time = time;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public World getWorld() {
        return this.world;
    }

    public Time getTime() {
        return this.time;
    }

    public enum Time {
        DAY, NIGHT
    }
}

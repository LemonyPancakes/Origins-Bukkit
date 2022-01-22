package me.lemonypancakes.originsbukkit.factory.event;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.events.world.WorldDayAndNightCycleEvent;
import me.lemonypancakes.originsbukkit.api.util.Registry;

public class WorldDayAndNightCycleConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/listener/world_day_and_night_cycle_event/compare/time"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof WorldDayAndNightCycleEvent) {
                                WorldDayAndNightCycleEvent event = (WorldDayAndNightCycleEvent) temp.getEvent();

                                if (data.has("time")) {
                                    WorldDayAndNightCycleEvent.Time time
                                            = WorldDayAndNightCycleEvent.Time.valueOf(
                                                    data.get("time").getAsString());

                                    return event.getTime() == time;
                                }
                            }
                            return false;
                        }
                )
        );
    }
}

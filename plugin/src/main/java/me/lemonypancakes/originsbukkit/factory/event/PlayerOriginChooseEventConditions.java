package me.lemonypancakes.originsbukkit.factory.event;

import me.lemonypancakes.originsbukkit.OriginsBukkit;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.type.Temp;
import me.lemonypancakes.originsbukkit.api.events.player.PlayerOriginChooseEvent;
import me.lemonypancakes.originsbukkit.api.util.Registry;

public class PlayerOriginChooseEventConditions {

    public static void register() {
        Registry.register(
                new ConditionContainer<Temp>(
                        new IdentifierContainer(
                                OriginsBukkit.KEY, "condition/listener/player_origin_choose_event/compare/origin"
                        ), null,
                        (data, temp) -> {
                            if (temp.getEvent() instanceof PlayerOriginChooseEvent) {
                                PlayerOriginChooseEvent event = (PlayerOriginChooseEvent) temp.getEvent();

                                if (data.has("origin")) {
                                    String origin = data.get("origin").getAsString();

                                    if (origin != null) {

                                        return event.getOrigin().equals(origin);
                                    }
                                }
                            }
                            return false;
                        }
                )
        );
    }
}

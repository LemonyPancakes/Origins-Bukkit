package me.lemonypancakes.originsbukkit.api.data.type;

import me.lemonypancakes.originsbukkit.api.data.container.OriginPlayerContainer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface OriginPlayer {

    UUID getPlayerUUID();

    Player getPlayer();

    Origin getOrigin();

    OriginPlayerContainer.Schedulers getSchedulers();
}

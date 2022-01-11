package me.lemonypancakes.originsbukkit.api.data.type;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.Set;

public interface Listener extends org.bukkit.event.Listener {

    Set<Player> getPlayersToListen();

    Power newInstance(Identifier identifier,
                      JsonObject jsonObject,
                      Action<?>[] actions,
                      Condition<?> condition);

    Power newInstance(Identifier identifier,
                      JsonObject jsonObject,
                      Action<?>[] actions);

    Power newInstance(Identifier identifier,
                      JsonObject jsonObject);

    Power newInstance(Identifier identifier);

    Power newInstance();

    <T> void onInvoke(T t);
}

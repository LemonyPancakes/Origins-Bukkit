package me.lemonypancakes.originsbukkit.factory.power.action;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Action;
import me.lemonypancakes.originsbukkit.DataType;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class CraftActionOverTimePower extends CraftPower {

    private int interval = 20;
    private Action<Entity> entityAction;
    private Action<Entity> risingAction;
    private Action<Entity> fallingAction;
    private Set<Player> active = new HashSet<>();

    public CraftActionOverTimePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("interval")) {
            interval = jsonObject.get("interval").getAsInt();
        }
        this.entityAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "entity_action");
        this.risingAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "rising_action");
        this.fallingAction = plugin.getLoader().loadAction(DataType.ENTITY, jsonObject, "falling_action");
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> {
                    if (getCondition().test(player)) {
                        if (!active.contains(player)) {
                            active.add(player);
                            risingAction.accept(player);
                        }
                        entityAction.accept(player);
                    } else {
                        if (active.contains(player)) {
                            fallingAction.accept(player);
                            active.remove(player);
                        }
                    }
                });
            }
        }.runTaskTimer(plugin.getJavaPlugin(), 0L, interval);
    }

    @Override
    protected void onMemberRemove(Player player) {
        active.remove(player);
    }
}

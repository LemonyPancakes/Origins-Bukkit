package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftSelfGlowPower extends CraftPower {

    private boolean useTeams;

    public CraftSelfGlowPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("use_teams")) {
            this.useTeams = jsonObject.get("use_teams").getAsBoolean();
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                getMembers().forEach(player -> player.setGlowing(getCondition().test(player)));
            }
        }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
    }

    public CraftSelfGlowPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftSelfGlowPower(plugin, identifier, jsonObject);
    }

    @Override
    protected void onMemberAdd(Player player) {
        player.setGlowing(getCondition().test(player));
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setGlowing(false);
    }
}

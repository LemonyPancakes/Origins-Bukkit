package me.lemonypancakes.bukkit.origins.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.CraftPower;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftEntityGlowPower extends CraftPower {

    private boolean useTeams;

    public CraftEntityGlowPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
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

    @Override
    protected void onMemberAdd(Player player) {
        player.setGlowing(getCondition().test(player));
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setGlowing(false);
    }
}

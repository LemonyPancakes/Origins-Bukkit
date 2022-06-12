package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftEntityGlowPower extends CraftPower {

    private boolean useTeams;

    public CraftEntityGlowPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                if (jsonObject.has("use_teams")) {
                    this.useTeams = jsonObject.get("use_teams").getAsBoolean();
                }
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        getMembers().forEach(player -> {
                            Temp temp = new CraftTemp();

                            temp.set(DataType.ENTITY, player);
                            player.setGlowing(testAndAccept(temp));
                        });
                    }
                }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
            }
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftEntityGlowPower(plugin, identifier, jsonObject, false);
    }
}

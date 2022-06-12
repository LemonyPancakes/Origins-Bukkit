package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftScheduler;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CraftExhaustPower extends CraftPower {

    private long interval = 4;
    private long onsetDelay = interval;
    private int exhaustion;

    public CraftExhaustPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                if (jsonObject.has("interval")) {
                    this.interval = jsonObject.get("interval").getAsLong();
                }
                if (jsonObject.has("onset_delay")) {
                    this.onsetDelay = jsonObject.get("onset_delay").getAsLong();
                }
                if (jsonObject.has("exhaustion")) {
                    this.exhaustion = jsonObject.get("exhaustion").getAsInt();
                }
            }
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftExhaustPower(plugin, identifier, jsonObject, false);
    }

    @Override
    protected void onMemberAdd(Player player) {
        OriginPlayer originPlayer = getPlugin().getOriginPlayer(player);
        Temp temp = new CraftTemp();

        temp.set(DataType.ENTITY, player);
        if (originPlayer != null) {
            BukkitTask bukkitTask = new BukkitRunnable() {

                @Override
                public void run() {
                    if (testAndAccept(temp)) {
                        PlayerUtils.exhaust(player, exhaustion);
                    }
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), onsetDelay, interval);
            Scheduler scheduler = new CraftScheduler(Identifier.fromString("origins-bukkit:exhaust"));
            scheduler.setBukkitTask(bukkitTask);
            originPlayer.getSchedulers().add(scheduler);
        }
    }
}

package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginPlayer;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.Scheduler;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftScheduler;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CraftBurnPower extends CraftPower {

    private long interval = 0;
    private long onsetDelay = interval;
    private int burnDuration = 0;

    public CraftBurnPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("interval")) {
            this.interval = jsonObject.get("interval").getAsLong();
        }
        if (jsonObject.has("onset_delay")) {
            this.onsetDelay = jsonObject.get("onset_delay").getAsLong();
        }
        if (jsonObject.has("burn_duration")) {
            this.burnDuration = jsonObject.get("burn_duration").getAsInt();
        }
    }

    @Override
    protected void onMemberAdd(Player player) {
        OriginPlayer originPlayer = getPlugin().getOriginPlayer(player);

        if (originPlayer != null) {
            BukkitTask bukkitTask = new BukkitRunnable() {

                @Override
                public void run() {
                    if (getCondition().test(player)) {
                        player.setFireTicks(burnDuration);
                    }
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), onsetDelay, interval);
            Scheduler scheduler = new CraftScheduler(Identifier.fromString("origins-bukkit:burn"));
            scheduler.setBukkitTask(bukkitTask);
            originPlayer.getSchedulers().add(scheduler);
        }
    }

    @Override
    protected void onMemberRemove(Player player) {
        player.setFireTicks(0);
    }
}

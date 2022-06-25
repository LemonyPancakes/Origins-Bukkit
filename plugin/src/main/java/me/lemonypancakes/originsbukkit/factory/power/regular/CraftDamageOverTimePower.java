package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginPlayer;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.Scheduler;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftScheduler;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CraftDamageOverTimePower extends CraftPower {

    private long interval = 4;
    private long onsetDelay = interval;
    private double damage = 1;
    private double damageEasy = damage;

    public CraftDamageOverTimePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("interval")) {
            this.interval = jsonObject.get("interval").getAsLong();
        }
        if (jsonObject.has("onset_delay")) {
            this.onsetDelay = jsonObject.get("onset_delay").getAsLong();
        }
        if (jsonObject.has("damage")) {
            this.damage = jsonObject.get("damage").getAsDouble();
        }
        if (jsonObject.has("damage_easy")) {
            this.damageEasy = jsonObject.get("damage_easy").getAsDouble();
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
                        if (player.getWorld().getDifficulty() == Difficulty.EASY) {
                            player.damage(damageEasy);
                        } else {
                            player.damage(damage);
                        }
                    }
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), onsetDelay, interval);
            Scheduler scheduler = new CraftScheduler(Identifier.fromString("origins-bukkit:damage_over_time"));
            scheduler.setBukkitTask(bukkitTask);
            originPlayer.getSchedulers().add(scheduler);
        }
    }
}
package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftScheduler;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
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

    public CraftDamageOverTimePower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                if (jsonObject.has("interval")) {
                    this.interval = jsonObject.get("interval").getAsLong();
                }
                if (jsonObject.has("onset_delay")) {
                    this.onsetDelay = jsonObject.get("onset_delay").getAsLong();
                }
                if (jsonObject.has("damage")) {
                    this.damage = jsonObject.get("damage").getAsDouble();
                }
                if (jsonObject.has("damageEasy")) {
                    this.damageEasy = jsonObject.get("damageEasy").getAsDouble();
                }
            }
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftDamageOverTimePower(plugin, identifier, jsonObject, false);
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
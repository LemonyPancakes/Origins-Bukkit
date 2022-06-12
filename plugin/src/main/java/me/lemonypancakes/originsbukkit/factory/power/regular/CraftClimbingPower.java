package me.lemonypancakes.originsbukkit.factory.power.regular;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.data.CraftScheduler;
import me.lemonypancakes.originsbukkit.data.CraftTemp;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CraftClimbingPower extends CraftPower {

    private double climbingSpeed = 0.175;

    public CraftClimbingPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject, boolean isFactory) {
        super(plugin, identifier, jsonObject, isFactory);
        if (!isFactory) {
            if (jsonObject != null) {
                if (jsonObject.has("climbing_speed")) {
                    this.climbingSpeed = jsonObject.get("climbing_speed").getAsDouble();
                }
            }
        }
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftClimbingPower(plugin, identifier, jsonObject, false);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onArachnidClimbToggle(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (getMembers().contains(player)) {
            if (event.isSneaking() && !player.isGliding()) {
                climb(player);
            }
        }
    }

    private void climb(Player player) {
        OriginPlayer originPlayer = getPlugin().getOriginPlayer(player);
        Temp temp = new CraftTemp();
        Scheduler scheduler = new CraftScheduler(Identifier.fromString("origins-bukkit:climbing"));

        temp.set(DataType.ENTITY, player);
        if (originPlayer != null) {
            BukkitTask bukkitTask = new BukkitRunnable() {

                @Override
                public void run() {
                    if (player.isSneaking() && !player.isGliding()) {
                        if (testAndAccept(temp)) {
                            player.setVelocity(player.getVelocity().setY(climbingSpeed));
                        }
                    } else {
                        cancel();
                        originPlayer.getSchedulers().removeByValue(scheduler);
                    }
                }
            }.runTaskTimer(getPlugin().getJavaPlugin(), 0L, 1L);
            scheduler.setBukkitTask(bukkitTask);
            originPlayer.getSchedulers().add(scheduler);
        }
    }
}

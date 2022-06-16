package me.lemonypancakes.originsbukkit.factory.power;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.Power;
import me.lemonypancakes.originsbukkit.data.CraftPower;
import me.lemonypancakes.originsbukkit.util.Identifier;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class CraftCooldownPower extends CraftPower {

    private final Map<Player, Integer> timeLeft = new HashMap<>();

    private int cooldown = 1;
    private boolean hudRender = true;

    public CraftCooldownPower(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        super(plugin, identifier, jsonObject);
        if (jsonObject.has("cooldown")) {
            this.cooldown = jsonObject.get("cooldown").getAsInt();
        }
        if (jsonObject.has("hud_render")) {
            this.hudRender = jsonObject.get("hud_render").getAsBoolean();
        }
        if (hudRender) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    for (Map.Entry<Player, Integer> map : timeLeft.entrySet()) {
                        Player player = map.getKey();

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(getProgressBar(getTimeLeftInTicks(player), cooldown, 64, '|', ChatColor.RED, ChatColor.GREEN)));
                        if (canUse(player)) {
                            timeLeft.remove(player);
                        }
                    }
                }
            }.runTaskTimer(plugin.getJavaPlugin(), 0L, 1L);
        }
    }

    public CraftCooldownPower(OriginsBukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public Power newInstance(OriginsBukkitPlugin plugin, Identifier identifier, JsonObject jsonObject) {
        return new CraftCooldownPower(plugin, identifier, jsonObject);
    }

    public boolean canUse(Player player) {
        return getTimeLeftInTicks(player) <= 0;
    }

    public int getTimeLeftInTicks(Player player) {
        if (timeLeft.containsKey(player)) {
            return Math.max(cooldown - (millisecondsToTicks(System.currentTimeMillis()) - timeLeft.get(player)), 0);
        }
        return 0;
    }

    public void setCooldown(Player player) {
        timeLeft.put(player, millisecondsToTicks(System.currentTimeMillis()));
    }

    public boolean isHudRender() {
        return hudRender;
    }

    public int millisecondsToTicks(long milliseconds) {
        return (int) (milliseconds / 20);
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    @Override
    protected void onMemberRemove(Player player) {
        timeLeft.remove(player);
    }
}

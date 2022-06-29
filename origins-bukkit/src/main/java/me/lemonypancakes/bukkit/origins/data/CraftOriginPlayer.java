package me.lemonypancakes.bukkit.origins.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.bukkit.origins.Origin;
import me.lemonypancakes.bukkit.origins.OriginPlayer;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.data.storage.other.Misc;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Config;
import me.lemonypancakes.bukkit.origins.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CraftOriginPlayer implements OriginPlayer {

    private final OriginsBukkitPlugin plugin;
    private final Player player;
    private final Schedulers schedulers;
    private JsonObject metadata;
    private Origin origin;

    public CraftOriginPlayer(OriginsBukkitPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.schedulers = new Schedulers();
        this.metadata = plugin.getStorage().getMetadata(getPlayer());
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(Origin origin) {
        if (origin == null) {
            if (this.origin != null) {
                unlistenAndDestroy();
                plugin.getStorage().setOrigin(getPlayer(), null);
                this.origin = null;
                Misc.VIEWERS.put(player.getUniqueId(), 0);
                getPlayer().openInventory(Misc.GUIS.get(0));
            }
        } else {
            if (!origin.getIdentifier().toString().equals("origins-bukkit:dummy_origin")) {
                if (plugin.getRegistry().hasOrigin(origin.getIdentifier())) {
                    if (this.origin != null) {
                        unlistenAndDestroy();
                    }
                    this.origin = origin;
                    if (Misc.VIEWERS.containsKey(player.getUniqueId())) {
                        Misc.VIEWERS.remove(player.getUniqueId());
                        getPlayer().closeInventory();
                    }
                    if (origin.getPowers() != null) {
                        origin.getPowers().forEach(power -> power.addMember(getPlayer()));
                    }
                    plugin.getStorage().setOrigin(getPlayer(), origin.getIdentifier());
                }
            }
        }
    }

    @Override
    public Schedulers getSchedulers() {
        return schedulers;
    }

    @Override
    public void unlisten() {
        if (getOrigin() != null) {
            if (getOrigin().getPowers() != null) {
                getOrigin().getPowers().forEach(power -> power.removeMember(getPlayer()));
            }
        }
    }

    @Override
    public void unlistenAndDestroy() {
        unlisten();
        getSchedulers().destroy();
    }

    @Override
    public void refresh() {
        if (plugin.getStorage().getOrigin(getPlayer()) != null) {
            Identifier originIdentifier = plugin.getStorage().getOrigin(getPlayer());

            if (plugin.getRegistry().hasOrigin(originIdentifier)) {
                Origin origin = plugin.getRegistry().getOrigin(originIdentifier);

                if (origin != null) {
                    setOrigin(origin);
                }
            } else {
                plugin.getStorage().setOrigin(getPlayer(), null);
                Bukkit.getScheduler().runTaskLater(plugin.getJavaPlugin(), bukkitTask -> {
                    Misc.VIEWERS.put(getPlayer().getUniqueId(), 0);
                    getPlayer().openInventory(Misc.GUIS.get(0));
                }, 20L);
                ChatUtils.sendPlayerMessage(getPlayer(), "&cYour origin (&e\"" + originIdentifier + "\"&c) doesn't exist so we pruned your player data.");
            }
        } else {
            if (!plugin.getStorage().hasOriginPlayerData(getPlayer())) {
                String starterOriginString = Config.STARTER_ORIGIN.toString();

                if (!starterOriginString.isEmpty()) {
                    Identifier starterOriginIdentifier = Identifier.fromString(starterOriginString);

                    if (starterOriginIdentifier != null) {
                        Origin starterOrigin = plugin.getRegistry().getOrigin(starterOriginIdentifier);

                        if (starterOrigin != null) {
                            setOrigin(starterOrigin);
                            return;
                        }
                    }
                }
            }
            Bukkit.getScheduler().runTaskLater(plugin.getJavaPlugin(), bukkitTask -> {
                Misc.VIEWERS.put(getPlayer().getUniqueId(), 0);
                getPlayer().openInventory(Misc.GUIS.get(0));
            }, 20L);
        }
    }

    @Override
    public JsonObject getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOriginPlayer)) return false;
        CraftOriginPlayer that = (CraftOriginPlayer) itemStack;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(getPlayer(), that.getPlayer()) && Objects.equals(getSchedulers(), that.getSchedulers()) && Objects.equals(getMetadata(), that.getMetadata()) && Objects.equals(getOrigin(), that.getOrigin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), getPlayer(), getSchedulers(), getMetadata(), getOrigin());
    }

    @Override
    public String toString() {
        return "CraftOriginPlayer{" +
                "plugin=" + plugin +
                ", player=" + player +
                ", schedulers=" + schedulers +
                ", metadata=" + metadata +
                ", origin=" + origin +
                '}';
    }
}

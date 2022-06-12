package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.util.Identifier;
import me.lemonypancakes.originsbukkit.Origin;
import me.lemonypancakes.originsbukkit.OriginPlayer;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class CraftOriginPlayer implements OriginPlayer {

    private final OriginsBukkitPlugin plugin;
    private final UUID uuid;
    private final Schedulers schedulers;
    private Origin origin;

    public CraftOriginPlayer(OriginsBukkitPlugin plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.schedulers = new Schedulers();

        if (plugin.getOriginPlayerDataFetcher().getOrigin(getPlayer()) != null) {
            Identifier originIdentifier = plugin.getOriginPlayerDataFetcher().getOrigin(getPlayer());

            if (plugin.getRegistry().hasOrigin(originIdentifier)) {
                Origin origin = plugin.getRegistry().getOrigin(originIdentifier);

                if (origin != null) {
                    setOrigin(origin);
                }
            } else {
                plugin.getOriginPlayerDataFetcher().setOrigin(getPlayer(), null);
                Bukkit.getScheduler().runTaskLater(plugin.getJavaPlugin(), bukkitTask -> {
                    Misc.VIEWERS.put(getPlayer().getUniqueId(), 0);
                    getPlayer().openInventory(Misc.GUIS.get(0));
                }, 20L);
                ChatUtils.sendPlayerMessage(getPlayer(), "&cYour origin (&e\"" + originIdentifier + "\"&c) doesn't exist so we pruned your player data.");
            }
        } else {
            Bukkit.getScheduler().runTaskLater(plugin.getJavaPlugin(), bukkitTask -> {
                Misc.VIEWERS.put(getPlayer().getUniqueId(), 0);
                getPlayer().openInventory(Misc.GUIS.get(0));
                }, 20L);
        }
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Player getPlayer() {
        if (Bukkit.getPlayer(uuid) != null) {
            return Bukkit.getPlayer(uuid);
        }
        return (Player) Bukkit.getOfflinePlayer(uuid);
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
                plugin.getOriginPlayerDataFetcher().setOrigin(getPlayer(), null);
                this.origin = null;
                Misc.VIEWERS.put(getUUID(), 0);
                getPlayer().openInventory(Misc.GUIS.get(0));
            }
        } else {
            if (!origin.getIdentifier().toString().equals("origins-bukkit:dummy_origin")) {
                if (plugin.getRegistry().hasOrigin(origin.getIdentifier())) {
                    if (this.origin != null) {
                        unlistenAndDestroy();
                    }
                    this.origin = origin;
                    if (Misc.VIEWERS.containsKey(getUUID())) {
                        Misc.VIEWERS.remove(getUUID());
                        getPlayer().closeInventory();
                    }
                    if (origin.getPowers() != null) {
                        origin.getPowers().forEach(power -> power.addMember(getPlayer()));
                    }
                    plugin.getOriginPlayerDataFetcher().setOrigin(getPlayer(), origin.getIdentifier());
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
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftOriginPlayer)) return false;
        CraftOriginPlayer that = (CraftOriginPlayer) o;
        return Objects.equals(getUUID(), that.getUUID()) && Objects.equals(getSchedulers(), that.getSchedulers()) && Objects.equals(getOrigin(), that.getOrigin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUUID(), getSchedulers(), getOrigin());
    }

    @Override
    public String toString() {
        return "OriginPlayerContainer{" +
                "playerUUID=" + uuid +
                ", schedulers=" + schedulers +
                ", origin=" + origin +
                '}';
    }
}

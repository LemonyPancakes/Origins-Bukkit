package me.lemonypancakes.originsbukkit.data;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.Origin;
import me.lemonypancakes.originsbukkit.OriginPlayer;
import me.lemonypancakes.originsbukkit.OriginsBukkitPlugin;
import me.lemonypancakes.originsbukkit.data.storage.other.Misc;
import me.lemonypancakes.originsbukkit.util.ChatUtils;
import me.lemonypancakes.originsbukkit.util.Config;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class CraftOriginPlayer implements OriginPlayer {

    private final OriginsBukkitPlugin plugin;
    private final UUID uuid;
    private final Schedulers schedulers;
    private JsonObject metadata;
    private Origin origin;

    public CraftOriginPlayer(OriginsBukkitPlugin plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.schedulers = new Schedulers();
        this.metadata = plugin.getStorage().getMetadata(getPlayer());

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
                plugin.getStorage().setOrigin(getPlayer(), null);
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
    public JsonObject getMetadata() {
        return metadata;
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public boolean equals(Object itemStack) {
        if (this == itemStack) return true;
        if (!(itemStack instanceof CraftOriginPlayer)) return false;
        CraftOriginPlayer that = (CraftOriginPlayer) itemStack;
        return Objects.equals(getPlugin(), that.getPlugin()) && Objects.equals(uuid, that.uuid) && Objects.equals(getSchedulers(), that.getSchedulers()) && Objects.equals(getOrigin(), that.getOrigin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlugin(), uuid, getSchedulers(), getOrigin());
    }

    @Override
    public String toString() {
        return "CraftOriginPlayer{" +
                "plugin=" + plugin +
                ", uuid=" + uuid +
                ", schedulers=" + schedulers +
                ", origin=" + origin +
                '}';
    }
}

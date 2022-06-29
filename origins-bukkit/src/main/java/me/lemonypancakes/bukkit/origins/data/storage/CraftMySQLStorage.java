package me.lemonypancakes.bukkit.origins.data.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.lemonypancakes.bukkit.origins.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.Storage;
import me.lemonypancakes.bukkit.origins.util.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CraftMySQLStorage implements Storage, Listener {

    private final OriginsBukkitPlugin plugin;
    private final HikariDataSource hikariDataSource;

    private static final String tablePrefix = "originsBukkit_";

    private static final String INSERT = "INSERT INTO " + tablePrefix + "originPlayers" + " (UUID, name, origin) VALUES (?,?,?) ON DUPLICATE KEY UPDATE name=?, origin=?";
    private static final String SELECT = "SELECT * FROM " + tablePrefix + "originPlayers" + " WHERE UUID=?";
    private static final String DELETE = "DELETE FROM " + tablePrefix + "originPlayers" + " WHERE UUID=?";

    private static final Map<UUID, Pair<Identifier, Boolean>> CACHE = new HashMap<>();

    public CraftMySQLStorage(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(Config.STORAGE_DATA_POOL_SETTINGS_MAXIMUM_POOL_SIZE.toInt());
        hikariConfig.setMinimumIdle(Config.STORAGE_DATA_POOL_SETTINGS_MINIMUM_IDLE.toInt());
        hikariConfig.setMaxLifetime(Config.STORAGE_DATA_POOL_SETTINGS_MAXIMUM_LIFETIME.toLong());
        hikariConfig.setKeepaliveTime(Config.STORAGE_DATA_POOL_SETTINGS_KEEPALIVE_TIME.toLong());
        hikariConfig.setConnectionTimeout(Config.STORAGE_DATA_POOL_SETTINGS_CONNECTION_TIMEOUT.toLong());
        this.hikariDataSource = new HikariDataSource();
        this.hikariDataSource.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        this.hikariDataSource.addDataSourceProperty("serverName", getUrl().split(":")[0]);
        this.hikariDataSource.addDataSourceProperty("port", getUrl().split(":")[1]);
        this.hikariDataSource.addDataSourceProperty("databaseName", getDatabase());
        this.hikariDataSource.addDataSourceProperty("user", getUsername());
        this.hikariDataSource.addDataSourceProperty("password", getPassword());
        setupTable();
        Bukkit.getPluginManager().registerEvents(this, plugin.getJavaPlugin());
        try {
            CompletableFuture.runAsync(() -> Bukkit.getOnlinePlayers().forEach(player -> loadData(player, null))).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}

    @Override
    public Identifier getOrigin(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();

        if (CACHE.containsKey(uuid)) {
            return CACHE.get(uuid).getLeft();
        }
        return null;
    }

    @Override
    public void setOrigin(OfflinePlayer offlinePlayer, Identifier identifier) {
        UUID uuid = offlinePlayer.getUniqueId();

        if (identifier == null) {
            CACHE.get(uuid).setLeft(null);
            CACHE.get(uuid).setRight(false);
        } else {
            CACHE.get(uuid).setLeft(identifier);
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                saveData(offlinePlayer);
            }
        }.runTaskAsynchronously(plugin.getJavaPlugin());
    }

    @Override
    public boolean hasOriginPlayerData(OfflinePlayer offlinePlayer) {
        return CACHE.containsKey(offlinePlayer.getUniqueId()) && CACHE.get(offlinePlayer.getUniqueId()) != null && CACHE.get(offlinePlayer.getUniqueId()).getRight() != null && !CACHE.get(offlinePlayer.getUniqueId()).getRight();
    }

    @Override
    public void wipeOriginPlayerData(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();

        new BukkitRunnable() {

            @Override
            public void run() {
                try (Connection connection = hikariDataSource.getConnection();
                     PreparedStatement delete = connection.prepareStatement(DELETE)) {
                    delete.setString(1, uuid.toString());
                    delete.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin.getJavaPlugin());
        CACHE.remove(uuid);
    }

    @Override
    public JsonObject getMetadata(OfflinePlayer offlinePlayer) {
        String string = BukkitPersistentDataUtils.getPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "metadata"), PersistentDataType.STRING);

        if (string != null) {
            return new Gson().fromJson(string, JsonObject.class);
        }
        BukkitPersistentDataUtils.setPersistentData((PersistentDataHolder) offlinePlayer, new Identifier(Identifier.ORIGINS_BUKKIT, "metadata"), PersistentDataType.STRING, "{}");
        return new Gson().fromJson("{}", JsonObject.class);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        loadData(Bukkit.getOfflinePlayer(event.getUniqueId()), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        new BukkitRunnable() {

            @Override
            public void run() {
                saveData(player);
            }
        }.runTaskAsynchronously(plugin.getJavaPlugin());
        CACHE.remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == this.plugin) {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Pushing cached player data into database.");
                Bukkit.getOnlinePlayers().forEach(this::saveData);
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Finalizing data.");
            }
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Closing database connection.");
            hikariDataSource.close();
        }
    }

    private void loadData(OfflinePlayer offlinePlayer, AsyncPlayerPreLoginEvent event) {
        UUID uuid = offlinePlayer.getUniqueId();

        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement select = connection.prepareStatement(SELECT)) {
            select.setString(1, uuid.toString());
            ResultSet result = select.executeQuery();
            if (result.next()) {
                String string = result.getString("origin");

                if (string != null) {
                    if (string.contains(":")) {
                        Identifier identifier = Identifier.fromString(string);

                        if (identifier != null) {
                            CACHE.put(uuid, new Pair<>(identifier, false));
                        }
                    } else {
                        CACHE.put(uuid, new Pair<>(null, false));
                    }
                } else {
                    CACHE.put(uuid, new Pair<>(null, false));
                }
            } else {
                CACHE.put(uuid, new Pair<>(null, true));
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (offlinePlayer instanceof Player) {
                ((Player) offlinePlayer).kickPlayer(null);
            } else {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            }
        }
    }

    private void saveData(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        String name = offlinePlayer.getName();

        if (CACHE.containsKey(uuid)) {
            try (Connection connection = hikariDataSource.getConnection();
                 PreparedStatement insert = connection.prepareStatement(INSERT)){
                insert.setString(1, uuid.toString());
                insert.setString(2, name);
                insert.setString(3, String.valueOf(CACHE.get(uuid).getLeft()));
                insert.setString(4, name);
                insert.setString(5, String.valueOf(CACHE.get(uuid).getLeft()));
                insert.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupTable() {
        try (Connection connection = hikariDataSource.getConnection();
            Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + getTablePrefix() + "originPlayers(UUID varchar(36), name VARCHAR(16), origin varchar(256), PRIMARY KEY (UUID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        String url = Config.STORAGE_DATA_ADDRESS.toString();
        if (!url.contains(":")) {
            return url + ":" + "3306";
        }
        return url;
    }

    public String getDatabase() {
        return Config.STORAGE_DATA_DATABASE.toString();
    }

    public String getUsername() {
        return Config.STORAGE_DATA_USERNAME.toString();
    }

    public String getPassword() {
        return Config.STORAGE_DATA_PASSWORD.toString();
    }

    public String getTablePrefix() {
        return Config.STORAGE_DATA_TABLE_PREFIX.toString();
    }
}

/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.data.storage;

import me.lemonypancakes.bukkit.common.com.zaxxer.hikari.HikariConfig;
import me.lemonypancakes.bukkit.common.com.zaxxer.hikari.HikariDataSource;
import me.lemonypancakes.bukkit.origins.data.serialization.StorageSerializable;
import me.lemonypancakes.bukkit.origins.entity.player.OriginPlayer;
import me.lemonypancakes.bukkit.origins.plugin.OriginsBukkitPlugin;
import me.lemonypancakes.bukkit.origins.util.ChatUtils;
import me.lemonypancakes.bukkit.origins.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.*;

public class CraftMySQLStorage implements Storage, Listener {

    private final OriginsBukkitPlugin plugin;
    private final HikariDataSource hikariDataSource;

    private static final String tablePrefix = Config.STORAGE_DATA_TABLE_PREFIX.toString();

    private static final String INSERT = "INSERT INTO " + tablePrefix + "originPlayers" + " (UUID, name, origin, power, metadata, hasOriginBefore) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?, origin=?, power=?, metadata=?, hasOriginBefore=?";
    private static final String SELECT = "SELECT * FROM " + tablePrefix + "originPlayers" + " WHERE UUID=?";
    private static final String DELETE = "DELETE FROM " + tablePrefix + "originPlayers" + " WHERE UUID=?";

    private final Set<UUID> saving = new HashSet<>();
    private final Map<UUID, Map<String, Object>> data = new HashMap<>();

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
        Bukkit.getOnlinePlayers().forEach(player -> loadData(player, null));
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}


    @Override
    public Map<String, Object> getData(OfflinePlayer offlinePlayer) {
        return data.get(offlinePlayer.getUniqueId());
    }

    @Override
    public void saveData(OfflinePlayer offlinePlayer, StorageSerializable storageSerializable) {
        saveData(offlinePlayer, storageSerializable.serialize());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (saving.contains(event.getUniqueId())) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("Not yet finished saving your data. Please try again later.");
        } else {
            loadData(Bukkit.getOfflinePlayer(event.getUniqueId()), event);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        OriginPlayer originPlayer = plugin.getOriginPlayer(player);

        saving.add(player.getUniqueId());
        if (originPlayer != null) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    saveData(player, originPlayer.serialize());
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            saving.remove(player.getUniqueId());
                            data.remove(player.getUniqueId());
                        }
                    }.runTask(plugin.getJavaPlugin());
                }
            }.runTaskAsynchronously(plugin.getJavaPlugin());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == plugin) {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Pushing player data into database.");
                Bukkit.getOnlinePlayers().forEach(player -> saveData(player, plugin.getOriginPlayer(player)));
            }
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Closing database connection.");
            hikariDataSource.close();
        }
    }

    private void loadData(OfflinePlayer offlinePlayer, AsyncPlayerPreLoginEvent event) {
        UUID uuid = offlinePlayer.getUniqueId();

        try (Connection connection = hikariDataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement select = connection.prepareStatement(SELECT)) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + getTablePrefix() + "originPlayers(UUID varchar(36), name VARCHAR(16), origin longtext, power longtext, metadata longtext, hasOriginBefore int(1), PRIMARY KEY (UUID))");
            select.setString(1, uuid.toString());
            ResultSet result = select.executeQuery();
            Map<String, Object> map = new LinkedHashMap<>();

            if (result.next()) {
                map.put("origin", result.getString("origin"));
                map.put("power", result.getString("power"));
                map.put("metadata", result.getString("metadata"));
                map.put("hasOriginBefore", result.getInt("hasOriginBefore") > 0);
            }
            data.put(uuid, map);
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (event != null) {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.setKickMessage("An error occurred while loading your origin data.");
            } else {
                if (offlinePlayer instanceof Player) {
                    ((Player) offlinePlayer).kickPlayer(null);
                }
            }
        }
    }

    private void saveData(OfflinePlayer offlinePlayer, Map<String, Object> data) {
        UUID uuid = offlinePlayer.getUniqueId();
        String name = offlinePlayer.getName();

        try (Connection connection = hikariDataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement insert = connection.prepareStatement(INSERT)) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + getTablePrefix() + "originPlayers(UUID varchar(36), name VARCHAR(16), origin longtext, power longtext, metadata longtext, hasOriginBefore int(1), PRIMARY KEY (UUID))");
            insert.setString(1, uuid.toString());
            insert.setString(2, name);
            insert.setString(3, (String) data.get("origin"));
            insert.setString(4, (String) data.get("power"));
            insert.setString(5, (String) data.get("metadata"));
            insert.setInt(6, (boolean) data.get("hasOriginBefore") ? 1 : 0);
            insert.setString(7, name);
            insert.setString(8, (String) data.get("origin"));
            insert.setString(9, (String) data.get("power"));
            insert.setString(10, (String) data.get("metadata"));
            insert.setInt(11, (boolean) data.get("hasOriginBefore") ? 1 : 0);
            insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {
        try (Connection connection = hikariDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + getTablePrefix() + "originPlayers(UUID varchar(36), name VARCHAR(16), origin longtext, power longtext, metadata longtext, hasOriginBefore int(1), PRIMARY KEY (UUID))");
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

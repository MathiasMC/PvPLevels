package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    private final PvPLevels plugin;

    private Connection connection;
    private final boolean debug_database;

    public Database(final PvPLevels plugin) {
        this.plugin = plugin;
        debug_database = plugin.config.get.getBoolean("debug.database");
        (new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.createStatement().execute("SELECT 1");
                        if (debug_database) { plugin.textUtils.debug("[Database] connection is still valid"); }
                    }
                } catch (SQLException e) {
                    connection = get();
                    if (debug_database) { plugin.textUtils.debug("[Database] connection is not valid creating new"); }
                }
            }
        }).runTaskTimerAsynchronously(plugin, 60 * 20, 60 * 20);
    }

    private Connection get() {
        try {
            if (plugin.config.get.getBoolean("mysql.use")) {
                Class.forName("com.mysql.jdbc.Driver");
                if (debug_database) { plugin.textUtils.debug("[Database] Getting connection (MySQL)"); }
                return DriverManager.getConnection("jdbc:mysql://" + plugin.config.get.getString("mysql.host") + ":" + plugin.config.get.getString("mysql.port") + "/" + plugin.config.get.getString("mysql.database"), plugin.config.get.getString("mysql.username"), plugin.config.get.getString("mysql.password"));
            } else {
                Class.forName("org.sqlite.JDBC");
                if (debug_database) { plugin.textUtils.debug("[Database] Getting connection (SQLite)"); }
                return DriverManager.getConnection("jdbc:sqlite:" + new File(plugin.getDataFolder(), "data.db"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            plugin.textUtils.exception(e.getStackTrace(), e.getMessage());
            return null;
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            if (debug_database) { plugin.textUtils.debug("[Database] Closing connection"); }
        }
    }

    private boolean check() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = get();
            if (connection == null || connection.isClosed()) {
                return false;
            }
            if (plugin.config.get.contains("mysql.alter") && plugin.config.get.getBoolean("mysql.alter")) {
                connection.createStatement().execute("ALTER TABLE `players` ADD COLUMN `name` longtext(255);");
                connection.createStatement().execute("ALTER TABLE `players` RENAME TO `players_temp`;");
                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255) PRIMARY KEY, `name` longtext(255), `kills` bigint(255), `deaths` bigint(255), `xp` bigint(255), `level` bigint(255), `killstreak` bigint(255), `killstreak_top` bigint(255), `coins` bigint(255), `lastseen` DATETIME);");
                connection.createStatement().execute("INSERT INTO `players` (uuid, name, kills, deaths, xp, level, killstreak, killstreak_top, coins, lastseen) SELECT uuid, name, kills, deaths, xp, level, killstreak, killstreak_top, coins, lastseen FROM players_temp");
                connection.createStatement().execute("DROP TABLE `players_temp`;");
                plugin.textUtils.warning(" ");
                plugin.textUtils.warning(" ");
                plugin.textUtils.warning("Altered database with new data (Restart the server and DELETE ALTER FROM config.yml!)");
                plugin.textUtils.warning(" ");
                plugin.textUtils.warning(" ");
            } else {
                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255) PRIMARY KEY, `name` longtext(255), `kills` bigint(255), `deaths` bigint(255), `xp` bigint(255), `level` bigint(255), `killstreak` bigint(255), `killstreak_top` bigint(255), `coins` bigint(255), `lastseen` DATETIME);");
            }
            if (debug_database) { plugin.textUtils.debug("[Database] Creating table if not exists"); }
        }
        return true;
    }

    public boolean set() {
        try {
            return check();
        } catch (SQLException e) {
            return false;
        }
    }

    public void insert(final String uuid, final String name) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM players WHERE uuid= '" + uuid + "';");
                        if (!resultSet.next()) {
                            preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, name, kills, deaths, xp, level, killstreak, killstreak_top, coins, lastseen) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                            preparedStatement.setString(1, uuid);
                            preparedStatement.setString(2, name);
                            preparedStatement.setLong(3, 0L);
                            preparedStatement.setLong(4, 0L);
                            preparedStatement.setLong(5, 0L);
                            preparedStatement.setLong(6, 0L);
                            preparedStatement.setLong(7, 0L);
                            preparedStatement.setLong(8, 0L);
                            preparedStatement.setLong(9, 0L);
                            preparedStatement.setTimestamp(10, new Timestamp(new Date().getTime()));
                            preparedStatement.executeUpdate();
                            if (debug_database) { plugin.textUtils.debug("[Database] Inserting default values for UUID: " + uuid); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public void delete(final String uuid) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM players WHERE uuid= '" + uuid + "';");
                        if (resultSet.next()) {
                            preparedStatement = connection.prepareStatement("DELETE FROM players WHERE uuid = ?");
                            preparedStatement.setString(1, uuid);
                            preparedStatement.executeUpdate();
                            plugin.unload(uuid);
                            if (debug_database) { plugin.textUtils.debug("[Database] Deleting player: " + uuid); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public void setValues(final String uuid, final String name, final Long kills, final Long deaths, final Long xp, final Long level, final Long killstreak, final Long killstreak_top, final Long coins, Timestamp timestamp) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM players WHERE uuid= '" + uuid + "';");
                        if (resultSet.next()) {
                            preparedStatement = connection.prepareStatement("UPDATE players SET name = ?, kills = ?, deaths = ?, xp = ?, level = ?, killstreak = ?, killstreak_top = ?, coins = ?, lastseen = ? WHERE uuid = ?");
                            preparedStatement.setString(1, name);
                            preparedStatement.setLong(2, kills);
                            preparedStatement.setLong(3, deaths);
                            preparedStatement.setLong(4, xp);
                            preparedStatement.setLong(5, level);
                            preparedStatement.setLong(6, killstreak);
                            preparedStatement.setLong(7, killstreak_top);
                            preparedStatement.setLong(8, coins);
                            preparedStatement.setTimestamp(9, timestamp);
                            preparedStatement.setString(10, uuid);
                            preparedStatement.executeUpdate();
                            if (debug_database) { plugin.textUtils.debug("[Database] Updating values for UUID: " + uuid); }
                        }
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    } finally {
                        if (resultSet != null)
                            try {
                                resultSet.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                        if (preparedStatement != null)
                            try {
                                preparedStatement.close();
                            } catch (SQLException exception) {
                                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                            }
                    }
                }
            };
            r.runTaskAsynchronously(plugin);
        }
    }

    public String[] getValues(String uuid) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players WHERE uuid= '" + uuid + "';");
            if (resultSet.next()) {
                if (debug_database) { plugin.textUtils.debug("[Database] Getting values for UUID: " + uuid); }
                return new String[]{ resultSet.getString("name"), String.valueOf(resultSet.getLong("kills")), String.valueOf(resultSet.getLong("deaths")), String.valueOf(resultSet.getLong("xp")), String.valueOf(resultSet.getLong("level")), String.valueOf(resultSet.getLong("killstreak")), String.valueOf(resultSet.getLong("killstreak_top")), String.valueOf(resultSet.getLong("coins")), String.valueOf(resultSet.getTimestamp("lastseen")) };
            }
        } catch (SQLException exception) {
            plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException exception) {
                    plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                }
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException exception) {
                    plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                }
        }
        return new String[] { "", String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), String.valueOf(new Timestamp(new Date().getTime())) };
    }

    private ArrayList<String> getUUIDList() {
        if (set()) {
            ArrayList<String> array = new ArrayList<>();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM players");
                while (resultSet.next()) {
                    array.add(resultSet.getString("uuid"));
                }
            } catch (SQLException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            } finally {
                if (resultSet != null)
                    try {
                        resultSet.close();
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    }
                if (statement != null)
                    try {
                        statement.close();
                    } catch (SQLException exception) {
                        plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
                    }
            }
            if (debug_database) { plugin.textUtils.debug("[Database] Getting list of UUID in the table"); }
            return array;
        }
        return null;
    }

    public void loadOnline() {
        if (plugin.getServer().getOnlinePlayers().size() > 0 && set()) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (!plugin.list().contains(player.getUniqueId().toString()))
                    plugin.load(player.getUniqueId().toString());
            }
        }
        if (debug_database) { plugin.textUtils.debug("[Database] Loading all online players into cache"); }
    }

    public void loadALL() {
        if (set()) {
            for (String list : getUUIDList()) {
                if (!plugin.list().contains(list))
                    plugin.load(list);
            }
        }
        if (debug_database) { plugin.textUtils.debug("[Database] Loading all players into cache"); }
    }
}
package me.MathiasMC.PvPLevels.data;

import me.MathiasMC.PvPLevels.PvPLevels;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    private final PvPLevels plugin;

    private Connection connection;

    public Database(final PvPLevels plugin) {
        this.plugin = plugin;
        (new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.createStatement().execute("SELECT 1");
                    }
                } catch (SQLException e) {
                    connection = get();
                }
            }
        }).runTaskTimerAsynchronously(plugin, 60 * 20, 60 * 20);
    }

    private Connection get() {
        try {
            if (plugin.config.get.getBoolean("mysql.use")) {
                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://" + plugin.config.get.getString("mysql.host") + ":" + plugin.config.get.getString("mysql.port") + "/" + plugin.config.get.getString("mysql.database"), plugin.config.get.getString("mysql.username"), plugin.config.get.getString("mysql.password"));
            } else {
                Class.forName("org.sqlite.JDBC");
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
        }
    }

    private boolean check() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = get();
            if (connection == null || connection.isClosed()) {
                return false;
            }
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255) PRIMARY KEY, `group` TINYTEXT(255), `kills` bigint(255), `deaths` bigint(255), `xp` bigint(255), `level` bigint(255), `killstreak` bigint(255), `killstreak_top` bigint(255), `multiplier` LONGTEXT(255), `lastseen` DATETIME);");
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

    public void insert(final String uuid) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM players WHERE uuid= '" + uuid + "';");
                        if (!resultSet.next()) {
                            preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, `group`, kills, deaths, xp, level, killstreak, killstreak_top, multiplier, lastseen) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                            preparedStatement.setString(1, uuid);
                            preparedStatement.setString(2, "default");
                            preparedStatement.setLong(3, 0L);
                            preparedStatement.setLong(4, 0L);
                            preparedStatement.setLong(5, 0L);
                            preparedStatement.setLong(6, plugin.config.get.getLong("start-level"));
                            preparedStatement.setLong(7, 0L);
                            preparedStatement.setLong(8, 0L);
                            preparedStatement.setString(9, "0.0 0");
                            preparedStatement.setTimestamp(10, new Timestamp(new Date().getTime()));
                            preparedStatement.executeUpdate();
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

    public void setValues(final String uuid, final String group, final Long kills, final Long deaths, final Long xp, final Long level, final Long killstreak, final Long killstreak_top, final String multiplier, final Timestamp timestamp) {
        if (set()) {
            BukkitRunnable r = new BukkitRunnable() {
                public void run() {
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
                    try {
                        resultSet = connection.createStatement().executeQuery("SELECT * FROM players WHERE uuid= '" + uuid + "';");
                        if (resultSet.next()) {
                            preparedStatement = connection.prepareStatement("UPDATE players SET `group` = ?, kills = ?, deaths = ?, xp = ?, level = ?, killstreak = ?, killstreak_top = ?, multiplier = ?, lastseen = ? WHERE uuid = ?");
                            preparedStatement.setString(1, group);
                            preparedStatement.setLong(2, kills);
                            preparedStatement.setLong(3, deaths);
                            preparedStatement.setLong(4, xp);
                            preparedStatement.setLong(5, level);
                            preparedStatement.setLong(6, killstreak);
                            preparedStatement.setLong(7, killstreak_top);
                            preparedStatement.setString(8, multiplier);
                            preparedStatement.setTimestamp(9, timestamp);
                            preparedStatement.setString(10, uuid);
                            preparedStatement.executeUpdate();
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
                return new String[]{ resultSet.getString("group"), String.valueOf(resultSet.getLong("kills")), String.valueOf(resultSet.getLong("deaths")), String.valueOf(resultSet.getLong("xp")), String.valueOf(resultSet.getLong("level")), String.valueOf(resultSet.getLong("killstreak")), String.valueOf(resultSet.getLong("killstreak_top")), resultSet.getString("multiplier"), String.valueOf(resultSet.getTimestamp("lastseen")) };
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
        return new String[] { "default", String.valueOf(0L), String.valueOf(0L), String.valueOf(0L), String.valueOf(plugin.config.get.getLong("start-level")), String.valueOf(0L), String.valueOf(0L), "0.0 0", String.valueOf(new Timestamp(new Date().getTime())) };
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
            return array;
        }
        return null;
    }

    public void loadALL() {
        if (set()) {
            for (String list : getUUIDList()) {
                if (!plugin.list().contains(list)) {
                    plugin.load(list);
                }
            }
        }
    }
}
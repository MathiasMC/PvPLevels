package me.MathiasMC.PvPLevels;

import me.MathiasMC.PvPLevels.commands.*;
import me.MathiasMC.PvPLevels.data.Database;
import me.MathiasMC.PvPLevels.data.PlayerConnect;
import me.MathiasMC.PvPLevels.data.Purge;
import me.MathiasMC.PvPLevels.listeners.*;
import me.MathiasMC.PvPLevels.managers.*;
import me.MathiasMC.PvPLevels.support.ActionBar_1_8;
import me.MathiasMC.PvPLevels.support.PlaceholderAPI;
import me.MathiasMC.PvPLevels.utils.FileUtils;
import me.MathiasMC.PvPLevels.utils.MetricsLite;
import me.MathiasMC.PvPLevels.utils.TextUtils;
import me.MathiasMC.PvPLevels.utils.UpdateUtils;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.sql.SQLException;
import java.util.*;

public class PvPLevels extends JavaPlugin {

    private static PvPLevels call;

    public final ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();

    public Database database;

    private TextUtils textUtils;

    private FileUtils fileUtils;

    private CalculateManager calculateManager;
    private PlaceholderManager placeholderManager;
    private KillSessionManager killSessionManager;
    private StatsManager statsManager;
    private XPManager xpManager;

    public ActionBar_1_8 actionBar_1_8;

    private final Map<String, PlayerConnect> playerConnect = new HashMap<>();

    public final HashSet<String> spawners = new HashSet<>();

    public final ArrayList<Location> blocksList = new ArrayList<>();

    public final Map<String, String> lastDamagers = new HashMap<>();

    public final HashSet<OfflinePlayer> multipliers = new HashSet<>();

    public String generateGroup = null;

    public long generateAmount = 0;

    public boolean isGenerate = false;

    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private final ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");

    private long startLevel = 0;

    private boolean debug = false;

    public void onEnable() {
        call = this;

        textUtils = new TextUtils(this);

        fileUtils = new FileUtils(this);

        startLevel = fileUtils.config.getLong("start-level");

        debug = fileUtils.config.getBoolean("debug");

        database = new Database(this);

        placeholderManager = new PlaceholderManager(this);
        killSessionManager = new KillSessionManager(this);
        statsManager = new StatsManager(this);
        xpManager = new XPManager(this);
        calculateManager = new CalculateManager(this);
        if (getServer().getVersion().contains("1.8")) {
            actionBar_1_8 = new ActionBar_1_8(this);
        }

        if (database.set()) {
            getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
            getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
            getServer().getPluginManager().registerEvents(new EntityDeath(this), this);
            getServer().getPluginManager().registerEvents(new CreatureSpawn(this), this);
            if (fileUtils.config.getBoolean("blocks")) {
                getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
                getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
            }
            getServer().getPluginManager().registerEvents(new EntityDamageByEntity(this), this);
            getCommand("pvplevels").setExecutor(new PvPLevels_Command(this));
            getCommand("pvplevels").setTabCompleter(new PvPLevels_TabComplete(this));
            new MetricsLite(this, 1174);
            if (fileUtils.config.getBoolean("update-check")) {
                new UpdateUtils(this, 20807).getVersion(version -> {
                    if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                        textUtils.info("You are using the latest version of PvPLevels (" + getDescription().getVersion() + ")");
                    } else {
                        textUtils.warning("Version: " + version + " has been released! you are currently using version: " + getDescription().getVersion());
                    }
                });
            }
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new PlaceholderAPI(this).register();
                textUtils.info("PlaceholderAPI (found)");
            }

            if (fileUtils.config.contains("mysql.purge")) {
                new Purge(this);
            }

            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                Iterator<OfflinePlayer> iterator = multipliers.iterator();
                while (iterator.hasNext()) {
                    final OfflinePlayer offlinePlayer = iterator.next();
                    if (offlinePlayer.isOnline()) {
                        final PlayerConnect playerConnect = getPlayerConnect(offlinePlayer.getUniqueId().toString());
                        long left = playerConnect.getMultiplierTimeLeft();
                        if (left > 0) {
                            left--;
                            playerConnect.setMultiplierTimeLeft(left);
                            return;
                        }
                        xpManager.sendCommands((Player) offlinePlayer, fileUtils.language.getStringList("multiplier.lost"));
                        playerConnect.setMultiplier(0D);
                        playerConnect.setMultiplierTime(0);
                        playerConnect.setMultiplierTimeLeft(0);
                        playerConnect.save();
                    }
                    iterator.remove();
                }
            }, 20, 20);

        } else {
            textUtils.error("Disabling plugin cannot connect to database");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onDisable() {
        try {
            database.close();
        } catch (SQLException exception) {
            textUtils.exception(exception.getStackTrace(), exception.getMessage());
        }
        call = null;
    }

    public static PvPLevels getInstance() {
        return call;
    }

    public FileUtils getFileUtils() {
        return this.fileUtils;
    }

    public TextUtils getTextUtils() {
        return this.textUtils;
    }

    public XPManager getXPManager() {
        return this.xpManager;
    }

    public KillSessionManager getKillSessionManager() {
        return this.killSessionManager;
    }

    public StatsManager getStatsManager() {
        return this.statsManager;
    }

    public PlaceholderManager getPlaceholderManager() {
        return this.placeholderManager;
    }

    public CalculateManager getCalculateManager() {
        return this.calculateManager;
    }

    public long getStartLevel() {
        return this.startLevel;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void unloadPlayerConnect(final String uuid) {
        playerConnect.remove(uuid);
    }

    public void updatePlayerConnect(final String uuid) {
        unloadPlayerConnect(uuid);
        getPlayerConnect(uuid);
    }

    public ScriptEngine getScriptEngine() {
        return this.scriptEngine;
    }

    public PlayerConnect getPlayerConnect(final String uuid) {
        if (playerConnect.containsKey(uuid)) {
            return playerConnect.get(uuid);
        }
        final PlayerConnect playerConnect = new PlayerConnect(uuid);
        this.playerConnect.put(uuid, playerConnect);
        return playerConnect;
    }

    public Set<String> listPlayerConnect() {
        return playerConnect.keySet();
    }
}